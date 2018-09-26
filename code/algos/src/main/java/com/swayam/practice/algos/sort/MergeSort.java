package com.swayam.practice.algos.sort;

public class MergeSort {

    public int[] sort(int[] input) {
        split(input);
        return null;
    }

    private void split(int[] input) {
        if (input.length > 1) {
            int midPoint = input.length / 2;
            int[] upper = new int[midPoint];
            System.arraycopy(input, 0, upper, 0, midPoint);
            split(upper);
            int[] lower = new int[input.length - midPoint];
            System.arraycopy(input, midPoint, lower, 0, input.length - midPoint);
            split(lower);
        }
    }

}
