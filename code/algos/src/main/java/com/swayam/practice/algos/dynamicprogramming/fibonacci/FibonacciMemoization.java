package com.swayam.practice.algos.dynamicprogramming.fibonacci;

import java.util.HashMap;
import java.util.Map;

public class FibonacciMemoization {

	private final Map<Integer, Integer> cache = new HashMap<>();

	public FibonacciMemoization() {
		cache.put(0, 0);
		cache.put(1, 1);
	}

	public int computeFibonacci(int number) {

		if (number < 0) {
			throw new IllegalArgumentException("number cannot be negative");
		}

		if (cache.containsKey(number)) {
			System.out.println("returning " + number + " from cache");
			return cache.get(number);
		}

		int value = computeFibonacci(number - 1) + computeFibonacci(number - 2);
		cache.put(number, value);

		return value;
	}

}
