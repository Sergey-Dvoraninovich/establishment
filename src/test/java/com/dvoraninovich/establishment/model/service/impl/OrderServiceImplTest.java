package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.OrderDao;
import com.dvoraninovich.establishment.model.dao.impl.OrderDaoImpl;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.service.OrderService;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.entity.OrderState.IN_CREATION;
import static com.dvoraninovich.establishment.model.entity.PaymentType.CASH;

public class OrderServiceImplTest {
    private OrderService service = OrderServiceImpl.getInstance();
    private Order defaultOrder;

    @BeforeClass
    public void init() {
        LocalDateTime defaultOrderTime = LocalDateTime.of(1971, 1, 1, 0, 0, 1);
        LocalDateTime defaultFinishTime = LocalDateTime.of(2038, 1, 19, 3, 14, 7);
        defaultOrder = Order.builder()
                .setOrderState(IN_CREATION)
                .setOrderTime(defaultOrderTime)
                .setFinishTime(defaultFinishTime)
                .setPaymentType(CASH)
                .setBonusesInPayment(new BigDecimal("0"))
                .setFinalPrice(new BigDecimal("0.00"))
                .build();
    }
    
    @DataProvider(name = "getCustomerBasketNewTest")
    public static Object[][] validDishesData() {
        return new Object[][] {{1L, Optional.empty(), 6L}, {165L, Optional.empty(), 3454L}};
    }
    @Test(dataProvider = "getCustomerBasketNewTest")
    public void getCustomerBasketNewTest(long customerId, Optional<Order> storedBasket, long orderId) {
        OrderDao serviceDao = OrderDaoImpl.getInstance();
        serviceDao = Mockito.mock(OrderDao.class);
        try {
            Mockito.when(serviceDao.findOrderInCreation(customerId))
                    .thenReturn(storedBasket);
            defaultOrder.setUserId(customerId);
            Mockito.when(serviceDao.insertAndGetId(defaultOrder))
                    .thenReturn(orderId);
            Class serviceClass = OrderServiceImpl.class;
            Field dishDaoField = serviceClass.getDeclaredField("orderDao");
            dishDaoField.setAccessible(true);
            dishDaoField.set(service, serviceDao);
        } catch (NoSuchFieldException | IllegalAccessException | DaoException e) {
            Assert.fail("Impossible to setup mocks");
        }
        Optional<Order> order = Optional.empty();
        try {
            order = service.getCustomerBasket(customerId);
        } catch (ServiceException e) {
            Assert.fail("Service caused exception");
        }
        boolean result = false;
        if (order.isPresent()) {
            result = order.get().getId() == orderId;
        }
        Assert.assertTrue(result);
    }

    @DataProvider(name = "getCustomerBasketExistingData")
    public static Object[][] getCustomerBasketExistingData() {
        return new Object[][] {{1L, Optional.of(Order.builder().setId(6L).build())},
                {165L, Optional.of(Order.builder().setId(65466L).build())}};
    }
    @Test(dataProvider = "getCustomerBasketExistingData")
    public void getCustomerBasketExistingTest(long customerId, Optional<Order> storedBasket) {
        OrderDao serviceDao = OrderDaoImpl.getInstance();
        serviceDao = Mockito.mock(OrderDao.class);
        try {
            Mockito.when(serviceDao.findOrderInCreation(customerId))
                    .thenReturn(storedBasket);
            defaultOrder.setUserId(customerId);
            Mockito.when(serviceDao.insertAndGetId(storedBasket.get()))
                    .thenReturn(storedBasket.get().getId());
            Class serviceClass = OrderServiceImpl.class;
            Field dishDaoField = serviceClass.getDeclaredField("orderDao");
            dishDaoField.setAccessible(true);
            dishDaoField.set(service, serviceDao);
        } catch (NoSuchFieldException | IllegalAccessException | DaoException e) {
            Assert.fail("Impossible to setup mocks");
        }
        Optional<Order> order = Optional.empty();
        try {
            order = service.getCustomerBasket(customerId);
        } catch (ServiceException e) {
            Assert.fail("Service caused exception");
        }
        boolean result = false;
        if (order.isPresent()) {
            result = order.get().getId() == storedBasket.get().getId();
        }
        Assert.assertTrue(result);
    }

    @DataProvider(name = "countBonusesInPaymentData")
    public static Object[][] countBonusesInPaymentData() {
        return new Object[][] {{Order.builder().setFinalPrice(new BigDecimal("9.99")).setBonusesInPayment(new BigDecimal("0")).build(),
                    new BigDecimal("500"), new BigDecimal("4.99")},
                {Order.builder().setFinalPrice(new BigDecimal("24.99")).setBonusesInPayment(new BigDecimal("400")).build(),
                        new BigDecimal("1299"), new BigDecimal("16.00")},
                {Order.builder().setFinalPrice(new BigDecimal("24.99")).setBonusesInPayment(new BigDecimal("400")).build(),
                        new BigDecimal("0"), new BigDecimal("28.99")}};
    }
    @Test(dataProvider = "countBonusesInPaymentData")
    public void countBonusesInPaymentTest(Order order, BigDecimal newBonusesAmount, BigDecimal expectedPrice) {
        try {
            BigDecimal resultPrice = service.countNewOrderPrice(order, newBonusesAmount);
            Assert.assertEquals(resultPrice, expectedPrice);
        } catch (ServiceException e) {
            Assert.fail("Service caused exception");
        }

    }
}
