package com.swayam.practice.algos.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.swayam.practice.algos.sort.MergeSort.Node;
import com.swayam.practice.algos.sort.MergeSort.SubArray;

public class MergeSortTest {

    @Test
    public void testSplit_startIndex_0_length_1() {
	// given
	SubArray input = new SubArray(0, 1);
	MergeSort testClass = new MergeSort();

	// when
	Node result = testClass.split(input);

	// then
	assertEquals(0, result.upper.startIndex);
	assertEquals(1, result.upper.length);
	assertFalse(result.lower.isPresent());
    }

    @Test
    public void testSplit_startIndex_0_length_2() {
	// given
	SubArray input = new SubArray(0, 2);
	MergeSort testClass = new MergeSort();

	// when
	Node result = testClass.split(input);

	// then
	assertEquals(0, result.upper.startIndex);
	assertEquals(1, result.upper.length);
	assertEquals(1, result.lower.get().startIndex);
	assertEquals(1, result.lower.get().length);
    }

    @Test
    public void testSplit_startIndex_0_length_8() {
	// given
	SubArray input = new SubArray(0, 8);
	MergeSort testClass = new MergeSort();

	// when
	Node result = testClass.split(input);

	// then
	assertEquals(0, result.upper.startIndex);
	assertEquals(4, result.upper.length);
	assertEquals(4, result.lower.get().startIndex);
	assertEquals(4, result.lower.get().length);
    }

    @Test
    public void testSort_startIndex_0_length_9() {
	// given
	SubArray input = new SubArray(0, 9);
	MergeSort testClass = new MergeSort();

	// when
	Node result = testClass.split(input);

	// then
	assertEquals(0, result.upper.startIndex);
	assertEquals(4, result.upper.length);
	assertEquals(4, result.lower.get().startIndex);
	assertEquals(5, result.lower.get().length);
    }

    @Test
    public void testSort_startIndex_4_length_5() {
	// given
	SubArray input = new SubArray(4, 5);
	MergeSort testClass = new MergeSort();

	// when
	Node result = testClass.split(input);

	// then
	assertEquals(4, result.upper.startIndex);
	assertEquals(2, result.upper.length);
	assertEquals(6, result.lower.get().startIndex);
	assertEquals(3, result.lower.get().length);
    }

    @Test
    public void testSort_startIndex_4_length_1() {
	// given
	SubArray input = new SubArray(4, 1);
	MergeSort testClass = new MergeSort();

	// when
	Node result = testClass.split(input);

	// then
	assertEquals(4, result.upper.startIndex);
	assertEquals(1, result.upper.length);
	assertFalse(result.lower.isPresent());
    }

    @Test
    public void testSort_startIndex_4_length_2() {
	// given
	SubArray input = new SubArray(4, 2);
	MergeSort testClass = new MergeSort();

	// when
	Node result = testClass.split(input);

	// then
	assertEquals(4, result.upper.startIndex);
	assertEquals(1, result.upper.length);
	assertEquals(5, result.lower.get().startIndex);
	assertEquals(1, result.lower.get().length);
    }

    @Test
    public void testSort_even_length() {
	// given
	int[] input = new int[] { 40, 2, 54, 1, 8, 11, 95, 10 };
	MergeSort testClass = new MergeSort();

	// when
	testClass.sort(input);

	// then
    }

    @Test
    public void testSort_odd_length() {
	// given
	int[] input = new int[] { 40, 2, 54, 1, 8, 11, 95, 10, 57 };
	MergeSort testClass = new MergeSort();

	// when
	testClass.sort(input);

	// then
    }

}
