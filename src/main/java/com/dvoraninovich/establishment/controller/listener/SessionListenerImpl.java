package com.dvoraninovich.establishment.controller.listener;

//import com.dvoraninovich.establishment.controller.command.SessionAttribute;
//import com.dvoraninovich.establishment.model.entity.Role;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.USER;
import static com.dvoraninovich.establishment.model.entity.Role.GUEST;

@WebListener
public class SessionListenerImpl implements HttpSessionListener {
    private final static String LOCALE = "locale";
    private final static String RUSSIAN_LOCALE = "ru";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
//        User defaultUser = User.builder()
//                .setRole(GUEST)
//                .build();
//        session.setAttribute(USER, defaultUser);
        session.setAttribute(LOCALE, RUSSIAN_LOCALE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
