package com.swayam.practice.algos.sort.quicksort;

public class QuickSort {

    public int[] sort(int[] input) {
        quickSort(input, input.length / 2);
        return input;
    }

    private void quickSort(int[] input, int pivotIndex) {
        if (input.length == 1) {
            return;
        }
    }

}
