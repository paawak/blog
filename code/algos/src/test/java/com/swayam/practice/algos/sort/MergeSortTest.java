package com.swayam.practice.algos.sort;

import org.junit.Test;

public class MergeSortTest {

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
