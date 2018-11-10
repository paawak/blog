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

package com.swayam.practice.algos.tree.balanced;

import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.practice.algos.tree.GenericTree;
import com.swayam.practice.algos.tree.ui.GenericTreeDisplayerFrame;

/**
 * 
 * @author paawak
 */
public class BalancedTreeDisplayer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BalancedTreeDisplayer.class);

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.error("Error setting lookNfeel", ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GenericTree<Integer> tree = new Tree();

                new GenericTreeDisplayerFrame(tree).setVisible(true);
            }
        });
    }
}
