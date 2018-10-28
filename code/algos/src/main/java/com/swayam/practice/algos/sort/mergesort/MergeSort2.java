package com.swayam.practice.algos.sort.mergesort;

public class MergeSort2 {

    public int[] sort(int[] input) {

        if (input.length <= 1) {
            return input;
        }

        return splitAndMerge(input, 0, input.length);
    }

    private int[] splitAndMerge(int[] input, int startIndex, int length) {
        if (length == 1) {
            return new int[] { input[startIndex] };
        } else {
            int midPoint = length / 2;
            int[] upperSubArray = splitAndMerge(input, startIndex, midPoint);
            int[] lowerSubArray = splitAndMerge(input, startIndex + midPoint, length - midPoint);
            return merge(upperSubArray, lowerSubArray);
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
