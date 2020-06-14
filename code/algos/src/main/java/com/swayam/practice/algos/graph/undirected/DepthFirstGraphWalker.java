package com.swayam.practice.algos.graph.undirected;

import java.util.Arrays;

public class DepthFirstGraphWalker {

    private final Graph graph;
    private final boolean[] traversed;
    private final int[] startVertices;

    public DepthFirstGraphWalker(Graph graph) {
	this.graph = graph;
	traversed = new boolean[graph.getVerticesCount()];
	startVertices = initStartVertices(graph.getVerticesCount());
    }

    public void startTraversal() {
	traverse(0);
    }

    private void traverse(int vertex) {

	if (traversed[vertex]) {
	    return;
	}

	traversed[vertex] = true;

	graph.getAdjacentVertices(vertex).forEach(adjacentVertex -> {
	    startVertices[adjacentVertex] = vertex;
	    traverse(adjacentVertex);
	});

    }

    private int[] initStartVertices(int count) {
	int[] startVertices = new int[count];
	for (int i = 0; i < count; i++) {
	    startVertices[i] = -1;
	}
	return startVertices;
    }

    @Override
    public String toString() {
	return "DepthFirstGraphWalker [traversed=" + Arrays.toString(traversed) + ", startVertices=" + Arrays.toString(startVertices) + "]";
    }

}
