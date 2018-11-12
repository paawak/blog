package com.swayam.practice.algos.tree.balanced.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.practice.algos.tree.balanced.Tree;
import com.swayam.practice.algos.tree.balanced.Tree.Node;

public class BinaryTreeImageGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryTreeImageGenerator.class);

    private static final int TREE_GAP = 100;
    private static final int NODE_DIA = 40;
    private static final int NODE_GAP = 20;

    public BufferedImage getImage(Tree binaryTree) {
        int treeHeight = binaryTree.getHeight();
        int maxBaseWidth = getBaseWidth(1, treeHeight);
        int imageWidth = 2 * (TREE_GAP + maxBaseWidth) + NODE_DIA;
        int imageHeight = calculateTreeHeight(maxBaseWidth) + 2 * TREE_GAP;

        LOGGER.info("imageWidth: {}, imageHeight: {}", imageWidth, imageHeight);

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics g = image.getGraphics();
        g.fillRect(0, 0, imageWidth, imageHeight);

        // start from root
        g.setColor(Color.RED);

        paintNode(g, binaryTree, binaryTree.getRoot(), treeHeight, new Point(imageWidth / 2 - NODE_DIA / 2, TREE_GAP - NODE_DIA / 2));

        return image;
    }

    private void paintNode(Graphics g, Tree binaryTree, Node node, int treeHeight, Point start) {
        if (node == null) {
            return;
        }

        int heightOfNode = binaryTree.getHeight(node);

        LOGGER.debug("heightOfNode: {}", heightOfNode);

        paintNode(g, start, node.getValue());

        if (heightOfNode == 1) {
            return;
        }

        int nextDeltaX = getBaseWidth(heightOfNode - 1, treeHeight);
        int nextDeltaY = calculateTreeHeight(nextDeltaX);

        LOGGER.debug("nextDeltaX: {}, nextDeltaY: {}", nextDeltaX, nextDeltaY);

        if (node.getLeft() != null) {
            g.setColor(Color.BLACK);
            Point nextStart = new Point(start.x - nextDeltaX, start.y + nextDeltaY);
            g.drawLine(start.x, start.y, nextStart.x, nextStart.y);
            g.setColor(Color.BLUE);
            paintNode(g, binaryTree, node.getLeft(), treeHeight, nextStart);
        }

        if (node.getRight() != null) {
            g.setColor(Color.BLACK);
            Point nextStart = new Point(start.x + nextDeltaX, start.y + nextDeltaY);
            g.drawLine(start.x, start.y, nextStart.x, nextStart.y);
            g.setColor(Color.GREEN);
            paintNode(g, binaryTree, node.getRight(), treeHeight, nextStart);
        }

    }

    private int calculateTreeHeight(int baseWidth) {
        return (int) Math.ceil(baseWidth * Math.tan(60 * Math.PI / 180));
    }

    private void paintNode(Graphics g, Point start, int value) {

        g.fillOval(start.x, start.y, NODE_DIA, NODE_DIA);
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(value), start.x + NODE_DIA / 2, start.y + NODE_DIA / 2);

        LOGGER.info("Printed node {} at ({}, {})", value, start.x, start.y);

    }

    private int getMaxNodes(int nodeHeight, int treeHeight) {
        int nodeDepth = treeHeight - nodeHeight;
        return (int) Math.pow(2, nodeDepth);
    }

    private int getBaseWidth(int nodeHeight, int treeHeight) {
        int maxNodes = getMaxNodes(nodeHeight, treeHeight);
        return (maxNodes - 1) * (NODE_GAP + NODE_DIA) / 2;
    }

}
