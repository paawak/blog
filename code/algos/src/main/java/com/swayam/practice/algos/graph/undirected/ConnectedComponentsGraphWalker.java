package com.swayam.practice.algos.graph.undirected;

import java.util.Arrays;

public class ConnectedComponentsGraphWalker {

    private final Graph graph;
    private final boolean[] traversed;
    private final int[] connectedComponentIndex;
    private final int connectedComponentCount;

    public ConnectedComponentsGraphWalker(Graph graph) {
	this.graph = graph;
	traversed = new boolean[graph.getVerticesCount()];
	connectedComponentIndex = initConnectedComponentIndex(graph.getVerticesCount());
	connectedComponentCount = traverse();
    }

    public boolean isConnected(int vertex1, int vertex2) {
	return connectedComponentIndex[vertex1] == connectedComponentIndex[vertex2];
    }

    public int getConnectedComponentCount() {
	return connectedComponentCount;
    }

    private int traverse() {

	int connectedComponentId = 0;

	for (int initialVertex = 0; initialVertex < graph.getVerticesCount(); initialVertex++) {
	    connectedComponentId = markConnectedComponents(connectedComponentId, initialVertex);
	}

	return connectedComponentId;

    }

    private int markConnectedComponents(int connectedComponentId, int currentVertex) {

	if (traversed[currentVertex]) {
	    return connectedComponentId;
	}

	traversed[currentVertex] = true;
	connectedComponentIndex[currentVertex] = connectedComponentId;

	for (int adjacentVertex : graph.getAdjacentVertices(currentVertex)) {
	    connectedComponentIndex[adjacentVertex] = connectedComponentId;
	    markConnectedComponents(connectedComponentId, adjacentVertex);
	}

	return connectedComponentId + 1;

    }

    private int[] initConnectedComponentIndex(int count) {
	int[] startVertices = new int[count];
	for (int i = 0; i < count; i++) {
	    startVertices[i] = -1;
	}
	return startVertices;
    }

    @Override
    public String toString() {
	return "ConnectedComponentsGraphWalker [traversed=" + Arrays.toString(traversed) + ", connectedComponentIndex=" + Arrays.toString(connectedComponentIndex) + "]";
    }

}
