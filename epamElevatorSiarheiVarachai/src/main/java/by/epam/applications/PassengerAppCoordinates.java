package by.epam.applications;

import by.epam.model.beans.Passenger;

public class PassengerAppCoordinates extends InstanceAppCoordinates {

	private Passenger passenger;
	private int currentStory;
	
	public PassengerAppCoordinates(Passenger passenger,int currentStory , int xCoordinate, int yCoordinate) {
		super(xCoordinate, yCoordinate);
		this.passenger = passenger;
		this.currentStory = currentStory;
	}
	
	public PassengerAppCoordinates(Passenger passenger, int currentStory) {
		this(passenger, currentStory, 0, 0);
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public int getCurrentStory() {
		return currentStory;
	}
	
}
