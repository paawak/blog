package com.swayam.practice.algos.dynamicprogramming.fibonacci;

public class FibonacciRecursion {

	public int computeFibonacci(int number) {

		if (number < 0) {
			throw new IllegalArgumentException("number cannot be negative");
		}

		if (number == 0) {
			return 0;
		}

		if (number == 1) {
			return 1;
		}

		return computeFibonacci(number - 1) + computeFibonacci(number - 2);
	}

}
