/*
 * BinaryHeapDisplayer.java
 *
 * Created on 05-Dec-2016 11:11:03 PM
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

package com.swayam.practice.algos.heap.ui;

import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.practice.algos.heap.BinaryHeapBackedByFixedArray;
import com.swayam.practice.algos.tree.Tree;
import com.swayam.practice.algos.tree.ui.TreeDisplayer;

/**
 * 
 * @author paawak
 */
public class BinaryHeapDisplayer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryHeapDisplayer.class);

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
         * html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            LOGGER.error("Error setting lookNfeel", ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Tree<Integer> tree = new BinaryHeapBackedByFixedArray<>(100);

                // tree.add(552);
                // tree.add(12);
                // tree.add(100);
                // tree.add(200);
                // tree.add(3);
                // tree.add(44);
                // tree.add(57);
                // tree.add(89);
                // tree.add(30);
                // tree.add(10);
                // tree.add(17);
                // tree.add(4);
                // tree.add(23);
                // tree.add(15);
                // tree.add(122);

                tree.add(4);
                tree.add(9);
                tree.add(6);
                tree.add(17);
                tree.add(26);
                tree.add(8);
                tree.add(16);
                tree.add(19);
                tree.add(69);
                tree.add(32);
                tree.add(93);
                tree.add(55);
                tree.add(50);
                new TreeDisplayer(tree).setVisible(true);
            }
        });
    }
}
