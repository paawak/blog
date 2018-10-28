package com.swayam.practice.algos.sort;

import java.util.Stack;

public class MergeSort {

    private final Stack<Integer> splitstack = new Stack<>();

    public int[] sort(int[] input) {

        if (input.length < 2) {
            return input;
        }

        System.out.println("__________________");
        split(input);
        System.out.println(splitstack);

        int[] merged = new int[] { splitstack.pop() };

        while (!splitstack.empty()) {
            merged = merge(new int[] { splitstack.pop() }, merged);
        }

        return merged;
    }

    private void split(int[] input) {
        if (input.length == 1) {
            splitstack.push(input[0]);
        } else {
            int midPoint = input.length / 2;
            int[] upper = new int[midPoint];
            System.arraycopy(input, 0, upper, 0, midPoint);
            split(upper);
            int[] lower = new int[input.length - midPoint];
            System.arraycopy(input, midPoint, lower, 0, input.length - midPoint);
            split(lower);
        }
    }

    private int[] merge(int[] arr1, int[] arr2) {
        int[] merged = new int[arr1.length + arr2.length];

        for (int mergedCtr = 0, arr1Ctr = 0, arr2Ctr = 0; mergedCtr < merged.length; mergedCtr++) {

            if (arr1Ctr < arr1.length && arr2Ctr < arr2.length) {
                if (arr1[arr1Ctr] < arr2[arr2Ctr]) {
                    merged[mergedCtr] = arr1[arr1Ctr++];
                } else {
                    merged[mergedCtr] = arr2[arr2Ctr++];
                }
            } else if (arr1Ctr < arr1.length) {
                merged[mergedCtr] = arr1[arr1Ctr++];
            } else if (arr2Ctr < arr2.length) {
                merged[mergedCtr] = arr2[arr2Ctr++];
            }
        }

        return merged;
    }

}
