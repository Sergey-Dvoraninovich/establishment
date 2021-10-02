package com.dvoraninovich.establishment.util;

import java.util.Random;

public class SaltGenerator {
    private static final String SALT_ITEMS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static SaltGenerator instance;

    private SaltGenerator() {
    }

    public static SaltGenerator getInstance() {
        if (instance == null) {
            instance = new SaltGenerator();
        }
        return instance;
    }

    public String makeSalt(Integer length) {
        Random random = new Random();
        int symbolsAmount = SALT_ITEMS.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int symbolPosition = random.nextInt(symbolsAmount);
            char symbol = SALT_ITEMS.charAt(symbolPosition);
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
}
