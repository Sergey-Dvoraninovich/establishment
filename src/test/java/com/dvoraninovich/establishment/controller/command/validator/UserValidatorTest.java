package com.dvoraninovich.establishment.controller.command.validator;

import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static com.dvoraninovich.establishment.model.entity.Role.*;
import static com.dvoraninovich.establishment.model.entity.UserStatus.*;

public class UserValidatorTest {

    private UserValidator validator = UserValidator.getInstance();

    @BeforeClass
    public void init() {
        UserService validatorService = UserServiceImpl.getInstance();
        validatorService = Mockito.mock(UserService.class);
        Mockito.when(validatorService.isLoginUnique(Mockito.anyString()))
                .thenReturn(true);
        try {
            Class validatorClass = UserValidator.class;
            Field field = validatorClass.getDeclaredField("service");
            field.setAccessible(true);
            field.set(validator, validatorService);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Assert.fail("Impossible to setup mocks");
        }
    }

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
    public static Object[][] validFilterParameters() {
        return new Object[][] {{"Alex", "mail@gmail.com", "+375299970202", "1111222233334444",
                        new String[]{IN_REGISTRATION.name()}, new String[]{CUSTOMER.name()}},
                {"Alex_Z", "mail.mail@yandex.ru", "+375299970202", "1111222233334444",
                        new String[]{IN_REGISTRATION.name(), CONFIRMED.name()}, new String[]{CUSTOMER.name(), GUEST.name()}},
                {"AlexZ", "mail99@gmail.com", "+375299970202", "1111222233334444",
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

    @DataProvider(name = "invalidFilterParameters")
    public static Object[][] invalidFilterParameters() {
        return new Object[][] {{"1Alex$", "mail@gmail.com", "+375299970202", "1111222233334444",
                new String[]{IN_REGISTRATION.name()}, new String[]{CUSTOMER.name()}},
                {"alex", "!yandex.ru", "+375299970202", "1111222233334444",
                        new String[]{IN_REGISTRATION.name(), CONFIRMED.name()}, new String[]{CUSTOMER.name(), GUEST.name()}},
                {"Alex", "mail.mail@yandex.ru", "plus375299970202", "1111222233334444",
                        new String[]{IN_REGISTRATION.name(), CONFIRMED.name()}, new String[]{CUSTOMER.name(), GUEST.name()}},
                {"alex", "mail99@gmail.com", "+375299970202", "1111222233334444b",
                        new String[]{IN_REGISTRATION.name(), CONFIRMED.name(), BLOCKED.name()}, new String[]{CUSTOMER.name(), GUEST.name(), ADMIN.name()}},
                {"Alex", "mail.mail@yandex.ru", "+375299970202", "1111222233334444",
                        new String[]{IN_REGISTRATION.name(), "NEW"}, new String[]{CUSTOMER.name(), GUEST.name()}},
                {"Alex", "mail.mail@yandex.ru", "+375299970202", "1111222233334444",
                        new String[]{IN_REGISTRATION.name(), CONFIRMED.name()}, new String[]{"NEW", GUEST.name()}}};
    }
    @Test(dataProvider = "invalidFilterParameters")
    public void userValidatorFilterLoginInvalidTest(String login, String mail, String phoneNumber, String cardNumber,
                                        String[] userStatusesLines, String[] userRolesLines) {
        HashMap<String, Boolean> validationMap = validator.validateFilterParameters(login,
                mail, phoneNumber, cardNumber, userStatusesLines, userRolesLines);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertTrue(containsError);
    }


    @DataProvider(name = "validUserData")
    public static Object[][] validUserData() {
        return new Object[][] {{"Alex", "Password1", "mail@gmail.com", "+375299970202", "1111222233334444"},
                {"Alex_Z", "pASSWORD1", "mail.mail@yandex.ru", "+375299970202", "1111222233334444"},
                {"AlexZ", "Pa123456", "mail99@gmail.com", "+375299970202", "1111222233334444"}};
    }
    @Test(dataProvider = "validUserData")
    public void userValidatorDataTest(String login, String password, String mail, String phoneNumber, String cardNumber) {
        HashMap<String, Boolean> validationMap = validator.validateUserData(login, password, mail, phoneNumber, cardNumber);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertFalse(containsError);
    }

    @DataProvider(name = "inValidUserData")
    public static Object[][] inValidUserData() {
        return new Object[][] {{"Alex$", "Password1", "mail@gmail.com", "+375299970202", "1111222233334444"},
                {"Alex", "Pass", "mail@gmail.com", "+375299970202", "1111222233334444"},
                {"Alex", "Password", "mail@gmail.com", "+375299970202", "1111222233334444"},
                {"Alex", "Password1", "@gmail.com", "+375299970202", "1111222233334444"},
                {"Alex", "Password1", "mail@gmail", "+375299970202", "1111222233334444"},
                {"Alex", "Password1", "mail@gmail.com", "plus375299970202", "1111222233334444"},
                {"Alex", "Password1", "mail@gmail.com", "+375299970202", "1111222233334444d"}};
    }
    @Test(dataProvider = "inValidUserData")
    public void userValidatorDataInvalidTest(String login, String password, String mail, String phoneNumber, String cardNumber) {
        HashMap<String, Boolean> validationMap = validator.validateUserData(login, password, mail, phoneNumber, cardNumber);
        boolean containsError = validationMap.containsValue(true);
        Assert.assertTrue(containsError);
    }
}
