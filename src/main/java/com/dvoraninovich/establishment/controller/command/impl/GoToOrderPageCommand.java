package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.SessionAttribute;
import com.dvoraninovich.establishment.controller.command.impl.customer.GoToCustomerBasketCommand;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.*;
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

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.Role.CUSTOMER;

public class GoToOrderPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToCustomerBasketCommand.class);
    OrderService orderService = OrderServiceImpl.getInstance();
    DishService dishService = DishServiceImpl.getInstance();
    UserService userService = UserServiceImpl.getInstance();
    DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<Order> optionalOrder = Optional.empty();
        Optional<User> optionalOrderUser = Optional.empty();
        List<DishListItem> dishListItems = new ArrayList<>();
        HashMap<Long, Dish> dishesHashMap = new HashMap<>();

        User user = (User) session.getAttribute(USER);
        String orderIdLine = request.getParameter(ID_ORDER);

        try {
            Long orderId = Long.valueOf(orderIdLine);
            optionalOrder = orderService.findById(orderId);
            if (optionalOrder.isPresent()){
                Order order = optionalOrder.get();
                if (user.getRole().equals(CUSTOMER) && user.getId() != order.getUserId()) {
                    return new Router(CUSTOMER_ORDERS + "?id=" + user.getId(), REDIRECT);
                }
                dishListItems = dishListItemService.findAllByOrderId(order.getId());
                List<Dish> dishes = dishService.findOrderDishes(order.getId());
                for (Dish dish: dishes){
                    dishesHashMap.put(dish.getId(), dish);
                }
                optionalOrderUser = userService.findById(order.getUserId());
            }
        } catch (ServiceException e) {
            logger.info("Impossible to find order with id: " + orderIdLine, e);
        }
        session.setAttribute(ORDER, optionalOrder.get());
        session.setAttribute(ORDER_DISH_LIST_ITEMS, dishListItems);
        session.setAttribute(ORDER_DISHES_MAP, dishesHashMap);
        session.setAttribute(ORDER_USER, optionalOrderUser.get());
        return new Router(ORDER_PAGE + "?id=" + orderIdLine, REDIRECT);
    }
}
