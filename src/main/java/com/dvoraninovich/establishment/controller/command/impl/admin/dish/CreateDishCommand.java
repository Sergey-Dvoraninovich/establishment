package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.DishValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class CreateDishCommand implements Command {
    private DishService service = DishServiceImpl.getInstance();
    private DishValidator validator = DishValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        String photo = request.getParameter(PHOTO);
        String priceLine = request.getParameter(PRICE);
        String amountGramsLine = request.getParameter(AMOUNT_GRAMS);
        String caloriesAmountLine = request.getParameter(CALORIES_AMOUNT);

        List<String> validationMessages = new ArrayList<>();
        validationMessages = validator.validateUserData(name, priceLine, amountGramsLine, caloriesAmountLine);
        if (!validationMessages.isEmpty()) {
            validationMessages.forEach(validationMessage -> session.setAttribute(validationMessage, true));
            router = new Router(CREATE_DISH_PAGE, REDIRECT);
            return router;
        }

        Dish dish = Dish.builder()
                .setName(name)
                .setPhoto(photo)
                .setPrice(new BigDecimal(priceLine))
                .setAmountGrams(new Integer(amountGramsLine))
                .setCalories(new Integer(caloriesAmountLine))
                .setIsAvailable(true)
                .build();

        try {
            boolean creationResult = false;
            creationResult = service.addDish(dish);
            if (creationResult) {
                //TODO work with it
                List<Dish> dishes = service.findAll();
                session.setAttribute("dishes", dishes);
                router = new Router(DISHES_PAGE, REDIRECT);
            }
            else {
                session.setAttribute(ADD_DISH_ERROR, true);
                router = new Router(CREATE_DISH_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
