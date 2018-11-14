package com.swayam.practice.algos.tree.bfs;

/**
 * Given the below Binary Tree structure:
 * 
 * <pre>
 * 
 *                A
 *             /    \
 *            B      C
 *           / \    / \
 *          D   E   F  G
 * 
 * 
 * </pre>
 * 
 * Print the below sequence:
 * 
 * <pre>
 *      A 
 *      B       C 
 *      D       E       F       G
 * </pre>
 * 
 * @author paawak
 *
 */
public class TreeBFSWithoutQueue {

    public void printSiblings(TreeNode root) {

	int height = getHeight(root);

	for (int depth = 1; depth <= height; depth++) {
	    printSiblings(root, depth);
	    System.out.println();
	}

    }

    private void printSiblings(TreeNode node, int depth) {

	if (node == null) {
	    return;
	}

	if (depth == 1) {
	    System.out.print(node.getText() + "\t");
	    return;
	}

	if (node.getLeft() != null) {
	    printSiblings(node.getLeft(), depth - 1);
	}

	if (node.getRight() != null) {
	    printSiblings(node.getRight(), depth - 1);
	}

    }

    private int getHeight(TreeNode node) {

	if (node == null) {
	    return 0;
	} else if (node.getLeft() == null && node.getRight() == null) {
	    return 1;
	} else {
	    return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
	}

    }

}
