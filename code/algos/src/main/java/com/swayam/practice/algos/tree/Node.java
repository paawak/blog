/*
 * TreeNode.java
 *
 * Created on 10-Dec-2016 6:12:24 PM
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

/**
 * 
 * @author paawak
 */
public class Node<E extends Comparable<E>> {

    private final E value;
    private Node<E> parentNode;

    private Node<E> leftChild;

    private Node<E> rightChild;

    private Node(E value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
        this.value = value;
    }

    public Node(E value, Node<E> parentNode) {
        this(value);

        if (parentNode == null) {
            throw new IllegalArgumentException("parentNode cannot be null");
        }
        this.parentNode = parentNode;
    }

    public Optional<Node<E>> getLeftChild() {
        return Optional.ofNullable(leftChild);
    }

    public void setLeftChild(Node<E> leftNode) {
        if (leftNode == null) {
            throw new IllegalArgumentException("leftNode cannot be null");
        }
        this.leftChild = leftNode;
    }

    public Optional<Node<E>> getRightChild() {
        return Optional.ofNullable(rightChild);
    }

    public void setRightChild(Node<E> rightNode) {
        if (rightNode == null) {
            throw new IllegalArgumentException("rightNode cannot be null");
        }
        this.rightChild = rightNode;
    }

    public E getValue() {
        return value;
    }

    public Optional<Node<E>> getParentNode() {
        return Optional.ofNullable(parentNode);
    }

    public void removeLeftChild() {
        leftChild = null;
    }

    public void removeRightChild() {
        rightChild = null;
    }

    public void removeParent() {
        parentNode = null;
    }

    public static <E extends Comparable<E>> Node<E> createRootNode(E element) {
        return new Node<E>(element);
    }

    @Override
    public String toString() {
        return "Node [value=" + value + ", parentNode=" + parentNode + ", leftChild=" + leftChild + ", rightChild="
                + rightChild + "]";
    }

}
