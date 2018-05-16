package com.swayam.practice.algos.strings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IntegerArrayMatcherTrieImplTest {

	@Test
	public void testContains_positive_1() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 84, 112, 93, 104, 82, 61, 96, 102, 93, 104, 87, 110 });

		// when
		boolean result = testClass.contains(104);

		// then
		assertTrue(result);
	}

	@Test
	public void testContains_positive_2() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 67, 112, 96, 103, 93, 108, 93 });

		// when
		boolean result = testClass.contains(67);

		// then
		assertTrue(result);
	}

	@Test
	public void testContains_positive_3() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 94, 67, 112, 96, 112, 91, 103 });

		// when
		boolean result = testClass.contains(103);

		// then
		assertTrue(result);
	}

	@Test
	public void testContains_negative_1() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 94, 167, 112, 91, 103 });

		// when
		boolean result = testClass.contains(1671);

		// then
		assertFalse(result);
	}

	@Test
	public void testContains_negative_2() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 94, 167, 112, 91, 103 });

		// when
		boolean result = testClass.contains(16);

		// then
		assertFalse(result);
	}

	@Test
	public void testDelete_positive_1() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 94, 167, 112, 91, 103 });

		// when
		testClass.delete(167);

		// then
		assertFalse(testClass.contains(167));
	}

	@Test
	public void testDelete_positive_2() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 1167, 194, 167, 112, 91, 103, 94 });

		// when
		testClass.delete(167);

		// then
		assertFalse(testClass.contains(167));
		assertTrue(testClass.contains(1167));
	}

	@Test
	public void testDelete_positive_3() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 1167, 194, 167, 112, 91, 103, 94 });

		// when
		testClass.delete(1167);

		// then
		assertFalse(testClass.contains(1167));
		assertTrue(testClass.contains(167));
	}

	@Test
	public void testDelete_negative_1() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 94, 167, 112, 91, 103 });

		// when
		testClass.delete(16);

		// then
		assertTrue(testClass.contains(167));
	}

	@Test
	public void testDelete_negative_2() {
		// given
		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(new int[] { 94, 167, 112, 91, 103 });

		// when
		testClass.delete(9);

		// then
		assertTrue(testClass.contains(94));
		assertTrue(testClass.contains(91));
	}

}
