package by.epam.model.core;

import java.util.ArrayList;
import java.util.List;

import by.epam.ProgramConstants;
import by.epam.model.beans.Building;
import by.epam.model.beans.Elevator;
import by.epam.model.containers.ArrivalStoryContainer;
import by.epam.model.containers.DispatchStoryContainer;

public class MyBuilding extends Building {
    
	private List<DispatchStoryContainer> dispatchStoriesContainer;
	private List<ArrivalStoryContainer> arrivalStoriesContainer;
	private int passengersNumber;
	private Elevator elevator;
	
	public MyBuilding(int storiesNumber, int elevatorCapacity, int passengersNumber) {
		super(storiesNumber);
		this.passengersNumber = passengersNumber;
		// initial common container for dispatchStoriesContainer by storiesNumber
		this.dispatchStoriesContainer = new ArrayList<DispatchStoryContainer>();
		for (int i = ProgramConstants.INITIAL_STORY; i <= storiesNumber; i++) {
			dispatchStoriesContainer.add(new DispatchStoryContainer(i));
		}
		// initial common container for arrivalStoriesContainer by storiesNumber
		this.arrivalStoriesContainer = new ArrayList<ArrivalStoryContainer>();
		for (int i = ProgramConstants.INITIAL_STORY; i <= storiesNumber; i++) {
			arrivalStoriesContainer.add(new ArrivalStoryContainer(i));
		}		
		this.elevator = new Elevator(elevatorCapacity);
	}
	
	public int getPassengersNumber() {
		return passengersNumber;
	}

	public Elevator getElevator() {
		return elevator;
	}
	
	public List<DispatchStoryContainer> getDispatchStoriesContainer() {
		return dispatchStoriesContainer;
	}
	
	public List<ArrivalStoryContainer> getArrivalStoriesContainer() {
		return arrivalStoriesContainer;
	}

	public DispatchStoryContainer getDispatchStoryContainer(int storyNumber) {
		final int CORRECT_BY_ZERO_ELEMENT = 1;
		return dispatchStoriesContainer.get(storyNumber - CORRECT_BY_ZERO_ELEMENT);
	}
	
	public ArrivalStoryContainer getArrivalStoryContainer(int storyNumber) {
		final int CORRECT_BY_ZERO_ELEMENT = 1;
		return arrivalStoriesContainer.get(storyNumber - CORRECT_BY_ZERO_ELEMENT);
	}

}
