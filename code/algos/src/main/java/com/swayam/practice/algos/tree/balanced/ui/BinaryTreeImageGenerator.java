package com.swayam.practice.algos.tree.balanced.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.swayam.practice.algos.tree.balanced.BinaryTree;
import com.swayam.practice.algos.tree.balanced.BreadthFirstTreeWalker;

public class BinaryTreeImageGenerator {

    private static final int TREE_GAP = 100;
    private static final int NODE_DIA = 100;
    private static final int NODE_GAP = 70;

    private int nodeStartX;
    private int nodeStartY;

    public BufferedImage getImage(BinaryTree<Integer> binaryTree) {
        int treeHeight = binaryTree.getHeight();
        int treeBreadth = binaryTree.getBreadth();
        int imageWidth = treeBreadth * NODE_DIA + (treeBreadth + 1) * NODE_GAP + 2 * TREE_GAP;
        int imageHeight = treeHeight * NODE_DIA + (treeHeight + 1) * NODE_GAP + 2 * TREE_GAP;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        int midPointX = imageWidth / 2 - NODE_DIA;

        nodeStartX = midPointX;
        nodeStartY = TREE_GAP - NODE_DIA;

        Graphics g = image.getGraphics();
        g.fillRect(0, 0, imageWidth, imageHeight);

        binaryTree.breadthFirstWalker(new BreadthFirstTreeWalker() {

            @Override
            public void newElement(int value) {
                nodeStartX = paintNode(g, new Point(nodeStartX, nodeStartY), value);
            }

            @Override
            public void depthChange(int depth) {
                int gap = NODE_DIA + NODE_GAP;
                nodeStartY += gap;
                nodeStartX = midPointX - gap;
            }
        });

        return image;
    }

    private int paintNode(Graphics g, Point start, Integer value) {

        g.setColor(Color.BLUE);
        g.fillOval(start.x, start.y, NODE_DIA, NODE_DIA);
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(value), start.x + NODE_DIA / 2, start.y + NODE_DIA / 2);

        return start.x + NODE_DIA + NODE_GAP;

    }

}
