package com.dvoraninovich.establishment.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.OrderState.*;
import static com.dvoraninovich.establishment.model.entity.PaymentType.CARD;
import static com.dvoraninovich.establishment.model.entity.PaymentType.CASH;

public class OrderValidatorTest {
    private final String VALID_USER_ID = "10";
    private final String VALID_MIN_PRICE = "4.99";
    private final String VALID_MAX_PRICE = "15.99";
    private final String[] VALID_ORDER_STATES = new String[] {ACTIVE.name(), COMPLETED.name(), EXPIRED.name()};
    private final String[] VALID_PAYMENT_TYPES = new String[] {CASH.name(), CARD.name()};

    private OrderValidator validator = OrderValidator.getInstance();


    @DataProvider(name = "validPaymentTypes")
    public static Object[][] validPaymentTypes() {
        return new Object[][] {{CASH.name()}, {CARD.name()}};
    }
    @Test(dataProvider = "validPaymentTypes")
    public void orderValidatorPaymentTypeTest(String paymentTypeLine) {
        boolean result = validator.validatePaymentType(paymentTypeLine);
        Assert.assertTrue(result);
    }

    @DataProvider(name = "invalidPaymentTypes")
    public static Object[][] invalidPaymentTypes() {
        return new Object[][] {{"BYN"}, {"GOLD"}};
    }
    @Test(dataProvider = "invalidPaymentTypes")
    public void orderValidatorPaymentTypeInvalidTest(String paymentTypeLine) {
        boolean result = validator.validatePaymentType(paymentTypeLine);
        Assert.assertFalse(result);
    }

    @DataProvider(name = "validOrderStates")
    public static Object[][] validOrderStates() {
        return new Object[][] {{IN_CREATION.name()}, {CREATED.name()},
                {ACTIVE.name()}, {COMPLETED.name()}, {EXPIRED.name()}};
    }
    @Test(dataProvider = "validOrderStates")
    public void orderValidatorOrderStateTest(String orderStateLine) {
        boolean result = validator.validateOrderState(orderStateLine);
        Assert.assertTrue(result);
    }

    @DataProvider(name = "invalidOrderStates")
    public static Object[][] invalidOrderStates() {
        return new Object[][] {{"NEW"}, {"DELETED"}};
    }
    @Test(dataProvider = "invalidOrderStates")
    public void orderValidatorOrderStateInvalidTest(String orderStateLine) {
        boolean result = validator.validateOrderState(orderStateLine);
        Assert.assertFalse(result);
    }

    @DataProvider(name = "validUserId")
    public static Object[][] validUserId() {
        return new Object[][] {{Long.toString(12)}};
    }
    @Test(dataProvider = "validUserId")
    public void orderValidatorUserIdTest(String userIdLine) {
        boolean result = validator.validateUserId(userIdLine);
        Assert.assertTrue(result);
    }

    @DataProvider(name = "invalidUserId")
    public static Object[][] invalidUserId() {
        return new Object[][] {{"-44"}, {"44s"}};
    }
    @Test(dataProvider = "invalidUserId")
    public void orderValidatorUserIdInvalidTest(String userIdLine) {
        boolean result = validator.validateUserId(userIdLine);
        Assert.assertFalse(result);
    }

    @Test
    public void orderValidatorFilterTest() {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_USER_ID,
                VALID_MIN_PRICE, VALID_MAX_PRICE, VALID_ORDER_STATES, VALID_PAYMENT_TYPES);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertFalse(containsError);
    }

    @DataProvider(name = "invalidFilterOrderStates")
    public static Object[][] invalidFilterOrderStates() {
        return new Object[][] {{new String[] {"NEW"}}, {new String[] {"NEW", COMPLETED.name(), EXPIRED.name()}}};
    }
    @Test(dataProvider = "invalidFilterOrderStates")
    public void orderValidatorFilterOrderStatesInvalidTest(String[] orderStatesLines) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_USER_ID,
                VALID_MIN_PRICE, VALID_MAX_PRICE, orderStatesLines, VALID_PAYMENT_TYPES);
        boolean containsError = validationMap.get(INVALID_ORDER_STATES);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidFilterPaymentTypes")
    public static Object[][] invalidFilterPaymentTypes() {
        return new Object[][] {{new String[] {"BYN"}}, {new String[] {"BYN", CASH.name()}}};
    }
    @Test(dataProvider = "invalidFilterPaymentTypes")
    public void orderValidatorFilterPaymentTypesInvalidTest(String[] paymentTypesLine) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_USER_ID,
                VALID_MIN_PRICE, VALID_MAX_PRICE, VALID_ORDER_STATES, paymentTypesLine);
        boolean containsError = validationMap.get(INVALID_PAYMENT_TYPES);
        Assert.assertTrue(containsError);
    }

    @DataProvider(name = "invalidFilterPrice")
    public static Object[][] invalidFilterPrice() {
        return new Object[][] {{"19.99", "9.99"}, {"-9.99", "9.99"}, {"19.99", "89.9999"}};
    }
    @Test(dataProvider = "invalidFilterPrice")
    public void orderValidatorFilterPriceTest(String minPriceLine, String maxPriceLine) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(VALID_USER_ID,
                minPriceLine, maxPriceLine, VALID_ORDER_STATES, VALID_PAYMENT_TYPES);
        boolean containsError = validationMap.getOrDefault(INVALID_FILTER_PARAMETERS, false);
        containsError |= validationMap.get(INVALID_MIN_PRICE);
        containsError |= validationMap.get(INVALID_MAX_PRICE);
        Assert.assertTrue(containsError);
    }
}
