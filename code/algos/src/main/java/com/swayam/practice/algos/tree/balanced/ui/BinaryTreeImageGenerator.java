package com.swayam.practice.algos.tree.balanced.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.practice.algos.tree.balanced.BinaryTree;
import com.swayam.practice.algos.tree.balanced.BreadthFirstTreeWalker;
import com.swayam.practice.algos.tree.balanced.PreOrderTreeWalker;
import com.swayam.practice.algos.tree.balanced.PreOrderTreeWalker.NodeType;;

public class BinaryTreeImageGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryTreeImageGenerator.class);

    private static final int INCLINE_ANGLE_IN_DEGREES = 45;

    private static final int TREE_GAP = 50;
    private static final int NODE_GAP = 40;
    private static final int NODE_DIA = 40;

    public BufferedImage getImage(BinaryTree<Integer> binaryTree) {

        int treeHeight = binaryTree.getHeight();
        int maxBaseWidth = getMaxSiblingsWidth(treeHeight);
        int imageWidth = 2 * TREE_GAP + maxBaseWidth + NODE_DIA;
        int imageHeight = calculateHeight(maxBaseWidth / 2) + 2 * TREE_GAP + NODE_DIA;

        Map<Integer, Point> nodeLocationMap = new HashMap<>();

        binaryTree.breadthFirstWalker(new BreadthFirstTreeWalker<Integer>() {

            private int currentX = -1;
            private int currentY = -1;

            @Override
            public void newElement(Integer value) {
                nodeLocationMap.put(value, new Point(currentX, currentY));
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
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, imageWidth, imageHeight);

        binaryTree.preOrderTreeWalker(new PreOrderTreeWalker<Integer>() {

            @Override
            public void treeNode(Integer value, NodeType nodeType, boolean hasLeftChild, boolean hasRightChild, Integer leftChildValue,
                    Integer rightChildValue) {

                Point start = nodeLocationMap.get(value);

                paintNode(g, nodeType, start, value);

                if (hasLeftChild) {
                    Point arrowEnd = nodeLocationMap.get(leftChildValue);
                    drawArrow(g, start, arrowEnd);
                }

                if (hasRightChild) {
                    Point arrowEnd = nodeLocationMap.get(rightChildValue);
                    drawArrow(g, start, arrowEnd);
                }

            }
        });

        return image;
    }

    private void drawArrow(Graphics g, Point arrowStart, Point arrowEnd) {
        g.setColor(Color.BLACK);
        g.drawLine(arrowStart.x + NODE_DIA / 2, arrowStart.y + NODE_DIA, arrowEnd.x + NODE_DIA / 2, arrowEnd.y + NODE_DIA / 2);
    }

    private void paintNode(Graphics g, NodeType nodeType, Point start, int value) {

        // set the color of the node
        switch (nodeType) {
        case ROOT:
            g.setColor(Color.CYAN);
            break;

        case LEFT_CHILD:
            g.setColor(Color.GREEN);
            break;

        case RIGHT_CHILD:
            g.setColor(Color.YELLOW);
            break;

        default:
            throw new UnsupportedOperationException();
        }

        g.fillOval(start.x, start.y, NODE_DIA, NODE_DIA);
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(value), start.x + NODE_DIA / 2, start.y + NODE_DIA / 2);

        LOGGER.info("Printed node {} at ({}, {})", value, start.x, start.y);
    }

    private int calculateHeight(int width) {
        return (int) Math.ceil(width * Math.tan(INCLINE_ANGLE_IN_DEGREES * Math.PI / 180));
    }

    private int getMaxNodes(int oneBasedNodeDepth) {
        return (int) Math.pow(2, oneBasedNodeDepth - 1);
    }

    private int getMaxSiblingsWidth(int oneBasedNodeDepth) {
        int maxNodes = getMaxNodes(oneBasedNodeDepth);
        return (maxNodes - 1) * (NODE_GAP + NODE_DIA);
    }

}
