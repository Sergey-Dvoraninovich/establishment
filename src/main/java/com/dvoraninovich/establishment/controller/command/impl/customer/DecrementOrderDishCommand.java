package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
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

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class DecrementOrderDishCommand implements Command {
    private DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();
    private DishService dishService = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        Optional<DishListItem> optionalDishListItem = Optional.empty();
        DishListItem dishListItem;
        Optional<Order> optionalBasket = Optional.empty();

        User user = (User) session.getAttribute(USER);
        Long dishesAmount = (Long) session.getAttribute(DISHES_IN_BASKET);
        List<DishListItem> dishListItems = (List<DishListItem>) session.getAttribute(ORDER_DISH_LIST_ITEMS);
        String id = request.getParameter(ID_DISH_LIST_ITEM);
        Long dishListItemId = Long.parseLong(id);
        Order basket;

        try {
            optionalBasket = orderService.getCustomerBasket(user.getId());
            if (optionalBasket.isPresent()) {
                basket = optionalBasket.get();
                optionalDishListItem = dishListItemService.findById(dishListItemId);
                if (optionalDishListItem.isPresent()) {
                    dishListItem = optionalDishListItem.get();
                    dishListItems.remove(dishListItem);
                    Integer currentDishAmount = dishListItem.getDishAmount();
                    if (currentDishAmount > 1) {
                        dishListItem.setDishAmount(dishListItem.getDishAmount() - 1);
                        dishListItemService.update(dishListItem);
                        dishListItems.add(dishListItem);
                    }
                    else {
                        dishListItemService.delete(dishListItem.getId());
                    }
                }
                BigDecimal finalPrice = orderService.countOrderFinalPrice(basket.getId());
                basket.setFinalPrice(finalPrice);
                orderService.update(basket);
                dishesAmount = orderService.countDishesAmount(basket.getId());
                session.setAttribute(ORDER, basket);
            }
            session.setAttribute(ORDER_DISH_LIST_ITEMS, dishListItems);
            session.setAttribute(DISHES_IN_BASKET, dishesAmount);
            router = new Router(CUSTOMER_BASKET, REDIRECT);
        } catch (ServiceException e) {
            e.printStackTrace();
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
