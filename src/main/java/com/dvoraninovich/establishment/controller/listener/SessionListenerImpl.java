package com.dvoraninovich.establishment.controller.listener;

import com.dvoraninovich.establishment.controller.command.CommandType;
import com.dvoraninovich.establishment.controller.command.RolesCommandTypes;
import com.dvoraninovich.establishment.model.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.util.Arrays;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.LOCALE;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.USER;
import static com.dvoraninovich.establishment.model.entity.Role.GUEST;

@WebListener
public class SessionListenerImpl implements HttpSessionListener {
    private final static String RUSSIAN_LOCALE = "ru";

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(USER, User.builder().setId(0).setRole(GUEST).build());
        session.setAttribute(LOCALE, RUSSIAN_LOCALE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    }
}
