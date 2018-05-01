package com.swayam.practice.algos.strings;

public class IntegerArrayMatcherTrieImpl implements IntegerArrayMatcher {

	private static final int TRIE_ARRAY_SIZE = 10;

	private final TrieNode root;

	public IntegerArrayMatcherTrieImpl(int[] tokens) {
		root = createTrieStructure(tokens);
	}

	@Override
	public boolean contains(int needle) {

		char[] digitArray = Integer.toString(needle).toCharArray();

		TrieNode currentParent = root;

		for (int index = 0; index < digitArray.length; index++) {
			char digit = digitArray[index];
			boolean endOfToken = index == digitArray.length - 1;
			int trieIndex = getTrieIndex(digit);
			if (!currentParent.hasChildAt(trieIndex)) {
				return false;
			}

			currentParent = currentParent.getChildAt(trieIndex);

			if (endOfToken) {
				return currentParent.isEndOfToken();
			}
		}

		return false;
	}

	private TrieNode createTrieStructure(int[] tokens) {
		// create the root node
		TrieNode root = new TrieNode(false);

		for (int token : tokens) {
			addToken(root, token);
		}

		return root;
	}

	private void addToken(final TrieNode parent, int token) {

		char[] digitArray = Integer.toString(token).toCharArray();

		TrieNode currentParent = parent;

		for (int index = 0; index < digitArray.length; index++) {
			char digit = digitArray[index];
			boolean endOfToken = index == digitArray.length - 1;
			int trieIndex = getTrieIndex(digit);
			currentParent = addTrieNode(currentParent, trieIndex, endOfToken);
		}

	}

	private TrieNode addTrieNode(TrieNode parent, int trieIndex, boolean endOfToken) {
		// check if the parent has this data, if yes, return the child node, else,
		// create a new child node
		if (parent.hasChildAt(trieIndex)) {
			return parent.getChildAt(trieIndex);
		} else {
			TrieNode newNode = new TrieNode(endOfToken);
			parent.setChildAt(trieIndex, newNode);
			return newNode;
		}
	}

	private int getTrieIndex(char digit) {
		return digit - '0';
	}

	private static class TrieNode {

		private final boolean endOfToken;
		private final TrieNode[] children = new TrieNode[TRIE_ARRAY_SIZE];

		public TrieNode(boolean endOfToken) {
			this.endOfToken = endOfToken;
		}

		public void setChildAt(int trieIndex, TrieNode newNode) {
			if (hasChildAt(trieIndex)) {
				throw new IllegalStateException("A child already exists at the given index: " + trieIndex);
			}
			children[trieIndex] = newNode;
		}

		public TrieNode getChildAt(int trieIndex) {
			return children[trieIndex];
		}

		public boolean hasChildAt(int trieIndex) {
			return children[trieIndex] != null;
		}

		boolean isEndOfToken() {
			return endOfToken;
		}

	}

}
