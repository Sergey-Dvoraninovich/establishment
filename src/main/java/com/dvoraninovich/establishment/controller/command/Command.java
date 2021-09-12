package com.dvoraninovich.establishment.controller.command;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request);
}
