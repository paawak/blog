package com.swayam.practice.algos.search.binarysearch;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>
 * Fredo is assigned a task today. He is given an array A containing N integers.
 * His task is to update all elements of array to some minimum value x, that is,
 * ; such that product of all elements of this new array is strictly greater
 * than the product of all elements of the initial array. Note that x should be
 * as minimum as possible such that it meets the given condition. Help him find
 * the value of x.
 * </p>
 * 
 * <p>
 * <b>Input Format:</b><br>
 * The first line consists of an integer N , denoting the number of elements in
 * the array. The next line consists of N space separated integers, denoting the
 * array elements.
 * </p>
 * 
 * <p>
 * <b>Output Format:</b><br>
 * The only line of output consists of value of x.
 * </p>
 * 
 * <p>
 * <a href=
 * "https://www.hackerearth.com/practice/algorithms/searching/binary-search/practice-problems/algorithm/help-fredo/">Problem
 * Details</a>
 * </p>
 * 
 * 
 * @author paawak
 *
 */
public class HelpFedoHackerEarthBadPerforming {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int arrayLength = Integer.parseInt(s.nextLine());

        String[] numbers = s.nextLine().split("\\s");
        BigInteger initialGuess = new BigInteger(numbers[0]);

        BigInteger product = Arrays.stream(numbers).parallel().map((String token) -> {
            return new BigInteger(token);
        }).reduce((BigInteger left, BigInteger right) -> {
            return left.multiply(right);
        }).get();

        for (int i = 0; i < 10; i++) {
            System.out.println("********** iteration: " + i);
            BigInteger nextGuess = findNRoot(initialGuess, product, arrayLength);
            if (nextGuess.equals(initialGuess)) {
                break;
            }
            initialGuess = nextGuess;
        }

        // product.
        //
        // int result = (int) Math.pow(product, power);
        //
        // while (Math.pow(result, arrayCount) < product) {
        // result++;
        // }

    }

    private static BigInteger findNRoot(BigInteger initialGuess, BigInteger num, int power) {
        BigInteger func = initialGuess.pow(power).subtract(num);
        System.err.println("11111");
        BigInteger derivative = initialGuess.multiply(initialGuess);
        System.err.println("22222");
        BigInteger nextGuess = initialGuess.subtract(func.divide(derivative));
        System.err.println("33333");
        return nextGuess;
    }

}
