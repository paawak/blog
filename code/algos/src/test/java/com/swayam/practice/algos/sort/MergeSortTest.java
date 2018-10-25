package com.swayam.practice.algos.sort;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class MergeSortTest {

    @Test
    public void testSort_even_length() {
	// given
	int[] input = new int[] { 40, 2, 54, 1, 8, 11, 95, 10 };
	MergeSort testClass = new MergeSort();

	// when
	int[] result = testClass.sort(input);

	// then
	assertArrayEquals(new int[] { 1, 2, 8, 10, 11, 40, 54, 95 }, result);
    }

    @Test
    public void testSort_odd_length() {
	// given
	int[] input = new int[] { 40, 2, 54, 1, 8, 11, 95, 10, 57 };
	MergeSort testClass = new MergeSort();

	// when
	int[] result = testClass.sort(input);

	// then
	assertArrayEquals(new int[] { 1, 2, 8, 10, 11, 40, 54, 57, 95 }, result);
    }

}
