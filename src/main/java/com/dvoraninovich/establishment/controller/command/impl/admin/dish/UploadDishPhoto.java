package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.FileUploadService;
import com.dvoraninovich.establishment.model.service.IngredientService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.FileUploadServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.IngredientServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.DISH_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class UploadDishPhoto implements Command {
    private static final Logger logger = LogManager.getLogger(UploadDishPhoto.class);
    DishService dishService = DishServiceImpl.getInstance();
    FileUploadService fileUploadService = FileUploadServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        String idParameter = request.getParameter(ID);
        Optional<Dish> dish = Optional.empty();
        List<Ingredient> ingredients = new ArrayList<>();
        HttpSession session = request.getSession();
        Collection<Part> parts;
        String applicationDir;

        try {
            applicationDir = request.getServletContext().getRealPath("");
            parts = request.getParts();
            fileUploadService.uploadFile(applicationDir, parts, Dish.class.getName() , idParameter);
            dish = dishService.findDishById(Long.valueOf(idParameter));
            Long id = Long.valueOf(idParameter);
            ingredients = dishService.findDishIngredients(id);
        } catch (ServiceException | IOException | ServletException e) {
            logger.info("Impossible to upload dish info", e);
        }

        session.setAttribute(DISH, dish.get());
        session.setAttribute(INGREDIENTS, ingredients);
        return new Router(DISH_PAGE + "?id=" + idParameter, REDIRECT);
    }
}
