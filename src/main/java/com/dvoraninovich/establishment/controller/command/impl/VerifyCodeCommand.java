package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.entity.UserStatus;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.CODE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.MAIL;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class VerifyCodeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(VerifyCodeCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        UserService service = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(USER);
        String code = request.getParameter(CODE);

        session.setAttribute(USER_IS_NOT_AUTHENTICATED, false);
        session.setAttribute(USER_ALREADY_VERIFIED, false);
        session.setAttribute(USER_BLOCKED, false);
        session.setAttribute(INCORRECT_USER_CODE, false);
        session.setAttribute(VERIFICATION_ERROR, false);

        if (user == null) {
            session.setAttribute(USER_IS_NOT_AUTHENTICATED, true);
            return new Router(VERIFICATION_PAGE, REDIRECT);
        }

        if (user.getStatus() == UserStatus.CONFIRMED) {
            session.setAttribute(USER_ALREADY_VERIFIED, true);
            return new Router(VERIFICATION_PAGE, REDIRECT);
        }

        if (user.getStatus() == UserStatus.BLOCKED) {
            session.setAttribute(USER_BLOCKED, true);
            return new Router(VERIFICATION_PAGE, REDIRECT);
        }

        try {
            Optional<LocalDateTime> codeExpirationTime = Optional.empty();
            Optional<String> optionalCode = Optional.empty();
            LocalDateTime currentTime = LocalDateTime.now();
            codeExpirationTime = service.getCodeExpirationTime(user.getId());
            optionalCode = service.getCode(user.getId());

            if (!optionalCode.isPresent()
                || !codeExpirationTime.isPresent()) {
                session.setAttribute(VERIFICATION_ERROR, true);
                return new Router(VERIFICATION_PAGE, REDIRECT);
            }
            if (!code.equals(optionalCode.get())) {
                session.setAttribute(INCORRECT_USER_CODE, true);
                return new Router(VERIFICATION_PAGE, REDIRECT);
            }
            if (currentTime.compareTo(codeExpirationTime.get()) > 0) {
                session.setAttribute(EXPIRED_USER_CODE, true);
                return new Router(VERIFICATION_PAGE, REDIRECT);
            }

            user.setStatus(UserStatus.CONFIRMED);
            service.updateUser(user);
        } catch (ServiceException e) {
            session.setAttribute(VERIFICATION_ERROR, true);
            logger.info("Can't handle verification of user " + user.getLogin());
            return new Router(VERIFICATION_PAGE, REDIRECT);
        }

        logger.info("User " + user.getLogin() + " successfully verified!");
        return new Router(INDEX, REDIRECT);
    }
}
