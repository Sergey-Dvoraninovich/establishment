package com.dvoraninovich.establishment.controller.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.CommandType.*;

public enum RolesCommandTypes {
    ADMIN(new CommandType[]{
            GO_TO_START_PAGE,
            GO_TO_USERS_PAGE,
            DEFAULT,
            SIGN_OUT,
            GO_TO_INGREDIENTS_PAGE,
            GO_TO_CREATE_INGREDIENT_PAGE,
            CREATE_INGREDIENT,
            GO_TO_DISHES_PAGE,
            MAKE_DISH_AVAILABLE,
            DISABLE_DISH,
            CREATE_DISH,
            EDIT_DISH,
            GO_TO_EDIT_DISH,
            GO_TO_CREATE_DISH,
            VERIFY_CODE,
            GO_TO_VERIFY_CODE_PAGE,
            GO_TO_DISH_PAGE,
            ADD_DISH_INGREDIENT,
            REMOVE_DISH_INGREDIENT,
            GO_TO_USER_PAGE,
            GO_TO_ORDERS_PAGE,
            ADD_ORDER,
            EDIT_USER_DATA,
            GO_TO_ADMIN_PAGE,
            UPLOAD_DISH_PHOTO,
            INCREMENT_ORDER_DISH,
            DECREMENT_ORDER_DISH,
            DELETE_ORDER_DISH,
            RECALCULATE_PRICE,
            GO_TO_CUSTOMER_ORDERS,
            GO_TO_ORDER_PAGE,
            UPLOAD_USER_PHOTO,
            SET_ORDERS_FILTER_PARAMETERS,
            EDIT_ORDER,
            SET_USERS_FILTER_PARAMETERS,
            SET_DISHES_FILTER_PARAMETERS,
            CHANGE_USER_PASSWORD,
            CHANGE_USER_STATUS,
            SEND_ACTIVATION_CODE,
            SET_LOCALE,
            ADD_DISH_TO_ORDER,
            CHANGE_USER_BONUSES_AMOUNT,
    }),
    CUSTOMER(new CommandType[]{
            GO_TO_START_PAGE,
            DEFAULT,
            SIGN_OUT,
            GO_TO_DISHES_PAGE,
            VERIFY_CODE,
            GO_TO_VERIFY_CODE_PAGE,
            GO_TO_DISH_PAGE,
            EDIT_USER_DATA,
            GO_TO_CUSTOMER_BASKET,
            ADD_TO_BASKET,
            INCREMENT_ORDER_DISH,
            DECREMENT_ORDER_DISH,
            DELETE_ORDER_DISH,
            BUY_BASKET,
            RECALCULATE_PRICE,
            GO_TO_CUSTOMER_ORDERS,
            GO_TO_ORDER_PAGE,
            GO_TO_USER_PAGE,
            UPLOAD_USER_PHOTO,
            SET_ORDERS_FILTER_PARAMETERS,
            SET_DISHES_FILTER_PARAMETERS,
            CHANGE_USER_PASSWORD,
            SEND_ACTIVATION_CODE,
            SET_LOCALE,
    }),
    GUEST(new CommandType[]{
            GO_TO_START_PAGE,
            GO_TO_LOGIN_PAGE,
            GO_TO_SIGN_UP_PAGE,
            DEFAULT,
            LOGIN_PAGE,
            SIGN_UP_PAGE,
            GO_TO_DISHES_PAGE,
            GO_TO_DISH_PAGE,
            SET_DISHES_FILTER_PARAMETERS,
            SET_LOCALE,
    });
    private CommandType[] roleCommandTypesArray;
    private RolesCommandTypes(CommandType[] roleCommandTypesArray) {
        this.roleCommandTypesArray = roleCommandTypesArray;
    }
    public List<CommandType> getRoleCommandTypesList() {
        return new ArrayList<CommandType>(Arrays.asList(roleCommandTypesArray));
    }
}