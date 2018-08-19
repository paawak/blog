package com.swayam.practice.algos.tree.hackerearth;

import java.util.Scanner;

public class HeightOfTree {

    private static final String TEST_FILE_PATH = "/com/swayam/practice/algos/tree/hackerearth/height_of_tree/1.txt";

    public static void main(String[] a) {

        doWork(new Scanner(HeightOfTree.class.getResourceAsStream(TEST_FILE_PATH)));
    }

    private static void doWork(Scanner scanner) {

        int totalNodes = Integer.parseInt(scanner.nextLine());

        while (totalNodes-- > 1) {
            String[] tokens = scanner.nextLine().split("\\s");
            int parent = Integer.parseInt(tokens[0]);
            int child = Integer.parseInt(tokens[1]);
        }

    }

}
