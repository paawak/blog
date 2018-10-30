package com.swayam.practice.algos.tree.bfs;

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
