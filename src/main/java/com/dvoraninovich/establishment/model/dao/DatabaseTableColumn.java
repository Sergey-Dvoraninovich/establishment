package com.dvoraninovich.establishment.model.dao;

public final class DatabaseTableColumn {

    public static final String INGREDIENT_ID = "ingredients.id";
    public static final String INGREDIENT_NAME = "ingredients.name";

    public static final String DISH_ID = "dishes.id";
    public static final String DISH_PRICE = "dishes.price";
    public static final String DISH_CALORIES = "dishes.calories";
    public static final String DISH_AMOUNT_GRAMS = "dishes.amount_grams";
    public static final String DISH_NAME = "dishes.name";
    public static final String IS_DISH_AVAILABLE = "dishes.is_available";
    public static final String DISH_PHOTO = "dishes.photo";

    public static final String DISH_LIST_ITEM_ID = "dishes_lists_items.id";
    public static final String DISH_LIST_ITEM_ID_ORDER = "dishes_lists_items.id_order";
    public static final String DISH_LIST_ITEM_ID_DISH = "dishes_lists_items.id_dish";
    public static final String DISH_LIST_ITEM_DISH_AMOUNT = "dishes_lists_items.dish_amount";

    public static final String ORDER_ID = "orders.id";
    public static final String ORDER_USER_ID = "orders.id_user";
    public static final String ORDER_ORDER_STATUS = "orders_statuses.order_status";
    public static final String ORDER_TIME = "orders.order_time";
    public static final String ORDER_FINISH_TIME = "orders.finish_time";
    public static final String ORDER_CARD_NUMBER = "orders.card_number";
    public static final String ORDER_PAYMENT_TYPE = "payment_types.payment_type";
    public static final String ORDER_BONUSES_IN_PAYMENT = "orders.bonuses_in_payment";
    public static final String ORDER_FINAL_PRICE = "orders.final_price";

    public static final String USER_ID = "users.id";
    public static final String USER_LOGIN = "users.login";
    public static final String USER_MAIL = "users.mail";
    public static final String USER_PASSWORD_HASH = "users.password_hash";
    public static final String USER_SALT = "users.salt";
    public static final String USER_STATUS = "statuses.status";
    public static final String USER_ROLE = "roles.role";
    public static final String USER_CARD_NUMBER = "users.card_number";
    public static final String USER_PHONE_NUMBER = "users.phone_number";
    public static final String USER_BONUSES_AMOUNT = "users.bonuses_amount";
    public static final String USER_PHOTO = "users.photo";
    public static final String USER_CODE = "users.code";
    public static final String USER_CODE_EXPIRATION_TIME = "users.code_expiration_time";

    private DatabaseTableColumn() {

    }
}
