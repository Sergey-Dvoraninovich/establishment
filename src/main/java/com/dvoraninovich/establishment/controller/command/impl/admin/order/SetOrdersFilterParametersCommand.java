package com.dvoraninovich.establishment.controller.command.impl.admin.order;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.OrderValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.INVALID_FILTER_PARAMETERS;

public class SetOrdersFilterParametersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetOrdersFilterParametersCommand.class);
    OrderValidator validator = OrderValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        session.setAttribute(INVALID_FILTER_PARAMETERS, false);

        String paymentTypeLine = request.getParameter(REQUEST_FILTER_PAYMENT_TYPE);
        String minPriceLine = request.getParameter(REQUEST_FILTER_MIN_PRICE);
        String maxPriceLine = request.getParameter(REQUEST_FILTER_MAX_PRICE);

        HashMap<String, Boolean> validationResult = new HashMap<>();
        validationResult = validator.validateFilterParameters(minPriceLine, maxPriceLine);

        Set<String> validationMessages = validationResult.keySet();
        HashMap<String, Boolean> finalValidationResult = validationResult;
        validationMessages.forEach(message -> session.setAttribute(message, finalValidationResult.get(message)));

        Collection<Boolean> validationErrors = validationResult.values();
        if (validationErrors.contains(true)) {
            router = new Router(CREATE_DISH_PAGE, REDIRECT);
            return router;
        }

//        try {
//            boolean creationResult = false;
//            creationResult = service.addDish(dish);
//            if (creationResult) {
//                List<Dish> dishes = service.findAll();
//                session.setAttribute(DISHES, dishes);
//                router = new Router(DISHES_PAGE, REDIRECT);
//            }
//            else {
//                session.setAttribute(ADD_DISH_ERROR, true);
//                router = new Router(CREATE_DISH_PAGE, REDIRECT);
//            }
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            session.setAttribute(EXCEPTION, e);
//            router = new Router(ERROR_PAGE, FORWARD);
//        }
        return new Router(ERROR_PAGE, FORWARD);
    }
}
