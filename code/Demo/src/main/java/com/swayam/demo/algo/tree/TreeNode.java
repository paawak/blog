package com.swayam.demo.algo.tree;

public class TreeNode<T extends Comparable<T>> {

    private final T content;
    private TreeNode<T> leftChild;
    private TreeNode<T> rightChild;

    public TreeNode(T content) {
	this.content = content;
    }

    public T getContent() {
	return content;
    }

    public TreeNode<T> getLeftChild() {
	return leftChild;
    }

    public TreeNode<T> getRightChild() {
	return rightChild;
    }

    public void setLeftChild(TreeNode<T> leftChild) {
	this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode<T> rightChild) {
	this.rightChild = rightChild;
    }

}
