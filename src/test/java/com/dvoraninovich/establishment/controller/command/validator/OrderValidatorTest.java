package com.dvoraninovich.establishment.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.OrderState.*;
import static com.dvoraninovich.establishment.model.entity.PaymentType.CARD;
import static com.dvoraninovich.establishment.model.entity.PaymentType.CASH;

public class OrderValidatorTest {
    private OrderValidator validator = OrderValidator.getInstance();

    @Test
    public void orderValidatorPaymentTypeTest() {
        String paymentTypeLine = CASH.toString();
        boolean result = validator.validatePaymentType(paymentTypeLine);
        Assert.assertTrue(result);
    }

    @Test
    public void orderValidatorPaymentTypeInvalidTest() {
        String paymentTypeLine = "BYN";
        boolean result = validator.validatePaymentType(paymentTypeLine);
        Assert.assertFalse(result);
    }

    @Test
    public void orderValidatorOrderStateTest() {
        String orderStateLine = COMPLETED.toString();
        boolean result = validator.validateOrderState(orderStateLine);
        Assert.assertTrue(result);
    }

    @Test
    public void orderValidatorOrderStateInvalidTest() {
        String orderStateLine = "NEW";
        boolean result = validator.validateOrderState(orderStateLine);
        Assert.assertFalse(result);
    }

    @Test
    public void orderValidatorUserIdTest() {
        String userIdLine = Long.toString(12);
        boolean result = validator.validateUserId(userIdLine);
        Assert.assertTrue(result);
    }

    @Test
    public void orderValidatorUserIdInvalidTest() {
        String userIdLine = "-44";
        boolean result = validator.validateUserId(userIdLine);
        Assert.assertFalse(result);
    }

    @Test
    public void orderValidatorFilterTest() {
        String userIdLine = "10";
        String minPriceLine = "4.99";
        String maxPriceLine = "15.99";
        String[] orderStatesLines = new String[] {COMPLETED.name(), ACTIVE.name()};
        String[] paymentTypesLine = new String[] {CASH.name(), CARD.name()};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(userIdLine,
                minPriceLine, maxPriceLine, orderStatesLines, paymentTypesLine);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertFalse(containsError);
    }

    @Test
    public void orderValidatorFilterOrderStatesInvalidTest() {
        String userIdLine = "10";
        String minPriceLine = "4.99";
        String maxPriceLine = "15.99";
        String[] orderStatesLines = new String[] {"NEW", ACTIVE.name()};
        String[] paymentTypesLine = new String[] {CASH.name(), CARD.name()};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(userIdLine,
                minPriceLine, maxPriceLine, orderStatesLines, paymentTypesLine);
        boolean containsError = validationMap.get(INVALID_ORDER_STATES);
        Assert.assertTrue(containsError);
    }

    @Test
    public void orderValidatorFilterPaymentTypesInvalidTest() {
        String userIdLine = "10";
        String minPriceLine = "4.99";
        String maxPriceLine = "15.99";
        String[] orderStatesLines = new String[] {ACTIVE.name()};
        String[] paymentTypesLine = new String[] {"BYN", CARD.name()};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(userIdLine,
                minPriceLine, maxPriceLine, orderStatesLines, paymentTypesLine);
        boolean containsError = validationMap.get(INVALID_PAYMENT_TYPES);
        Assert.assertTrue(containsError);
    }

    @Test
    public void orderValidatorFilterBoundsTest() {
        String userIdLine = "";
        String minPriceLine = "4.99";
        String maxPriceLine = "15.99";
        String[] orderStatesLines = new String[] {COMPLETED.name(), ACTIVE.name()};
        String[] paymentTypesLine = new String[] {CASH.name(), CARD.name()};
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(userIdLine,
                minPriceLine, maxPriceLine, orderStatesLines, paymentTypesLine);
        boolean containsError = validationMap.get(INVALID_FILTER_PARAMETERS);
        Assert.assertFalse(containsError);
    }

    @Test
    public void orderValidatorFilterBoundsInvalidTest() {
        String userIdLine = "";
        String minPriceLine = "114.99";
        String maxPriceLine = "15.99";
        String[] orderStatesLines = new String[] {COMPLETED.name(), ACTIVE.name()};
        String[] paymentTypesLine = new String[0];
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(userIdLine,
                minPriceLine, maxPriceLine, orderStatesLines, paymentTypesLine);
        boolean containsError = validationMap.get(INVALID_FILTER_PARAMETERS);
        Assert.assertTrue(containsError);
    }
}
