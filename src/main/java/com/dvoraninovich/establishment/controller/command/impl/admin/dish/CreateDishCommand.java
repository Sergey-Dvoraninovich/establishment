package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.DishValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class CreateDishCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateDishCommand.class);
    private DishService service = DishServiceImpl.getInstance();
    private DishValidator validator = DishValidator.getInstance();
    private final static String DEFAULT_DISH_PHOTO = "default_dish.png";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        session.setAttribute(INVALID_DISH_NAME, false);
        session.setAttribute(INVALID_DISH_PRICE, false);
        session.setAttribute(INVALID_DISH_AMOUNT_GRAMS, false);
        session.setAttribute(INVALID_DISH_CALORIES_AMOUNT, false);
        session.setAttribute(ADD_DISH_ERROR, false);

        String name = request.getParameter(NAME);
        String priceLine = request.getParameter(PRICE);
        String amountGramsLine = request.getParameter(AMOUNT_GRAMS);
        String caloriesAmountLine = request.getParameter(CALORIES_AMOUNT);

        HashMap<String, Boolean> validationResult;
        validationResult = validator.validateDishData(name, priceLine, amountGramsLine, caloriesAmountLine);

        Set<String> validationMessages = validationResult.keySet();
        HashMap<String, Boolean> finalValidationResult = validationResult;
        validationMessages.forEach(message -> session.setAttribute(message, finalValidationResult.get(message)));

        Collection<Boolean> validationErrors = validationResult.values();
        if (validationErrors.contains(true)) {
            router = new Router(CREATE_DISH_PAGE, REDIRECT);
            return router;
        }

        Dish dish = Dish.builder()
                .setName(name)
                .setPhoto(DEFAULT_DISH_PHOTO)
                .setPrice(new BigDecimal(priceLine))
                .setAmountGrams(new Integer(amountGramsLine))
                .setCalories(new Integer(caloriesAmountLine))
                .setIsAvailable(true)
                .build();

        try {
            boolean creationResult;
            creationResult = service.addDish(dish);
            if (creationResult) {
                List<Dish> dishes = service.findAll();
                session.setAttribute(DISHES, dishes);
                router = new Router(DISHES_PAGE, REDIRECT);
            }
            else {
                session.setAttribute(ADD_DISH_ERROR, true);
                router = new Router(CREATE_DISH_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.error("Impossible to create dish", e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
