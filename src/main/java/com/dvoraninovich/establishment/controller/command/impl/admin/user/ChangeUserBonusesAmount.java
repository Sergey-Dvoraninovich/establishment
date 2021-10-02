package com.dvoraninovich.establishment.controller.command.impl.admin.user;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.impl.customer.GoToUserPageCommand;
import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.entity.UserStatus;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.USER_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class ChangeUserBonusesAmount implements Command {
    private static final Logger logger = LogManager.getLogger(GoToUserPageCommand.class);
    UserService userService = UserServiceImpl.getInstance();
    UserValidator userValidator = UserValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.setAttribute(CHANGE_BONUSES_AMOUNT_ERROR, false);

        String idParameter = request.getParameter(ID);
        String bonusesAmountLine = request.getParameter(BONUSES_AMOUNT);

        if (bonusesAmountLine ==  null) {
            return new Router(USER_PAGE + "?id=" + idParameter + "&edit_form=true", REDIRECT);
        }

        if (!userValidator.validateBonusesAmount(bonusesAmountLine)) {
            session.setAttribute(CHANGE_BONUSES_AMOUNT_ERROR, true);
            return new Router(USER_PAGE + "?id=" + idParameter + "&edit_form=true", REDIRECT);
        }

        try {
            long userId = Long.parseLong(idParameter);
            BigDecimal bonusesAmount = new BigDecimal(bonusesAmountLine);
            Optional<User> optionalProfileUser = userService.findById(userId);
            if (optionalProfileUser.isPresent()) {
                User user = optionalProfileUser.get();
                user.setBonusesAmount(bonusesAmount);
                boolean isSuccessful = userService.updateUser(user);
                if (isSuccessful) {
                    session.setAttribute(USER_PROFILE, user);
                }
                else {
                    session.setAttribute(CHANGE_BONUSES_AMOUNT_ERROR, true);
                }
            }
        } catch (ServiceException e) {
            logger.info("Impossible to change bonuses amount for user with id=" + idParameter, e);
            session.setAttribute(CHANGE_BONUSES_AMOUNT_ERROR, true);
        }
        return new Router(USER_PAGE + "?id=" + idParameter + "&edit_form=true", REDIRECT);
    }
}
