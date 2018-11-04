package com.swayam.practice.algos.sort.quicksort;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class QuickSortTest {

    @Test
    public void testSort_length_1() {
        // given
        int[] input = new int[] { 40 };
        QuickSort testClass = new QuickSort();

        // when
        testClass.sort(input);

        // then
        assertArrayEquals(new int[] { 40 }, input);
    }

    @Test
    public void testSort_even_length() {
        // given
        int[] input = new int[] { 40, 2, 54, 1, 8, 11, 95, 10 };
        QuickSort testClass = new QuickSort();

        // when
        testClass.sort(input);

        // then
        assertArrayEquals(new int[] { 1, 2, 8, 10, 11, 40, 54, 95 }, input);
    }

    @Test
    public void testSort_odd_length() {
        // given
        int[] input = new int[] { 40, 2, 54, 1, 8, 11, 95, 10, 57 };
        QuickSort testClass = new QuickSort();

        // when
        testClass.sort(input);

        // then
        assertArrayEquals(new int[] { 1, 2, 8, 10, 11, 40, 54, 57, 95 }, input);
    }

}
