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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.CUSTOMER_BASKET;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class GoToCustomerBasketCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToCustomerBasketCommand.class);
    OrderService orderService = OrderServiceImpl.getInstance();
    DishListItemService dishListItemService = DishListItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<Order> order = Optional.empty();
        List<DishListItem> dishListItems = new ArrayList<>();
        User user = (User) session.getAttribute(USER);
        try {
            System.out.println(dishListItems);
            order = orderService.getCustomerBasket(user.getId());
            dishListItems = dishListItemService.findAllByOrderId(order.get().getId());
            System.out.println(dishListItems);
        } catch (ServiceException e) {
            logger.info("Impossible to find customer basket", e);
        }
        session.setAttribute(ORDER, order.get());
        session.setAttribute(ORDER_DISH_LIST_ITEMS, dishListItems);
        return new Router(CUSTOMER_BASKET, REDIRECT);
    }
}
