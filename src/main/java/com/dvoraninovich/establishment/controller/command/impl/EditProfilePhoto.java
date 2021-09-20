package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.impl.admin.dish.EditDishPhoto;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.FileUploadService;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.FileUploadServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.Role.CUSTOMER;

public class EditProfilePhoto implements Command {
    private static final Logger logger = LogManager.getLogger(EditDishPhoto.class);
    UserService userService = UserServiceImpl.getInstance();
    FileUploadService fileUploadService = FileUploadServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String userIdLine = request.getParameter(ID);
        Optional<User> optionalUser = Optional.empty();
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(USER);
        session.setAttribute(IMPOSSIBLE_TO_UPLOAD_USER_PHOTO, false);
        session.setAttribute(CHANGE_PROFILE_PHOTO_ERROR, false);

        try {
            Long userId = Long.valueOf(userIdLine);
            optionalUser = userService.findById(userId);
            if (optionalUser.isPresent()) {
                User targetUser = optionalUser.get();
                if (user.getRole().equals(CUSTOMER) && user.getId() != targetUser.getId()) {
                    return new Router(CUSTOMER_ORDERS + "?id=" + user.getId(), REDIRECT);
                }

                String applicationDir = request.getServletContext().getRealPath("");
                Collection<Part> parts = request.getParts();
                String photoName = fileUploadService.uploadFile(applicationDir, parts, User.class.getName(), userIdLine);
                if (photoName.equals("")) {
                    session.setAttribute(IMPOSSIBLE_TO_UPLOAD_USER_PHOTO, true);
                }
                else {
                    System.out.println(photoName);
                    targetUser.setPhoto(photoName);
                    System.out.println(user);
                    boolean updateResult = userService.updateUser(targetUser);
                    System.out.println(updateResult);
                    if (!updateResult) {
                        session.setAttribute(CHANGE_PROFILE_PHOTO_ERROR, true);
                    } else {
                        session.setAttribute(EDIT_USER, targetUser);
                        if (user.getRole() == CUSTOMER) {
                            session.setAttribute(USER, targetUser);
                        }
                    }
                }
            }
        } catch (ServiceException | IOException | ServletException e) {
            e.printStackTrace();
            logger.info("Impossible to upload dish photo", e);
            session.setAttribute(CHANGE_PROFILE_PHOTO_ERROR, true);
        }
        switch (user.getRole()) {
            case ADMIN: {
                router = new Router(USER + "?id=" + userIdLine, REDIRECT);
                break;
            }
            case CUSTOMER: {
                router = new Router(CUSTOMER_PROFILE_PAGE, REDIRECT);
                break;
            }
            default: {
                router = new Router(INDEX, REDIRECT);
            }
        }
        return router;
    }
}
