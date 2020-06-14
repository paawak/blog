package com.swayam.practice.algos.graph.undirected;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Graph {

    private final int verticesCount;
    private final Map<Integer, List<Integer>> verticesIndex;

    public Graph(int verticesCount) {
	this.verticesCount = verticesCount;
	verticesIndex = createVerticesIndex(verticesCount);
    }

    public void addEdge(int startVertex, int endVertex) {
	List<Integer> adjacentVertices = verticesIndex.get(startVertex);
	adjacentVertices.add(endVertex);
    }

    public List<Integer> getAdjacentVertices(int vertex) {
	return Collections.unmodifiableList(verticesIndex.get(vertex));
    }

    public int getVerticesCount() {
	return verticesCount;
    }

    public static Graph from(Path path) throws IOException {
	List<String> lines = Files.readAllLines(path);
	Graph graph = new Graph(Integer.parseInt(lines.get(0)));
	for (int count = 2; count < lines.size(); count++) {
	    String line = lines.get(count).trim();
	    if (line.isEmpty()) {
		continue;
	    }
	    String[] tokens = line.split("\\s");

	    graph.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
	}
	return graph;
    }

    private Map<Integer, List<Integer>> createVerticesIndex(int verticesCount) {
	return IntStream.range(0, verticesCount).mapToObj(count -> count).collect(Collectors.toMap(Function.identity(), count -> new ArrayList<Integer>()));
    }

    @Override
    public String toString() {
	return "Graph [verticesCount=" + verticesCount + ", verticesIndex=" + verticesIndex + "]";
    }

}
