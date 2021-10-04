package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.Role.ADMIN;

public class GoToCustomerOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToCustomerOrdersCommand.class);
    private static final Long ORDERS_PAGE_ITEMS_AMOUNT = Long.valueOf(10);
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        List<Order> orders = new ArrayList<>();
        String userIdLine = request.getParameter(USER_ID);
        String minPosLine = request.getParameter(NEXT_MIN_POS);
        String maxPosLine = request.getParameter(NEXT_MAX_POS);
        Long totalAmount = (Long) session.getAttribute(TOTAL_AMOUNT);

        if (!user.getRole().equals(ADMIN)) {
            userIdLine = String.valueOf(user.getId());
        }

        Long minPos;
        Long maxPos;
        if (minPosLine == null || maxPosLine == null) {
            minPos = 1L;
            maxPos = ORDERS_PAGE_ITEMS_AMOUNT;
        }
        else {
            minPos = Long.valueOf(minPosLine);
            maxPos = Long.valueOf(maxPosLine);
        }

        List<String> orderStatesList = (List<String>) session.getAttribute(ORDERS_FILTER_ORDER_STATES);
        List<String> paymentTypesList = (List<String>) session.getAttribute(ORDERS_FILTER_PAYMENT_TYPES);
        BigDecimal minPrice = (BigDecimal) session.getAttribute(ORDERS_FILTER_MIN_PRICE);
        BigDecimal maxPrice = (BigDecimal) session.getAttribute(ORDERS_FILTER_MAX_PRICE);

        try {
            if (minPos.equals(1L) || totalAmount == null){
                totalAmount = orderService.countFilteredOrders(userIdLine,
                        minPrice, maxPrice, orderStatesList, paymentTypesList);
                session.setAttribute(TOTAL_AMOUNT, totalAmount);
                session.setAttribute(PAGE_ITEMS_AMOUNT, ORDERS_PAGE_ITEMS_AMOUNT);
            }
            maxPos = maxPos > totalAmount ? totalAmount : maxPos;

            HashMap<Order, User> fullInfoHashMap;
            fullInfoHashMap = orderService.findFilteredOrdersWithUsers(userIdLine, minPrice, maxPrice, orderStatesList, paymentTypesList, minPos, maxPos);
            orders = new ArrayList<>(fullInfoHashMap.keySet());
            orders.sort((o1, o2) -> o2.getOrderTime().compareTo(o1.getOrderTime()));

            session.setAttribute(ORDERS, orders);
            session.setAttribute(USER_PROFILE_ID, Long.valueOf(userIdLine));
            session.setAttribute(ORDERS_USERS_MAP, fullInfoHashMap);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);

            router = new Router(CUSTOMER_ORDERS_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.info("Impossible to find user orders", e);
            router = new Router(ADMIN_PAGE, REDIRECT);
        }
        return router;
    }
}
