package by.epam.applications;

import by.epam.AppConstants;
import by.epam.model.beans.Passenger;

public class PassengerAppCoordinates extends InstanceAppCoordinates {

	private Passenger passenger;
	private int currentStory;
	private boolean onPosition;
	private int currentStep;
	
	public PassengerAppCoordinates(Passenger passenger,int currentStory , int xCoordinate, int yCoordinate) {
		super(xCoordinate, yCoordinate);
		this.passenger = passenger;
		this.currentStory = currentStory;
		this.onPosition = false;
		this.currentStep = AppConstants.APP_INITIALIZE_STEP_ONE;
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

	public boolean isOnPosition() {
		return onPosition;
	}

	public void setOnPosition(boolean onPosition) {
		this.onPosition = onPosition;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public void incCurrentStep() {
		this.currentStep++;
	}
	
}
