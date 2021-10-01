package com.dvoraninovich.establishment.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.INVALID_INGREDIENT_NAME;

public class IngredientValidatorTest {
    private IngredientValidator validator = IngredientValidator.getInstance();

    @DataProvider(name = "validNames")
    public static Object[][] validNames() {
        return new Object[][] {{"Carrot"}, {"Cheese"}, {"Milk"}};
    }
    @Test(dataProvider = "validNames")
    public void ingredientValidatorTest(String name) {
        List<String> resultList = validator.validateIngredient(name);
        boolean result = resultList.isEmpty();
        Assert.assertTrue(result);
    }

    @DataProvider(name = "invalidNames")
    public static Object[][] invalidNames() {
        return new Object[][] {{"alert('Carrot')"}, {"Cheese1"}, {"Milk!"}};
    }
    @Test(dataProvider = "invalidNames")
    public void ingredientValidatorInvalidTest(String name) {
        List<String> resultList = validator.validateIngredient(name);
        boolean result = resultList.contains(INVALID_INGREDIENT_NAME);
        Assert.assertTrue(result);
    }
}
