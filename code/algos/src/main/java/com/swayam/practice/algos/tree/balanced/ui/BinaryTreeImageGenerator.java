package com.swayam.practice.algos.tree.balanced.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.swayam.practice.algos.tree.balanced.BinaryTree;
import com.swayam.practice.algos.tree.balanced.BreadthFirstTreeWalker;

public class BinaryTreeImageGenerator {

    private static final int TREE_GAP = 100;
    private static final int NODE_DIA = 30;
    private static final int NODE_GAP = 30;

    private int nodeStartX;
    private int nodeStartY;

    public BufferedImage getImage(BinaryTree<Integer> binaryTree) {
        int treeHeight = binaryTree.getHeight();
        int treeBreadth = binaryTree.getBreadth();
        int imageWidth = treeBreadth * NODE_DIA + (treeBreadth + 1) * NODE_GAP + 2 * TREE_GAP;
        int imageHeight = treeHeight * NODE_DIA + (treeHeight + 1) * NODE_GAP + 2 * TREE_GAP;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        nodeStartX = imageWidth / 2 - NODE_DIA;
        nodeStartY = TREE_GAP - NODE_DIA;

        Graphics g = image.getGraphics();

        binaryTree.breadthFirstWalker(new BreadthFirstTreeWalker() {

            @Override
            public void newElement(int value) {
                nodeStartX = paintNode(g, new Point(nodeStartX, nodeStartY), value);
            }

            @Override
            public void depthChange(int depth) {
                nodeStartY += NODE_DIA + NODE_GAP;
                nodeStartX = imageWidth / 2 - NODE_DIA;
            }
        });

        return image;
    }

    private int paintNode(Graphics g, Point start, Integer value) {

        g.drawOval(start.x, start.y, NODE_DIA, NODE_DIA);
        g.drawString(Integer.toString(value), start.x + NODE_DIA / 2, start.y + NODE_DIA / 2);

        return start.x + NODE_DIA + NODE_GAP;

    }

}
