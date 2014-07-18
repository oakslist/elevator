package by.epam.applications.beans;

public class InstanceAppCoordinates {
	
	private int x;
	private int y;
	
	public InstanceAppCoordinates(int xCoordinate, int yCoordinate) {
		this.x = xCoordinate;
		this.y = yCoordinate;
	}
	
	public InstanceAppCoordinates() {
		this(0, 0);	
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	

}
