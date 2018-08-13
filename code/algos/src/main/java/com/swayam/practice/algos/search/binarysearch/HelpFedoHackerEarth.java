package com.swayam.practice.algos.search.binarysearch;

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
	int arrayCount = Integer.parseInt(s.nextLine());

	long product = Arrays.asList(s.nextLine().split("\\s")).parallelStream().mapToInt((String ele) -> {
	    return Integer.valueOf(ele);
	}).asLongStream().reduce((long left, long right) -> {
	    return left * right;
	}).getAsLong();

	double power = 1 / arrayCount;

	int result = (int) Math.pow(product, power);

	while (Math.pow(result, arrayCount) < product) {
	    result++;
	}

	System.out.println(result);

    }

}
