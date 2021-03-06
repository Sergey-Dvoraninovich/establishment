package com.dvoraninovich.establishment.controller.command.validator;

import com.dvoraninovich.establishment.model.entity.OrderState;
import com.dvoraninovich.establishment.model.entity.PaymentType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class OrderValidator {
    private static final Logger logger = LogManager.getLogger(OrderValidator.class);
    private static OrderValidator instance;

    private static final String USER_ID_REGEXP = "^\\d{1,19}$";
    private static final String PRICE_REGEXP = "^[+-]?([0-9]{1,4}([.][0-9]{0,2})?|[.][0-9]{1,2})$";
    private static final BigDecimal MIN_PRICE = new BigDecimal("0.00");
    private static final BigDecimal MAX_PRICE = new BigDecimal("9999.99");

    private OrderValidator() {
    }

    public static OrderValidator getInstance() {
        if (instance == null) {
            instance = new OrderValidator();
        }
        return instance;
    }

    public boolean validateOrderState(String orderStateLine) {
        Boolean result = false;
        ArrayList<String> orderStateValues = new ArrayList<>();
        for (OrderState state: OrderState.values()){
            orderStateValues.add(state.name());
        }
        if (orderStateValues.contains(orderStateLine)) {
            result = true;
        }
        return result;
    }

    public boolean validatePaymentType(String paymentTypeLine) {
        Boolean result = false;
        ArrayList<String> paymentTypesValues = new ArrayList<>();
        for (PaymentType type: PaymentType.values()){
            paymentTypesValues.add(type.name());
        }
        if (paymentTypesValues.contains(paymentTypeLine)) {
            result = true;
        }
        return result;
    }

    public boolean validateUserId(String userIdLine) {
        Boolean result = true;
        if (!userIdLine.equals("")) {
            result = Pattern.matches(USER_ID_REGEXP, userIdLine);
        }
        return result;
    }

    public HashMap<String, Boolean> validateFilterParameters(String userIdLine, String minPriceLine, String maxPriceLine,
                                                             String[] orderStatesLines, String[] paymentTypesLine){

        HashMap<String, Boolean> validationMessages = new HashMap<>();
        boolean currentResult;

        try {

            if (!userIdLine.equals("")) {
                currentResult = Pattern.matches(USER_ID_REGEXP, userIdLine);
                validationMessages.put(INVALID_USER_ID, !currentResult);
            }

            if (!minPriceLine.equals("")) {
                currentResult = Pattern.matches(PRICE_REGEXP, minPriceLine);
                if (currentResult) {
                    BigDecimal minPrice = new BigDecimal(minPriceLine);
                    currentResult = minPrice.compareTo(MIN_PRICE) >= 0;
                    currentResult &= minPrice.compareTo(MAX_PRICE) <= 0;
                }
                validationMessages.put(INVALID_MIN_PRICE, !currentResult);
            }

            if (!maxPriceLine.equals("")) {
                currentResult = Pattern.matches(PRICE_REGEXP, maxPriceLine);
                if (currentResult) {
                    BigDecimal maxPrice = new BigDecimal(maxPriceLine);
                    currentResult = maxPrice.compareTo(MIN_PRICE) >= 0;
                    currentResult &= maxPrice.compareTo(MAX_PRICE) <= 0;
                }
                validationMessages.put(INVALID_MAX_PRICE, !currentResult);
            }

            if (Pattern.matches(PRICE_REGEXP, minPriceLine)
                    && Pattern.matches(PRICE_REGEXP, maxPriceLine)) {
                BigDecimal minPrice = new BigDecimal(minPriceLine);
                BigDecimal maxPrice = new BigDecimal(maxPriceLine);
                currentResult = minPrice.compareTo(maxPrice) >= 0;
                validationMessages.put(INVALID_FILTER_PARAMETERS, currentResult);
            }


            currentResult = false;
            ArrayList<String> orderStateValues = new ArrayList<>();
            for (OrderState state : OrderState.values()) {
                orderStateValues.add(state.name());
            }
            for (String line : orderStatesLines) {
                if (!orderStateValues.contains(line)) {
                    currentResult = true;
                }
            }
            validationMessages.put(INVALID_ORDER_STATES, currentResult);

            currentResult = false;
            ArrayList<String> paymentTypesValues = new ArrayList<>();
            for (PaymentType type : PaymentType.values()) {
                paymentTypesValues.add(type.name());
            }
            for (String line : paymentTypesLine) {
                if (!paymentTypesValues.contains(line)) {
                    currentResult = true;
                }
            }
            validationMessages.put(INVALID_PAYMENT_TYPES, currentResult);
        }
        catch (Exception e) {
            logger.info("order filter validation exception: " + e);
            validationMessages.put(ORDER_VALIDATION_ERROR, true);
        }

        return validationMessages;
    }
}
