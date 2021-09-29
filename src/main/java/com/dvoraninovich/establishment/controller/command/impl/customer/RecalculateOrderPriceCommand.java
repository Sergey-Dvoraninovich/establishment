package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.BONUSES_IN_PAYMENT;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class RecalculateOrderPriceCommand implements Command {
    private OrderService orderService = OrderServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        Optional<Order> optionalOrder = Optional.empty();
        Order order;

        User user = (User) session.getAttribute(USER);
        session.setAttribute(NOT_ENOUGH_BONUSES, false);
        session.setAttribute(TOO_MANY_BONUSES, false);
        session.setAttribute(YOU_SHOULD_CHOOSE_SOMETHING, false);
        String newBonusesAmountLine = request.getParameter(BONUSES_IN_PAYMENT);
        String orderIdLine = request.getParameter(ID_ORDER);
        BigDecimal newBonusesAmount = new BigDecimal(newBonusesAmountLine);

        if (newBonusesAmount.compareTo(user.getBonusesAmount()) > 0){
            session.setAttribute(NOT_ENOUGH_BONUSES, true);
            return new Router(CUSTOMER_BASKET_PAGE, REDIRECT);
        }

        try {
            Long orderId = Long.valueOf(orderIdLine);
            optionalOrder = orderService.findById(orderId);
            user = userService.findById(user.getId()).get();
            session.setAttribute(USER, user);
            if (optionalOrder.isPresent()) {
                order = optionalOrder.get();
                BigDecimal resultPrice = orderService.countNewOrderPrice(order, newBonusesAmount);
                if (resultPrice.compareTo(new BigDecimal(0)) >= 0) {
                    order.setBonusesInPayment(newBonusesAmount);
                    order.setFinalPrice(resultPrice);
                    orderService.update(order);
                    session.setAttribute(ORDER, order);
                }
                else {
                    session.setAttribute(TOO_MANY_BONUSES, true);
                }
            }
            switch (user.getRole()) {
                case ADMIN: {
                    router = new Router(ORDER + "?id_order=" + orderIdLine, REDIRECT);
                    break;
                }
                case CUSTOMER: {
                    router = new Router(CUSTOMER_BASKET_PAGE, REDIRECT);
                    break;
                }
                default: {
                    router = new Router(INDEX_PAGE, REDIRECT);
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
