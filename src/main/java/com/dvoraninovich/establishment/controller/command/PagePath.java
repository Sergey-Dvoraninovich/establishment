package com.dvoraninovich.establishment.controller.command;

public final class PagePath {
    private static final String ARTIFACT_PATH = "/establishment_war_exploded";

    public static final String INDEX_PAGE = ARTIFACT_PATH + "/index.jsp";
    public static final String LOGIN_PAGE = ARTIFACT_PATH + "/pages/login.jsp";
    public static final String SIGN_UP_PAGE = ARTIFACT_PATH + "/pages/sign_up.jsp";
    public static final String VERIFICATION_PAGE = ARTIFACT_PATH + "/pages/verification.jsp";

    public static final String USER_PAGE = ARTIFACT_PATH + "/pages/user.jsp";
    public static final String CUSTOMER_BASKET_PAGE = ARTIFACT_PATH + "/pages/customer/customer_basket.jsp";
    public static final String CUSTOMER_ORDERS_PAGE = ARTIFACT_PATH + "/pages/customer/customer_orders.jsp";

    public static final String USERS_PAGE = ARTIFACT_PATH + "/pages/admin/users/users.jsp";

    public static final String ADMIN_PAGE = ARTIFACT_PATH + "/pages/admin/admin.jsp";
    public static final String INGREDIENTS_PAGE = ARTIFACT_PATH + "/pages/admin/ingredients/ingredients.jsp";
    public static final String CREATE_INGREDIENT_PAGE = ARTIFACT_PATH + "/pages/admin/ingredients/add_ingredient.jsp";

    public static final String DISHES_PAGE = ARTIFACT_PATH + "/pages/dishes.jsp";
    public static final String CREATE_DISH_PAGE = ARTIFACT_PATH + "/pages/admin/dishes/add_dish.jsp";
    public static final String EDIT_DISH_PAGE = ARTIFACT_PATH + "/pages/admin/dishes/edit_dish.jsp";
    public static final String DISH_PAGE = ARTIFACT_PATH + "/pages/dish.jsp";

    public static final String ERROR_PAGE = ARTIFACT_PATH + "/pages/errors/error500.jsp";

    public static final String ORDER_PAGE = ARTIFACT_PATH + "/pages/order.jsp";
    public static final String ORDERS_PAGE = ARTIFACT_PATH + "/pages/admin/orders/orders.jsp";

    public static final String INFO_PAGE = ARTIFACT_PATH + "/pages/info.jsp";

    private PagePath() {
    }
}
