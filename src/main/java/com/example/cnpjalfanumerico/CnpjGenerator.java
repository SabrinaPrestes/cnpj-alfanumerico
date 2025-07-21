package com.example.cnpjalfanumerico;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class CnpjGenerator {

    public static final String ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();

    public static String generateRandomBaseCnpj() {
        StringBuilder stringBuilder = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(ALPHANUM.length());
            stringBuilder.append(ALPHANUM.charAt(randomIndex));

        }
        return stringBuilder.toString();
    }


}
