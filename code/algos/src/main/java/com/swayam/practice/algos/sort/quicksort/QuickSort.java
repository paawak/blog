package com.swayam.practice.algos.sort.quicksort;

public class QuickSort {

    public void sort(int[] input) {
        quickSort(input, 0, input.length - 1);
    }

    private void quickSort(int[] input, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        int pivotIndex = partition(input, startIndex, endIndex);

        quickSort(input, startIndex, pivotIndex - 1);
        quickSort(input, pivotIndex + 1, endIndex);

    }

    private int partition(int[] input, int startIndex, int endIndex) {

        int pivotIndex = (endIndex + startIndex) / 2;

        swap(input, pivotIndex, endIndex);

        int newPivotIndex = startIndex;

        for (int index = startIndex; index < endIndex; index++) {
            if (input[index] <= input[endIndex]) {
                swap(input, newPivotIndex++, index);
            }
        }

        swap(input, newPivotIndex, endIndex);

        return newPivotIndex;

    }

    private void swap(int[] input, int firstIndex, int secondIndex) {
        if (firstIndex == secondIndex) {
            return;
        }
        int temp = input[firstIndex];
        input[firstIndex] = input[secondIndex];
        input[secondIndex] = temp;
    }

}
