package com.sar.ws.shared.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class Utils {

    private LocalDate currentDate = LocalDate.of(2020, 4, 2);

    public LocalDate getCurrentDate() {
        return currentDate;
    }


    private final Random RANDOM = new SecureRandom();

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
}
