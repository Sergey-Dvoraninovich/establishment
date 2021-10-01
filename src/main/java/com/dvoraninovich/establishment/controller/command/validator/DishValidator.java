package com.dvoraninovich.establishment.controller.command.validator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class DishValidator {
    private static final Logger logger = LogManager.getLogger(DishValidator.class);
    private static DishValidator instance;

    public static final String DISH_AVAILABLE = "AVAILABLE";
    public static final String DISH_DISABLED = "DISABLED";

    private static final String DISH_ID_REGEXP = "^[0-9]+$";
    private static final String NAME_REGEXP = "^[A-za-z\\s]{1,50}$";
    private static final String PRICE_REGEXP = "^[+-]?([0-9]+([.][0-9]{0,2})?|[.][0-9]{1,2})$";
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

    public boolean validateDishId(String dishIdLine) {
        Boolean result = Pattern.matches(DISH_ID_REGEXP, dishIdLine);
        return result;
    }

    public HashMap<String, Boolean> validateDishData(String name, String priceLine,
                                                     String amountGramsLine, String caloriesAmountLine) {

        HashMap<String, Boolean> validationMessages = new HashMap<>();
        boolean currentResult;

        try {

            currentResult = Pattern.matches(NAME_REGEXP, name);
            validationMessages.put(INVALID_DISH_NAME, !currentResult);

            currentResult = Pattern.matches(PRICE_REGEXP, priceLine);
            validationMessages.put(INVALID_DISH_PRICE, !currentResult);
            if (currentResult) {
                BigDecimal price = new BigDecimal(priceLine);
                currentResult = price.compareTo(MIN_PRICE) >= 0;
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

        } catch (Exception e) {
            logger.info("dish validation error: " + e);
            validationMessages.put(DISH_VALIDATION_ERROR, true);
        }

        return validationMessages;
    }

    public HashMap<String, Boolean> validateFilterParameters(String name, String minPriceLine, String maxPriceLine,
                                                             String minCaloriesAmountLine, String maxCaloriesAmountLine,
                                                             String minAmountGramsLine, String maxAmountGramsLine,
                                                             String[] dishStates){

        HashMap<String, Boolean> validationMessages = new HashMap<>();
        boolean currentResult;

        try {

            if (!name.equals("")) {
                currentResult = Pattern.matches(NAME_REGEXP, name);
                validationMessages.put(INVALID_DISH_NAME, !currentResult);
            }

            if (!minPriceLine.equals("")) {
                currentResult = Pattern.matches(PRICE_REGEXP, minPriceLine);
                if (currentResult) {
                    BigDecimal minPrice = new BigDecimal(minPriceLine);
                    currentResult = minPrice.compareTo(MIN_PRICE) >= 0;
                }
                validationMessages.put(INVALID_MIN_PRICE, !currentResult);
            }

            if (!maxPriceLine.equals("")) {
                currentResult = Pattern.matches(PRICE_REGEXP, maxPriceLine);
                if (currentResult) {
                    BigDecimal maxPrice = new BigDecimal(maxPriceLine);
                    currentResult = maxPrice.compareTo(MIN_PRICE) >= 0;
                }
                validationMessages.put(INVALID_MAX_PRICE, !currentResult);
            }

            if (Pattern.matches(PRICE_REGEXP, minPriceLine)
                    && Pattern.matches(PRICE_REGEXP, maxPriceLine)) {
                BigDecimal minPrice = new BigDecimal(minPriceLine);
                BigDecimal maxPrice = new BigDecimal(maxPriceLine);
                currentResult = minPrice.compareTo(maxPrice) <= 0;
                currentResult &= !validationMessages.getOrDefault(INVALID_FILTER_PARAMETERS, false);
                validationMessages.put(INVALID_FILTER_PARAMETERS, !currentResult);
            }

            if (!minCaloriesAmountLine.equals("")) {
                currentResult = Pattern.matches(CALORIES_AMOUNT_REGEXP, minCaloriesAmountLine);
                if (currentResult) {
                    Integer minCaloriesAmount = Integer.valueOf(minCaloriesAmountLine);
                    currentResult = minCaloriesAmount.compareTo(MIN_CALORIES_AMOUNT) >= 0;
                }
                validationMessages.put(INVALID_MIN_CALORIES_AMOUNT, !currentResult);
            }

            if (!maxCaloriesAmountLine.equals("")) {
                currentResult = Pattern.matches(CALORIES_AMOUNT_REGEXP, maxCaloriesAmountLine);
                if (currentResult) {
                    Integer maxCaloriesAmount = Integer.valueOf(maxCaloriesAmountLine);
                    currentResult = maxCaloriesAmount.compareTo(MIN_CALORIES_AMOUNT) >= 0;
                }
                validationMessages.put(INVALID_MAX_CALORIES_AMOUNT, !currentResult);
            }

            if (Pattern.matches(CALORIES_AMOUNT_REGEXP, minCaloriesAmountLine)
                    && Pattern.matches(CALORIES_AMOUNT_REGEXP, maxCaloriesAmountLine)) {
                Integer minPrice = Integer.valueOf(minCaloriesAmountLine);
                Integer maxPrice = Integer.valueOf(maxCaloriesAmountLine);
                currentResult = minPrice.compareTo(maxPrice) <= 0;
                currentResult &= !validationMessages.getOrDefault(INVALID_FILTER_PARAMETERS, false);
                validationMessages.put(INVALID_FILTER_PARAMETERS, !currentResult);
            }

            if (!minAmountGramsLine.equals("")) {
                currentResult = Pattern.matches(AMOUNT_GRAMS_REGEXP, minAmountGramsLine);
                if (currentResult) {
                    Integer minAmountGrams = Integer.valueOf(minAmountGramsLine);
                    currentResult = minAmountGrams.compareTo(MIN_AMOUNT_GRAMS) >= 0;
                }
                validationMessages.put(INVALID_MIN_AMOUNT_GRAMS, !currentResult);
            }

            if (!maxAmountGramsLine.equals("")) {
                currentResult = Pattern.matches(AMOUNT_GRAMS_REGEXP, maxAmountGramsLine);
                if (currentResult) {
                    Integer maxAmountGrams = Integer.valueOf(maxAmountGramsLine);
                    currentResult = maxAmountGrams.compareTo(MIN_AMOUNT_GRAMS) >= 0;
                }
                validationMessages.put(INVALID_MAX_AMOUNT_GRAMS, !currentResult);
            }

            if (Pattern.matches(AMOUNT_GRAMS_REGEXP, minAmountGramsLine)
                    && Pattern.matches(AMOUNT_GRAMS_REGEXP, maxAmountGramsLine)) {
                Integer minAmountGrams = Integer.valueOf(minAmountGramsLine);
                Integer maxAmountGrams = Integer.valueOf(maxAmountGramsLine);
                currentResult = minAmountGrams.compareTo(maxAmountGrams) <= 0;
                currentResult &= !validationMessages.getOrDefault(INVALID_FILTER_PARAMETERS, false);
                validationMessages.put(INVALID_FILTER_PARAMETERS, !currentResult);
            }

            if (dishStates.length != 0) {
                currentResult = false;
                for (String line : dishStates) {
                    if (!line.equals(DISH_AVAILABLE) && !line.equals(DISH_DISABLED)) {
                        currentResult = true;
                    }
                }
                validationMessages.put(INVALID_DISH_STATES, currentResult);
            }

        } catch (Exception e) {
            logger.info("dish filter validation exception: " + e);
            validationMessages.put(DISH_VALIDATION_ERROR, true);
        }

        return validationMessages;
    }
}
