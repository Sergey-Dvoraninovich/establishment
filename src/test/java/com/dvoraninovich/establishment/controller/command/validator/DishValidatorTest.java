package com.dvoraninovich.establishment.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.validator.DishValidator.*;

public class DishValidatorTest {
    private final String VALID_NAME = "Pizza";
    private final String VALID_PRICE = "9.99";
    private final String VALID_MIN_PRICE = "9.99";
    private final String VALID_MAX_PRICE = "19.99";
    private final String VALID_AMOUNT_GRAMS = "500";
    private final String VALID_MIN_AMOUNT_GRAMS = "500";
    private final String VALID_MAX_AMOUNT_GRAMS = "1500";
    private final String VALID_CALORIES_AMOUNT = "900";
    private final String VALID_MIN_CALORIES_AMOUNT = "900";
    private final String VALID_MAX_CALORIES_AMOUNT = "1900";
    private final String[] VALID_DISH_STATES = new String[] {DISH_DISABLED};;

    private DishValidator validator = getInstance();

    @DataProvider(name = "validId")
    public static Object[][] validId() {
        return new Object[][] {{"1"}, {"37"}};
    }
    @Test(dataProvider = "validId")
    public void dishValidatorIdTest(String idLine) {
        boolean result = validator.validateDishId(idLine);
        Assert.assertTrue(result);
    }

    @DataProvider(name = "invalidId")
    public static Object[][] invalidId() {
        return new Object[][] {{"-1"}, {"37fv"}};
    }
    @Test(dataProvider = "invalidId")
    public void dishValidatorIdInvalidTest(String idLine) {
        boolean result = validator.validateDishId(idLine);
        Assert.assertFalse(result);
    }

    @Test
    public void dishValidatorTest() {
        HashMap<String, Boolean> validationMap = validator.validateDishData(VALID_NAME,
                VALID_PRICE, VALID_AMOUNT_GRAMS, VALID_CALORIES_AMOUNT);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertFalse(containsError);
    }

