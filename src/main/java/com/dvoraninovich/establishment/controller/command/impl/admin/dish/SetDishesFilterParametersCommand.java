package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.impl.admin.order.SetOrdersFilterParametersCommand;
import com.dvoraninovich.establishment.controller.command.validator.DishValidator;
import com.dvoraninovich.establishment.controller.command.validator.OrderValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.REQUEST_FILTER_MAX_PRICE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.Role.ADMIN;
import static com.dvoraninovich.establishment.model.entity.Role.GUEST;

public class SetDishesFilterParametersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetDishesFilterParametersCommand.class);
    private static final Long ORDERS_PAGE_ITEMS_AMOUNT = Long.valueOf(10);
    private static final String DISH_AVAILABLE = "AVAILABLE";
    DishValidator validator = DishValidator.getInstance();
    DishService service = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);

        resetErrors(session);

        String dishName = request.getParameter(REQUEST_FILTER_NAME);
        String[] dishStatesLines = request.getParameterValues(REQUEST_FILTER_DISH_STATES);
        System.out.println(dishStatesLines);
        dishStatesLines = dishStatesLines == null ? new String[0] : dishStatesLines;
        String minPriceLine = request.getParameter(REQUEST_FILTER_MIN_PRICE);
        String maxPriceLine = request.getParameter(REQUEST_FILTER_MAX_PRICE);
        String minCaloriesAmountLine = request.getParameter(REQUEST_FILTER_MIN_CALORIES_AMOUNT);
        String maxCaloriesAmountLine = request.getParameter(REQUEST_FILTER_MAX_CALORIES_AMOUNT);
        String minAmountGramsLine = request.getParameter(REQUEST_FILTER_MIN_AMOUNT_GRAMS);
        String maxAmountGramsLine = request.getParameter(REQUEST_FILTER_MAX_AMOUNT_GRAMS);

        if (!user.getRole().equals(ADMIN)) {
            dishStatesLines = new String[1];
            dishStatesLines[0] = DISH_AVAILABLE;
        }

        safeFilterParameters(session, dishName, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStatesLines);

        HashMap<String, Boolean> validationResult = new HashMap<>();
        validationResult = validator.validateFilterParameters(dishName, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStatesLines);

        Set<String> validationMessages = validationResult.keySet();
        HashMap<String, Boolean> finalValidationResult = validationResult;
        validationMessages.forEach(message -> session.setAttribute(message, finalValidationResult.get(message)));

        Collection<Boolean> validationErrors = validationResult.values();
        if (validationErrors.contains(true)) {
            return new Router(DISHES_PAGE, REDIRECT);
        }

        try {
            List<Order> orders = new ArrayList<>();
            Long minPos = Long.valueOf(1);
            Long maxPos = ORDERS_PAGE_ITEMS_AMOUNT;

            Long totalAmount = service.countFilteredDishes(dishName, minPriceLine, maxPriceLine,
                    minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStatesLines);
            maxPos = maxPos > totalAmount ? totalAmount : maxPos;
            session.setAttribute(TOTAL_AMOUNT, totalAmount);
            session.setAttribute(PAGE_ITEMS_AMOUNT, ORDERS_PAGE_ITEMS_AMOUNT);

            List<Dish> dishes = new ArrayList<>();
            dishes = service.findFilteredDishes(dishName, minPriceLine, maxPriceLine, minCaloriesAmountLine, maxCaloriesAmountLine,
                    minAmountGramsLine, maxAmountGramsLine, dishStatesLines, minPos, maxPos);

            session.setAttribute(DISHES, dishes);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);
        } catch (ServiceException e) {
            logger.info("Impossible to find filtered dishes", e);
            session.setAttribute(FILTER_ERROR, true);
        }
        return new Router(DISHES_PAGE, REDIRECT);
    }

    private void resetErrors(HttpSession session) {
        session.setAttribute(INVALID_DISH_NAME, false);
        session.setAttribute(INVALID_DISH_STATES, false);
        session.setAttribute(INVALID_MIN_PRICE, false);
        session.setAttribute(INVALID_MAX_PRICE, false);
        session.setAttribute(INVALID_MIN_CALORIES_AMOUNT, false);
        session.setAttribute(INVALID_MAX_CALORIES_AMOUNT, false);
        session.setAttribute(INVALID_MIN_AMOUNT_GRAMS, false);
        session.setAttribute(INVALID_MAX_AMOUNT_GRAMS, false);
        session.setAttribute(INVALID_FILTER_PARAMETERS, false);
    }

    private void safeFilterParameters(HttpSession session, String dishName, String minPriceLine, String maxPriceLine,
                                      String minCaloriesAmountLine, String maxCaloriesAmountLine, String minAmountGramsLine, String maxAmountGramsLine, String[] dishStatesLines) {
        session.setAttribute(DISHES_FILTER_NAME, dishName);
        session.setAttribute(DISHES_FILTER_MIN_PRICE, minPriceLine.equals("") ? null : new BigDecimal(minPriceLine));
        session.setAttribute(DISHES_FILTER_MAX_PRICE, maxPriceLine.equals("") ? null : new BigDecimal(maxPriceLine));
        session.setAttribute(DISHES_FILTER_MIN_CALORIES_AMOUNT, minCaloriesAmountLine.equals("") ? null : new BigDecimal(minCaloriesAmountLine));
        session.setAttribute(DISHES_FILTER_MAX_CALORIES_AMOUNT, maxCaloriesAmountLine.equals("") ? null : new BigDecimal(maxCaloriesAmountLine));
        session.setAttribute(DISHES_FILTER_MIN_AMOUNT_GRAMS, minAmountGramsLine.equals("") ? null : new BigDecimal(minAmountGramsLine));
        session.setAttribute(DISHES_FILTER_MAX_AMOUNT_GRAMS, maxAmountGramsLine.equals("") ? null : new BigDecimal(maxAmountGramsLine));
        session.setAttribute(DISHES_FILTER_STATES, Arrays.asList(dishStatesLines));
    }
}
