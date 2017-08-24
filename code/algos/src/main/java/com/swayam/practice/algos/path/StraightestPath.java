package com.swayam.practice.algos.path;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class StraightestPath {

	public static void main(String[] a) throws Exception {
		StraightestPath straightestPath = new StraightestPath();
		InputData inputData = straightestPath.getInputData();

		MatrixPosition startPosition = straightestPath.getPosition(inputData, (String value) -> {
			return value.equals("V");
		});

		MatrixPosition endPosition = straightestPath.getPosition(inputData, (String value) -> {
			return value.equals("H");
		});

		System.out.println("startPosition: " + startPosition + ", endPosition: " + endPosition);

		Set<MatrixPosition> traversedPositions = new HashSet<>();

		straightestPath.traverse(startPosition, endPosition, inputData, traversedPositions);

	}

	private void traverse(MatrixPosition startPosition, MatrixPosition endPosition, InputData inputData,
			Set<MatrixPosition> traversedPositions) {
		List<PathDirection> pathPriority = getTraversalPriority(startPosition, endPosition);

		List<GoPoint> goPoints = countPathChange(startPosition, inputData, pathPriority);

		for (GoPoint goPoint : goPoints) {
			goPoint.setStarted(true);
			MatrixPosition currentPosition = goPoint.getPosition();
			if (traversedPositions.contains(currentPosition)) {
				continue;
			}
			traversedPositions.add(currentPosition);
			traverse(currentPosition, endPosition, inputData, traversedPositions);
		}
	}

	private List<GoPoint> countPathChange(MatrixPosition position, InputData inputData,
			List<PathDirection> pathPriority) {

		List<GoPoint> goPoints = new ArrayList<>();

		List<PathDirection> pathOrdered = new ArrayList<>();
		pathOrdered.addAll(pathPriority);

		for (PathDirection pathDirection : PathDirection.values()) {
			if (pathPriority.contains(pathDirection)) {
				continue;
			}
			pathOrdered.add(pathDirection);
		}

		for (PathDirection pathDirection : pathOrdered) {
			Optional<MatrixPosition> nextPosition = goOneUnit(position, pathDirection, inputData);
			if (nextPosition.isPresent()) {
				boolean end = applyTraverseLogic(nextPosition.get(), inputData, goPoints);
				if (end) {
					return Collections.emptyList();
				}
			}
		}

		return Collections.unmodifiableList(goPoints);

	}

	private List<PathDirection> getTraversalPriority(MatrixPosition position1, MatrixPosition position2) {

		List<PathDirection> priority = new ArrayList<>();

		// horizontal
		if (position1.getColumnIndex() < position2.getColumnIndex()) {
			priority.add(PathDirection.RIGHT);
		} else if (position1.getColumnIndex() > position2.getColumnIndex()) {
			priority.add(PathDirection.LEFT);
		}

		// vertical
		if (position1.getRowIndex() < position2.getRowIndex()) {
			priority.add(PathDirection.DOWN);
		} else if (position1.getRowIndex() > position2.getRowIndex()) {
			priority.add(PathDirection.UP);
		}

		return Collections.unmodifiableList(priority);

	}

	private boolean applyTraverseLogic(MatrixPosition position, InputData inputData, List<GoPoint> goPoints) {
		String value = inputData.getMatrix()[position.getRowIndex()][position.getColumnIndex()];
		// if *, then dont proceed
		if (value.equals("*")) {
			System.out.println("Found *, NO GO");
		} else if (value.equals("V")) {
			System.out.println("Found V, NO GO");
		} else if (value.equals(".")) {
			// go forward
			goPoints.add(new GoPoint(position));
		} else if (value.equals("H")) {
			// terminate
			System.out.println("TERMINATING AT (" + position.getRowIndex() + ", " + position.getColumnIndex() + ")");
			return true;
		} else {
			throw new IllegalArgumentException("unknown value " + value + " encountered");
		}

		return false;
	}

	private Optional<MatrixPosition> goOneUnit(MatrixPosition position, PathDirection pathDirection,
			InputData inputData) {

		switch (pathDirection) {
		case LEFT:
			if (position.getColumnIndex() == 0) {
				return Optional.empty();
			}
			return Optional.of(new MatrixPosition(position.getRowIndex(), position.getColumnIndex() - 1));

		case RIGHT:
			if (position.getColumnIndex() == inputData.getColumnSize() - 1) {
				return Optional.empty();
			}
			return Optional.of(new MatrixPosition(position.getRowIndex(), position.getColumnIndex() + 1));

		case UP:
			if (position.getRowIndex() == 0) {
				return Optional.empty();
			}
			return Optional.of(new MatrixPosition(position.getRowIndex() - 1, position.getColumnIndex()));
		case DOWN:
			if (position.getRowIndex() == inputData.getRowSize() - 1) {
				return Optional.empty();
			}
			return Optional.of(new MatrixPosition(position.getRowIndex() + 1, position.getColumnIndex()));

		default:
			throw new IllegalArgumentException("unknown value of " + PathDirection.class + " " + pathDirection);
		}

	}

	private MatrixPosition getPosition(InputData inputData, Function<String, Boolean> valueMatch) {
		for (int rowIndex = 0; rowIndex < inputData.getRowSize(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < inputData.getColumnSize(); columnIndex++) {
				String cellValue = inputData.getMatrix()[rowIndex][columnIndex];
				if (valueMatch.apply(cellValue)) {
					return new MatrixPosition(rowIndex, columnIndex);
				}
			}
		}
		throw new IllegalArgumentException("The value `V` could not be found in the input matrix");
	}

	private InputData getInputData() throws Exception {
		Path filePath = Paths.get(
				StraightestPath.class.getResource("/com/swayam/practice/algos/path/straightest_path_2.txt").toURI());

		List<String> allLines = Files.readAllLines(filePath);
		String[] rowCloumn = allLines.get(0).split("\\s");
		int rows = Integer.parseInt(rowCloumn[0]);
		int columns = Integer.parseInt(rowCloumn[1]);

		String[][] matrix = new String[rows][columns];

		for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
			char[] lineArray = allLines.get(rowIndex + 1).toCharArray();
			for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
				matrix[rowIndex][columnIndex] = Character.toString(lineArray[columnIndex]);
				System.out.print(matrix[rowIndex][columnIndex] + " ");
			}
			System.out.println();
		}

		return new InputData(rows, columns, matrix);

	}

}
