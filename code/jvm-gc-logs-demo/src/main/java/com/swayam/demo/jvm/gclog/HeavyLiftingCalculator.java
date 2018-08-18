package com.swayam.demo.jvm.gclog;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>
 * A program that manipulates large numbers and does heavy arithmatic, intening
 * to show the generation of GC logs by JVM.
 * </p>
 * 
 * <p>
 * Inspired from:<br>
 * <a href=
 * "https://www.hackerearth.com/practice/algorithms/searching/binary-search/practice-problems/algorithm/help-fredo/">Problem
 * Details</a>
 * </p>
 * 
 * 
 * @author paawak
 *
 */
public class HeavyLiftingCalculator {

    private static final String TEST_FILE_PATH_PREFIX = "/com/swayam/demo/jvm/gclog/";
    private static final int TEST_COUNT = 11;

    public static void main(String[] args) {

        for (int count = 1; count <= TEST_COUNT; count++) {
            System.out.println("************************************* START File " + count);
            InputStream inputStream = HeavyLiftingCalculator.class.getResourceAsStream(TEST_FILE_PATH_PREFIX + count + ".txt");
            doWork(inputStream);
            System.out.println("************************************* END File " + count);
        }

    }

    private static void doWork(InputStream is) {
        try (Scanner s = new Scanner(is);) {
            int arrayLength = Integer.parseInt(s.nextLine());

            String[] numbers = s.nextLine().split("\\s");
            BigInteger initialGuess = new BigInteger(numbers[0]);

            BigInteger product = Arrays.stream(numbers).parallel().map((String token) -> {
                return new BigInteger(token);
            }).reduce((BigInteger left, BigInteger right) -> {
                return left.multiply(right);
            }).get();

            for (int i = 0; i < 10; i++) {
                BigInteger nextGuess = findNRoot(initialGuess, product, arrayLength);
                if (nextGuess.equals(initialGuess)) {
                    break;
                }
                initialGuess = nextGuess;
            }
        }
    }

    private static BigInteger findNRoot(BigInteger initialGuess, BigInteger num, int power) {
        BigInteger func = initialGuess.pow(power).subtract(num);
        BigInteger derivative = initialGuess.multiply(initialGuess);
        BigInteger nextGuess = initialGuess.subtract(func.divide(derivative));
        return nextGuess;
    }

}
