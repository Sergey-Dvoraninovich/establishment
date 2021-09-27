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
import static com.dvoraninovich.establishment.model.entity.Role.ADMIN;
import static com.dvoraninovich.establishment.model.entity.Role.CUSTOMER;

public class EditProfilePhotoCommand implements Command {
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
                    return new Router(CUSTOMER_ORDERS_PAGE + "?id=" + user.getId(), REDIRECT);
                }

                String applicationDir = request.getServletContext().getRealPath("");
                Collection<Part> parts = request.getParts();
                String photoName = fileUploadService.uploadFile(applicationDir, parts, User.class.getName(), userIdLine);
                if (photoName.equals("")) {
                    session.setAttribute(IMPOSSIBLE_TO_UPLOAD_USER_PHOTO, true);
                }
                else {
                    targetUser.setPhoto(photoName);
                    boolean updateResult = userService.updateUser(targetUser);
                    if (!updateResult) {
                        session.setAttribute(CHANGE_PROFILE_PHOTO_ERROR, true);
                    } else {
                        session.setAttribute(USER_PROFILE, targetUser);
                        if (user.getRole() == CUSTOMER
                            || (user.getRole() == ADMIN && targetUser.getId() == user.getId())) {
                            session.setAttribute(USER, targetUser);
                        }
                    }
                }
            }
        } catch (ServiceException | IOException | ServletException e) {
            logger.info("Impossible to upload dish photo", e);
            session.setAttribute(CHANGE_PROFILE_PHOTO_ERROR, true);
        }
        return new Router(USER_PAGE + "?id=" + userIdLine, REDIRECT);
    }
}
