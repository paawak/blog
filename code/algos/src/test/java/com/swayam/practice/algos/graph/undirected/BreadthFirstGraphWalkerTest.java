package com.swayam.practice.algos.graph.undirected;

import java.nio.file.Paths;
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
	graph = Graph.from(Paths.get(DepthFirstGraphWalkerTest.class.getResource("/com/swayam/practice/algos/graph/undirected/graph-bfs-1.txt").toURI()));
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
	System.out.println(result);
    }

}
