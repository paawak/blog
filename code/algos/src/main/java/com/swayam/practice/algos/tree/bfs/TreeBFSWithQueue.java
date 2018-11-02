package com.swayam.practice.algos.tree.bfs;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Given the below Binary Tree structure:
 * 
 * <pre>
 * 
 *                A
 *             /    \
 *            B      C
 *           / \    / \
 *          D   E   F  G
 * 
 * 
 * </pre>
 * 
 * Print the below sequence:
 * 
 * <pre>
 *      A 
 *      B       C 
 *      D       E       F       G
 * </pre>
 * 
 * @author paawak
 *
 */
public class TreeBFSWithQueue {

    public void printSiblings(TreeNode root) {

        Queue<TreeNode> queue = new ArrayBlockingQueue<>(100);

        TreeNode node = root;

        while (node != null) {

            System.out.println(node.getText());

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }

            if (node.getRight() != null) {
                queue.add(node.getRight());
            }

            node = queue.poll();

        }

    }

}
