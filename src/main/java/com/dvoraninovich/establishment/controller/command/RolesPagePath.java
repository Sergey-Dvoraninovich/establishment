package com.dvoraninovich.establishment.controller.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.PagePath.LOGIN_PAGE;
import static com.dvoraninovich.establishment.controller.command.PagePath.SIGN_UP_PAGE;

public enum RolesPagePath {
    ADMIN(new String[]{
            INDEX_PAGE,
            USER_PAGE,
            CUSTOMER_BASKET_PAGE,
            CUSTOMER_ORDERS_PAGE,
            USERS_PAGE,
            ADMIN_PAGE,
            INGREDIENTS_PAGE,
            CREATE_INGREDIENT_PAGE,
            DISHES_PAGE,
            CREATE_DISH_PAGE,
            EDIT_DISH_PAGE,
            DISH_PAGE,
            ERROR_PAGE,
            ORDER_PAGE,
            ORDERS_PAGE,
            INFO_PAGE,
}),
    CUSTOMER(new String[]{
            INDEX_PAGE,
            USER_PAGE,
            CUSTOMER_BASKET_PAGE,
            CUSTOMER_ORDERS_PAGE,
            DISHES_PAGE,
            DISH_PAGE,
            ERROR_PAGE,
            ORDER_PAGE,
            INFO_PAGE,
    }),
    GUEST(new String[]{
            INDEX_PAGE,
            LOGIN_PAGE,
            SIGN_UP_PAGE,
            DISHES_PAGE,
            DISH_PAGE,
            ERROR_PAGE,
            INFO_PAGE,
    });
    private String[] rolePagesArray;
    private RolesPagePath(String[] rolePagesArray) {
        this.rolePagesArray = rolePagesArray;
    }
    public List<String> getRolePagesList() {
        return new ArrayList<String>(Arrays.asList(rolePagesArray));
    }
}
