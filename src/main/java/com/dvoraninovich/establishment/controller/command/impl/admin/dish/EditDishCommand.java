package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.DishValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.CALORIES_AMOUNT;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.EXCEPTION;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.INGREDIENTS;

public class EditDishCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditDishCommand.class);
    private DishService service = DishServiceImpl.getInstance();
    private DishValidator validator = DishValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        long dishId;

        session.setAttribute(INVALID_DISH_NAME, false);
        session.setAttribute(INVALID_DISH_PRICE, false);
        session.setAttribute(INVALID_DISH_AMOUNT_GRAMS, false);
        session.setAttribute(INVALID_DISH_CALORIES_AMOUNT, false);
        session.setAttribute(DISH_VALIDATION_ERROR, false);
        session.setAttribute(EDIT_DISH_ERROR, false);

        String id = request.getParameter(ID);
        dishId = Long.parseLong(id);
        String name = request.getParameter(NAME);
        String photo = request.getParameter(PHOTO);
        String priceLine = request.getParameter(PRICE);
        String amountGramsLine = request.getParameter(AMOUNT_GRAMS);
        String caloriesAmountLine = request.getParameter(CALORIES_AMOUNT);

        HashMap<String, Boolean> validationResult = new HashMap<>();
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
        .setId(dishId)
        .setName(name)
        .setPhoto(photo)
        .setPrice(new BigDecimal(priceLine))
        .setAmountGrams(new Integer(amountGramsLine))
        .setCalories(new Integer(caloriesAmountLine))
        .setIsAvailable(true)
        .build();

        try {
            boolean creationResult;
            creationResult = service.editDish(dish);
            if (creationResult) {
                session.setAttribute(DISH, dish);
                router = new Router(DISH_PAGE + "?id:" + id, REDIRECT);
            }
            else {
                session.setAttribute(EDIT_DISH_ERROR, true);
                router = new Router(EDIT_DISH_PAGE + "?id:" + id, REDIRECT);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.info("Impossible to edit dish with id:" + dishId, e);
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
