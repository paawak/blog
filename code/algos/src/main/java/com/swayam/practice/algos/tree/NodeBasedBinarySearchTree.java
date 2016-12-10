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
public class NodeBasedBinarySearchTree<E extends Comparable<E>> implements Tree<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeBasedBinarySearchTree.class);

    private Node<E> rootNode;

    @Override
    public void add(E element) {

        if (element == null) {
            throw new IllegalArgumentException("element cannot be null");
        }

        if (rootNode == null) {
            rootNode = new Node<E>(element);
            return;
        }

        addRecursive(element, rootNode);
    }

    @Override
    public E remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TreeNode getElementsAsTreeNode() {
        return getAsTreeNode(rootNode);
    }

    @Override
    public int getElementCount() {
        throw new UnsupportedOperationException();
    }

    private void addRecursive(E element, Node<E> parentNode) {

        int comparison = element.compareTo(parentNode.getValue());

        if (comparison == 0) {
            LOGGER.warn("The element {} already exists, ignoring silently without inserting", element);
        } else if (comparison < 0) {
            // is lesser than the node: should go to the left sub-tree
            Optional<Node<E>> leftNode = parentNode.getLeftNode();

            if (leftNode.isPresent()) {
                addRecursive(element, leftNode.get());
            } else {
                parentNode.setLeftNode(new Node<E>(element));
            }

        } else {
            // is greater than the node: should go to the right sub-tree
            Optional<Node<E>> rightNode = parentNode.getRightNode();

            if (rightNode.isPresent()) {
                addRecursive(element, rightNode.get());
            } else {
                parentNode.setRightNode(new Node<E>(element));
            }

        }

    }

    private MutableTreeNode getAsTreeNode(Node<E> node) {
        // traverse the current node
        DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode(node.getValue());

        // traverse the left node/sub-tree
        Optional<Node<E>> leftChildNode = node.getLeftNode();

        if (leftChildNode.isPresent()) {
            elementNode.add(getAsTreeNode(leftChildNode.get()));
        }

        // traverse the right node/sub-tree
        Optional<Node<E>> rightChildNode = node.getRightNode();

        if (rightChildNode.isPresent()) {
            elementNode.add(getAsTreeNode(rightChildNode.get()));
        }

        return elementNode;
    }

}
