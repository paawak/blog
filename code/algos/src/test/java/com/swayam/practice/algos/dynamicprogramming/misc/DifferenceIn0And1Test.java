package com.swayam.practice.algos.dynamicprogramming.misc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DifferenceIn0And1Test {

	@Test
	public void testCompute() {
		// given
		DifferenceIn0And1 testClass = new DifferenceIn0And1();

		// when
		int result = testClass.compute("11000010001");

		// then
		assertEquals(6, result);
	}

}
