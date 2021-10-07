package com.dvoraninovich.establishment.util;

import com.dvoraninovich.establishment.controller.command.CommandType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.regex.Pattern;

public class CodeGeneratorTest {
    private CodeGenerator generator = CodeGenerator.getInstance();

    @Test
    public void Test() {
       if (!Arrays.asList(CommandType.values()).contains("c")) {
           System.out.println("works");
       }
    }

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
