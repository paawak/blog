/*
 * BinarySearchTree.java
 *
 * Created on 10-Dec-2016 8:22:01 PM
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

package com.swayam.practice.algos.tree;

/**
 * 
 * @author paawak
 */
public interface BinarySearchTree<E extends Comparable<E>> extends Tree<E> {

    void remove(E element);

}
