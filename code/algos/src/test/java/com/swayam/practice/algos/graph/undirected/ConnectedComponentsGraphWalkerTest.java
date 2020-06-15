package com.swayam.practice.algos.graph.undirected;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConnectedComponentsGraphWalkerTest {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectedComponentsGraphWalkerTest.class);

    private Graph graph;

    @BeforeEach
    void setUp() throws Exception {
	graph = Graph.from(Paths.get(DepthFirstGraphWalkerTest.class.getResource("/com/swayam/practice/algos/graph/undirected/graph-1.txt").toURI()));
	LOG.info("Graph:\n\n {}", graph);
    }

    @Test
    void testIsConnected_1() {
	// given
	ConnectedComponentsGraphWalker testClass = new ConnectedComponentsGraphWalker(graph);
	LOG.info("BreadthFirstWalker:\n\n {}", testClass);

	// when
	boolean result = testClass.isConnected(0, 7);

	// then
	assertFalse(result);
    }

    @Test
    void testIsConnected_2() {
	// given
	ConnectedComponentsGraphWalker testClass = new ConnectedComponentsGraphWalker(graph);
	LOG.info("BreadthFirstWalker:\n\n {}", testClass);

	// when
	boolean result = testClass.isConnected(8, 7);

	// then
	assertTrue(result);
    }

    @Test
    void testIsConnected_3() {
	// given
	ConnectedComponentsGraphWalker testClass = new ConnectedComponentsGraphWalker(graph);
	LOG.info("BreadthFirstWalker:\n\n {}", testClass);

	// when
	boolean result = testClass.isConnected(0, 3);

	// then
	assertTrue(result);
    }

    @Test
    void testGetConnectedComponentCount() {
	// given
	ConnectedComponentsGraphWalker testClass = new ConnectedComponentsGraphWalker(graph);
	LOG.info("BreadthFirstWalker:\n\n {}", testClass);

	// when
	int result = testClass.getConnectedComponentCount();

	// then
	assertEquals(3, result);
    }

}
