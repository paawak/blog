package com.swayam.practice.algos.tree.bfs;

class TreeNode {
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