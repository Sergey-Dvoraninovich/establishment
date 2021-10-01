package com.dvoraninovich.establishment.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.INVALID_INGREDIENT_NAME;

public class IngredientValidatorTest {
    private IngredientValidator validator = IngredientValidator.getInstance();

    @Test
    public void ingredientValidatorTest() {
        String name = "Carrot";
        List<String> resultList = validator.validateIngredient(name);
        boolean result = resultList.isEmpty();
        Assert.assertTrue(result);
    }

    @Test
    public void ingredientValidatorInvalidTest() {
        String name = "alert('Carrot')";
        List<String> resultList = validator.validateIngredient(name);
        boolean result = resultList.contains(INVALID_INGREDIENT_NAME);
        Assert.assertTrue(result);
    }
}
