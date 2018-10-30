package com.swayam.practice.algos.tree.bfs;

import org.junit.Test;

import com.swayam.practice.algos.tree.bfs.TreeSibLingsPrinter.TreeNode;

public class TreeSibLingsPrinterTest {

    @Test
    public void testPrintSiblings() {

        TreeNode nodeA = new TreeNode();
        nodeA.setText("A");
        TreeNode nodeB = new TreeNode();
        nodeB.setText("B");
        TreeNode nodeC = new TreeNode();
        nodeC.setText("C");
        nodeA.setLeft(nodeB);
        nodeA.setRight(nodeC);

        TreeNode nodeD = new TreeNode();
        nodeD.setText("D");
        TreeNode nodeE = new TreeNode();
        nodeE.setText("E");
        nodeB.setLeft(nodeD);
        nodeB.setRight(nodeE);

        TreeNode nodeF = new TreeNode();
        nodeF.setText("F");
        TreeNode nodeG = new TreeNode();
        nodeG.setText("G");
        nodeC.setLeft(nodeF);
        nodeC.setRight(nodeG);

        TreeSibLingsPrinter testClass = new TreeSibLingsPrinter();

        testClass.printSiblings(nodeA);

    }

}
