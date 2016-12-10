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

    private Node<E> leftNode;

    private Node<E> rightNode;

    public Node(E value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
        this.value = value;
    }

    public Optional<Node<E>> getLeftNode() {
        return Optional.ofNullable(leftNode);
    }

    public void setLeftNode(Node<E> leftNode) {
        if (leftNode == null) {
            throw new IllegalArgumentException("leftNode cannot be null");
        }
        this.leftNode = leftNode;
    }

    public Optional<Node<E>> getRightNode() {
        return Optional.ofNullable(rightNode);
    }

    public void setRightNode(Node<E> rightNode) {
        if (rightNode == null) {
            throw new IllegalArgumentException("rightNode cannot be null");
        }
        this.rightNode = rightNode;
    }

    public E getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((leftNode == null) ? 0 : leftNode.hashCode());
        result = prime * result + ((rightNode == null) ? 0 : rightNode.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        Node other = (Node) obj;
        if (leftNode == null) {
            if (other.leftNode != null)
                return false;
        } else if (!leftNode.equals(other.leftNode))
            return false;
        if (rightNode == null) {
            if (other.rightNode != null)
                return false;
        } else if (!rightNode.equals(other.rightNode))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Node [value=" + value + ", leftNode=" + leftNode + ", rightNode=" + rightNode + "]";
    }

}
