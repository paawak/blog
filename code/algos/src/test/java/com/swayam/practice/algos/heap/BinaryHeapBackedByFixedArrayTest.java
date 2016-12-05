/*
 * BinaryHeapBackedByArrayTest.java
 *
 * Created on 03-Dec-2016 8:00:04 PM
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

import static org.junit.Assert.assertArrayEquals;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 
 * @author paawak
 */
public class BinaryHeapBackedByFixedArrayTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testAdd_happy_1() {
        // given
        Integer[] expectedArray = new Integer[] { 552 };

        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(1);

        // when
        testClass.add(552);

        // then
        assertArrayEquals(expectedArray, testClass.getElementsAsArray());
    }

    @Test
    public void testAdd_happy_2() {
        // given
        Integer[] expectedArray = new Integer[] { 12, 552 };

        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(2);

        // when
        testClass.add(552);
        testClass.add(12);

        // then
        assertArrayEquals(expectedArray, testClass.getElementsAsArray());
    }

    @Test
    public void testAdd_happy_3() {
        // given
        Integer[] expectedArray = new Integer[] { 12, 552, 100 };

        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(3);

        // when
        testClass.add(552);
        testClass.add(12);
        testClass.add(100);

        // then
        assertArrayEquals(expectedArray, testClass.getElementsAsArray());
    }

    @Test
    public void testAdd_happy_4() {
        // given
        Integer[] expectedArray = new Integer[] { 12, 200, 100, 552 };

        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(4);

        // when
        testClass.add(552);
        testClass.add(12);
        testClass.add(100);
        testClass.add(200);

        // then
        assertArrayEquals(expectedArray, testClass.getElementsAsArray());
    }

    @Test
    public void testAdd_happy_5() {
        // given
        Integer[] expectedArray = new Integer[] { 3, 12, 100, 552, 200 };

        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(10);

        // when
        testClass.add(552);
        testClass.add(12);
        testClass.add(100);
        testClass.add(200);
        testClass.add(3);

        // then
        assertArrayEquals(expectedArray, testClass.getElementsAsArray());
    }

    @Test
    public void testAdd_happy_6() {
        // given
        Integer[] expectedArray = new Integer[] { 3, 12, 44, 552, 200, 100 };

        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(10);

        // when
        testClass.add(552);
        testClass.add(12);
        testClass.add(100);
        testClass.add(200);
        testClass.add(3);
        testClass.add(44);

        // then
        assertArrayEquals(expectedArray, testClass.getElementsAsArray());
    }

    /**
     * Test case courtesy: <br/>
     * <a href=
     * "http://opendatastructures.org/ods-java/10_1_BinaryHeap_Implicit_Bi.html">http://opendatastructures.org/ods-java/10_1_BinaryHeap_Implicit_Bi.html</a>
     */
    @Test
    public void testAdd_happy_7() {
        // given
        Integer[] expectedArray = new Integer[] { 4, 9, 6, 17, 26, 8, 16, 19, 69, 32, 93, 55, 50 };

        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(13);

        // when
        testClass.add(4);
        testClass.add(9);
        testClass.add(8);
        testClass.add(17);
        testClass.add(26);
        testClass.add(50);
        testClass.add(16);
        testClass.add(19);
        testClass.add(69);
        testClass.add(32);
        testClass.add(93);
        testClass.add(55);
        testClass.add(6);

        // then
        assertArrayEquals(expectedArray, testClass.getElementsAsArray());
    }

    @Test
    public void testAdd_exceeds_maxSize() {
        // given
        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(2);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("The array cannot exceed its maximum allocated size of 2");

        // when
        testClass.add(552);
        testClass.add(12);
        testClass.add(100);

        // then: error
    }

    @Test
    public void testAdd_duplicateElement() {
        // given
        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(10);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("The element 3 already exists, at position 0");

        // when
        testClass.add(552);
        testClass.add(12);
        testClass.add(100);
        testClass.add(3);
        testClass.add(600);
        testClass.add(3);

        // then: error
    }

    /**
     * Test case courtesy: <br/>
     * <a href=
     * "http://opendatastructures.org/ods-java/10_1_BinaryHeap_Implicit_Bi.html">http://opendatastructures.org/ods-java/10_1_BinaryHeap_Implicit_Bi.html</a>
     */
    @Test
    public void testRemove_happy() {
        // given
        Integer[] expectedArray = new Integer[] { 6, 17, 8, 19, 26, 9, 16, 50, 69, 32, 93, 55 };

        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(14);

        // when
        testClass.add(4);
        testClass.add(9);
        testClass.add(6);
        testClass.add(17);
        testClass.add(26);
        testClass.add(8);
        testClass.add(16);
        testClass.add(19);
        testClass.add(69);
        testClass.add(32);
        testClass.add(93);
        testClass.add(55);
        testClass.add(50);
        testClass.remove();

        // then
        assertArrayEquals(expectedArray, testClass.getElementsAsArray());
    }

    @Test
    public void testRemove_whenEmpty() {
        // given
        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(10);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Cannot remove elements from empty array");

        // when
        testClass.add(552);
        testClass.add(12);
        testClass.remove();
        testClass.remove();
        testClass.remove();

        // then: error
    }

    public static void main(String[] a) {
        new BinaryHeapBackedByFixedArrayTest().displayTree();
    }

    private void displayTree() {

        BinaryHeapBackedByFixedArray<Integer> testClass = new BinaryHeapBackedByFixedArray<>(100);
        // testClass.add(552);
        // testClass.add(12);
        // testClass.add(100);
        // testClass.add(200);
        // testClass.add(3);
        // testClass.add(44);
        // testClass.add(57);
        // testClass.add(89);
        // testClass.add(30);
        // testClass.add(10);
        // testClass.add(17);
        // testClass.add(4);
        // testClass.add(23);
        // testClass.add(15);
        // testClass.add(122);

        testClass.add(4);
        testClass.add(9);
        testClass.add(6);
        testClass.add(17);
        testClass.add(26);
        testClass.add(8);
        testClass.add(16);
        testClass.add(19);
        testClass.add(69);
        testClass.add(32);
        testClass.add(93);
        testClass.add(55);
        testClass.add(50);
        testClass.remove();

        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.getContentPane().setLayout(new BorderLayout());

            JTree tree = new JTree(new DefaultTreeModel(testClass.getElementsAsTreeNode()));

            frame.getContentPane().add(tree, BorderLayout.CENTER);

            frame.setSize(500, 500);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
        });

    }

}
