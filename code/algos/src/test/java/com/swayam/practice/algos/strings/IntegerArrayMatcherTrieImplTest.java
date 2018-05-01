package com.swayam.practice.algos.strings;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class IntegerArrayMatcherTrieImplTest {

	@Test
	public void testSplit_1() {
		// given
		Set<List<Integer>> matchers = new HashSet<>(
				Arrays.asList(Arrays.asList(84, 93), Arrays.asList(102, 82), Arrays.asList(104, 87)));
		List<Integer> glyphIds = Arrays.asList(84, 112, 93, 104, 82, 61, 96, 102, 93, 104, 87, 110);

		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(glyphIds);

		// when
		List<List<Integer>> tokens = testClass.split(matchers);

		// then
		assertEquals(Arrays.asList(Arrays.asList(84, 112, 93, 104, 82, 61, 96, 102, 93), Arrays.asList(104, 87),
				Arrays.asList(110)), tokens);
	}

	@Test
	public void testSplit_2() {

		// given
		Set<List<Integer>> matchers = new HashSet<>(
				Arrays.asList(Arrays.asList(67, 112, 96), Arrays.asList(74, 112, 76)));

		List<Integer> glyphIds = Arrays.asList(67, 112, 96, 103, 93, 108, 93);

		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(glyphIds);

		// when
		List<List<Integer>> tokens = testClass.split(matchers);

		// then
		assertEquals(Arrays.asList(Arrays.asList(67, 112, 96), Arrays.asList(103, 93, 108, 93)), tokens);
	}

	@Test
	public void testSplit_3() {

		// given
		Set<List<Integer>> matchers = new HashSet<>(
				Arrays.asList(Arrays.asList(67, 112, 96), Arrays.asList(74, 112, 76)));

		List<Integer> glyphIds = Arrays.asList(94, 67, 112, 96, 112, 91, 103);

		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(glyphIds);

		// when
		List<List<Integer>> tokens = testClass.split(matchers);

		// then
		assertEquals(Arrays.asList(Arrays.asList(94), Arrays.asList(67, 112, 96), Arrays.asList(112, 91, 103)), tokens);
	}

	@Test
	public void testSplit_4() {

		// given
		Set<List<Integer>> matchers = new HashSet<>(Arrays.asList(Arrays.asList(67, 112), Arrays.asList(76, 112)));

		List<Integer> glyphIds = Arrays.asList(94, 167, 112, 91, 103);

		IntegerArrayMatcher testClass = new IntegerArrayMatcherTrieImpl(glyphIds);

		// when
		List<List<Integer>> tokens = testClass.split(matchers);

		// then
		assertEquals(Arrays.asList(Arrays.asList(94, 167, 112, 91, 103)), tokens);
	}

}
