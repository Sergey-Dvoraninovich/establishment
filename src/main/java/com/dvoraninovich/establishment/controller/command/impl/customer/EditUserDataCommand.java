package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.INDEX;
import static com.dvoraninovich.establishment.controller.command.PagePath.USER_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.Role.ADMIN;
import static com.dvoraninovich.establishment.model.entity.Role.CUSTOMER;

public class EditUserDataCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToUserPageCommand.class);
    UserService userService = UserServiceImpl.getInstance();
    UserValidator userValidator = UserValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(INDEX, REDIRECT);
        HttpSession session = request.getSession();
        String idParameter = request.getParameter(ID);
        String phoneNumberLine = request.getParameter(PHONE_NUM);
        String cardNumberLine = request.getParameter(CARD_NUM);
        Long userId = Long.valueOf(idParameter);
        User user = (User) session.getAttribute(USER);

        System.out.println(user);
        System.out.println(userId);
        if (!user.getRole().equals(ADMIN) || user.getId() != userId) {
            return new Router(INDEX, REDIRECT);
        }

        if (!phoneNumberLine.equals("")){
        if (!userValidator.validatePhoneNum(phoneNumberLine)) {
            session.setAttribute(INVALID_PHONE_NUM, true);
            return new Router(USER_PAGE + "?id=" + idParameter, REDIRECT);
        }}

        if (!cardNumberLine.equals("")){
        if (!userValidator.validateCardNum(cardNumberLine)) {
            session.setAttribute(INVALID_CARD_NUM, true);
            return new Router(USER_PAGE + "?id=" + idParameter, REDIRECT);
        }}

        try {
            Optional<User> optionalProfileUser = userService.findById(userId);
            if (optionalProfileUser.isPresent()) {
                User userProfile = optionalProfileUser.get();
                if (!phoneNumberLine.equals("")) {
                    user.setPhoneNumber(phoneNumberLine);
                }
                if (!cardNumberLine.equals("")) {
                    user.setCardNumber(cardNumberLine);
                }
                userService.updateUser(userProfile);
                session.setAttribute(USER_PROFILE, userProfile);
                if (user.getRole().equals(CUSTOMER)) {
                    session.setAttribute(USER, userProfile);
                }
                router = new Router(USER_PAGE + "?id=" + idParameter, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.info("Impossible to go to user page", e);
        }
        return router;
    }
}