    @DataProvider(name = "invalidNames")
    public static Object[][] invalidNames() {
        return new Object[][] {{"$Pizza$"}, {"Pizza1"}};
    }
    @Test(dataProvider = "invalidNames")
    public void dishValidatorNameInvalidTest(String name) {
        HashMap<String, Boolean> validationMap = validator.validateDishData(name,
                VALID_PRICE, VALID_AMOUNT_GRAMS, VALID_CALORIES_AMOUNT);
        boolean containsError = validationMap.get(INVALID_DISH_NAME);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidPrices")
    public static Object[][] invalidPrices() {
        return new Object[][] {{"-9.99"}, {"9.9999"}};
    }
    @Test(dataProvider = "invalidPrices")
    public void dishValidatorPriceInvalidTest(String priceLine) {
        HashMap<String, Boolean> validationMap = validator.validateDishData(VALID_NAME,
                priceLine, VALID_AMOUNT_GRAMS, VALID_CALORIES_AMOUNT);
        boolean containsError = validationMap.get(INVALID_DISH_PRICE);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidAmountGrams")
    public static Object[][] invalidAmountGrams() {
        return new Object[][] {{"559.99"}, {"-100"}};
    }
    @Test(dataProvider = "invalidAmountGrams")
    public void dishValidatorAmountGramsInvalidTest(String amountGramsLine) {
        HashMap<String, Boolean> validationMap = validator.validateDishData(VALID_NAME,
                VALID_PRICE, amountGramsLine, VALID_CALORIES_AMOUNT);
        boolean containsError = validationMap.get(INVALID_DISH_AMOUNT_GRAMS);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidCaloriesAmount")
    public static Object[][] invalidCaloriesAmount() {
        return new Object[][] {{"559.99"}, {"-100"}};
    }
    @Test(dataProvider = "invalidCaloriesAmount")
    public void dishValidatorCaloriesAmountInvalidTest(String caloriesAmountLine) {
        HashMap<String, Boolean> validationMap = validator.validateDishData(VALID_NAME,
                VALID_PRICE, VALID_AMOUNT_GRAMS, caloriesAmountLine);
        boolean containsError = validationMap.get(INVALID_DISH_CALORIES_AMOUNT);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorFilterTest() {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_NAME,
                VALID_MIN_PRICE, VALID_MAX_PRICE, VALID_MIN_CALORIES_AMOUNT, VALID_MAX_CALORIES_AMOUNT,
                VALID_MIN_AMOUNT_GRAMS, VALID_MAX_AMOUNT_GRAMS, VALID_DISH_STATES);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertFalse(containsError);
    }

    @DataProvider(name = "invalidPriceBounds")
    public static Object[][] invalidPriceBounds() {
        return new Object[][] {{"29.99", "19.99"}};
    }
    @Test(dataProvider = "invalidPriceBounds")
    public void dishValidatorFilterPriceBoundsInvalidTest(String minPriceLine, String maxPriceLine) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_NAME,
                minPriceLine, maxPriceLine, VALID_MIN_CALORIES_AMOUNT, VALID_MAX_CALORIES_AMOUNT,
                VALID_MIN_AMOUNT_GRAMS, VALID_MAX_AMOUNT_GRAMS, VALID_DISH_STATES);
        boolean containsError = validationMap.get(INVALID_FILTER_PARAMETERS);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidPrice")
    public static Object[][] invalidPrice() {
        return new Object[][] {{"29.9945", "-19.99"}};
    }
    @Test(dataProvider = "invalidPrice")
    public void dishValidatorFilterPriceInvalidTest(String minPriceLine, String maxPriceLine) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_NAME,
                minPriceLine, maxPriceLine, VALID_MIN_CALORIES_AMOUNT, VALID_MAX_CALORIES_AMOUNT,
                VALID_MIN_AMOUNT_GRAMS, VALID_MAX_AMOUNT_GRAMS, VALID_DISH_STATES);
        boolean containsError = validationMap.get(INVALID_MIN_PRICE);
        containsError &= validationMap.get(INVALID_MAX_PRICE);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidFilterCaloriesAmountBounds")
    public static Object[][] invalidFilterCaloriesAmountBounds() {
        return new Object[][] {{"1900", "900"}};
    }
    @Test(dataProvider = "invalidFilterCaloriesAmountBounds")
    public void dishValidatorFilterCaloriesAmountBoundsInvalidTest(String minCaloriesAmountLine, String maxCaloriesAmountLine) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_NAME,
                VALID_MIN_PRICE, VALID_MAX_PRICE, minCaloriesAmountLine, maxCaloriesAmountLine,
                VALID_MIN_AMOUNT_GRAMS, VALID_MAX_AMOUNT_GRAMS, VALID_DISH_STATES);
        boolean containsError = validationMap.get(INVALID_FILTER_PARAMETERS);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidFilterCaloriesAmount")
    public static Object[][] invalidFilterCaloriesAmount() {
        return new Object[][] {{"-1900", "19.99"}};
    }
    @Test(dataProvider = "invalidFilterCaloriesAmount")
    public void dishValidatorFilterCaloriesAmountInvalidTest(String minCaloriesAmountLine, String maxCaloriesAmountLine) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_NAME,
                VALID_MIN_PRICE, VALID_MAX_PRICE, minCaloriesAmountLine, maxCaloriesAmountLine,
                VALID_MIN_AMOUNT_GRAMS, VALID_MAX_AMOUNT_GRAMS, VALID_DISH_STATES);
        boolean containsError = validationMap.get(INVALID_MIN_CALORIES_AMOUNT);
        containsError &= validationMap.get(INVALID_MAX_CALORIES_AMOUNT);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidFilterAmountGramsBounds")
    public static Object[][] invalidFilterAmountGramsBounds() {
        return new Object[][] {{"1900", "900"}};
    }
    @Test(dataProvider = "invalidFilterAmountGramsBounds")
    public void dishValidatorFilterAmountGramsBoundsInvalidTest(String minAmountGramsLine, String maxAmountGramsLine) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_NAME,
                VALID_MIN_PRICE, VALID_MAX_PRICE, VALID_MIN_CALORIES_AMOUNT, VALID_MAX_CALORIES_AMOUNT,
                minAmountGramsLine, maxAmountGramsLine, VALID_DISH_STATES);
        boolean containsError = validationMap.get(INVALID_FILTER_PARAMETERS);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidFilterAmountGrams")
    public static Object[][] invalidFilterAmountGrams() {
        return new Object[][] {{"-900", "90.0"}, {"-900b", "90.0!"}};
    }
    @Test(dataProvider = "invalidFilterAmountGrams")
    public void dishValidatorFilterAmountGramsInvalidTest(String minAmountGramsLine, String maxAmountGramsLine) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_NAME,
                VALID_MIN_PRICE, VALID_MAX_PRICE, VALID_MIN_CALORIES_AMOUNT, VALID_MAX_CALORIES_AMOUNT,
                minAmountGramsLine, maxAmountGramsLine, VALID_DISH_STATES);
        boolean containsError = validationMap.get(INVALID_MIN_AMOUNT_GRAMS);
        containsError &= validationMap.get(INVALID_MAX_AMOUNT_GRAMS);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidFilterDishStates")
    public static Object[][] invalidFilterDishStates() {
        return new Object[][] {{new String[]{"NEW"}}, {new String[]{"NEW", DISH_DISABLED}}};
    }
    @Test(dataProvider = "invalidFilterDishStates")
    public void dishValidatorFilterDishStatesBoundsInvalidTest(String[] dishStates) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_NAME,
                VALID_MIN_PRICE, VALID_MAX_PRICE, VALID_MIN_CALORIES_AMOUNT, VALID_MAX_CALORIES_AMOUNT,
                VALID_MIN_AMOUNT_GRAMS, VALID_MAX_AMOUNT_GRAMS, dishStates);
        boolean containsError = validationMap.get(INVALID_DISH_STATES);
        Assert.assertTrue(containsError);
    }
}
