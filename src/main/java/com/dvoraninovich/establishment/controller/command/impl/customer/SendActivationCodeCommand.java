package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.MailService;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.MailServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dvoraninovich.establishment.controller.command.PagePath.INDEX_PAGE;
import static com.dvoraninovich.establishment.controller.command.PagePath.USER_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.UserStatus.*;

public class SendActivationCodeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SendActivationCodeCommand.class);
    UserService userService = UserServiceImpl.getInstance();
    MailService mailService = MailServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(INDEX_PAGE, REDIRECT);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);

        try {
            if (user != null) {
                if (user.getStatus().equals(IN_REGISTRATION)) {
                    //TODO generate code
                    mailService.sendActivateMail(user.getMail(), "1234");
                    router = new Router(USER_PAGE + "?id=" + user.getId(), REDIRECT);
                }
            }
        } catch (ServiceException e) {
            logger.info("impossible to send email", e);
            router = new Router(USER_PAGE + "?id=" + user.getId(), REDIRECT);
        }
        return router;
    }
}
