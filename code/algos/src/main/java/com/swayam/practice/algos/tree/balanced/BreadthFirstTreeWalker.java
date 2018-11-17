package com.swayam.practice.algos.tree.balanced;

public interface BreadthFirstTreeWalker<E extends Comparable<E>> {

    void depthChange(int depth);

    void newElement(E value);

}
