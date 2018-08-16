package com.swayam.practice.algos.search.binarysearch;

import java.math.BigDecimal;
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
public class HelpFedoHackerEarth {

    public static void main(String[] args) {
	Scanner s = new Scanner(System.in);
	int arrayLength = Integer.parseInt(s.nextLine());

	double logProduct = Arrays.stream(s.nextLine().split("\\s")).parallel().reduce(0d, (Double initial, String nextToken) -> {
	    return initial + findLog10(new BigInteger(nextToken));
	}, (Double right, Double left) -> {
	    return right + left;
	});

	double logRoot = logProduct / arrayLength;
	BigInteger root = findAntiLog10(logRoot).add(BigInteger.ONE);

	System.out.println(root);

    }

    static double findLog10(BigInteger number) {
	String decimalStringValue = number.toString(10);
	int integralPart = decimalStringValue.length() - 1;
	double decimalPart = Math.log10(new BigDecimal(number).divide(BigDecimal.TEN.pow(integralPart)).doubleValue());
	return integralPart + decimalPart;
    }

    static BigInteger findAntiLog10(double log) {
	int integralPart = (int) Math.floor(log);
	double decimalPart = log - integralPart;
	double decimalPartAntiLog = Math.pow(10, decimalPart);
	BigDecimal antiLog = BigDecimal.TEN.pow(integralPart).multiply(new BigDecimal(decimalPartAntiLog));
	return antiLog.setScale(1, BigDecimal.ROUND_HALF_UP).toBigInteger();
    }

}
