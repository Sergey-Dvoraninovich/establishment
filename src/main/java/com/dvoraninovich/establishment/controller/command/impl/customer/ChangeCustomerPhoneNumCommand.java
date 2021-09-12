package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.SessionAttribute;
import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.PHONE_NUM;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class ChangeCustomerPhoneNumCommand implements Command {
    private UserService service = UserServiceImpl.getInstance();
    private UserValidator validator = UserValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);

        String phoneNum = request.getParameter(PHONE_NUM);

        if (!validator.validatePhoneNum(phoneNum)) {
            session.setAttribute(INVALID_PHONE_NUM, true);
            router = new Router(SIGN_UP_PAGE, REDIRECT);
            return router;
        }

        user.setPhoneNumber(phoneNum.substring(1));

        try {
            boolean isSuccessful = service.updateUser(user);
            if (isSuccessful) {
                session.setAttribute(USER, user);
                router = new Router(EDIT_CUSTOMER_PAGE, REDIRECT);
            }
            else {
                session.setAttribute(CHANGE_PHONE_NUM_ERROR, true);
                router = new Router(SIGN_UP_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
