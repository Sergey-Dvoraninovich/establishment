package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.DishListItem;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.DishListItemService;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.impl.DishListItemServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.EXCEPTION;

public class IncrementOrderDishCommand implements Command {
    private static final Logger logger = LogManager.getLogger(IncrementOrderDishCommand.class);
    private DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        Optional<DishListItem> optionalDishListItem;
        DishListItem dishListItem;
        Optional<Order> optionalOrder;
        Order order;

        User user = (User) session.getAttribute(USER);
        Long dishesAmount = (Long) session.getAttribute(DISHES_IN_BASKET);
        List<DishListItem> dishListItems = (List<DishListItem>) session.getAttribute(ORDER_DISH_LIST_ITEMS);
        String dishListItemIdLine = request.getParameter(ID_DISH_LIST_ITEM);
        String orderIdLine = request.getParameter(ID_ORDER);

        try {
            long dishListItemId = Long.parseLong(dishListItemIdLine);
            long orderId = Long.parseLong(orderIdLine);
            optionalOrder = orderService.findById(orderId);
            if (optionalOrder.isPresent()) {
                order = optionalOrder.get();
                optionalDishListItem = dishListItemService.findById(dishListItemId);
                if (optionalDishListItem.isPresent()) {
                    dishListItem = optionalDishListItem.get();
                    dishListItems.remove(dishListItem);
                    dishListItem.setDishAmount(dishListItem.getDishAmount() + 1);
                    dishListItemService.update(dishListItem);
                    dishListItems.add(dishListItem);
                    session.setAttribute(ORDER_DISH_LIST_ITEMS, dishListItems);
                }
                BigDecimal finalPrice = orderService.countOrderFinalPrice(order.getId());
                order.setFinalPrice(finalPrice);
                orderService.update(order);
                dishesAmount = orderService.countDishesAmount(order.getId());
                session.setAttribute(ORDER, order);
            }
            switch (user.getRole()) {
                case ADMIN: {
                    router = new Router(ORDER_PAGE + "?id_order=" + orderIdLine, REDIRECT);
                    break;
                }
                case CUSTOMER: {
                    session.setAttribute(DISHES_IN_BASKET, dishesAmount);
                    router = new Router(CUSTOMER_BASKET_PAGE, REDIRECT);
                    break;
                }
                default: {
                    router = new Router(INDEX_PAGE, REDIRECT);
                }
            }
        } catch (ServiceException e) {
            logger.error("Impossible to increment dish list item with id: "
                    + dishListItemIdLine + " to order with id: " + orderIdLine, e);
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
