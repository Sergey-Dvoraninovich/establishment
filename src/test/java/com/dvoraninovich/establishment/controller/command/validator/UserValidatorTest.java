package com.dvoraninovich.establishment.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.util.HashMap;

import static com.dvoraninovich.establishment.model.entity.Role.*;
import static com.dvoraninovich.establishment.model.entity.UserStatus.*;

public class UserValidatorTest {

    private UserValidator validator = UserValidator.getInstance();

    @DataProvider(name = "validLogins")
    public static Object[][] validLogins() {
        return new Object[][] {{"Alex"}, {"Alex_z"}};
    }
    @Test(dataProvider = "validLogins")
    public void orderValidatorLoginTest(String login) {
        boolean result = validator.validateLogin(login);
        Assert.assertTrue(result);
    }

    @DataProvider(name = "invalidLogins")
    public static Object[][] invalidLogins() {
        return new Object[][] {{"1Alex"}, {"$Alex"}, {"alex?"}, {"alex-b"}};
    }
    @Test(dataProvider = "invalidLogins")
    public void orderValidatorLoginInvalidTest(String login) {
        boolean result = validator.validateLogin(login);
        Assert.assertFalse(result);
    }


    @DataProvider(name = "validPasswords")
    public static Object[][] validPasswords() {
        return new Object[][] {{"Password1"}, {"pASSWORD1"}, {"Pa123456"}};
    }
    @Test(dataProvider = "validPasswords")
    public void orderValidatorPasswordTest(String password) {
        boolean result = validator.validatePassword(password);
        Assert.assertTrue(result);
    }

    @DataProvider(name = "invalidPasswords")
    public static Object[][] invalidPasswords() {
        return new Object[][] {{"Pass"}, {"Password"}, {"12345678"}};
    }
    @Test(dataProvider = "invalidPasswords")
    public void orderValidatorPasswordInvalidTest(String password) {
        boolean result = validator.validatePassword(password);
        Assert.assertFalse(result);
    }

    @DataProvider(name = "validPhoneNumbers")
    public static Object[][] validPhoneNumbers() {
        return new Object[][] {{"+375299970202"}, {"+711223334455"}};
    }
    @Test(dataProvider = "validPhoneNumbers")
    public void orderValidatorPhoneNumberTest(String phoneNum) {
        boolean result = validator.validatePhoneNum(phoneNum);
        Assert.assertTrue(result);
    }

    @DataProvider(name = "invalidPhoneNumbers")
    public static Object[][] invalidPhoneNumbers() {
        return new Object[][] {{"299970202"}, {"299970202"}, {"+77711223334455"}, {"+7"}, {"375299970202"}};
    }
    @Test(dataProvider = "invalidPhoneNumbers")
    public void orderValidatorPhoneNumberInvalidTest(String phoneNum) {
        boolean result = validator.validatePhoneNum(phoneNum);
        Assert.assertFalse(result);
    }

    @DataProvider(name = "validCardNumbers")
    public static Object[][] validCardNumbers() {
        return new Object[][] {{"1111222233334444"}};
    }
    @Test(dataProvider = "validCardNumbers")
    public void orderValidatorCardNumberTest(String cardNum) {
        boolean result = validator.validateCardNum(cardNum);
        Assert.assertTrue(result);
    }

    @DataProvider(name = "invalidCardNumbers")
    public static Object[][] invalidCardNumbers() {
        return new Object[][] {{"1234"}, {"1111222233334444a"}};
    }
    @Test(dataProvider = "invalidCardNumbers")
    public void orderValidatorCardNumberInvalidTest(String cardNum) {
        boolean result = validator.validateCardNum(cardNum);
        Assert.assertFalse(result);
    }

    @DataProvider(name = "validUserStatuses")
    public static Object[][] validUserStatuses() {
        return new Object[][] {{IN_REGISTRATION.name()}, {CONFIRMED.name()}, {BLOCKED.name()}};
    }
    @Test(dataProvider = "validUserStatuses")
    public void orderValidatorUserStatusTest(String status) {
        boolean result = validator.validateUserStatus(status);
        Assert.assertTrue(result);
    }


    @DataProvider(name = "invalidUserStatuses")
    public static Object[][] invalidUserStatuses() {
        return new Object[][] {{"NEW"}, {"STATUS"}};
    }
    @Test(dataProvider = "invalidUserStatuses")
    public void orderValidatorUserStatusInvalidTest(String status) {
        boolean result = validator.validateUserStatus(status);
        Assert.assertFalse(result);
    }

    @DataProvider(name = "validFilterParameters")
    public static Object[][] primeNumbers() {
        return new Object[][] {{"Alex", "mail@gmail.com", "+375299970202", "1111222233334444",
                        new String[]{IN_REGISTRATION.name()}, new String[]{CUSTOMER.name()}},
                {"Alex_z", "mail.mail@yandex.ru", "+375299970202", "1111222233334444",
                        new String[]{IN_REGISTRATION.name(), CONFIRMED.name()}, new String[]{CUSTOMER.name(), GUEST.name()}},
                {"Alex_z", "mail99@gmail.com", "+375299970202", "1111222233334444",
                        new String[]{IN_REGISTRATION.name(), CONFIRMED.name(), BLOCKED.name()}, new String[]{CUSTOMER.name(), GUEST.name(), ADMIN.name()}}};
    }
    @Test(dataProvider = "validFilterParameters")
    public void userValidatorFilterTest(String login, String mail, String phoneNumber, String cardNumber,
                                        String[] userStatusesLines, String[] userRolesLines) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(login,
                mail, phoneNumber, cardNumber, userStatusesLines, userRolesLines);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertFalse(containsError);
    }
}
