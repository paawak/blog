package com.swayam.practice.algos.strings;

import java.util.List;
import java.util.Set;

public class IntegerArrayMatcherTrieImpl implements IntegerArrayMatcher {

	private static final int TRIE_ARRAY_SIZE = 10;

	private final List<Integer> originalArray;

	public IntegerArrayMatcherTrieImpl(List<Integer> originalArray) {
		this.originalArray = originalArray;
	}

	@Override
	public List<List<Integer>> split(Set<List<Integer>> patterns) {
		// TODO Auto-generated method stub
		return null;
	}

	private static class TrieNode {

		private final int data;
		private final boolean endOfArray;
		private final int[] children = new int[TRIE_ARRAY_SIZE];

		public TrieNode(int data, boolean endOfArray) {
			this.data = data;
			this.endOfArray = endOfArray;
		}

		int getData() {
			return data;
		}

		boolean isEndOfArray() {
			return endOfArray;
		}

	}

}
