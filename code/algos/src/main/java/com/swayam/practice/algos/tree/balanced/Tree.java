package com.swayam.practice.algos.tree.balanced;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.swayam.practice.algos.tree.GenericTree;

public class Tree implements GenericTree<Integer> {

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
    public Integer remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TreeNode getSwingTree() {

        if (root == null) {
            return new DefaultMutableTreeNode();
        }

        return getAsTreeNode(root);
    }

    @Override
    public boolean isEmpty() {
        return (root.getLeft() == null) && (root.getRight() == null);
    }

    private DefaultMutableTreeNode getAsTreeNode(Node node) {
        DefaultMutableTreeNode swingNode = new DefaultMutableTreeNode(node.getValue());
        if ((node.getRight() != null) && (node.getLeft() != null)) {
            DefaultMutableTreeNode left = getAsTreeNode(node.getLeft());
            swingNode.add(left);
            DefaultMutableTreeNode right = getAsTreeNode(node.getRight());
            swingNode.add(right);

        } else if (node.getRight() != null) {
            DefaultMutableTreeNode right = getAsTreeNode(node.getRight());
            swingNode.add(right);
        } else if (node.getLeft() != null) {
            DefaultMutableTreeNode left = getAsTreeNode(node.getLeft());
            swingNode.add(left);
        }

        return swingNode;
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
