package com.dvoraninovich.establishment.util;

import java.util.Random;

public class CodeGenerator {
    private static CodeGenerator instance;

    private CodeGenerator() {
    }

    public static CodeGenerator getInstance() {
        if (instance == null) {
            instance = new CodeGenerator();
        }
        return instance;
    }

    public String getCode(String codeItems, Integer codeLength) {
        Random random = new Random();
        int symbolsAmount = codeItems.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            int symbolPosition = random.nextInt(symbolsAmount);
            char symbol = codeItems.charAt(symbolPosition);
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
}
