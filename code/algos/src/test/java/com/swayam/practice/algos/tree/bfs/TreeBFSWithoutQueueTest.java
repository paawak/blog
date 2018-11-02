package com.swayam.practice.algos.tree.bfs;

import org.junit.Test;

public class TreeBFSWithoutQueueTest {

    @Test
    public void testPrintSiblings() {

        TreeNode nodeA = new TreeNode("A");
        TreeNode nodeB = new TreeNode("B");
        TreeNode nodeC = new TreeNode("C");
        nodeA.setLeft(nodeB);
        nodeA.setRight(nodeC);

        TreeNode nodeD = new TreeNode("D");
        TreeNode nodeE = new TreeNode("E");
        nodeB.setLeft(nodeD);
        nodeB.setRight(nodeE);

        TreeNode nodeF = new TreeNode("F");
        TreeNode nodeG = new TreeNode("G");
        nodeC.setLeft(nodeF);
        nodeC.setRight(nodeG);

        TreeBFSWithoutQueue testClass = new TreeBFSWithoutQueue();

        testClass.printSiblings(nodeA);

    }

}
