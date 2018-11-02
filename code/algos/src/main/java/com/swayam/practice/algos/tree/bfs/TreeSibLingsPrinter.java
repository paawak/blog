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

        int height = getHeight(root);

        for (int depth = 1; depth <= height; depth++) {
            printSiblings(root, depth);
            System.out.println();
        }

    }

    private void printSiblings(TreeNode node, int depth) {

        if (node == null) {
            return;
        }

        if (depth == 1) {
            System.out.print(node.text + "\t");
        }

        if (depth > 1) {

            if (node.getLeft() != null) {
                printSiblings(node.getLeft(), depth - 1);
            }

            if (node.getRight() != null) {
                printSiblings(node.getRight(), depth - 1);
            }

        }

    }

    private int getHeight(TreeNode node) {

        if (node == null) {
            return 0;
        } else if (node.left == null && node.right == null) {
            return 1;
        } else {
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
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

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("TreeNode [text=");
            builder.append(text);
            builder.append("]");
            return builder.toString();
        }

    }

}
