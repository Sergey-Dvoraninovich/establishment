package com.dvoraninovich.establishment.controller.command.impl.admin.user;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.RequestParameter;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.impl.customer.GoToCustomerBasketCommand;
import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.USER_ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.AVAILABLE_DISHES;

public class CreateCustomerOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToCustomerBasketCommand.class);
    private OrderService orderService = OrderServiceImpl.getInstance();
    private DishService dishService = DishServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<Order> optionalOrder;

        String userIdLine = request.getParameter(USER_ID);

        try {
            long userId = Long.parseLong(userIdLine);
            optionalOrder = orderService.createDefaultOrderForCustomer(userId);

            if (!optionalOrder.isPresent()){
                session.setAttribute(USER_ORDER_CREATION_ERROR, true);
                return new Router(USER_PAGE + "?id=" + userIdLine, FORWARD);
            }

            Optional<User> optionalOrderUser = userService.findById(userId);
            List<Dish> availableDishes = dishService.findAllAvailable();

            session.setAttribute(ORDER, optionalOrder.get());
            session.setAttribute(ORDER_DISH_LIST_ITEMS, new ArrayList<DishListItem>());
            session.setAttribute(ORDER_DISHES_MAP, new HashMap<Long, Dish>());
            session.setAttribute(ORDER_USER, optionalOrderUser.get());
            session.setAttribute(AVAILABLE_DISHES, availableDishes);
        } catch (ServiceException e) {
            logger.info("Impossible to create order for user with id: " + userIdLine, e);
            session.setAttribute(USER_ORDER_CREATION_ERROR, true);
            return new Router(USER_PAGE + "?id=" + userIdLine, FORWARD);
        }
        return new Router(ORDER_PAGE + "?id=" + optionalOrder.get().getId(), REDIRECT);
    }
}
