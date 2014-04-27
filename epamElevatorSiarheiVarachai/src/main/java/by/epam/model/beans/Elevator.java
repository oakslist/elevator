package by.epam.model.beans;

import by.epam.constants.ProgramConstants;
import by.epam.model.containers.ElevatorContainer;

public class Elevator {
	
	private final int ELEVATOR_CAPACITY;
	
	private ElevatorContainer elevatorContainer;
	private int currentStory;

	public Elevator(int elevatorCapacity) {
		this.ELEVATOR_CAPACITY = elevatorCapacity;
		this.elevatorContainer = new ElevatorContainer();
		this.currentStory = ProgramConstants.INITIAL_STORY;
	}

	public int getElevatorCapacity() {
		return ELEVATOR_CAPACITY;
	}
	
	public ElevatorContainer getElevatorContainer() {
		return elevatorContainer;
	}

	public int getCurrentStory() {
		return currentStory;
	}

	public void incrementCurrentStory() {
		this.currentStory++;
	}
	
	public void decrementCurrentStory() {
		this.currentStory--;
	}
	
}
