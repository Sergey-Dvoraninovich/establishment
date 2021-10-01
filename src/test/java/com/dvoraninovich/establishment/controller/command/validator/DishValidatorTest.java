package com.dvoraninovich.establishment.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.validator.DishValidator.*;

public class DishValidatorTest {
    private DishValidator validator = getInstance();

    @Test
    public void dishValidatorIdTest() {
        String idLine = Long.toString(12);
        boolean result = validator.validateDishId(idLine);
        Assert.assertTrue(result);
    }

    @Test
    public void dishValidatorIdInvalidTest() {
        String idLine = "12fg";
        boolean result = validator.validateDishId(idLine);
        Assert.assertFalse(result);
    }

    @Test
    public void dishValidatorTest() {
        String name = "Pizza";
        String priceLine = "9.99";
        String amountGramsLine = "500";
        String caloriesAmountLine = "900";
        HashMap<String, Boolean> validationMap = validator.validateDishData(name,
                priceLine, amountGramsLine, caloriesAmountLine);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertFalse(containsError);
    }

    @Test
    public void dishValidatorNameInvalidTest() {
        String name = "$Pizza$";
        String priceLine = "9.99";
        String amountGramsLine = "500";
        String caloriesAmountLine = "900";
        HashMap<String, Boolean> validationMap = validator.validateDishData(name,
                priceLine, amountGramsLine, caloriesAmountLine);
        boolean containsError = validationMap.get(INVALID_DISH_NAME);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorPriceInvalidTest() {
        String name = "Pizza";
        String priceLine = "-9.99";
        String amountGramsLine = "500";
        String caloriesAmountLine = "900";
        HashMap<String, Boolean> validationMap = validator.validateDishData(name,
                priceLine, amountGramsLine, caloriesAmountLine);
        boolean containsError = validationMap.get(INVALID_DISH_PRICE);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorAmountGramsInvalidTest() {
        String name = "Pizza";
        String priceLine = "9.99";
        String amountGramsLine = "500.99";
        String caloriesAmountLine = "900";
        HashMap<String, Boolean> validationMap = validator.validateDishData(name,
                priceLine, amountGramsLine, caloriesAmountLine);
        boolean containsError = validationMap.get(INVALID_DISH_AMOUNT_GRAMS);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorCaloriesAmountInvalidTest() {
        String name = "Pizza";
        String priceLine = "9.99";
        String amountGramsLine = "500";
        String caloriesAmountLine = "-900.99";
        HashMap<String, Boolean> validationMap = validator.validateDishData(name,
                priceLine, amountGramsLine, caloriesAmountLine);
        boolean containsError = validationMap.get(INVALID_DISH_CALORIES_AMOUNT);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorFilterTest() {
        String name = "Pizza";
        String minPriceLine = "9.99";
        String maxPriceLine = "19.99";
        String minCaloriesAmountLine = "900";
        String maxCaloriesAmountLine = "1900";
        String minAmountGramsLine = "500";
        String maxAmountGramsLine = "1500";
        String[] dishStates = new String[] {DISH_DISABLED};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(name, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertFalse(containsError);
    }

    @Test
    public void dishValidatorFilterPriceBoundsInvalidTest() {
        String name = "Pizza";
        String minPriceLine = "29.99";
        String maxPriceLine = "19.99";
        String minCaloriesAmountLine = "900";
        String maxCaloriesAmountLine = "1900";
        String minAmountGramsLine = "500";
        String maxAmountGramsLine = "1500";
        String[] dishStates = new String[] {DISH_DISABLED};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(name, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
        boolean containsError = validationMap.get(INVALID_FILTER_PARAMETERS);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorFilterPriceInvalidTest() {
        String name = "Pizza";
        String minPriceLine = "29.96669";
        String maxPriceLine = "-19.99";
        String minCaloriesAmountLine = "900";
        String maxCaloriesAmountLine = "1900";
        String minAmountGramsLine = "500";
        String maxAmountGramsLine = "1500";
        String[] dishStates = new String[] {DISH_DISABLED};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(name, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
        boolean containsError = validationMap.get(INVALID_MIN_PRICE);
        containsError &= validationMap.get(INVALID_MAX_PRICE);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorFilterCaloriesAmountBoundsInvalidTest() {
        String name = "Pizza";
        String minPriceLine = "9.99";
        String maxPriceLine = "19.99";
        String minCaloriesAmountLine = "1900";
        String maxCaloriesAmountLine = "900";
        String minAmountGramsLine = "500";
        String maxAmountGramsLine = "1500";
        String[] dishStates = new String[] {DISH_DISABLED};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(name, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
        boolean containsError = validationMap.get(INVALID_FILTER_PARAMETERS);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorFilterCaloriesAmountInvalidTest() {
        String name = "Pizza";
        String minPriceLine = "9.99";
        String maxPriceLine = "19.99";
        String minCaloriesAmountLine = "-1900";
        String maxCaloriesAmountLine = "900.897";
        String minAmountGramsLine = "500";
        String maxAmountGramsLine = "1500";
        String[] dishStates = new String[] {DISH_DISABLED};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(name, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
        boolean containsError = validationMap.get(INVALID_MIN_CALORIES_AMOUNT);
        containsError &= validationMap.get(INVALID_MAX_CALORIES_AMOUNT);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorFilterAmountGramsBoundsInvalidTest() {
        String name = "Pizza";
        String minPriceLine = "9.99";
        String maxPriceLine = "19.99";
        String minCaloriesAmountLine = "1900";
        String maxCaloriesAmountLine = "2900";
        String minAmountGramsLine = "2500";
        String maxAmountGramsLine = "1500";
        String[] dishStates = new String[] {DISH_DISABLED};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(name, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
        boolean containsError = validationMap.get(INVALID_FILTER_PARAMETERS);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorFilterAmountGramsInvalidTest() {
        String name = "Pizza";
        String minPriceLine = "9.99";
        String maxPriceLine = "19.99";
        String minCaloriesAmountLine = "1900";
        String maxCaloriesAmountLine = "2900";
        String minAmountGramsLine = "-2500";
        String maxAmountGramsLine = "1500d";
        String[] dishStates = new String[] {DISH_DISABLED};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(name, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
        boolean containsError = validationMap.get(INVALID_MIN_AMOUNT_GRAMS);
        containsError &= validationMap.get(INVALID_MAX_AMOUNT_GRAMS);
        Assert.assertTrue(containsError);
    }

    @Test
    public void dishValidatorFilterDishStatesBoundsInvalidTest() {
        String name = "Pizza";
        String minPriceLine = "9.99";
        String maxPriceLine = "19.99";
        String minCaloriesAmountLine = "1900";
        String maxCaloriesAmountLine = "2900";
        String minAmountGramsLine = "2500";
        String maxAmountGramsLine = "1500";
        String[] dishStates = new String[] {"NEW"};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(name, minPriceLine, maxPriceLine,
                minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
        boolean containsError = validationMap.get(INVALID_DISH_STATES);
        Assert.assertTrue(containsError);
    }
}
