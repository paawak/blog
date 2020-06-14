package com.swayam.practice.algos.graph.undirected;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DepthFirstGraphWalker {

    private final Graph graph;
    private final int startVertex;
    private final boolean[] traversed;
    private final int[] endVertices;

    public DepthFirstGraphWalker(Graph graph, int startVertex) {
	this.graph = graph;
	this.startVertex = startVertex;
	traversed = new boolean[graph.getVerticesCount()];
	endVertices = initEndVertices(graph.getVerticesCount());
	traverse(startVertex);
    }

    public boolean hasPathTo(int endVertex) {
	return traversed[endVertex];
    }

    public List<Integer> getPath(int endVertex) {

	if (!hasPathTo(endVertex) || (startVertex == endVertex)) {
	    return Collections.emptyList();
	}

	List<Integer> paths = new ArrayList<>();

	int nextVertex = endVertex;

	do {
	    paths.add(nextVertex);
	    nextVertex = endVertices[nextVertex];
	} while (nextVertex != startVertex);

	paths.add(startVertex);

	return paths;

    }

    private void traverse(int currentVertex) {

	if (traversed[currentVertex]) {
	    return;
	}

	traversed[currentVertex] = true;

	graph.getAdjacentVertices(currentVertex).forEach(adjacentVertex -> {
	    endVertices[adjacentVertex] = currentVertex;
	    traverse(adjacentVertex);
	});

    }

    private int[] initEndVertices(int count) {
	int[] startVertices = new int[count];
	for (int i = 0; i < count; i++) {
	    startVertices[i] = -1;
	}
	return startVertices;
    }

    @Override
    public String toString() {
	return "DepthFirstGraphWalker [startVertex=" + startVertex + ", traversed=" + Arrays.toString(traversed) + ", endVertices=" + Arrays.toString(endVertices) + "]";
    }

}
