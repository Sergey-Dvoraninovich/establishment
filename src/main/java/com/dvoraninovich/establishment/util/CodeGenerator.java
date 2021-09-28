package com.dvoraninovich.establishment.util;

import java.util.Random;

public class CodeGenerator {
    private static final String CODE_ITEMS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static CodeGenerator instance;

    private CodeGenerator() {
    }

    public static CodeGenerator getInstance() {
        if (instance == null) {
            instance = new CodeGenerator();
        }
        return instance;
    }

    public String getCode(Integer codeLength) {
        Random random = new Random();
        int symbolsAmount = CODE_ITEMS.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            int symbolPosition = random.nextInt(symbolsAmount);
            char symbol = CODE_ITEMS.charAt(symbolPosition);
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
}
