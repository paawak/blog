/*
 * BinarySearchTree.java
 *
 * Created on 10-Dec-2016 6:05:27 PM
 *
 * Copyright (c) 2002 - 2008 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.practice.algos.tree;

import java.util.Optional;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author paawak
 */
public class NodeBasedBinarySearchTree<E extends Comparable<E>> implements BinarySearchTree<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeBasedBinarySearchTree.class);

    private Node<E> rootNode;

    @Override
    public void add(E element) {

        if (element == null) {
            throw new IllegalArgumentException("element cannot be null");
        }

        if (rootNode == null) {
            rootNode = Node.createRootNode(element);
            return;
        }

        addRecursive(element, rootNode);
    }

    @Override
    public E remove() {
        if (rootNode == null) {
            throw new IllegalArgumentException("the tree is empty");
        }

        E root = rootNode.getValue();

        remove(root);

        return root;

    }

    @Override
    public void remove(E element) {

        Optional<Node<E>> nodeOptional = searchRecursive(element, rootNode);

        if (!nodeOptional.isPresent()) {
            LOGGER.warn("the element {} was not found", element);
            return;
        }

        Node<E> node = nodeOptional.get();

        if ((!node.getLeftChild().isPresent()) && (!node.getRightChild().isPresent())) {
            // if this is a leaf
            removeLeaf(node);
        } else if ((node.getLeftChild().isPresent()) && (!node.getRightChild().isPresent())) {
            // if only left child present

        } else if ((!node.getLeftChild().isPresent()) && (node.getRightChild().isPresent())) {
            // if only right child present

        } else {
            // if both children present

            Node<E> newNode = mergeTrees(node.getLeftChild().get(), node.getRightChild().get());

            if (node == rootNode) {
                rootNode = newNode;
            } else {
                Node<E> parentNode = node.getParentNode().get();
                rootNode = mergeTrees(parentNode, newNode);
            }

        }

    }

    @Override
    public TreeNode getElementsAsTreeNode() {
        return getAsTreeNode(rootNode);
    }

    @Override
    public boolean isEmpty() {
        return rootNode == null;
    }

    private Node<E> mergeTrees(Node<E> node1, Node<E> node2) {
        node1.removeParent();
        node2.removeParent();
        return null;
    }

    private void removeLeaf(Node<E> node) {

        if (node == rootNode) {
            rootNode = null;
            return;
        }

        Node<E> parent = node.getParentNode().get();

        int comparison = parent.getValue().compareTo(node.getValue());

        if (comparison < 0) {
            parent.removeLeftChild();
        } else if (comparison > 0) {
            parent.removeRightChild();
        } else {
            // should never come here, as duplicates are not allowed
            throw new IllegalStateException("duplicates present in the tree!");
        }

    }

    private Optional<Node<E>> searchRecursive(E element, Node<E> node) {

        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }

        if (element == null) {
            throw new IllegalArgumentException("element cannot be null");
        }

        int comparison = element.compareTo(node.getValue());

        if (comparison == 0) {
            return Optional.of(node);
        }

        Optional<Node<E>> childNode = Optional.empty();

        if (comparison < 0) {
            // is lesser than the node: should go to the left sub-tree
            childNode = node.getLeftChild();
        } else {
            // is greater than the node: should go to the right sub-tree
            childNode = node.getRightChild();
        }

        if (childNode.isPresent()) {
            return searchRecursive(element, childNode.get());
        } else {
            return Optional.empty();
        }
    }

    private void addRecursive(E element, Node<E> parentNode) {

        int comparison = element.compareTo(parentNode.getValue());

        if (comparison == 0) {
            LOGGER.warn("The element {} already exists, ignoring silently without inserting", element);
        } else if (comparison < 0) {
            // is lesser than the node: should go to the left sub-tree
            Optional<Node<E>> leftNode = parentNode.getLeftChild();

            if (leftNode.isPresent()) {
                addRecursive(element, leftNode.get());
            } else {
                parentNode.setLeftChild(new Node<E>(element, parentNode));
            }

        } else {
            // is greater than the node: should go to the right sub-tree
            Optional<Node<E>> rightNode = parentNode.getRightChild();

            if (rightNode.isPresent()) {
                addRecursive(element, rightNode.get());
            } else {
                parentNode.setRightChild(new Node<E>(element, parentNode));
            }

        }

    }

    private MutableTreeNode getAsTreeNode(Node<E> node) {
        // traverse the current node
        DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode(node.getValue());

        // traverse the left node/sub-tree
        Optional<Node<E>> leftChildNode = node.getLeftChild();

        if (leftChildNode.isPresent()) {
            elementNode.add(getAsTreeNode(leftChildNode.get()));
        }

        // traverse the right node/sub-tree
        Optional<Node<E>> rightChildNode = node.getRightChild();

        if (rightChildNode.isPresent()) {
            elementNode.add(getAsTreeNode(rightChildNode.get()));
        }

        return elementNode;
    }

}
