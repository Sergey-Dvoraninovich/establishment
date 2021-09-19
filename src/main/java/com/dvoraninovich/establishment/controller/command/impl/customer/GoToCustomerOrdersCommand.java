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
import java.util.ArrayList;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.CUSTOMER_ORDERS;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class GoToCustomerOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToCustomerOrdersCommand.class);
    private static final Long ORDERS_PAGE_ITEMS_AMOUNT = Long.valueOf(5);
    OrderService orderService = OrderServiceImpl.getInstance();


    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Order> orders = new ArrayList<>();
        User user = (User) session.getAttribute(USER);
        String minPosLine = request.getParameter(NEXT_MIN_POS);
        String maxPosLine = request.getParameter(NEXT_MAX_POS);
        String newTotalAmountLine = request.getParameter(NEW_TOTAL_AMOUNT);
        Long totalAmount;

        try {
            Long minPos = Long.valueOf(minPosLine);
            Long maxPos = Long.valueOf(maxPosLine);

            if (newTotalAmountLine != null){
                totalAmount = orderService.countUserOrders(user.getId());
                session.setAttribute(TOTAL_AMOUNT, totalAmount);
                session.setAttribute(PAGE_ITEMS_AMOUNT, ORDERS_PAGE_ITEMS_AMOUNT);
            }
            else {
                totalAmount = (Long) session.getAttribute(TOTAL_AMOUNT);
            }

            maxPos = maxPos > totalAmount ? totalAmount : maxPos;
            orders = orderService.findUserOrders(user.getId(), minPos, maxPos);

            session.setAttribute(ORDERS, orders);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);
        } catch (ServiceException e) {
            logger.info("Impossible to find user orders", e);
        }
        return new Router(CUSTOMER_ORDERS, REDIRECT);
    }
}
