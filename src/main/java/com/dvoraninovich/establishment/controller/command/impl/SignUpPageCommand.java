package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
import com.dvoraninovich.establishment.model.entity.Role;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.entity.UserStatus;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import com.dvoraninovich.establishment.util.CodeGenerator;
import javafx.util.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.*;

public class SignUpPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignUpPageCommand.class);
    private UserService service = UserServiceImpl.getInstance();
    private UserValidator validator = UserValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(USER);

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String repeat_password = request.getParameter(REPEAT_PASSWORD);
        String mail = request.getParameter(MAIL);
        String phoneNum = request.getParameter(PHONE_NUM);
        String cardNum = request.getParameter(CARD_NUM);

        if (!password.equals(repeat_password)) {
            session.setAttribute(DIFFERENT_PASSWORDS, true);
            router = new Router(SIGN_UP_PAGE, REDIRECT);
            return router;
        }

        HashMap<String, Boolean> validationResult = new HashMap<>();
        validationResult = validator.validateUserData(login, password, mail, phoneNum, cardNum);

        Set<String> validationMessages = validationResult.keySet();
        HashMap<String, Boolean> finalValidationResult = validationResult;
        validationMessages.forEach(message -> session.setAttribute(message, finalValidationResult.get(message)));

        Collection<Boolean> validationErrors = validationResult.values();
        if (validationErrors.contains(true)) {
            router = new Router(SIGN_UP_PAGE, REDIRECT);
            return router;
        }

        if (sessionUser != null) {
            session.setAttribute(USER_ALREADY_AUTHENTICATED, true);
            router = new Router(SIGN_UP_PAGE, REDIRECT);
            return router;
        }

        User user = User.builder()
                        .setLogin(login)
                        .setMail(mail)
                        .setCardNumber(cardNum)
                        .setPhoneNumber(phoneNum)
                        .setRole(Role.CUSTOMER)
                        .setStatus(UserStatus.IN_REGISTRATION)
                        .setPhoto("default_profile.png")
                        .setBonusesAmount(new BigDecimal(0))
                 .build();


        try {
            Optional<User> optionalUser = service.register(user, password);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
                service.authenticate(user.getLogin(), password);
                session.setAttribute(USER, optionalUser.get());
                session.setAttribute(IS_AUTHENTICATED, true);

                router = new Router(VERIFICATION_PAGE, REDIRECT);
            }
            else {
                session.setAttribute(REGISTRATION_ERROR, true);
                router = new Router(SIGN_UP_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.info("Registration of user " + user + "caused exception ", e);
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
