package com.swayam.practice.algos.graph.undirected;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DepthFirstGraphWalkerTest {

    private static final Logger LOG = LoggerFactory.getLogger(DepthFirstGraphWalkerTest.class);

    @Test
    void testStartTraversal() throws IOException, URISyntaxException {
	Graph graph = Graph.from(Paths.get(GraphTest.class.getResource("/com/swayam/practice/algos/graph/undirected/undirected-graph.txt").toURI()));
	LOG.info("Graph:\n\n {}", graph);
	DepthFirstGraphWalker walker = new DepthFirstGraphWalker(graph);
	walker.startTraversal();
	LOG.info("DepthFirstWalker:\n\n {}", walker);
    }

}
