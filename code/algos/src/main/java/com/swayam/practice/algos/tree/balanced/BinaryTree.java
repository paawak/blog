/*
 * Tree.java
 *
 * Created on 03-Dec-2016 8:13:27 PM
 *
 * Copyright (c) 2002 - 2008 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.practice.algos.tree.balanced;

/**
 * 
 * @author paawak
 */
public interface BinaryTree<E extends Comparable<E>> {

    void add(E element);

    int getHeight();

    int getBreadth();

    boolean isEmpty();

    void breadthFirstWalker(BreadthFirstTreeWalker<E> breadthFirstTreeWalker);

    void preOrderTreeWalker(PreOrderTreeWalker<E> preOrderTreeWalker);

}
