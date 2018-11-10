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

import java.util.ArrayList;
import java.util.List;
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

        LOGGER.info("Trying to remove element {} from tree", element);

        Optional<Node<E>> nodeOptional = searchRecursive(element, rootNode);

        if (!nodeOptional.isPresent()) {
            LOGGER.warn("the element {} was not found", element);
            return;
        }

        Node<E> nodeToBeRemoved = nodeOptional.get();

        if ((!nodeToBeRemoved.getLeftChild().isPresent()) && (!nodeToBeRemoved.getRightChild().isPresent())) {
            // if this is a leaf
            LOGGER.debug("the node {} is a leaf", nodeToBeRemoved);
            removeLeaf(nodeToBeRemoved);
        } else if ((nodeToBeRemoved.getLeftChild().isPresent()) && (!nodeToBeRemoved.getRightChild().isPresent())) {
            // if only left child present
            LOGGER.debug("the node {} has only a single LEFT child", nodeToBeRemoved);

            if (nodeToBeRemoved == rootNode) {
                // left child becomes the new root
                LOGGER.debug("the node {} is actually the ROOT node", nodeToBeRemoved);
                Node<E> newRoot = nodeToBeRemoved.getLeftChild().get();
                newRoot.removeParent();
                rootNode = newRoot;
                return;
            }

            detachNodeFromParent(nodeToBeRemoved);

            mergeNodes(nodeToBeRemoved.getParentNode().get(), nodeToBeRemoved.getLeftChild().get());

        } else if ((!nodeToBeRemoved.getLeftChild().isPresent()) && (nodeToBeRemoved.getRightChild().isPresent())) {
            // if only right child present
            LOGGER.debug("the node {} has only a single RIGHT child", nodeToBeRemoved);

            if (nodeToBeRemoved == rootNode) {
                // right child becomes the new root
                LOGGER.debug("the node {} is actually the ROOT node", nodeToBeRemoved);
                Node<E> newRoot = nodeToBeRemoved.getRightChild().get();
                newRoot.removeParent();
                rootNode = newRoot;
                return;
            }

            detachNodeFromParent(nodeToBeRemoved);

            mergeNodes(nodeToBeRemoved.getParentNode().get(), nodeToBeRemoved.getRightChild().get());

        } else {
            // if both children present
            LOGGER.debug("the node {} has both LEFT and RIGHT children", nodeToBeRemoved);

            Node<E> leftChild = nodeToBeRemoved.getLeftChild().get();
            Node<E> rightChild = nodeToBeRemoved.getRightChild().get();

            leftChild.removeParent();
            rightChild.removeParent();

            mergeNodes(leftChild, rightChild);

            if (nodeToBeRemoved == rootNode) {
                LOGGER.debug("the node {} is actually the ROOT node", nodeToBeRemoved);
                rootNode = leftChild;
            } else {
                Node<E> parentNode = nodeToBeRemoved.getParentNode().get();

                detachNodeFromParent(nodeToBeRemoved);

                mergeNodes(parentNode, leftChild);
            }

        }

    }

    @Override
    public boolean isEmpty() {
        return rootNode == null;
    }

    @Override
    public List<E> getSortedElementsDesc() {
        List<E> accumulator = new ArrayList<>();
        descOrderTraversal(rootNode, accumulator);
        return accumulator;
    }

    @Override
    public List<E> getSortedElementsAsc() {
        List<E> accumulator = new ArrayList<>();
        inOrderTraversal(rootNode, accumulator);
        return accumulator;
    }

    @Override
    public TreeNode getSwingTree() {
        return getAsTreeNode(rootNode);
    }

    private void inOrderTraversal(Node<E> node, List<E> accumulator) {
        // traverse the left child node
        Optional<Node<E>> leftChildNode = node.getLeftChild();

        if (leftChildNode.isPresent()) {
            inOrderTraversal(leftChildNode.get(), accumulator);
        }

        // traverse the current node
        accumulator.add(node.getValue());

        // traverse the right child node
        Optional<Node<E>> rightChildNode = node.getRightChild();

        if (rightChildNode.isPresent()) {
            inOrderTraversal(rightChildNode.get(), accumulator);
        }
    }

    private void descOrderTraversal(Node<E> node, List<E> accumulator) {

        // traverse the right child node
        Optional<Node<E>> rightChildNode = node.getRightChild();

        if (rightChildNode.isPresent()) {
            descOrderTraversal(rightChildNode.get(), accumulator);
        }

        // traverse the current node
        accumulator.add(node.getValue());

        // traverse the left child node
        Optional<Node<E>> leftChildNode = node.getLeftChild();

        if (leftChildNode.isPresent()) {
            descOrderTraversal(leftChildNode.get(), accumulator);
        }

    }

    private void mergeNodes(Node<E> newParentNode, Node<E> childNode) {

        childNode.setParentNode(newParentNode);

        int comparison = childNode.getValue().compareTo(newParentNode.getValue());

        if (comparison == 0) {
            throw new IllegalStateException("The value " + newParentNode.getValue() + " occurs more than once");
        } else if (comparison < 0) {
            // the child node should be the left child
            Optional<Node<E>> existingLeftChild = newParentNode.getLeftChild();
            if (existingLeftChild.isPresent()) {
                mergeNodes(existingLeftChild.get(), childNode);
            } else {
                newParentNode.setLeftChild(childNode);
            }
        } else {
            // the child node should be the right child
            Optional<Node<E>> existingRightChild = newParentNode.getRightChild();
            if (existingRightChild.isPresent()) {
                mergeNodes(existingRightChild.get(), childNode);
            } else {
                newParentNode.setRightChild(childNode);
            }
        }

    }

    private void detachNodeFromParent(Node<E> nodeToBeDetached) {

        Node<E> parent = nodeToBeDetached.getParentNode().get();

        int comparison = nodeToBeDetached.getValue().compareTo(parent.getValue());

        if (comparison < 0) {
            parent.removeLeftChild();
        } else if (comparison > 0) {
            parent.removeRightChild();
        } else {
            // should never come here, as duplicates are not allowed
            throw new IllegalStateException("duplicates present in the tree!");
        }

    }

    private void removeLeaf(Node<E> nodeToBeRemoved) {

        if (nodeToBeRemoved == rootNode) {
            LOGGER.debug("the node {} is actually the ROOT node", nodeToBeRemoved);
            rootNode = null;
            return;
        }

        detachNodeFromParent(nodeToBeRemoved);

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

        if (node == null) {
            return new DefaultMutableTreeNode();
        }

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
