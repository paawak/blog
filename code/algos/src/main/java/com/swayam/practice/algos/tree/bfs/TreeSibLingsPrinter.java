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
public class TreeSibLingsPrinter {

    public void printSiblings(TreeNode root) {
        Queue<String> queue = new ArrayBlockingQueue<>(100);
        queue.add(root.getText());
        traverse(queue, root);
        System.out.println(queue);
    }

    private void traverse(Queue<String> queue, TreeNode node) {

        if (node.getLeft() != null) {
            queue.add(node.getLeft().getText());
        }

        if (node.getRight() != null) {
            queue.add(node.getRight().getText());
        }

        if (node.getLeft() != null) {
            traverse(queue, node.getLeft());
        }

        if (node.getRight() != null) {
            traverse(queue, node.getRight());
        }

    }

    static class TreeNode {
        private String text;
        private TreeNode left;
        private TreeNode right;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }

}
