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

	for (int subArrayCtr = 0, mergedCtr = 0; subArrayCtr < merged.length; subArrayCtr++) {

	    if (subArrayCtr < arr1.length && subArrayCtr < arr2.length) {

		int ele1 = arr1[subArrayCtr];
		int ele2 = arr2[subArrayCtr];

		if (ele1 < ele2) {
		    merged[mergedCtr] = ele1;
		    merged[mergedCtr + 1] = ele2;
		} else {
		    merged[mergedCtr] = ele2;
		    merged[mergedCtr + 1] = ele1;
		}

		mergedCtr += 2;

	    } else if (subArrayCtr < arr1.length) {

		merged[mergedCtr] = arr1[subArrayCtr];
		mergedCtr++;

	    } else if (subArrayCtr < arr2.length) {

		merged[mergedCtr] = arr2[subArrayCtr];
		mergedCtr++;

	    }

	}

	return merged;
    }

}
