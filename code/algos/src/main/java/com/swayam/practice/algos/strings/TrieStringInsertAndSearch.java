package com.swayam.practice.algos.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Trie is an efficient information retrieval data structure. Use this data
 * structure to store Strings and search strings. Your task is to use TRIE data
 * structure and search the given string A. If found print 1 else 0.
 * 
 * Input:<br>
 * The first line of input contains a single integer T denoting the number of
 * test cases. Then T test cases follow. Each test case consists of three lines.
 * First line of each test case consist of a integer N, denoting the number of
 * element in a Trie to be stored. Second line of each test case consists of N
 * space separated strings denoting the elements to be stored in the trie. Third
 * line of each test case consists of a String A to be searched in the stored
 * elements.
 * 
 * Output:<br>
 * Print the respective output in the respective line.
 * 
 * Source:<br>
 * <a href=
 * "https://practice.geeksforgeeks.org/problems/trie-insert-and-search/0"
 * >https://practice.geeksforgeeks.org/problems/trie-insert-and-search/0</a>
 * 
 * @author paawak
 *
 */
public class TrieStringInsertAndSearch {

	private static final int TRIE_ARRAY_SIZE = 26;

	private final TrieNode root;

	public TrieStringInsertAndSearch(String[] tokens) {
		root = createTrieStructure(tokens);
	}

	public boolean contains(String needle) {

		char[] charArray = needle.toLowerCase().toCharArray();

		TrieNode currentParent = root;

		for (int index = 0; index < charArray.length; index++) {
			char alphabet = charArray[index];
			boolean endOfToken = index == charArray.length - 1;
			int trieIndex = getTrieIndex(alphabet);
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

	private TrieNode createTrieStructure(String[] tokens) {
		// create the root node
		TrieNode root = new TrieNode(false);

		for (String token : tokens) {
			addToken(root, token);
		}

		return root;
	}

	private void addToken(final TrieNode parent, String token) {

		char[] charArray = token.toLowerCase().toCharArray();

		TrieNode currentParent = parent;

		for (int index = 0; index < charArray.length; index++) {
			char alphabet = charArray[index];
			boolean endOfToken = index == charArray.length - 1;
			int trieIndex = getTrieIndex(alphabet);
			currentParent = addTrieNode(currentParent, trieIndex, endOfToken);
		}

	}

	private TrieNode addTrieNode(TrieNode parent, int trieIndex, boolean endOfToken) {
		// check if the parent has this data, if yes, return the child node,
		// else,
		// create a new child node
		if (parent.hasChildAt(trieIndex)) {
			return parent.getChildAt(trieIndex);
		} else {
			TrieNode newNode = new TrieNode(endOfToken);
			parent.setChildAt(trieIndex, newNode);
			return newNode;
		}
	}

	private int getTrieIndex(char alphabet) {
		return alphabet - 'a';
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

		public boolean isEndOfToken() {
			return endOfToken;
		}

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int numberOfTestCases = Integer.parseInt(br.readLine());
		for (int i = 0; i < numberOfTestCases; i++) {
			int numberOfTokens = Integer.parseInt(br.readLine());
			String spaceSeparatedTokens = br.readLine();
			String[] tokens = spaceSeparatedTokens.split("\\s");
			if (numberOfTokens != tokens.length) {
				throw new IllegalArgumentException();
			}
			String needle = br.readLine();
			TrieStringInsertAndSearch trieStringInsertAndSearch = new TrieStringInsertAndSearch(tokens);
			boolean found = trieStringInsertAndSearch.contains(needle);
			System.out.println(found ? 1 : 0);
		}
	}

}
