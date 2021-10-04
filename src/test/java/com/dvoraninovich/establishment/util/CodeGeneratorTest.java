package com.dvoraninovich.establishment.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

public class CodeGeneratorTest {
    private CodeGenerator generator = CodeGenerator.getInstance();

    @DataProvider(name = "codeGeneratorData")
    public static Object[][] codeGeneratorData() {
        return new Object[][] {{4}, {8}, {16}, {32}, {64}};
    }
    @Test(dataProvider = "codeGeneratorData")
    public void codeGeneratorTest(int codeLength) {
        String codeRegexp = new StringBuilder("^[A-Za-z0-9]{")
                .append(codeLength)
                .append("}$").toString();
        String codeLine = generator.getCode(codeLength);
        boolean result = Pattern.matches(codeRegexp, codeLine);
        Assert.assertTrue(result);
    }

    @Test(dataProvider = "codeGeneratorData")
    public void codeGeneratorLengthTest(int codeLength) {
        String codeLine = generator.getCode(codeLength);
        int result = codeLine.length();
        Assert.assertEquals(result, codeLength);
    }
}
