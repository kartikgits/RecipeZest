package com.northwindlabs.kartikeya.recipezest;

import java.util.Random;

final class RandomsGenerator {

    private RandomsGenerator() {
    }

    public static int getRandomIntegerBetweenRange(double min, double max) {
        int x = (int) ((int) (Math.random() * (max - min)) + min);
        return x;
    }

    public static String getRandomAlphabet() {
        Random rnd = new Random();
        char c = (char) (rnd.nextInt(26) + 'a');
        return String.valueOf(c);
    }

}