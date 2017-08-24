package com.swayam.practice.algos.path;

public class InputData {

	private final int rowSize;
	private final int columnSize;
	private final String[][] matrix;

	public InputData(int rowSize, int columnSize, String[][] matrix) {
		this.rowSize = rowSize;
		this.columnSize = columnSize;
		this.matrix = matrix;
	}

	public int getRowSize() {
		return rowSize;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public String[][] getMatrix() {
		return matrix;
	}

}
