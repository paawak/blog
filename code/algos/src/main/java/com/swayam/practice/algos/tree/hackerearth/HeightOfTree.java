package com.swayam.practice.algos.tree.hackerearth;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class HeightOfTree {

    private static final String TEST_FILE_PATH = "/com/swayam/practice/algos/tree/hackerearth/height_of_tree/1.txt";

    public static void main(String[] a) {

        doWork(new Scanner(HeightOfTree.class.getResourceAsStream(TEST_FILE_PATH)));
    }

    private static void doWork(Scanner scanner) {

        int totalNodes = Integer.parseInt(scanner.nextLine());

        Map<Integer, Node> nodeMap = new LinkedHashMap<>();
        Map<Integer, Integer> heightMap = new HashMap<>();

        // according to the problem statement, 1 is always the root
        Node rootNode = new Node(1);

        nodeMap.put(1, rootNode);

        // populate the tree
        while (totalNodes-- > 1) {
            String[] tokens = scanner.nextLine().split("\\s");
            int parent = Integer.parseInt(tokens[0]);
            int child = Integer.parseInt(tokens[1]);

            Node parentNode;
            if (nodeMap.containsKey(parent)) {
                parentNode = nodeMap.get(parent);
            } else {
                parentNode = getSubTree(rootNode, parent);
            }

            Node childNode = new Node(child);

            // first fill the left node, then right node
            if (parentNode.getLeft() == null) {
                parentNode.setLeft(childNode);
            } else if (parentNode.getRight() == null) {
                parentNode.setRight(childNode);
            } else {
                throw new IllegalArgumentException("could not add a child as both left and right nodes are full");
            }

            nodeMap.put(child, childNode);

        }

        nodeMap.forEach((Integer nodeValue, Node node) -> {
            System.out.print(getMaxDistanceBetweenEdges(node, heightMap) + " ");
        });

    }

    private static Node getSubTree(Node parentNode, int searchKey) {
        // do a pre-order traversal
        if (parentNode.getValue() == searchKey) {
            return parentNode;
        }

        if (parentNode.getLeft() != null) {
            Node fromLeft = getSubTree(parentNode.getLeft(), searchKey);
            if (fromLeft != null) {
                return fromLeft;
            }
        }

        if (parentNode.getRight() != null) {
            Node fromRight = getSubTree(parentNode.getRight(), searchKey);
            if (fromRight != null) {
                return fromRight;
            }
        }

        throw new IllegalArgumentException("could not find the key: " + searchKey + " in the Tree");

    }

    private static int getMaxDistanceBetweenEdges(Node parentNode, Map<Integer, Integer> heightMap) {

        int heightOfLeftChildSubTree = 0;

        if (parentNode.getLeft() != null) {
            heightOfLeftChildSubTree = getMaxHeight(parentNode.getLeft(), heightMap) + 1;
        }

        int heightOfRightChildSubTree = 0;

        if (parentNode.getRight() != null) {
            heightOfRightChildSubTree = getMaxHeight(parentNode.getRight(), heightMap) + 1;
        }

        return heightOfLeftChildSubTree + heightOfRightChildSubTree;

    }

    private static int getMaxHeight(Node parentNode, Map<Integer, Integer> heightMap) {

        if (heightMap.containsKey(parentNode.getValue())) {
            return heightMap.get(parentNode.getValue());
        }

        // do a pre-order traversal
        if ((parentNode.getLeft() == null) && (parentNode.getRight() == null)) {
            heightMap.put(parentNode.getValue(), 0);
            return 0;
        }

        int heightOfLeftChildSubTree = 0;

        if (parentNode.getLeft() != null) {
            heightOfLeftChildSubTree = getMaxHeight(parentNode.getLeft(), heightMap);
        }

        int heightOfRightChildSubTree = 0;

        if (parentNode.getRight() != null) {
            heightOfRightChildSubTree = getMaxHeight(parentNode.getRight(), heightMap);
        }

        int height = Math.max(heightOfLeftChildSubTree, heightOfRightChildSubTree) + 1;
        heightMap.put(parentNode.getValue(), height);
        return height;

    }

    static class Node {

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

    }

}
