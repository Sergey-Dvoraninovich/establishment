package com.dvoraninovich.establishment.controller.command.impl.admin.order;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.DishListItem;
import com.dvoraninovich.establishment.model.entity.Order;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class AddDishToOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddDishToOrderCommand.class);
    private DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();
    private DishService dishService = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<DishListItem> optionalDishListItem;
        HashMap<Long, Dish> dishesHashMap = new HashMap<>();
        List<DishListItem> dishListItems;
        List<Dish> availableDishes;
        DishListItem dishListItem;
        Optional<Order> optionalOrder;

        String orderIdLine = request.getParameter(ID);
        String dishIdLine = request.getParameter(AVAILABLE_DISHES);

        if (dishIdLine == null) {
            return new Router(ORDER_PAGE + "?id=" + orderIdLine, REDIRECT);
        }

        try {
            long orderId = Long.parseLong(orderIdLine);
            long dishId = Long.parseLong(dishIdLine);
            optionalOrder = orderService.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                optionalDishListItem = dishListItemService.findByOrderAndDishId(order.getId(), dishId);
                if (optionalDishListItem.isPresent()) {
                    dishListItem = optionalDishListItem.get();
                    dishListItem.setDishAmount(dishListItem.getDishAmount() + 1);
                    dishListItemService.update(dishListItem);
                } else {
                    dishListItem = DishListItem.builder()
                            .setOrderId(order.getId())
                            .setDishId(dishId)
                            .setDishAmount(1)
                            .build();
                    dishListItemService.insert(dishListItem);
                }
                BigDecimal finalPrice = orderService.countOrderFinalPrice(order.getId());
                order.setFinalPrice(finalPrice);
                orderService.update(order);

                dishListItems = dishListItemService.findAllByOrderId(order.getId());
                List<Dish> dishes = dishService.findOrderDishes(order.getId());
                for (Dish dish: dishes){
                    dishesHashMap.put(dish.getId(), dish);
                }
                availableDishes = dishService.findAllAvailable();
                for (Dish dish: dishes) {
                    availableDishes.remove(dish);
                }

                session.setAttribute(ORDER, optionalOrder.get());
                session.setAttribute(ORDER_DISH_LIST_ITEMS, dishListItems);
                session.setAttribute(ORDER_DISHES_MAP, dishesHashMap);
                session.setAttribute(AVAILABLE_DISHES, availableDishes);
            }
        } catch (ServiceException e) {
            logger.error("Impossible to add dish with id: "
                    + dishIdLine + " to order with id: " + orderIdLine, e);
            session.setAttribute(ADD_DISH_TO_ORDER_ERROR, true);
        }
        return new Router(ORDER_PAGE + "?id=" + orderIdLine, REDIRECT);
    }
}
