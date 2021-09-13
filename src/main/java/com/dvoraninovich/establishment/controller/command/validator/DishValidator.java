package com.dvoraninovich.establishment.controller.command.validator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.NOT_UNIQUE_LOGIN;

public class DishValidator {
    private static final Logger logger = LogManager.getLogger(DishValidator.class);
    private static DishValidator instance;

    private static final String NAME_REGEXP = "^[A-za-z]+$";
    private static final String PRICE_REGEXP = "^[0-9]*[.,]?[0-9]{0,2}$";
    private static final String AMOUNT_GRAMS_REGEXP = "^[0-9]+$";
    private static final String CALORIES_AMOUNT_REGEXP = "^[0-9]+$";
    private static final BigDecimal MIN_PRICE = new BigDecimal("0.00");
    private static final Integer MIN_AMOUNT_GRAMS = 0;
    private static final Integer MIN_CALORIES_AMOUNT = 0;

    private DishValidator() {
    }

    public static DishValidator getInstance() {
        if (instance == null) {
            instance = new DishValidator();
        }
        return instance;
    }

    public boolean validateName(String name){
        boolean result;
        result = Pattern.matches(NAME_REGEXP, name);
        return result;
    }

    public boolean validatePrice(String priceLine){
        boolean result = Pattern.matches(PRICE_REGEXP, priceLine);
        if (result) {
            BigDecimal price = new BigDecimal(priceLine);
            result = price.compareTo(MIN_PRICE) == 1;
        }
        return result;
    }

    public boolean validateAmountGrams(String amountGramsLine){
        boolean result = Pattern.matches(AMOUNT_GRAMS_REGEXP, amountGramsLine);
        if (result) {
            Integer amountGrams = new Integer(amountGramsLine);
            result = amountGrams > MIN_AMOUNT_GRAMS;
        }
        return result;
    }

    public boolean validateCaloriesAmount(String caloriesAmountLine){
        boolean result = Pattern.matches(CALORIES_AMOUNT_REGEXP, caloriesAmountLine);
        if (result) {
            Integer caloriesAmount = new Integer(caloriesAmountLine);
            result = caloriesAmount > MIN_CALORIES_AMOUNT;
        }
        return result;
    }

    public HashMap<String, Boolean> validateDishData(String name, String priceLine,
                                                     String amountGramsLine, String caloriesAmountLine) {

        HashMap<String, Boolean> validationMessages = new HashMap<>();
        boolean currentResult;

        currentResult = Pattern.matches(NAME_REGEXP, name);
        validationMessages.put(INVALID_DISH_NAME, !currentResult);

        currentResult = Pattern.matches(PRICE_REGEXP, priceLine);
        validationMessages.put(INVALID_DISH_PRICE, !currentResult);
        if (currentResult) {
            BigDecimal price = new BigDecimal(priceLine);
            currentResult = price.compareTo(MIN_PRICE) == 1;
        }
        validationMessages.put(INVALID_DISH_PRICE, !currentResult);

        currentResult = Pattern.matches(AMOUNT_GRAMS_REGEXP, amountGramsLine);
        if (currentResult) {
            Integer amountGrams = new Integer(amountGramsLine);
            currentResult = amountGrams > MIN_AMOUNT_GRAMS;
        }
        validationMessages.put(INVALID_DISH_AMOUNT_GRAMS, !currentResult);

        currentResult = Pattern.matches(CALORIES_AMOUNT_REGEXP, caloriesAmountLine);
        if (currentResult) {
            Integer caloriesAmount = new Integer(caloriesAmountLine);
            currentResult = caloriesAmount > MIN_CALORIES_AMOUNT;
        }
        validationMessages.put(INVALID_DISH_CALORIES_AMOUNT, !currentResult);

        return validationMessages;
    }

//    public List<String> validateOrderData(String name, String priceLine,
//                                         String amountGramsLine, String caloriesAmountLine){
//
//        List<String> validationMessages = new ArrayList<>();
//        boolean currentResult;
//
//        currentResult = Pattern.matches(NAME_REGEXP, name);
//        if (!currentResult) {
//            validationMessages.add(INVALID_DISH_NAME);
//        }
//
//        currentResult = Pattern.matches(PRICE_REGEXP, priceLine);
//        if (currentResult) {
//            BigDecimal price = new BigDecimal(priceLine);
//            currentResult = price.compareTo(MIN_PRICE) == 1;
//        }
//        if (!currentResult) {
//            validationMessages.add(INVALID_DISH_PRICE);
//        }
//
//        currentResult = Pattern.matches(AMOUNT_GRAMS_REGEXP, amountGramsLine);
//        if (currentResult) {
//            Integer amountGrams = new Integer(amountGramsLine);
//            currentResult = amountGrams > MIN_AMOUNT_GRAMS;
//        }
//        if (!currentResult) {
//            validationMessages.add(INVALID_DISH_AMOUNT_GRAMS);
//        }
//
//        currentResult = Pattern.matches(CALORIES_AMOUNT_REGEXP, caloriesAmountLine);
//        if (currentResult) {
//            Integer caloriesAmount = new Integer(caloriesAmountLine);
//            currentResult = caloriesAmount > MIN_CALORIES_AMOUNT;
//        }
//        if (!currentResult) {
//            validationMessages.add(INVALID_DISH_CALORIES_AMOUNT);
//        }
//
//        return validationMessages;
//    }
}
