package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.DishListItem;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.PaymentType;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.DishListItemService;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.DishListItemServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.PAYMENT_TYPE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.OrderState.CREATED;
import static com.dvoraninovich.establishment.model.entity.PaymentType.CARD;

public class BuyBasketCommand implements Command {
    private DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        Optional<DishListItem> optionalDishListItem = Optional.empty();
        DishListItem dishListItem;
        Optional<Order> optionalBasket = Optional.empty();
        Order basket;

        session.setAttribute(NOT_ENOUGH_BONUSES, false);
        session.setAttribute(TOO_MANY_BONUSES, false);
        session.setAttribute(YOU_SHOULD_CHOOSE_SOMETHING, false);
        User user = (User) session.getAttribute(USER);
        String paymentTypeLine = request.getParameter(PAYMENT_TYPE);
        PaymentType paymentType = PaymentType.valueOf(paymentTypeLine);

        try {
            optionalBasket = orderService.getCustomerBasket(user.getId());
            if (optionalBasket.isPresent()) {
                basket = optionalBasket.get();

                List<DishListItem> dishListItems = dishListItemService.findAllByOrderId(basket.getId());
                if (dishListItems.isEmpty()) {
                    session.setAttribute(YOU_SHOULD_CHOOSE_SOMETHING, true);
                    return new Router(CUSTOMER_BASKET, REDIRECT);
                }

                basket.setPaymentType(paymentType);
                if (basket.getPaymentType().equals(CARD)){
                    basket.setCardNumber(user.getCardNumber());
                }
                basket.setOrderTime(LocalDateTime.now());
                basket.setOrderState(CREATED);
                orderService.update(basket);

                BigDecimal userBonuses = user.getBonusesAmount();
                user.setBonusesAmount(userBonuses.subtract(basket.getBonusesInPayment()));
                userService.updateUser(user);
                session.setAttribute(USER, user);

                Optional<Order> newBasket = orderService.getCustomerBasket(user.getId());
                if (newBasket.isPresent()){
                    session.setAttribute(ORDER, newBasket.get());
                    session.setAttribute(ORDER_DISH_LIST_ITEMS, new ArrayList<DishListItem>());
                    session.setAttribute(DISHES_IN_BASKET, Long.valueOf(0));
                }
            }
            router = new Router(CUSTOMER_BASKET, REDIRECT);
        } catch (ServiceException e) {
            e.printStackTrace();
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
