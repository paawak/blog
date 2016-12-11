/*
 * NodeBasedBinarySearchTreeTest.java
 *
 * Created on 11-Dec-2016 6:53:04 PM
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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author paawak
 */
public class NodeBasedBinarySearchTreeTest {

    @Test
    public void testGetSortedElementsAsc() {
        // given
        List<Integer> expected = Arrays.asList(4, 5, 6, 8, 10, 11, 17, 19, 31, 43, 49);
        NodeBasedBinarySearchTree<Integer> testClass = new NodeBasedBinarySearchTree<>();
        testClass.add(6);
        testClass.add(11);
        testClass.add(8);
        testClass.add(19);
        testClass.add(4);
        testClass.add(10);
        testClass.add(5);
        testClass.add(17);
        testClass.add(43);
        testClass.add(49);
        testClass.add(31);

        // when
        List<Integer> result = testClass.getSortedElementsAsc();

        // then
        assertEquals(expected, result);
    }

}
