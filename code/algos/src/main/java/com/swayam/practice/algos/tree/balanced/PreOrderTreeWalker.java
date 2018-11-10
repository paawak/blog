package com.swayam.practice.algos.tree.balanced;

public interface PreOrderTreeWalker {

    void treeNode(int value, NodeType nodeType, boolean hasLeftChild, boolean hasRightChild);

    static enum NodeType {
        ROOT, LEFT_CHILD, RIGHT_CHILD;
    }

}
