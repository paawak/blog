package com.swayam.practice.algos.path;

public class GoPoint {

	private final MatrixPosition position;

	private boolean started;
	private boolean normalExit;

	public GoPoint(MatrixPosition position) {
		this.position = position;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isNormalExit() {
		return normalExit;
	}

	public void setNormalExit(boolean normalExit) {
		this.normalExit = normalExit;
	}

	public MatrixPosition getPosition() {
		return position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (normalExit ? 1231 : 1237);
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + (started ? 1231 : 1237);
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
		GoPoint other = (GoPoint) obj;
		if (normalExit != other.normalExit)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (started != other.started)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GoPoint [position=" + position + ", started=" + started + ", normalExit=" + normalExit + "]";
	}

}
