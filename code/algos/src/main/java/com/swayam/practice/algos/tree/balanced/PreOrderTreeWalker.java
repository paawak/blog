package com.swayam.practice.algos.tree.balanced;

public interface PreOrderTreeWalker<E extends Comparable<E>> {

    void treeNode(E value, NodeType nodeType, boolean hasLeftChild, boolean hasRightChild, int heightOfNode);

    static enum NodeType {
        ROOT, LEFT_CHILD, RIGHT_CHILD;
    }

}
