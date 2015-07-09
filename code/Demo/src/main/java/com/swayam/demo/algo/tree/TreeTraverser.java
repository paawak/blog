package com.swayam.demo.algo.tree;

import java.util.Arrays;
import java.util.List;

public class TreeTraverser<T extends Comparable<T>> {

    public void inOrderLeftFirst(TreeNode<T> node) {
	// left
	TreeNode<T> left = node.getLeftChild();
	if (left != null) {
	    inOrderLeftFirst(left);
	}
	// this
	System.out.println(node.getContent());
	// right
	TreeNode<T> right = node.getRightChild();
	if (right != null) {
	    inOrderLeftFirst(right);
	}
    }

    public void inOrderRightFirst(TreeNode<T> node) {
	// right
	TreeNode<T> right = node.getRightChild();
	if (right != null) {
	    inOrderRightFirst(right);
	}

	// this
	System.out.println(node.getContent());

	// left
	TreeNode<T> left = node.getLeftChild();
	if (left != null) {
	    inOrderRightFirst(left);
	}

    }

    public void preOrderLeftFirst(TreeNode<T> node) {

	// this
	System.out.println(node.getContent());

	// left
	TreeNode<T> left = node.getLeftChild();
	if (left != null) {
	    inOrderRightFirst(left);
	}

	// right
	TreeNode<T> right = node.getRightChild();
	if (right != null) {
	    inOrderRightFirst(right);
	}

    }

    public void postOrderRightFirst(TreeNode<T> node) {
	// right
	TreeNode<T> right = node.getRightChild();
	if (right != null) {
	    inOrderRightFirst(right);
	}

	// left
	TreeNode<T> left = node.getLeftChild();
	if (left != null) {
	    inOrderRightFirst(left);
	}

	// this
	System.out.println(node.getContent());

    }

    public static void main(String[] a) {
	List<Integer> elements = Arrays.asList(100, 88, 200, 56, 1, 89, 67, 9, 100, 110, 45, 64, 19);

	TreeNode<Integer> rootNode = new TreePopulator<Integer>().getRootNode(elements);

	TreeTraverser<Integer> treeTraverser = new TreeTraverser<>();
	System.out.println("________________InOrderLeftFirst________________");
	treeTraverser.inOrderLeftFirst(rootNode);
	System.out.println("________________InOrderRightFirst________________");
	treeTraverser.inOrderRightFirst(rootNode);
	System.out.println("________________PreOrderLeftFirst________________");
	treeTraverser.preOrderLeftFirst(rootNode);
	System.out.println("________________PostOrderRightFirst________________");
	treeTraverser.postOrderRightFirst(rootNode);
    }

}
