package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.DishValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

import static com.dvoraninovich.establishment.controller.command.PagePath.DISHES_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.Role.ADMIN;

public class SetDishesFilterParametersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetDishesFilterParametersCommand.class);
    private static final Long DISHES_PAGE_ITEMS_AMOUNT = 10L;
    private static final String DISH_AVAILABLE = "AVAILABLE";
    private DishValidator validator = DishValidator.getInstance();
    private DishService service = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);

        session.setAttribute(INVALID_DISH_NAME, false);
        session.setAttribute(INVALID_DISH_STATES, false);
        session.setAttribute(INVALID_MIN_PRICE, false);
        session.setAttribute(INVALID_MAX_PRICE, false);
        session.setAttribute(INVALID_MIN_CALORIES_AMOUNT, false);
        session.setAttribute(INVALID_MAX_CALORIES_AMOUNT, false);
        session.setAttribute(INVALID_MIN_AMOUNT_GRAMS, false);
        session.setAttribute(INVALID_MAX_AMOUNT_GRAMS, false);
        session.setAttribute(INVALID_FILTER_PARAMETERS, false);

        String dishName = request.getParameter(REQUEST_FILTER_NAME);
        String[] dishStatesLines = request.getParameterValues(REQUEST_FILTER_DISH_STATES);
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

        HashMap<String, Boolean> validationResult;
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
            Long minPos = 1L;
            Long maxPos = DISHES_PAGE_ITEMS_AMOUNT;

            Long totalAmount = service.countFilteredDishes(dishName, minPriceLine, maxPriceLine,
                    minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStatesLines);
            maxPos = maxPos > totalAmount ? totalAmount : maxPos;
            session.setAttribute(TOTAL_AMOUNT, totalAmount);
            session.setAttribute(PAGE_ITEMS_AMOUNT, DISHES_PAGE_ITEMS_AMOUNT);

            List<Dish> dishes;
            dishes = service.findFilteredDishes(dishName, minPriceLine, maxPriceLine, minCaloriesAmountLine, maxCaloriesAmountLine,
                    minAmountGramsLine, maxAmountGramsLine, dishStatesLines, minPos, maxPos);

            session.setAttribute(DISHES, dishes);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);

            session.setAttribute(DISHES_FILTER_NAME, dishName);
            session.setAttribute(DISHES_FILTER_MIN_PRICE, minPriceLine.equals("") ? null : new BigDecimal(minPriceLine));
            session.setAttribute(DISHES_FILTER_MAX_PRICE, maxPriceLine.equals("") ? null : new BigDecimal(maxPriceLine));
            session.setAttribute(DISHES_FILTER_MIN_CALORIES_AMOUNT, minCaloriesAmountLine.equals("") ? null : Integer.valueOf(minCaloriesAmountLine));
            session.setAttribute(DISHES_FILTER_MAX_CALORIES_AMOUNT, maxCaloriesAmountLine.equals("") ? null : Integer.valueOf(maxCaloriesAmountLine));
            session.setAttribute(DISHES_FILTER_MIN_AMOUNT_GRAMS, minAmountGramsLine.equals("") ? null : Integer.valueOf(minAmountGramsLine));
            session.setAttribute(DISHES_FILTER_MAX_AMOUNT_GRAMS, maxAmountGramsLine.equals("") ? null : Integer.valueOf(maxAmountGramsLine));
            session.setAttribute(DISHES_FILTER_STATES, Arrays.asList(dishStatesLines));

        } catch (ServiceException e) {
            logger.info("Impossible to find filtered dishes", e);
            session.setAttribute(FILTER_ERROR, true);
        }
        return new Router(DISHES_PAGE, REDIRECT);
    }
}
