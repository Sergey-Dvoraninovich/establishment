package com.dvoraninovich.establishment.controller.command;

public final class SessionAttribute {
    public static final String USER = "user";
    public static final String AUTH_ERROR = "auth_error";
    public static final String REGISTRATION_ERROR = "registration_error";
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
    public static final String CHANGE_PHONE_NUM_ERROR = "change_phone_num_error";
    public static final String CHANGE_CARD_NUM_ERROR = "change_card_num_error";
    public static final String CHANGE_PROFILE_PHOTO_ERROR = "change_profile_photo_error";
    public static final String USER_VALIDATION_ERROR = "user_validation_error";

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

    public static final String DISH = "dish";
    public static final String INGREDIENTS = "ingredients";
    public static final String ADD_DISH_ERROR = "add_dish_error";
    public static final String EDIT_DISH_ERROR = "edit_dish_error";
    public static final String INVALID_DISH_NAME = "invalid_dish_name";
    public static final String INVALID_DISH_PRICE = "invalid_dish_price";
    public static final String INVALID_DISH_AMOUNT_GRAMS = "invalid_dish_amount_grams";
    public static final String INVALID_DISH_CALORIES_AMOUNT = "invalid_dish_calories_amount";
    public static final String ADD_DISH_INGREDIENT_ERROR = "add_dish_ingredient_error";
    public static final String REMOVE_DISH_INGREDIENT_ERROR = "remove_dish_ingredient_error";
    public static final String DISH_VALIDATION_ERROR = "dish_validation_error";

    public static final String ORDERS_WITH_USER_INFO = "orders_with_users_info";


    private SessionAttribute() {
    }
}
