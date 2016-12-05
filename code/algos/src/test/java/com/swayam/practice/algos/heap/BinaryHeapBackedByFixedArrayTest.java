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

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

/**
 * 
 * @author paawak
 */
public class BinaryHeapBackedByFixedArrayTest {

    public static void main(String[] a) {
        new BinaryHeapBackedByFixedArrayTest().displayTree();
    }

    private void displayTree() {

        BinaryHeapBackedByFixedArray<Integer> heap = new BinaryHeapBackedByFixedArray<>(100);
        heap.add(552);
        heap.add(12);
        heap.add(100);
        heap.add(200);
        heap.add(3);
        heap.add(44);
        heap.add(57);
        heap.add(89);
        heap.add(30);
        heap.add(10);
        heap.add(17);
        heap.add(4);
        heap.add(23);
        heap.add(15);
        heap.add(122);

        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.getContentPane().setLayout(new BorderLayout());

            JTree tree = new JTree(new DefaultTreeModel(heap.getElementAsTreeNode(0)));

            frame.getContentPane().add(tree, BorderLayout.CENTER);

            frame.setSize(500, 500);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
        });

    }

}
