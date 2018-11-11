package com.swayam.practice.algos.tree.balanced.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.swayam.practice.algos.tree.balanced.BinaryTree;
import com.swayam.practice.algos.tree.balanced.PreOrderTreeWalker;

public class BinaryTreeImageGenerator {

    private static final int TREE_GAP = 100;
    private static final int NODE_DIA = 100;
    private static final int NODE_GAP = 70;

    private int nodeStartX;

    public BufferedImage getImage(BinaryTree<Integer> binaryTree) {
        int treeHeight = binaryTree.getHeight();
        int treeBreadth = binaryTree.getBreadth();
        int imageWidth = treeBreadth * NODE_DIA + (treeBreadth + 1) * NODE_GAP + 2 * TREE_GAP;
        int imageHeight = treeHeight * NODE_DIA + (treeHeight + 1) * NODE_GAP + 2 * TREE_GAP;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        int midPointX = imageWidth / 2 - NODE_DIA;

        nodeStartX = midPointX;

        Graphics g = image.getGraphics();
        g.fillRect(0, 0, imageWidth, imageHeight);

        binaryTree.preOrderTreeWalker(new PreOrderTreeWalker() {

            @Override
            public void treeNode(int value, NodeType nodeType, boolean hasLeftChild, boolean hasRightChild, int heightOfNode) {

                int nodeStartY = TREE_GAP + NODE_DIA + (treeHeight - heightOfNode) * (NODE_DIA + NODE_GAP);

                if (nodeType == NodeType.ROOT) {
                    g.setColor(Color.RED);
                } else if (nodeType == NodeType.LEFT_CHILD) {
                    g.setColor(Color.BLUE);
                } else if (nodeType == NodeType.RIGHT_CHILD) {
                    g.setColor(Color.GREEN);
                }

                paintNode(g, new Point(nodeStartX, nodeStartY), value);

                g.setColor(Color.BLACK);

                Point lowerMidPointOfNode = new Point(nodeStartX + NODE_DIA / 2, nodeStartY + NODE_DIA);

                if (hasLeftChild) {
                    g.drawLine(lowerMidPointOfNode.x, lowerMidPointOfNode.y, lowerMidPointOfNode.x - (NODE_GAP + NODE_DIA / 2),
                            lowerMidPointOfNode.y + NODE_GAP);
                }

                if (hasRightChild) {
                    g.drawLine(lowerMidPointOfNode.x, lowerMidPointOfNode.y, lowerMidPointOfNode.x + (NODE_GAP + NODE_DIA / 2),
                            lowerMidPointOfNode.y + NODE_GAP);
                }

                if (nodeType == NodeType.LEFT_CHILD) {
                    nodeStartX = nodeStartX - (NODE_DIA + NODE_GAP);
                } else if (nodeType == NodeType.RIGHT_CHILD) {
                    nodeStartX = nodeStartX + (NODE_DIA + NODE_GAP);
                }

            }
        });

        return image;
    }

    private void paintNode(Graphics g, Point start, Integer value) {

        g.fillOval(start.x, start.y, NODE_DIA, NODE_DIA);
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(value), start.x + NODE_DIA / 2, start.y + NODE_DIA / 2);

    }

}
