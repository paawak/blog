package com.swayam.practice.algos.tree.balanced.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.tree.DefaultMutableTreeNode;

import com.swayam.practice.algos.tree.balanced.BinaryTree;

public class BinaryTreeImageGenerator {

    private static final int TREE_GAP = 100;
    private static final int NODE_DIA = 30;
    private static final int NODE_GAP = 30;

    public BufferedImage getImage(BinaryTree<Integer> binaryTree) {
        int treeHeight = binaryTree.getHeight();
        int treeBreadth = binaryTree.getBreadth();
        int imageWidth = treeBreadth * NODE_DIA + (treeBreadth + 1) * NODE_GAP + 2 * TREE_GAP;
        int imageHeight = treeHeight * NODE_DIA + (treeHeight + 1) * NODE_GAP + 2 * TREE_GAP;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        int nodeStartX = imageWidth / 2 - NODE_DIA;
        int nodeStartY = TREE_GAP - NODE_DIA;

        DefaultMutableTreeNode swingRootNode = binaryTree.getSwingTree();

        Graphics g = image.getGraphics();

        // draw root
        nodeStartX = paintNode(g, new Point(nodeStartX, nodeStartY), swingRootNode);

        // draw its children
        for (int depth = 0; depth < swingRootNode.getChildCount(); depth++) {
            DefaultMutableTreeNode swingSameDepthParentNode = (DefaultMutableTreeNode) swingRootNode.getChildAt(depth);
            nodeStartY += NODE_DIA + NODE_GAP;

            for (int childIndex = 0; childIndex < swingSameDepthParentNode.getChildCount(); childIndex++) {
                nodeStartX = paintNode(g, new Point(nodeStartX, nodeStartY), (DefaultMutableTreeNode) swingSameDepthParentNode.getChildAt(childIndex));
            }
        }

        return image;
    }

    private int paintNode(Graphics g, Point start, DefaultMutableTreeNode node) {

        g.drawOval(start.x, start.y, NODE_DIA, NODE_DIA);
        g.drawString(node.getUserObject().toString(), start.x + NODE_DIA / 2, start.y + NODE_DIA / 2);

        return start.x + NODE_DIA + NODE_GAP;

    }

}
