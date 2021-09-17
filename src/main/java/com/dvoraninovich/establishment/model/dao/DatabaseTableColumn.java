package com.dvoraninovich.establishment.model.dao;

public final class DatabaseTableColumn {

    public static final String INGREDIENT_ID = "ingredients.id";
    public static final String INGREDIENT_NAME = "ingredients.name";

    public static final String DISH_ID = "id";
    public static final String DISH_PRICE = "price";
    public static final String DISH_CALORIES = "calories";
    public static final String DISH_AMOUNT_GRAMS = "amount_grams";
    public static final String DISH_NAME = "name";
    public static final String IS_DISH_AVAILABLE = "is_available";
    public static final String DISH_PHOTO = "photo";

    public static final String DISH_LIST_ITEM_ID = "id";
    public static final String DISH_LIST_ITEM_ID_ORDER = "id_order";
    public static final String DISH_LIST_ITEM_ID_DISH = "id_dish";
    public static final String DISH_LIST_ITEM_DISH_AMOUNT = "dish_amount";

    public static final String ORDER_ID = "orders.id";
    public static final String ORDER_USER_ID = "orders.id_user";
    public static final String ORDER_ORDER_STATUS = "orders_statuses.order_status";
    public static final String ORDER_TIME = "orders.order_time";
    public static final String ORDER_FINISH_TIME = "orders.finish_time";
    public static final String ORDER_CARD_NUMBER = "orders.card_number";
    public static final String ORDER_PAYMENT_TYPE = "payment_types.payment_type";
    public static final String ORDER_BONUSES_IN_PAYMENT = "orders.bonuses_in_payment";
    public static final String ORDER_FINAL_PRICE = "orders.final_price";

    public static final String FULL_ORDER_ID = "orders.id";
    public static final String FULL_ORDER_USER_ID = "orders.id_user";
    public static final String FULL_ORDER_ORDER_STATUS = "orders_statuses.order_status";
    public static final String FULL_ORDER_TIME = "orders.order_time";
    public static final String FULL_ORDER_CARD_NUMBER = "orders.card_number";
    public static final String FULL_ORDER_PAYMENT_TYPE = "payment_types.payment_type";
    public static final String FULL_ORDER_FINAL_PRICE = "orders.final_price";

    public static final String FULL_ORDER_USER_LOGIN = "users.login";
    public static final String FULL_ORDER_USER_PHOTO = "users.photo";
    public static final String FULL_ORDER_USER_PHONE_NUMBER = "users.phone_number";
    public static final String FULL_ORDER_USER_MAIL = "users.mail";

    public static final String ORDER_FEEDBACK_ID = "id";
    public static final String ORDER_FEEDBACK_USER_ID = "id_user";
    public static final String ORDER_FEEDBACK_ORDER_ID = "id_order";
    public static final String ORDER_FEEDBACK_TEXT = "text";
    public static final String ORDER_FEEDBACK_TIME = "time";
    public static final String ORDER_FEEDBACK_MARK = "mark";

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
