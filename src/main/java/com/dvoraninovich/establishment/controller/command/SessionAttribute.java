package com.dvoraninovich.establishment.controller.command;

public final class SessionAttribute {
    public static final String USER = "user";
    public static final String USER_PROFILE = "user_profile";
    public static final String USERS = "users";
    public static final String AUTH_ERROR = "auth_error";
    public static final String REGISTRATION_ERROR = "registration_error";
    public static final String IS_EDITING_PAGE = "is_editing_page";
    public static final String EXCEPTION = "exception";
    public static final String USER_ALREADY_VERIFIED = "user_already_verified";
    public static final String USER_BLOCKED = "user_blocked";
    public static final String INCORRECT_USER_CODE = "incorrect_user_code";
    public static final String INVALID_USER_CODE = "invalid_user_code";
    public static final String EXPIRED_USER_CODE = "expired_user_code";
    public static final String USER_IS_NOT_AUTHENTICATED = "user_is_not_authenticated";
    public static final String USER_IS_NOT_REGISTERED = "user_is_not_registered";
    public static final String VERIFICATION_ERROR = "verification_error";
    public static final String IS_AUTHENTICATED = "is_authenticated";
    public static final String CHANGE_PASSWORD_ERROR = "change_password_error";
    public static final String CHANGE_MAIL_ERROR = "change_mail_error";
    public static final String CHANGE_USER_DATA_ERROR = "change_phone_num_error";
    public static final String CHANGE_PROFILE_PHOTO_ERROR = "change_profile_photo_error";
    public static final String USER_VALIDATION_ERROR = "user_validation_error";
    public static final String IMPOSSIBLE_TO_UPLOAD_USER_PHOTO = "impossible_to_upload_user_photo";

    public static final String DIFFERENT_PASSWORDS = "different_passwords";
    public static final String USER_ALREADY_AUTHENTICATED = "user_already_authenticated";
    public static final String INVALID_LOGIN = "invalid_login";
    public static final String NOT_UNIQUE_LOGIN = "not_unique_login";
    public static final String INVALID_PASSWORD = "invalid_password";
    public static final String INVALID_MAIL = "invalid_mail";
    public static final String NOT_UNIQUE_MAIL = "not_unique_mail";
    public static final String INVALID_PHONE_NUM = "invalid_phone_num";
    public static final String INVALID_CARD_NUM = "invalid_card_num";

    public static final String INVALID_INGREDIENT_NAME = "invalid_ingredient_name";
    public static final String ADD_INGREDIENT_ERROR = "add_ingredient_error";
    public static final String INGREDIENT_VALIDATION_ERROR = "ingredient_validation_error";

    public static final String MIN_POS = "min_pos";
    public static final String MAX_POS = "max_pos";
    public static final String PAGE_ITEMS_AMOUNT = "page_items_amount";
    public static final String TOTAL_AMOUNT = "total_amount";

    public static final String DISH = "dish";
    public static final String DISHES = "dishes";
    public static final String INGREDIENTS = "ingredients";
    public static final String UNUSED_INGREDIENTS = "unused_ingredients";
    public static final String ADD_DISH_ERROR = "add_dish_error";
    public static final String EDIT_DISH_ERROR = "edit_dish_error";
    public static final String INVALID_DISH_NAME = "invalid_dish_name";
    public static final String INVALID_DISH_PRICE = "invalid_dish_price";
    public static final String INVALID_DISH_AMOUNT_GRAMS = "invalid_dish_amount_grams";
    public static final String INVALID_DISH_CALORIES_AMOUNT = "invalid_dish_calories_amount";
    public static final String ADD_DISH_INGREDIENT_ERROR = "add_dish_ingredient_error";
    public static final String REMOVE_DISH_INGREDIENT_ERROR = "remove_dish_ingredient_error";
    public static final String DISH_VALIDATION_ERROR = "dish_validation_error";
    public static final String IMPOSSIBLE_TO_UPLOAD_DISH_PHOTO = "impossible_to_upload_dish_photo";

    public static final String ORDER = "order";
    public static final String ID_ORDER = "id_order";
    public static final String ORDERS = "orders";
    public static final String ORDERS_USERS_MAP = "orders_users_map";
    public static final String ORDER_USER = "order_user";
    public static final String ORDER_STATE = "order_state";
    public static final String ORDER_DISH_LIST_ITEMS = "order_dish_list_items";
    public static final String ORDERS_WITH_USER_INFO = "orders_with_users_info";
    public static final String ORDER_DISHES_MAP = "order_dishes_map";
    public static final String DISHES_IN_BASKET = "dishes_in_basket";
    public static final String ID_DISH_LIST_ITEM = "id_dish_list_item";
    public static final String TOO_MANY_BONUSES = "too_many_bonuses";
    public static final String NOT_ENOUGH_BONUSES = "NOT_ENOUGH_BONUSES";
    public static final String YOU_SHOULD_CHOOSE_SOMETHING = "you_should_buy_something";
    public static final String EDIT_ORDER_ERROR = "edit_order_error";

    public static final String ORDERS_FILTER_ORDER_STATES = "orders_filter_order_states";
    public static final String ORDERS_FILTER_PAYMENT_TYPES = "orders_filter_payment_types";
    public static final String ORDERS_FILTER_MIN_PRICE = "orders_filter_min_price";
    public static final String ORDERS_FILTER_MAX_PRICE = "orders_filter_max_price";

    public static final String USERS_FILTER_USER_STATUSES = "users_filter_user_statuses";
    public static final String USERS_FILTER_USER_ROLES = "users_filter_user_roles";
    public static final String USERS_FILTER_LOGIN = "users_filter_login";
    public static final String USERS_FILTER_PHONE_NUMBER = "users_filter_phone_number";
    public static final String USERS_FILTER_MAIL = "users_filter_mail";
    public static final String USERS_FILTER_CARD_NUMBER = "users_filter_card_number";

    public static final String INVALID_USER_STATUS = "invalid_user_status";
    public static final String INVALID_USER_ROLE = "invalid_user_role";
    public static final String INVALID_ORDER_STATES = "invalid_order_states";
    public static final String INVALID_PAYMENT_TYPES = "invalid_payment_types";
    public static final String INVALID_MIN_PRICE = "invalid_min_price";
    public static final String INVALID_MAX_PRICE = "invalid_max_price";
    public static final String INVALID_FILTER_PARAMETERS = "invalid_filter_parameters";

    private SessionAttribute() {
    }
}
