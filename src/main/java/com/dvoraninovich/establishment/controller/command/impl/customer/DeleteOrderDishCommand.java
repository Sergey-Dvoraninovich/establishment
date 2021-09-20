package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.DishListItem;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.Role;
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

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.EXCEPTION;

public class DeleteOrderDishCommand implements Command {
    private DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        Optional<DishListItem> optionalDishListItem = Optional.empty();
        Optional<Order> optionalOrder = Optional.empty();
        Order order;

        User user = (User) session.getAttribute(USER);
        Long dishesAmount = (Long) session.getAttribute(DISHES_IN_BASKET);
        List<DishListItem> dishListItems = (List<DishListItem>) session.getAttribute(ORDER_DISH_LIST_ITEMS);
        String dishListItemIdLine = request.getParameter(ID_DISH_LIST_ITEM);
        String orderIdLine = request.getParameter(ID_ORDER);

        try {
            Long dishListItemId = Long.parseLong(dishListItemIdLine);
            Long orderId = Long.parseLong(orderIdLine);
            optionalOrder = orderService.findById(orderId);
            if (optionalOrder.isPresent()) {
                order = optionalOrder.get();
                optionalDishListItem = dishListItemService.findById(dishListItemId);
                if (optionalDishListItem.isPresent()) {
                    dishListItemService.delete(dishListItemId);
                    dishListItems.remove(optionalDishListItem.get());
                    session.setAttribute(ORDER_DISH_LIST_ITEMS, dishListItems);
                }
                BigDecimal finalPrice = orderService.countOrderFinalPrice(order.getId()) ;
                order.setFinalPrice(finalPrice);
                orderService.update(order);
                dishesAmount = orderService.countDishesAmount(order.getId());
                session.setAttribute(ORDER, order);
            }
            switch (user.getRole()) {
                case ADMIN: {
                    router = new Router(ORDER + "?id_order=" + orderIdLine, REDIRECT);
                    break;
                }
                case CUSTOMER: {
                    session.setAttribute(DISHES_IN_BASKET, dishesAmount);
                    router = new Router(CUSTOMER_BASKET, REDIRECT);
                    break;
                }
                default: {
                    router = new Router(INDEX, REDIRECT);
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
