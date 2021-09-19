package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.RequestParameter;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.DishListItem;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.DishListItemService;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.impl.DishListItemServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.CUSTOMER_BASKET;
import static com.dvoraninovich.establishment.controller.command.PagePath.ERROR_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.BONUSES_IN_PAYMENT;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.EXCEPTION;

public class RecalculatePriceCommand implements Command {
    private DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();
    private DishService dishService = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        Optional<Order> optionalBasket = Optional.empty();

        User user = (User) session.getAttribute(USER);
        session.setAttribute(NOT_ENOUGH_BONUSES, false);
        session.setAttribute(TOO_MANY_BONUSES, false);
        session.setAttribute(YOU_SHOULD_BUY_SOMETHING, false);
        String newBonusesAmountLine = request.getParameter(BONUSES_IN_PAYMENT);
        BigDecimal newBonusesAmount = new BigDecimal(newBonusesAmountLine);
        Order basket;

        if (newBonusesAmount.compareTo(user.getBonusesAmount()) > 0){
            session.setAttribute(NOT_ENOUGH_BONUSES, true);
            return new Router(CUSTOMER_BASKET, REDIRECT);
        }

        try {
            optionalBasket = orderService.getCustomerBasket(user.getId());
            if (optionalBasket.isPresent()) {
                basket = optionalBasket.get();
                BigDecimal currentBonusesAmount = basket.getBonusesInPayment().divide(new BigDecimal(100));
                newBonusesAmount = newBonusesAmount.divide(new BigDecimal(100));
                BigDecimal finalPrice = basket.getFinalPrice();
                BigDecimal resultPrice = finalPrice.add(currentBonusesAmount).subtract(newBonusesAmount);
                System.out.println(currentBonusesAmount);
                System.out.println(newBonusesAmount);
                System.out.println(resultPrice);
                if (resultPrice.compareTo(new BigDecimal(0)) >= 0) {
                    basket.setBonusesInPayment(newBonusesAmount.multiply(new BigDecimal(100)));
                    basket.setFinalPrice(resultPrice);
                    orderService.update(basket);
                    session.setAttribute(ORDER, basket);
                }
                else {
                    session.setAttribute(TOO_MANY_BONUSES, true);
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
