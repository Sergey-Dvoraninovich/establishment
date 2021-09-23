package com.dvoraninovich.establishment.controller.command.impl.admin.order;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.ADMIN_PAGE;
import static com.dvoraninovich.establishment.controller.command.PagePath.ORDERS_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class GoToOrdersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToOrdersPageCommand.class);
    private static final Long ORDERS_PAGE_ITEMS_AMOUNT = Long.valueOf(10);
    OrderService orderService = OrderServiceImpl.getInstance();


    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        List<Order> orders = new ArrayList<>();
        HashMap<Long, User> ordersUsersMap = new HashMap<>();
        String minPosLine = request.getParameter(NEXT_MIN_POS);
        String maxPosLine = request.getParameter(NEXT_MAX_POS);
        String newTotalAmountLine = request.getParameter(NEW_TOTAL_AMOUNT);
        Long totalAmount;

        //TODO set parameters from session

        try {
            Long minPos = Long.valueOf(minPosLine);
            Long maxPos = Long.valueOf(maxPosLine);

            if (newTotalAmountLine != null){
                totalAmount = orderService.countOrders();
                session.setAttribute(TOTAL_AMOUNT, totalAmount);
                session.setAttribute(PAGE_ITEMS_AMOUNT, ORDERS_PAGE_ITEMS_AMOUNT);
            }
            else {
                totalAmount = (Long) session.getAttribute(TOTAL_AMOUNT);
            }

            maxPos = maxPos > totalAmount ? totalAmount : maxPos;
            HashMap<Order, User> fullInfoHashMap = new HashMap<>();
            fullInfoHashMap = orderService.findOrdersWithUsersLimit(minPos, maxPos);
            orders.addAll(fullInfoHashMap.keySet());

            session.setAttribute(ORDERS, orders);
            session.setAttribute(ORDERS_USERS_MAP, fullInfoHashMap);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);
//            session.setAttribute(ORDERS_FILTER_ORDER_STATES, Arrays.asList(orderStatesLines));
//            session.setAttribute(ORDERS_FILTER_MIN_PRICE, minPriceLine.equals("") ? null : new BigDecimal(minPriceLine));
//            session.setAttribute(ORDERS_FILTER_MAX_PRICE, maxPriceLine.equals("") ? null : new BigDecimal(maxPriceLine));
//            session.setAttribute(ORDERS_FILTER_PAYMENT_TYPES, Arrays.asList(paymentTypesLines));
            router = new Router(ORDERS_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.info("Impossible to find user orders", e);
            router = new Router(ADMIN_PAGE, REDIRECT);
        }
        return router;
    }
}
