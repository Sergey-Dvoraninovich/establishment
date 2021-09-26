package com.dvoraninovich.establishment.controller.command.impl.admin.order;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.OrderValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.MAX_POS;

public class SetOrdersFilterParametersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetOrdersFilterParametersCommand.class);
    private static final Long ORDERS_PAGE_ITEMS_AMOUNT = Long.valueOf(10);
    OrderValidator validator = OrderValidator.getInstance();
    OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        session.setAttribute(INVALID_ORDER_STATES, false);
        session.setAttribute(INVALID_MIN_PRICE, false);
        session.setAttribute(INVALID_MAX_PRICE, false);
        session.setAttribute(INVALID_PAYMENT_TYPES, false);
        session.setAttribute(INVALID_FILTER_PARAMETERS, false);

        String userIdLine = request.getParameter(USER_ID);
        String[] orderStatesLines = request.getParameterValues(REQUEST_FILTER_ORDER_STATES);
        orderStatesLines = orderStatesLines == null ? new String[0] : orderStatesLines;
        String[] paymentTypesLines = request.getParameterValues(REQUEST_FILTER_PAYMENT_TYPES);
        paymentTypesLines = paymentTypesLines == null ? new String[0] : paymentTypesLines;
        String minPriceLine = request.getParameter(REQUEST_FILTER_MIN_PRICE);
        String maxPriceLine = request.getParameter(REQUEST_FILTER_MAX_PRICE);

        HashMap<String, Boolean> validationResult = new HashMap<>();
        validationResult = validator.validateFilterParameters(userIdLine, minPriceLine, maxPriceLine, orderStatesLines, paymentTypesLines);

        Set<String> validationMessages = validationResult.keySet();
        HashMap<String, Boolean> finalValidationResult = validationResult;
        validationMessages.forEach(message -> session.setAttribute(message, finalValidationResult.get(message)));

        Collection<Boolean> validationErrors = validationResult.values();
        if (validationErrors.contains(true)) {
            router = new Router(ORDERS_PAGE, REDIRECT);
            return router;
        }

        try {
            List<Order> orders = new ArrayList<>();
            Long minPos = Long.valueOf(1);
            Long maxPos = ORDERS_PAGE_ITEMS_AMOUNT;

            Long totalAmount = service.countFilteredOrders(userIdLine, minPriceLine, maxPriceLine, orderStatesLines, paymentTypesLines);
            maxPos = maxPos > totalAmount ? totalAmount : maxPos;
            session.setAttribute(TOTAL_AMOUNT, totalAmount);
            session.setAttribute(PAGE_ITEMS_AMOUNT, ORDERS_PAGE_ITEMS_AMOUNT);

            HashMap<Order, User> fullInfoHashMap = new HashMap<>();
            fullInfoHashMap = service.findFilteredOrdersWithUsers(userIdLine, minPriceLine, maxPriceLine, minPos, maxPos, orderStatesLines, paymentTypesLines);
            orders.addAll(fullInfoHashMap.keySet());

            session.setAttribute(ORDERS, orders);
            session.setAttribute(ORDERS_USERS_MAP, fullInfoHashMap);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);
            session.setAttribute(ORDERS_FILTER_ORDER_STATES, Arrays.asList(orderStatesLines));
            session.setAttribute(ORDERS_FILTER_MIN_PRICE, minPriceLine.equals("") ? null : new BigDecimal(minPriceLine));
            session.setAttribute(ORDERS_FILTER_MAX_PRICE, maxPriceLine.equals("") ? null : new BigDecimal(maxPriceLine));
            session.setAttribute(ORDERS_FILTER_PAYMENT_TYPES, Arrays.asList(paymentTypesLines));
            router = new Router(ORDERS_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.info("Impossible to find user orders", e);
            router = new Router(ADMIN_PAGE, REDIRECT);
        }
        return router;
    }
}
