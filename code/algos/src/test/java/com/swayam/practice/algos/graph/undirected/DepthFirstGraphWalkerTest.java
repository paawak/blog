package com.swayam.practice.algos.graph.undirected;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DepthFirstGraphWalkerTest {

    private static final Logger LOG = LoggerFactory.getLogger(DepthFirstGraphWalkerTest.class);

    private Graph graph;

    @BeforeEach
    void setup() throws IOException, URISyntaxException {
	graph = Graph.from(Paths.get(DepthFirstGraphWalkerTest.class.getResource("/com/swayam/practice/algos/graph/undirected/graph-dfs-1.txt").toURI()));
	LOG.info("Graph:\n\n {}", graph);
    }

    @Test
    void testGetPath_1() {
	// given
	DepthFirstGraphWalker testClass = new DepthFirstGraphWalker(graph, 0);
	LOG.info("DepthFirstWalker:\n\n {}", testClass);

	// when
	List<Integer> result = testClass.getPath(0);

	// then
	assertTrue(result.isEmpty());
    }

    @Test
    void testGetPath_2() {
	// given
	DepthFirstGraphWalker testClass = new DepthFirstGraphWalker(graph, 0);
	LOG.info("DepthFirstWalker:\n\n {}", testClass);

	// when
	List<Integer> result = testClass.getPath(7);

	// then
	assertTrue(result.isEmpty());
    }

    @Test
    void testGetPath_3() {
	// given
	DepthFirstGraphWalker testClass = new DepthFirstGraphWalker(graph, 0);
	LOG.info("DepthFirstWalker:\n\n {}", testClass);

	// when
	List<Integer> result = testClass.getPath(4);

	// then
	assertEquals(Arrays.asList(4, 6, 0), result);
    }

    @Test
    void testGetPath_4() {
	// given
	DepthFirstGraphWalker testClass = new DepthFirstGraphWalker(graph, 0);
	LOG.info("DepthFirstWalker:\n\n {}", testClass);

	// when
	List<Integer> result = testClass.getPath(3);

	// then
	assertEquals(Arrays.asList(3, 5, 0), result);
    }

}
