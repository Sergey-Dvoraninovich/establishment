package com.dvoraninovich.establishment.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.INVALID_INGREDIENT_NAME;

public class IngredientValidatorTest {
    private static final String VALID_NAME = "Carrot";
    private static final String INVALID_NAME = "alert('Carrot')";
    private IngredientValidator validator = IngredientValidator.getInstance();

    @Test
    public void ingredientValidatorTest() {
        List<String> resultList = validator.validateIngredient(VALID_NAME);
        Boolean result = resultList.isEmpty();
        Assert.assertTrue(result);
    }

    @Test
    public void ingredientValidatorInvalidTest() {
        List<String> resultList = validator.validateIngredient(INVALID_NAME);
        Boolean result = resultList.contains(INVALID_INGREDIENT_NAME);
        Assert.assertTrue(result);
    }
}
