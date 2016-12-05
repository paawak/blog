/*
 * BinaryHeapBackedByArray.java
 *
 * Created on 03-Dec-2016 7:48:19 PM
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

package com.swayam.practice.algos.heap;

import java.util.Optional;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.swayam.practice.algos.queue.PriorityQueue;

/**
 * Take a look at the below links for implementation details:
 * <ul>
 * <li><a href=
 * "http://opendatastructures.org/ods-java/10_1_BinaryHeap_Implicit_Bi.html">http://opendatastructures.org/ods-java/10_1_BinaryHeap_Implicit_Bi.html</a>
 * </li>
 * <li><a href=
 * "https://www.cs.cmu.edu/~adamchik/15-121/lectures/Binary%20Heaps/heaps.html">https://www.cs.cmu.edu/~adamchik/15-121/lectures/Binary%20Heaps/heaps.html</a></li>
 * </ul>
 * 
 * @author paawak
 */
public class BinaryHeapBackedByFixedArray<E extends Comparable<E>> implements PriorityQueue<E> {

    private static final int ROOT_INDEX = 0;

    private final int maxLengthOfArray;

    private final Object[] array;

    private int actualArraySize = 0;

    public BinaryHeapBackedByFixedArray(int maxLengthOfArray) {
        this.maxLengthOfArray = maxLengthOfArray;
        array = new Object[maxLengthOfArray];
    }

    @Override
    public void add(E element) {

        if (actualArraySize == maxLengthOfArray) {
            throw new IllegalStateException(String.format("The array cannot exceed its maximum allocated size of %d",
                    maxLengthOfArray));
        }

        array[actualArraySize++] = element;

        reAdjustArrayToConformToHeapProperty(actualArraySize - 1);

    }

    @Override
    public E remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E peek() {
        return getElement(ROOT_INDEX);
    }

    public TreeNode getElementsAsTreeNode() {
        return getElementAsTreeNode(ROOT_INDEX);
    }

    public Object[] getElementsAsArray() {
        Object[] backingArray = new Object[actualArraySize];
        System.arraycopy(array, 0, backingArray, 0, actualArraySize);
        return backingArray;
    }

    private MutableTreeNode getElementAsTreeNode(int nodeIndex) {

        // traverse the current node
        DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode(getElement(nodeIndex));

        // traverse the left node/sub-tree
        Optional<Integer> leftChildIndex = getLeftChildIndex(nodeIndex);

        if (leftChildIndex.isPresent()) {
            MutableTreeNode leftChildNode = getElementAsTreeNode(leftChildIndex.get());
            elementNode.add(leftChildNode);
        }

        // traverse the right node/sub-tree
        Optional<Integer> rightChildIndex = getRightChildIndex(nodeIndex);

        if (rightChildIndex.isPresent()) {
            MutableTreeNode rightChildNode = getElementAsTreeNode(rightChildIndex.get());
            elementNode.add(rightChildNode);
        }

        return elementNode;

    }

    private void reAdjustArrayToConformToHeapProperty(int startIndexForNode) {

        if (startIndexForNode == 0) {
            return;
        }

        E nodeElement = getElement(startIndexForNode);
        int parentNodeIndex = getParent(startIndexForNode);
        E parentNodeElement = getElement(parentNodeIndex);

        int comparisonWithParent = nodeElement.compareTo(parentNodeElement);

        if (comparisonWithParent == 0) {
            throw new IllegalStateException(
                    String.format("The element %s already exists, at position %d", nodeElement, parentNodeIndex));
        } else if (comparisonWithParent < 0) {

            // swap the parent with the child
            array[parentNodeIndex] = nodeElement;
            array[startIndexForNode] = parentNodeElement;
        }
        reAdjustArrayToConformToHeapProperty(parentNodeIndex);
    }

    @SuppressWarnings("unchecked")
    private E getElement(int nodeIndex) {
        return (E) array[nodeIndex];
    }

    private int getParent(int nodeIndex) {
        if (nodeIndex == ROOT_INDEX) {
            throw new IllegalArgumentException("The root element does not have any parent");
        }
        if (nodeIndex < 0) {
            throw new IllegalArgumentException("The index cannot be negative");
        }

        // check if this is the right child or left child
        if ((nodeIndex % 2) == 0) { // the right child is always even
            return (nodeIndex - 2) / 2;
        } else { // the left child is always odd
            return (nodeIndex - 1) / 2;
        }

    }

    private Optional<Integer> getLeftChildIndex(int nodeIndex) {
        return checkIndexExists(2 * nodeIndex + 1);

    }

    private Optional<Integer> getRightChildIndex(int nodeIndex) {
        return checkIndexExists(2 * nodeIndex + 2);
    }

    private Optional<Integer> checkIndexExists(int nodeIndex) {
        if (nodeIndex < 0) {
            throw new IllegalArgumentException("The index cannot be negative");
        }
        if (nodeIndex < actualArraySize) {
            return Optional.of(nodeIndex);
        }
        return Optional.empty();
    }

}
