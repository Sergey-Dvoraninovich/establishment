package com.dvoraninovich.establishment.controller.command.validator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.NOT_UNIQUE_LOGIN;

public class IngredientValidator {
    private static final Logger logger = LogManager.getLogger(IngredientValidator.class);
    private static IngredientValidator instance;

    private static final String NAME_REGEXP = "^[A-za-z\\s]{2,30}$";

    private IngredientValidator() {
    }

    public static IngredientValidator getInstance() {
        if (instance == null) {
            instance = new IngredientValidator();
        }
        return instance;
    }

    public List<String> validateIngredient(String name){

        List<String> validationMessages = new ArrayList<>();
        boolean currentResult;

        try {
            name = name != null ? name : "";
            currentResult = Pattern.matches(NAME_REGEXP, name);
            if (!currentResult) {
                validationMessages.add(INVALID_INGREDIENT_NAME);
            }

        } catch (Exception e) {
            logger.info("ingredient validation error: " + e);
            validationMessages.add(INGREDIENT_VALIDATION_ERROR);
        }

        return validationMessages;
    }
}
