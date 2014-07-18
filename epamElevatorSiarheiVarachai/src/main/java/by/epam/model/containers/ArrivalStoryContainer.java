package by.epam.model.containers;

import java.util.ArrayList;
import java.util.List;

import by.epam.model.beans.Passenger;

public class ArrivalStoryContainer extends StoryContainer {

	private List<Passenger> passengers;

	public ArrivalStoryContainer(int containerStory) {
		super(containerStory);
		passengers = new ArrayList<Passenger>();
	}
	
	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassenger(Passenger passenger) {
		this.passengers.add(passenger);
	}
	
	public void removePassenger(int passengerId) {
		this.passengers.remove(passengerId);
	}
	
	public Passenger getPassenger(int passengerId) {
		return this.passengers.get(passengerId);
	}

}
