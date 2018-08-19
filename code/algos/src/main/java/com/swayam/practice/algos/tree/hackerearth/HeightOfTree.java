package com.swayam.practice.algos.tree.hackerearth;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Tree-Values
 * 
 * You are given a tree consisting of nodes. The tree is rooted at node . Now,
 * for each node of this tree where , you need to find the maximum distance
 * between nodes lying in subtree of node
 * 
 * .
 * 
 * We call the distance between
 * 
 * nodes to be the number of edges lying on the unique path between them.
 * 
 * Input Format:
 * 
 * The first line contains a single integer denoting the number of nodes in tree
 * . Each of the next lines contains space separated integers and , denoting an
 * edge between nodes and in tree
 * 
 * .
 * 
 * It is guaranteed the given input edges form a valid tree.
 * 
 * Output Format:
 * 
 * Print space separated integers, where the integer denotes the answer for node
 * 
 * .
 * 
 * Constraints:
 * 
 * Sample Input
 * 
 * 4<br>
 * 1 2<br>
 * 1 3<br>
 * 2 4
 * 
 * Sample Output
 * 
 * 3 1 0 0
 * 
 * Explanation
 * 
 * For the node , the path from node to is largest in its subtree. For node the
 * path from node to
 * 
 * is largest in its subtree. For the other nodes this length is zero as there
 * are no edges in the subtrees of those nodes. Note: Your code should be able
 * to convert the sample input into the sample output. However, this is not
 * enough to pass the challenge, because the code will be run on multiple test
 * cases. Therefore, your code must solve this problem statement.
 * 
 * @author paawak
 *
 */
public class HeightOfTree {

    private static final String TEST_FILE_PATH = "/com/swayam/practice/algos/tree/hackerearth/height_of_tree/1.txt";

    public static void main(String[] a) {

        doWork(new Scanner(HeightOfTree.class.getResourceAsStream(TEST_FILE_PATH)));
    }

    private static void doWork(Scanner scanner) {

        int totalNodes = Integer.parseInt(scanner.nextLine());

        Map<Integer, Node> nodeMap = new LinkedHashMap<>();

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

            parentNode.addChild(childNode);

            nodeMap.put(child, childNode);

        }

        nodeMap.forEach((Integer nodeValue, Node node) -> {
            System.out.print(getMaxDistanceBetweenEdges(node) + " ");
        });

    }

    private static Node getSubTree(Node parentNode, int searchKey) {
        // do a pre-order traversal
        if (parentNode.getValue() == searchKey) {
            return parentNode;
        }

        for (Node child : parentNode.children.values()) {
            Node matchingNode = getSubTree(child, searchKey);
            if (matchingNode != null) {
                return matchingNode;
            }
        }

        return null;

    }

    private static int getMaxDistanceBetweenEdges(Node parentNode) {

        if (parentNode.getChildren().isEmpty()) {
            return 0;
        }

        int height = parentNode.children.values().stream().mapToInt((Node node) -> {

            int heightOfChild = getMaxHeight(node);
            if (heightOfChild >= 0) {
                heightOfChild++;
            }
            return heightOfChild;
        }).sum();

        return height;

    }

    private static int getMaxHeight(Node parentNode) {

        if (parentNode.children.isEmpty()) {
            return 0;
        }

        int height = parentNode.children.values().stream().mapToInt((Node node) -> {
            return getMaxHeight(node);
        }).max().getAsInt() + 1;

        return height;

    }

    static class Node {

        private final int value;
        private final Map<Integer, Node> children = new LinkedHashMap<>();

        public Node(int value) {
            this.value = value;
        }

        public void addChild(Node child) {
            children.put(children.size() + 1, child);
        }

        public Map<Integer, Node> getChildren() {
            return children;
        }

        public int getValue() {
            return value;
        }

    }

}
