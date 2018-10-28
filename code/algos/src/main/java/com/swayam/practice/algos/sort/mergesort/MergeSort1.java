package com.swayam.practice.algos.sort.mergesort;

public class MergeSort1 {

    public int[] sort(int[] input) {
        return splitAndMerge(input);
    }

    private int[] splitAndMerge(int[] input) {
        if (input.length == 1) {
            return new int[] { input[0] };
        } else {
            int midPoint = input.length / 2;
            int[] upperSubArray = new int[midPoint];
            System.arraycopy(input, 0, upperSubArray, 0, midPoint);
            upperSubArray = splitAndMerge(upperSubArray);
            int[] lowerSubArray = new int[input.length - midPoint];
            System.arraycopy(input, midPoint, lowerSubArray, 0, input.length - midPoint);
            lowerSubArray = splitAndMerge(lowerSubArray);
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
