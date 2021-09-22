package com.dvoraninovich.establishment.controller.command.validator;

import com.dvoraninovich.establishment.controller.command.Router;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static com.dvoraninovich.establishment.controller.command.PagePath.ORDERS_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.INVALID_FILTER_PARAMETERS;

public class OrderValidator {
    private static final Logger logger = LogManager.getLogger(OrderValidator.class);
    private static OrderValidator instance;

    private static final String NAME_REGEXP = "^[A-za-z\\s]{2,30}$";

    private OrderValidator() {
    }

    public static OrderValidator getInstance() {
        if (instance == null) {
            instance = new OrderValidator();
        }
        return instance;
    }

    public HashMap<String, Boolean> validateFilterParameters(String minPriceLine, String maxPriceLine){

        HashMap<String, Boolean> validationMessages = new HashMap<>();
        boolean currentResult;

        if (!minPriceLine.equals("")) {
            BigDecimal minPrice = new BigDecimal(minPriceLine);
            currentResult = minPrice.compareTo(new BigDecimal("0.00")) < 0;
            validationMessages.put(INVALID_MIN_PRICE, !currentResult);
        }

        if (!maxPriceLine.equals("")) {
            BigDecimal maxPrice = new BigDecimal(maxPriceLine);
            currentResult = maxPrice.compareTo(new BigDecimal("0.00")) < 0;
            validationMessages.put(INVALID_MAX_PRICE, !currentResult);
        }

        if (!maxPriceLine.equals("")) {
            BigDecimal minPrice = new BigDecimal(minPriceLine);
            BigDecimal maxPrice = new BigDecimal(maxPriceLine);
            currentResult = maxPrice.compareTo(minPrice) < 0;
            validationMessages.put(INVALID_FILTER_PARAMETERS, !currentResult);
        }

        return validationMessages;
    }
}
