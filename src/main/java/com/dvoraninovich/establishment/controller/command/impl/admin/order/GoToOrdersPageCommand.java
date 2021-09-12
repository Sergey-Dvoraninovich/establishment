package com.dvoraninovich.establishment.controller.command.impl.admin.order;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.SessionAttribute;
import com.dvoraninovich.establishment.controller.command.impl.admin.ingredient.GoToIngredientsPageCommand;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.IngredientService;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.impl.IngredientServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import javafx.util.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.ORDERS_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.ORDERS_WITH_USER_INFO;

public class GoToOrdersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToOrdersPageCommand.class);
    private OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        //TODO work with it
        HttpSession session = request.getSession();
        List<Pair<Order, User>> pairList = new ArrayList<>();
        try {
            pairList = service.findAllOrdersWithUserinfo();
        } catch (ServiceException e) {
            logger.info("Can't handle data extraction", e);
        }
        session.setAttribute(ORDERS_WITH_USER_INFO, pairList);
        return new Router(ORDERS_PAGE, REDIRECT);
    }
}
