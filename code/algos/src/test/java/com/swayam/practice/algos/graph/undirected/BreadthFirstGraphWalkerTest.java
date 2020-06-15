package com.swayam.practice.algos.graph.undirected;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BreadthFirstGraphWalkerTest {

    private static final Logger LOG = LoggerFactory.getLogger(BreadthFirstGraphWalkerTest.class);

    private Graph graph;

    @BeforeEach
    void setUp() throws Exception {
	graph = Graph.from(Paths.get(DepthFirstGraphWalkerTest.class.getResource("/com/swayam/practice/algos/graph/undirected/graph-2.txt").toURI()));
	LOG.info("Graph:\n\n {}", graph);
    }

    @Test
    void testGetPath() {
	// given
	BreadthFirstGraphWalker testClass = new BreadthFirstGraphWalker(graph, 0);
	LOG.info("BreadthFirstWalker:\n\n {}", testClass);

	// when
	List<Integer> result = testClass.getPath(4);

	// then
	assertEquals(Arrays.asList(4, 2, 0), result);
    }

}
