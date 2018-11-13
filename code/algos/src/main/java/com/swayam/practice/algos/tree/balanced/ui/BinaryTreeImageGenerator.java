package com.swayam.practice.algos.tree.balanced.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.practice.algos.tree.balanced.BreadthFirstTreeWalker;
import com.swayam.practice.algos.tree.balanced.Tree;
import com.swayam.practice.algos.tree.balanced.Tree.Node;

public class BinaryTreeImageGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryTreeImageGenerator.class);

    private static final int TREE_GAP = 100;
    private static final int NODE_DIA = 40;
    private static final int NODE_GAP = 40;

    private final Map<Integer, Point> valueToDepthMap = new HashMap<>();

    public BufferedImage getImage(Tree binaryTree) {

        int treeHeight = binaryTree.getHeight();
        int maxBaseWidth = getMaxSiblingsWidth(treeHeight);
        int imageWidth = 2 * TREE_GAP + maxBaseWidth + NODE_DIA;
        int imageHeight = calculateHeight(maxBaseWidth / 2) + 2 * TREE_GAP + NODE_DIA;

        binaryTree.breadthFirstWalker(new BreadthFirstTreeWalker() {

            private int currentX = -1;
            private int currentY = -1;

            @Override
            public void newElement(int value) {
                valueToDepthMap.put(value, new Point(currentX, currentY));
                currentX += NODE_GAP + NODE_DIA;
            }

            @Override
            public void depthChange(int depth) {
                int halfWidth = getMaxSiblingsWidth(depth) / 2;
                currentY = TREE_GAP + calculateHeight(halfWidth);
                currentX = imageWidth / 2 - halfWidth;
            }
        });

        LOGGER.info("imageWidth: {}, imageHeight: {}", imageWidth, imageHeight);

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics g = image.getGraphics();
        g.fillRect(0, 0, imageWidth, imageHeight);

        // start from root
        g.setColor(Color.RED);

        paintNode(g, binaryTree, binaryTree.getRoot(), treeHeight);

        return image;
    }

    private void paintNode(Graphics g, Tree binaryTree, Node node, int treeHeight) {
        if (node == null) {
            return;
        }

        paintNode(g, valueToDepthMap.get(node.getValue()), node.getValue());

        int nextDeltaX = 0;// getMaxSiblingsWidth(valueToDepthMap.get(node.getValue())
                           // + 1) / 2;
        int nextDeltaY = calculateHeight(nextDeltaX);

        LOGGER.debug("nextDeltaX: {}, nextDeltaY: {}", nextDeltaX, nextDeltaY);

        if (node.getLeft() != null) {
            g.setColor(Color.BLACK);
            g.setColor(Color.BLUE);
            paintNode(g, binaryTree, node.getLeft(), treeHeight);
        }

        if (node.getRight() != null) {
            g.setColor(Color.BLACK);
            g.setColor(Color.GREEN);
            paintNode(g, binaryTree, node.getRight(), treeHeight);
        }

    }

    private void paintNode(Graphics g, Point start, int value) {

        g.fillOval(start.x, start.y, NODE_DIA, NODE_DIA);
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(value), start.x + NODE_DIA / 2, start.y + NODE_DIA / 2);

        LOGGER.info("Printed node {} at ({}, {})", value, start.x, start.y);

    }

    private int calculateHeight(int width) {
        return (int) Math.ceil(width * Math.tan(60 * Math.PI / 180));
    }

    private int getMaxNodes(int oneBasedNodeDepth) {
        return (int) Math.pow(2, oneBasedNodeDepth - 1);
    }

    private int getMaxSiblingsWidth(int oneBasedNodeDepth) {
        int maxNodes = getMaxNodes(oneBasedNodeDepth);
        return (maxNodes - 1) * (NODE_GAP + NODE_DIA);
    }

}
