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
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.DishListItemServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.CUSTOMER_BASKET_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class GoToCustomerBasketCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToCustomerBasketCommand.class);
    private OrderService orderService = OrderServiceImpl.getInstance();
    private DishService dishService = DishServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<Order> order = Optional.empty();
        Long dishesAmount = 0L;
        List<DishListItem> dishListItems = new ArrayList<>();
        HashMap<Long, Dish> dishesHashMap = new HashMap<>();
        User user = (User) session.getAttribute(USER);
        try {
            user = userService.findById(user.getId()).get();
            order = orderService.getCustomerBasket(user.getId());
            dishListItems = dishListItemService.findAllByOrderId(order.get().getId());
            List<Dish> dishes = dishService.findOrderDishes(order.get().getId());
            for (Dish dish: dishes){
                dishesHashMap.put(dish.getId(), dish);
            }
            dishesAmount = orderService.countDishesAmount(order.get().getId());
            session.setAttribute(USER, user);
        } catch (ServiceException e) {
            logger.info("Impossible to find customer basket", e);
        }
        session.setAttribute(ORDER, order.get());
        session.setAttribute(ORDER_DISH_LIST_ITEMS, dishListItems);
        session.setAttribute(ORDER_DISHES_MAP, dishesHashMap);
        session.setAttribute(DISHES_IN_BASKET, dishesAmount);
        return new Router(CUSTOMER_BASKET_PAGE, REDIRECT);
    }
}
