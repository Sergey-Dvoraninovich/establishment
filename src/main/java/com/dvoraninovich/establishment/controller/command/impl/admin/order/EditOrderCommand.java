package com.dvoraninovich.establishment.controller.command.impl.admin.order;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.OrderValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.OrderState;
import com.dvoraninovich.establishment.model.entity.PaymentType;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.OrderState.*;

public class EditOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditOrderCommand.class);
    private OrderValidator orderValidator = OrderValidator.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.setAttribute(NOT_ENOUGH_BONUSES, false);
        session.setAttribute(TOO_MANY_BONUSES, false);
        session.setAttribute(YOU_SHOULD_CHOOSE_SOMETHING, false);
        session.setAttribute(INVALID_ORDER_STATES, false);
        session.setAttribute(INVALID_PAYMENT_TYPES, false);
        session.setAttribute(EDIT_ORDER_ERROR, false);

        String orderIdLine = request.getParameter(ID);
        String orderStateLine = request.getParameter(ORDER_STATE);
        String paymentTypeLine = request.getParameter(PAYMENT_TYPE);
        String newBonusesAmountLine = request.getParameter(BONUSES_IN_PAYMENT);
        BigDecimal newBonusesAmount = new BigDecimal(newBonusesAmountLine);

        if (orderStateLine != null) {
            if (!orderValidator.validateOrderState(orderStateLine)) {
                session.setAttribute(INVALID_ORDER_STATES, true);
                return new Router(ORDER_PAGE + "?id = " + orderIdLine, REDIRECT);
            }
        }

        if (!orderValidator.validatePaymentType(paymentTypeLine)){
            session.setAttribute(INVALID_PAYMENT_TYPES, true);
            return new Router(ORDER_PAGE + "?id = " + orderIdLine, REDIRECT);
        }

        try {
            long id = Long.parseLong(orderIdLine);
            Optional<Order> optionalOrder = orderService.findById(id);
            if (!optionalOrder.isPresent()){
                session.setAttribute(EDIT_ORDER_ERROR, true);
                return new Router(ORDER_PAGE + "?id = " + orderIdLine, REDIRECT);
            }

            Order order = optionalOrder.get();
            Optional<User> optionalUser = userService.findById(order.getUserId());
            if (!optionalUser.isPresent()){
                session.setAttribute(EDIT_ORDER_ERROR, true);
                return new Router(ORDER_PAGE + "?id = " + orderIdLine, REDIRECT);
            }

            User user = optionalUser.get();
            if (newBonusesAmount.compareTo(user.getBonusesAmount()) > 0){
                session.setAttribute(NOT_ENOUGH_BONUSES, true);
                return new Router(ORDER_PAGE + "?id = " + orderIdLine, REDIRECT);
            }

            BigDecimal bonusesDifference = newBonusesAmount.subtract(order.getBonusesInPayment());
            BigDecimal resultPrice = orderService.countNewOrderPrice(order, newBonusesAmount);
            if (resultPrice.compareTo(new BigDecimal(0)) < 0) {
                session.setAttribute(TOO_MANY_BONUSES, true);
                return new Router(ORDER_PAGE + "?id = " + orderIdLine, REDIRECT);
            }

            if (orderStateLine != null) {
                if (!order.getOrderState().equals(COMPLETED)
                    && !order.getOrderState().equals(EXPIRED)
                    && ( OrderState.valueOf(orderStateLine).equals(COMPLETED)
                         || OrderState.valueOf(orderStateLine).equals(EXPIRED))) {
                    order.setFinishTime(LocalDateTime.now());
                }
                order.setOrderState(OrderState.valueOf(orderStateLine));
            }
            order.setPaymentType(PaymentType.valueOf(paymentTypeLine));
            order.setBonusesInPayment(newBonusesAmount);
            order.setFinalPrice(resultPrice);
            orderService.update(order);

            user.setBonusesAmount(user.getBonusesAmount().subtract(bonusesDifference));
            userService.updateUser(user);

            session.setAttribute(ORDER_USER, user);
            session.setAttribute(ORDER, order);
        } catch (ServiceException e) {
            logger.info("Impossible to find user orders", e);
            session.setAttribute(EDIT_ORDER_ERROR, true);
        }
        return new Router(ORDER_PAGE + "?id = " + orderIdLine, REDIRECT);
    }
}
