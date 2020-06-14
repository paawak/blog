package com.swayam.practice.algos.graph.undirected;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class BreadthFirstGraphWalker {

    private final Graph graph;
    private final int startVertex;
    private final boolean[] traversed;
    private final int[] endVertices;

    public BreadthFirstGraphWalker(Graph graph, int startVertex) {
	this.graph = graph;
	this.startVertex = startVertex;
	traversed = new boolean[graph.getVerticesCount()];
	endVertices = initEndVertices(graph.getVerticesCount());

	traverse();
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

    private void traverse() {

	Queue<Integer> queue = new ArrayBlockingQueue<>(graph.getVerticesCount());

	queue.add(startVertex);
	traversed[startVertex] = true;

	while (!queue.isEmpty()) {

	    int currentVertex = queue.remove();

	    graph.getAdjacentVertices(currentVertex).forEach(adjacentVertex -> {
		if (traversed[adjacentVertex]) {
		    return;
		}
		traversed[adjacentVertex] = true;
		endVertices[adjacentVertex] = currentVertex;
		queue.add(adjacentVertex);
	    });

	}

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
	return "BreadthFirstGraphWalker [startVertex=" + startVertex + ", traversed=" + Arrays.toString(traversed) + ", endVertices=" + Arrays.toString(endVertices) + "]";
    }

}
