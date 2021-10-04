package com.dvoraninovich.establishment.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

public class SaltGeneratorTest {
    private SaltGenerator generator = SaltGenerator.getInstance();

    @DataProvider(name = "saltGeneratorData")
    public static Object[][] saltGeneratorData() {
        return new Object[][] {{4}, {8}, {16}, {32}, {64}};
    }
    @Test(dataProvider = "saltGeneratorData")
    public void saltGeneratorTest(int codeLength) {
        String codeRegexp = new StringBuilder("^[A-Za-z0-9]{")
                .append(codeLength)
                .append("}$").toString();
        String codeLine = generator.makeSalt(codeLength);
        boolean result = Pattern.matches(codeRegexp, codeLine);
        Assert.assertTrue(result);
    }

    @Test(dataProvider = "saltGeneratorData")
    public void saltGeneratorLengthTest(int codeLength) {
        String codeLine = generator.makeSalt(codeLength);
        int result = codeLine.length();
        Assert.assertEquals(result, codeLength);
    }
}
