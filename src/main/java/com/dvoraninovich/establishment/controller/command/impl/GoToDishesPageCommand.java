package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
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
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.Role.ADMIN;

public class GoToDishesPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToDishesPageCommand.class);
    private static final Long DISHES_PAGE_ITEMS_AMOUNT = Long.valueOf(10);
    private static final String DISH_AVAILABLE = "AVAILABLE";
    private DishService dishService = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String minPosLine = request.getParameter(NEXT_MIN_POS);
        String maxPosLine = request.getParameter(NEXT_MAX_POS);
        Long totalAmount;

        String dishName = (String) session.getAttribute(DISHES_FILTER_NAME);
        List<String> dishStatesList = (List<String>) session.getAttribute(DISHES_FILTER_STATES);
        BigDecimal minPrice = (BigDecimal) session.getAttribute(DISHES_FILTER_MIN_PRICE);
        BigDecimal maxPrice = (BigDecimal) session.getAttribute(DISHES_FILTER_MAX_PRICE);
        Integer minCaloriesAmount = (Integer) session.getAttribute(DISHES_FILTER_MIN_CALORIES_AMOUNT);
        Integer maxCaloriesAmount = (Integer) session.getAttribute(DISHES_FILTER_MAX_CALORIES_AMOUNT);
        Integer minAmountGrams = (Integer) session.getAttribute(DISHES_FILTER_MIN_AMOUNT_GRAMS);
        Integer maxAmountGrams = (Integer) session.getAttribute(DISHES_FILTER_MAX_AMOUNT_GRAMS);

        Long minPos;
        Long maxPos;
        if (minPosLine == null || maxPosLine == null) {
            minPos = 1L;
            maxPos = DISHES_PAGE_ITEMS_AMOUNT;
        }
        else {
            minPos = Long.valueOf(minPosLine);
            maxPos = Long.valueOf(maxPosLine);
        }

        String[] dishStatesLines;
        if (user.getRole().equals(ADMIN)) {
            dishStatesLines = dishStatesList == null
                    ? new String[0]
                    : (String[]) dishStatesList.toArray();
        }
        else {
            dishStatesLines = new String[1];
            dishStatesLines[0] = DISH_AVAILABLE;
        }

        try {
            if (minPos.equals(1L)) {
                totalAmount = dishService.countFilteredDishes(dishName, minPrice, maxPrice,
                        minCaloriesAmount, maxCaloriesAmount, minAmountGrams, maxAmountGrams, dishStatesLines);
                maxPos = maxPos > totalAmount ? totalAmount : maxPos;
                session.setAttribute(TOTAL_AMOUNT, totalAmount);
                session.setAttribute(PAGE_ITEMS_AMOUNT, DISHES_PAGE_ITEMS_AMOUNT);
System.out.println(totalAmount);
            }

System.out.println(maxPos);
            List<Dish> dishes;
            dishes = dishService.findFilteredDishes(dishName, minPrice, maxPrice, minCaloriesAmount, maxCaloriesAmount,
                    minAmountGrams, maxAmountGrams, dishStatesLines, minPos, maxPos);

System.out.println(dishes);

            session.setAttribute(DISHES, dishes);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);
        } catch (ServiceException e) {
            logger.info("Impossible to find dishes", e);
        }
        return new Router(DISHES_PAGE, REDIRECT);
    }
}
