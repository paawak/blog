package com.swayam.practice.algos.sort;

import java.util.Optional;
import java.util.Stack;

public class MergeSort {

    private final Stack<Integer> splitStack = new Stack<>();

    public int[] sort(int[] input) {
	splitRecursive(new SubArray(0, input.length));
	System.out.println(splitStack);
	return null;
    }

    private void splitRecursive(SubArray subArray) {
	Node node = split(subArray);

	if (node.lower.isPresent()) {
	    splitRecursive(node.upper);
	    splitRecursive(node.lower.get());
	} else {

	    if (node.upper.length == 1) {
		splitStack.push(node.upper.startIndex);
	    } else {
		throw new IllegalArgumentException();
	    }

	}

    }

    Node split(SubArray subArray) {

	if (subArray.length == 1) {
	    return new Node(new SubArray(subArray.startIndex, 1), null);
	}

	int midPoint = subArray.length / 2;

	SubArray upper = new SubArray(subArray.startIndex, midPoint);
	SubArray lower = new SubArray(midPoint + subArray.startIndex, subArray.length - midPoint);

	return new Node(upper, lower);
    }

    static class Node {
	final SubArray upper;
	final Optional<SubArray> lower;

	Node(SubArray upper, SubArray lower) {
	    this.upper = upper;
	    this.lower = Optional.ofNullable(lower);
	}
    }

    static class SubArray {
	final int startIndex;
	final int length;

	SubArray(int startIndex, int length) {
	    this.startIndex = startIndex;
	    this.length = length;
	}
    }

}
