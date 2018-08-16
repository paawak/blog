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

	BigInteger product = Arrays.stream(s.nextLine().split("\\s")).parallel().map((String token) -> {
	    return new BigInteger(token);
	}).reduce((BigInteger left, BigInteger right) -> {
	    return left.multiply(right);
	}).get();

	double logRoot = findLog10(product) / arrayLength;
	BigInteger root = findAntiLog10(logRoot);

	System.out.println(findMinRoot(product, root, arrayLength));

    }

    static BigInteger findMinRoot(BigInteger product, BigInteger root, int power) {

	BigInteger newProduct = root.pow(power);

	int compared = product.compareTo(newProduct);

	if (compared >= 0) {
	    return root.add(BigInteger.ONE);
	} else {
	    return root.subtract(BigInteger.ONE);
	}

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
