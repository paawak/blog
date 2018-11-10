package com.swayam.practice.algos.tree.balanced;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.swayam.practice.algos.tree.balanced.PreOrderTreeWalker.NodeType;

public class Tree implements BinaryTree<Integer> {

    private Node root;

    @Override
    public void add(Integer element) {

        if (root == null) {
            root = new Node(element);
            return;
        }

        add(root, element);
    }

    @Override
    public int getHeight() {
        return getHeight(root);
    }

    @Override
    public int getBreadth() {
        int height = getHeight();

        if (height == 0) {
            return 0;
        }

        int maxBreadth = 0;

        for (int depth = 1; depth <= height; depth++) {
            int siblingCount = getSiblingCount(root, depth, 0);
            maxBreadth = Math.max(maxBreadth, siblingCount);
        }

        return maxBreadth;

    }

    @Override
    public void breadthFirstWalker(BreadthFirstTreeWalker breadthFirstTreeWalker) {
        int height = getHeight();

        for (int depth = 1; depth <= height; depth++) {
            breadthFirstTreeWalker.depthChange(depth);
            breadthFirstTreeWalk(root, depth, breadthFirstTreeWalker);
        }

    }

    @Override
    public void preOrderTreeWalker(PreOrderTreeWalker preOrderTreeWalker) {
        preOrderTreeWalker(root, NodeType.ROOT, preOrderTreeWalker);
    }

    @Override
    public boolean isEmpty() {
        return (root.getLeft() == null) && (root.getRight() == null);
    }

    @Override
    public DefaultMutableTreeNode getSwingTree() {

        if (root == null) {
            return new DefaultMutableTreeNode();
        }

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root.getValue());

        int height = getHeight();

        for (int depth = 1; depth <= height; depth++) {
            List<Integer> siblings = new ArrayList<>();
            listSiblings(root, depth, siblings);

            DefaultMutableTreeNode node = new DefaultMutableTreeNode();

            if (depth > 1) {
                siblings.forEach(value -> node.add(new DefaultMutableTreeNode(value)));
            }

            rootNode.add(node);

            siblings.clear();

        }

        return rootNode;
    }

    private void preOrderTreeWalker(Node node, NodeType nodeType, PreOrderTreeWalker preOrderTreeWalker) {

        if (node == null) {
            return;
        }

        preOrderTreeWalker.treeNode(node.getValue(), nodeType, node.getLeft() != null, node.getRight() != null);

        preOrderTreeWalker(node.getLeft(), NodeType.LEFT_CHILD, preOrderTreeWalker);

        preOrderTreeWalker(node.getRight(), NodeType.RIGHT_CHILD, preOrderTreeWalker);

    }

    private void breadthFirstTreeWalk(Node node, int depth, BreadthFirstTreeWalker breadthFirstTreeWalker) {

        if (node == null) {
            return;
        }

        if (depth == 1) {
            breadthFirstTreeWalker.newElement(node.getValue());
            return;
        }

        if (node.getLeft() != null) {
            breadthFirstTreeWalk(node.getLeft(), depth - 1, breadthFirstTreeWalker);
        }

        if (node.getRight() != null) {
            breadthFirstTreeWalk(node.getRight(), depth - 1, breadthFirstTreeWalker);
        }

    }

    private int getHeight(Node node) {

        if (node == null) {
            return 0;
        }

        if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        }

        return Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1;

    }

    private int getSiblingCount(Node node, int depth, int siblingCount) {

        if (node == null) {
            return 0;
        }

        if (depth == 1) {
            return siblingCount + 1;
        }

        int leftSiblingCount = 0;
        int rightSiblingCount = 0;

        if (node.getLeft() != null) {
            leftSiblingCount = getSiblingCount(node.getLeft(), depth - 1, siblingCount);
        }

        if (node.getRight() != null) {
            rightSiblingCount = getSiblingCount(node.getRight(), depth - 1, siblingCount);
        }

        return siblingCount + leftSiblingCount + rightSiblingCount;

    }

    private void listSiblings(Node node, int depth, List<Integer> siblings) {

        if (node == null) {
            return;
        }

        if (depth == 1) {
            siblings.add(node.getValue());
            return;
        }

        if (node.getLeft() != null) {
            listSiblings(node.getLeft(), depth - 1, siblings);
        }

        if (node.getRight() != null) {
            listSiblings(node.getRight(), depth - 1, siblings);
        }

    }

    private void add(Node node, Integer element) {

        if (element < node.getValue()) {
            if (node.getLeft() == null) {
                node.setLeft(new Node(element));
            } else {
                add(node.getLeft(), element);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new Node(element));
            } else {
                add(node.getRight(), element);
            }
        }

    }

    private static class Node {
        private final int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public int getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((left == null) ? 0 : left.hashCode());
            result = prime * result + ((right == null) ? 0 : right.hashCode());
            result = prime * result + value;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (left == null) {
                if (other.left != null)
                    return false;
            } else if (!left.equals(other.left))
                return false;
            if (right == null) {
                if (other.right != null)
                    return false;
            } else if (!right.equals(other.right))
                return false;
            if (value != other.value)
                return false;
            return true;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Node [value=");
            builder.append(value);
            builder.append(", left=");
            builder.append(left);
            builder.append(", right=");
            builder.append(right);
            builder.append("]");
            return builder.toString();
        }
    }

}
