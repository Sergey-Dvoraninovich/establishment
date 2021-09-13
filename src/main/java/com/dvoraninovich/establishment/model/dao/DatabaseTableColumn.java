package com.dvoraninovich.establishment.model.dao;

public final class DatabaseTableColumn {
    //TODO update data to string
    public static final int INGREDIENT_ID = 1;
    public static final int INGREDIENT_NAME = 2;

    //TODO update data to string
//    public static final int DISH_ID = 1;
//    public static final int DISH_PRICE = 2;
//    public static final int DISH_CALORIES = 3;
//    public static final int DISH_AMOUNT_GRAMS = 4;
//    public static final int DISH_AVERAGE_MARK = 5;
//    public static final int DISH_NAME = 6;
//    public static final int IS_DISH_AVAILABLE = 7;
//    public static final int DISH_PHOTO = 8;

    public static final String DISH_ID = "id";
    public static final String DISH_PRICE = "price";
    public static final String DISH_CALORIES = "calories";
    public static final String DISH_AMOUNT_GRAMS = "amount_grams";
    public static final String DISH_NAME = "name";
    public static final String IS_DISH_AVAILABLE = "is_available";
    public static final String DISH_PHOTO = "photo";

    //TODO update data to string
    public static final int DISH_LIST_ITEM_ID = 1;
    public static final int DISH_LIST_ITEM_ID_ORDER = 2;
    public static final int DISH_LIST_ITEM_ID_DISH = 3;
    public static final int DISH_LIST_ITEM_DISH_AMOUNT = 4;
    public static final int DISH_LIST_ITEM_DISH_FINAL_PRICE = 5;

    //TODO update data to string
    public static final int ORDER_ID = 1;
    public static final int ORDER_USER_ID = 2;
    public static final int ORDER_ORDER_STATUS = 3;
    public static final int ORDER_TIME = 4;
    public static final int ORDER_CARD_NUMBER = 5;
    public static final int ORDER_PAYMENT_TYPE = 6;
    public static final int ORDER_FINAL_PRICE = 7;

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

    //TODO update data to string
//    public static final int USER_ID = 1;
//    public static final int USER_LOGIN = 2;
//    public static final int USER_MAIL = 3;
//    public static final int USER_PASSWORD_HASH = 4;
//    public static final int USER_SALT = 5;
//    public static final int USER_STATUS = 6;
//    public static final int USER_ROLE = 7;
//    public static final int USER_CARD_NUMBER = 8;
//    public static final int USER_PHONE_NUMBER = 9;
//    public static final int USER_BONUSES_AMOUNT = 10;
//    public static final int USER_PHOTO = 11;
//    public static final int USER_CODE = 12;
//    public static final int USER_EXPIRATION_CODE_TIME = 13;

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
