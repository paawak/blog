package com.swayam.practice.algos.dynamicprogramming.fibonacci;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FibonacciMemoizationTest {

	@Test
	public void testComputeFibonacci_0() {
		// given
		FibonacciMemoization testClass = new FibonacciMemoization();

		// when
		int result = testClass.computeFibonacci(0);

		// then
		assertEquals(0, result);
	}

	@Test
	public void testComputeFibonacci_1() {
		// given
		FibonacciMemoization testClass = new FibonacciMemoization();

		// when
		int result = testClass.computeFibonacci(1);

		// then
		assertEquals(1, result);
	}

	@Test
	public void testComputeFibonacci_9() {
		// given
		FibonacciMemoization testClass = new FibonacciMemoization();

		// when
		int result = testClass.computeFibonacci(9);

		// then
		assertEquals(34, result);
	}

	@Test
	public void testComputeFibonacci_12() {
		// given
		FibonacciMemoization testClass = new FibonacciMemoization();

		// when
		int result = testClass.computeFibonacci(12);

		// then
		assertEquals(144, result);
	}

}
