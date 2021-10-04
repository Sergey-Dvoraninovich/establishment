package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.DishDao;
import com.dvoraninovich.establishment.model.dao.IngredientDao;
import com.dvoraninovich.establishment.model.dao.impl.DishDaoImpl;
import com.dvoraninovich.establishment.model.dao.impl.IngredientDaoImpl;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.DishService;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DishServiceImplTest {
    private DishService service = DishServiceImpl.getInstance();
    private List<Ingredient> allIngredients;

    @BeforeClass
    public void init() {
        allIngredients = new ArrayList<>(Arrays.asList(
                Ingredient.builder().setId(1L).setName("Pasta").build(),
                Ingredient.builder().setId(2L).setName("Tomatoes").build(),
                Ingredient.builder().setId(3L).setName("Mozzarella").build(),
                Ingredient.builder().setId(4L).setName("Mushrooms").build(),
                Ingredient.builder().setId(5L).setName("Meat").build()
        ));
    }

    @DataProvider(name = "validDishesData")
    public static Object[][] validDishesData() {
        return new Object[][] {{Dish.builder().setId(1L).build(), new ArrayList<Ingredient>()},
                {Dish.builder().setId(135L).build(), new ArrayList<>(Arrays.asList(
                        Ingredient.builder().setId(1L).setName("Pasta").build(),
                        Ingredient.builder().setId(2L).setName("Tomatoes").build()
                ))}};
    }
    @Test(dataProvider = "validDishesData")
    public void dishServiceUnusedIngredientsTest(Dish dish, List<Ingredient> ingredientsList) {
        DishDao dishDao = DishDaoImpl.getInstance();
        dishDao = Mockito.mock(DishDao.class);
        IngredientDao ingredientDao = IngredientDaoImpl.getInstance();
        ingredientDao = Mockito.mock(IngredientDao.class);
        try {
            Mockito.when(dishDao.findDishIngredients(dish.getId()))
                    .thenReturn(ingredientsList);
            Class serviceClass = DishServiceImpl.class;
            Field dishDaoField = serviceClass.getDeclaredField("dishDao");
            dishDaoField.setAccessible(true);
            dishDaoField.set(service, dishDao);

            Mockito.when(ingredientDao.findAll())
                    .thenReturn(allIngredients.stream().collect(Collectors.toList()));
            Field ingredientDaoField = serviceClass.getDeclaredField("ingredientDao");
            ingredientDaoField.setAccessible(true);
            ingredientDaoField.set(service, ingredientDao);
        } catch (NoSuchFieldException | IllegalAccessException | DaoException e) {
            Assert.fail("Impossible to setup mocks");
        }
        List<Ingredient> unusedIngredients = new ArrayList<>();
        try {
            unusedIngredients = service.findUnusedDishIngredients(dish.getId());
        } catch (ServiceException e) {
            Assert.fail("Service caused exception");
        }
        boolean result = true;
        for (Ingredient dishIngredient: ingredientsList) {
            result &= !unusedIngredients.contains(dishIngredient);
        }
        result &= unusedIngredients.size() + ingredientsList.size() == allIngredients.size();
        Assert.assertTrue(result);
    }
}
