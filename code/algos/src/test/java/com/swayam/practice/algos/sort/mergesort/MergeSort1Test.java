package com.swayam.practice.algos.sort.mergesort;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class MergeSort1Test {

    @Test
    public void testSort_even_length() {
        // given
        int[] input = new int[] { 40, 2, 54, 1, 8, 11, 95, 10 };
        MergeSort1 testClass = new MergeSort1();

        // when
        int[] result = testClass.sort(input);

        // then
        assertArrayEquals(new int[] { 1, 2, 8, 10, 11, 40, 54, 95 }, result);
    }

    @Test
    public void testSort_odd_length() {
        // given
        int[] input = new int[] { 40, 2, 54, 1, 8, 11, 95, 10, 57 };
        MergeSort1 testClass = new MergeSort1();

        // when
        int[] result = testClass.sort(input);

        // then
        assertArrayEquals(new int[] { 1, 2, 8, 10, 11, 40, 54, 57, 95 }, result);
    }

}
