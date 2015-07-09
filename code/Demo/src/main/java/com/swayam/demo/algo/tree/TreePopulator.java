package com.swayam.demo.algo.tree;

import java.util.List;

public class TreePopulator<T extends Comparable<T>> {

    public TreeNode<T> getRootNode(List<T> elements) {
	TreeNode<T> rootNode = new TreeNode<>(elements.get(0));

	for (int i = 1; i < elements.size(); i++) {
	    insertNode(rootNode, elements.get(i));
	}

	return rootNode;
    }

    private void insertNode(TreeNode<T> currentNode, T content) {

	if (content.compareTo(currentNode.getContent()) < 0) {
	    TreeNode<T> leftChild = currentNode.getLeftChild();
	    if (leftChild == null) {
		currentNode.setLeftChild(new TreeNode<>(content));
		System.out.println("Inserted " + content + " left of " + currentNode.getContent());
	    } else {
		insertNode(leftChild, content);
	    }
	} else {
	    TreeNode<T> rightChild = currentNode.getRightChild();
	    if (rightChild == null) {
		currentNode.setRightChild(new TreeNode<>(content));
		System.out.println("Inserted " + content + " right of " + currentNode.getContent());
	    } else {
		insertNode(rightChild, content);
	    }
	}
    }

}
