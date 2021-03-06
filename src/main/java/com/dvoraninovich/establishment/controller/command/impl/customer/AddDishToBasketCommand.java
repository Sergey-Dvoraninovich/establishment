package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.*;
import com.dvoraninovich.establishment.model.service.DishListItemService;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.impl.DishListItemServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.PagePath.ERROR_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class AddDishToBasketCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddDishToBasketCommand.class);
    private DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();
    private DishService dishService = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        Optional<DishListItem> optionalDishListItem;
        DishListItem dishListItem;
        Optional<Order> optionalBasket;

        User user = (User) session.getAttribute(USER);
        Long dishesAmount = (Long) session.getAttribute(DISHES_IN_BASKET);
        String id = request.getParameter(ID);
        long dishId = Long.parseLong(id);

        try {
            optionalBasket = orderService.getCustomerBasket(user.getId());
            if (optionalBasket.isPresent()) {
                Order basket = optionalBasket.get();
                optionalDishListItem = dishListItemService.findByOrderAndDishId(basket.getId(), dishId);
                if (optionalDishListItem.isPresent()) {
                    dishListItem = optionalDishListItem.get();
                    dishListItem.setDishAmount(dishListItem.getDishAmount() + 1);
                    dishListItemService.update(dishListItem);
                } else {
                    dishListItem = DishListItem.builder()
                            .setOrderId(basket.getId())
                            .setDishId(dishId)
                            .setDishAmount(1)
                            .build();
                    dishListItemService.insert(dishListItem);
                }
                orderService.update(basket);
                orderService.updateOrderFinalPrice(basket.getId());
                dishesAmount = orderService.countDishesAmount(basket.getId());
            }
            session.setAttribute(DISHES_IN_BASKET, dishesAmount);
            router = new Router(DISHES_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("impossible to add dish to basket", e);
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
