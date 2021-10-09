package com.dvoraninovich.establishment.controller.command.validator;

import com.dvoraninovich.establishment.model.entity.Role;
import com.dvoraninovich.establishment.model.entity.UserStatus;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class UserValidator {
    private static final Logger logger = LogManager.getLogger(UserValidator.class);
    private static UserValidator instance;
    private UserService service;

    private static final String LOGIN_REGEXP = "^[A-Za-z_]{3,25}$";
    private static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$";
    private static final String MAIL_REGEXP = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String PHONE_NUM_REGEXP = "^\\+\\d{12}$";
    private static final String CARD_NUM_REGEXP = "^\\d{16}$";
    private static final String BONUSES_AMOUNT_REGEXP = "^\\d{1,7}$";

    private static final String FILTER_LOGIN_REGEXP = "^[A-Za-z_]{1,25}$";
    private static final String FILTER_MAIL_REGEXP = "^[a-z0-9_.@-]+$";
    private static final String FILTER_PHONE_NUM_REGEXP = "^[+]{0,1}[\\d]{1,12}$";
    private static final String FILTER_CARD_NUM_REGEXP = "^\\d{1,16}$";

    private UserValidator() {
    }

    public static UserValidator getInstance() {
        if (instance == null) {
            instance = new UserValidator();
            instance.service = UserServiceImpl.getInstance();
        }
        return instance;
    }

    public static UserValidator getInstance(UserService service) {
        if (instance == null) {
            instance = new UserValidator();
        }
        instance.service = service;
        return instance;
    }

    public boolean validateLogin(String login){
        boolean result;
        result = Pattern.matches(LOGIN_REGEXP, login);
        return result;
    }

    public boolean validatePassword(String password){
        boolean result;
        result = Pattern.matches(PASSWORD_REGEXP, password);
        return result;
    }

    public boolean validatePhoneNum(String phoneNum){
        boolean result;
        result = Pattern.matches(PHONE_NUM_REGEXP, phoneNum);
        return result;
    }

    public boolean validateCardNum(String cardNum){
        boolean result;
        result = Pattern.matches(CARD_NUM_REGEXP, cardNum);
        return result;
    }

    public boolean validateMail(String mail){
        boolean result;
        result = Pattern.matches(MAIL_REGEXP, mail);
        return result;
    }

    public boolean validateUserStatus(String status) {
        boolean currentResult = false;
        ArrayList<String> userStatusesValues = new ArrayList<>();
        for (UserStatus state: UserStatus.values()){
            userStatusesValues.add(state.name());
        }
        if (userStatusesValues.contains(status)) {
            currentResult = true;
        }
        return currentResult;
    }

    public boolean validateBonusesAmount(String bonusesAmountLine){
        boolean result;
        result = Pattern.matches(BONUSES_AMOUNT_REGEXP, bonusesAmountLine);
        if (result) {
            BigDecimal minPrice = new BigDecimal(bonusesAmountLine);
            result = minPrice.compareTo(new BigDecimal("0")) >= 0;
        }
        return result;
    }

    public HashMap<String, Boolean> validateUserData(String login, String password, String mail,
                                    String phoneNum, String cardNum){

        HashMap<String, Boolean> validationMessages = new HashMap<>();
        boolean currentResult;

        try {
            currentResult = Pattern.matches(LOGIN_REGEXP, login);
            validationMessages.put(INVALID_LOGIN, !currentResult);

            currentResult = Pattern.matches(PASSWORD_REGEXP, password);
            validationMessages.put(INVALID_PASSWORD, !currentResult);

            currentResult = Pattern.matches(MAIL_REGEXP, mail);
            validationMessages.put(INVALID_MAIL, !currentResult);

            currentResult = Pattern.matches(PHONE_NUM_REGEXP, phoneNum);
            validationMessages.put(INVALID_PHONE_NUM, !currentResult);

            currentResult = Pattern.matches(CARD_NUM_REGEXP, cardNum);
            validationMessages.put(INVALID_CARD_NUM, !currentResult);

            currentResult = service.isLoginUnique(login);
            validationMessages.put(NOT_UNIQUE_LOGIN, !currentResult);
        }
        catch (Exception e) {
            logger.info("user validation exception: " + e);
            validationMessages.put(USER_VALIDATION_ERROR, true);
        }

        return validationMessages;
    }

    public HashMap<String, Boolean> validateFilterParameters(String login, String mail, String phoneNumber, String cardNumber,
                                                             String[] userStatusesLines, String[] userRolesLine){

        HashMap<String, Boolean> validationMessages = new HashMap<>();
        boolean currentResult;

        try {
            if (!login.equals("")) {
                currentResult = Pattern.matches(FILTER_LOGIN_REGEXP, login);
                validationMessages.put(INVALID_LOGIN, !currentResult);
            }

            if (!mail.equals("")) {
                currentResult = Pattern.matches(FILTER_MAIL_REGEXP, mail);
                validationMessages.put(INVALID_MAIL, !currentResult);
            }

            if (!phoneNumber.equals("")) {
                currentResult = Pattern.matches(FILTER_PHONE_NUM_REGEXP, phoneNumber);
                validationMessages.put(INVALID_PHONE_NUM, !currentResult);
            }

            if (!cardNumber.equals("")) {
                currentResult = Pattern.matches(FILTER_CARD_NUM_REGEXP, cardNumber);
                validationMessages.put(INVALID_CARD_NUM, !currentResult);
            }


            currentResult = false;
            ArrayList<String> userStatusesValues = new ArrayList<>();
            for (UserStatus state : UserStatus.values()) {
                userStatusesValues.add(state.name());
            }
            for (String line : userStatusesLines) {
                if (!userStatusesValues.contains(line)) {
                    currentResult = true;
                }
            }
            validationMessages.put(INVALID_USER_STATUS, currentResult);

            currentResult = false;
            ArrayList<String> userRolesValues = new ArrayList<>();
            for (Role type : Role.values()) {
                userRolesValues.add(type.name());
            }
            for (String line : userRolesLine) {
                if (!userRolesValues.contains(line)) {
                    currentResult = true;
                }
            }
            validationMessages.put(INVALID_USER_ROLE, currentResult);
        }
        catch (Exception e) {
            logger.info("user filter validation exception: " + e);
            validationMessages.put(USER_VALIDATION_ERROR, true);
        }

        return validationMessages;
    }

}
