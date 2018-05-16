package com.swayam.practice.algos.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Trie is an efficient information retrieval data structure. This data
 * structure is used to store Strings and search strings, String stored can also
 * be deleted so, Your task is to complete the function deleteKey to delete the
 * given string A.The String A if exists in the larger String must be deleted
 * from Trie. And if the string is deleted successfully than 1 will be printed.
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
 * <a href= "https://practice.geeksforgeeks.org/problems/trie-delete/1/"
 * >https://practice.geeksforgeeks.org/problems/trie-delete/1/</a>
 * 
 * @author paawak
 *
 */
public class TrieStringInsertSearchDelete {

	private static final int TRIE_ARRAY_SIZE = 26;

	private final TrieNode root;

	public TrieStringInsertSearchDelete(String[] tokens) {
		root = createTrieStructure(tokens);
	}

	public boolean delete(String needle) {

		char[] charArray = needle.toLowerCase().toCharArray();

		TrieNode currentParent = root;

		for (int index = 0; index < charArray.length; index++) {
			char alphabet = charArray[index];
			boolean endOfToken = index == charArray.length - 1;
			int trieIndex = getTrieIndex(alphabet);
			if (!currentParent.hasChildAt(trieIndex)) {
				return false;
			}

			if (endOfToken) {
				if (currentParent.getChildAt(trieIndex).isEndOfToken()) {
					currentParent.setChildToNull(trieIndex);
					return true;
				}
			} else {
				currentParent = currentParent.getChildAt(trieIndex);
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

		public void setChildToNull(int trieIndex) {
			if (!hasChildAt(trieIndex)) {
				throw new IllegalStateException("No child exists at the given index: " + trieIndex);
			}
			children[trieIndex] = null;
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
			TrieStringInsertSearchDelete trieStringInsertAndSearch = new TrieStringInsertSearchDelete(tokens);
			boolean found = trieStringInsertAndSearch.delete(needle);
			System.out.println(found ? 1 : 0);
		}
	}

}
