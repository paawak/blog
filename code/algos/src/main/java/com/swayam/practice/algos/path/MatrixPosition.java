package com.swayam.practice.algos.path;

public class MatrixPosition {

	private final int rowIndex;
	private final int columnIndex;

	public MatrixPosition(int rowIndex, int columnIndex) {
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columnIndex;
		result = prime * result + rowIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatrixPosition other = (MatrixPosition) obj;
		if (columnIndex != other.columnIndex)
			return false;
		if (rowIndex != other.rowIndex)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MatrixPosition [rowIndex=" + rowIndex + ", columnIndex=" + columnIndex + "]";
	}

}
