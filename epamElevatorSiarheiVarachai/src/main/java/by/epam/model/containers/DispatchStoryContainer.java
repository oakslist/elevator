package by.epam.model.containers;

import java.util.ArrayList;
import java.util.List;

import by.epam.model.beans.Passenger;

public class DispatchStoryContainer extends StoryContainer {
	
	private List<Passenger> passengers;

	public DispatchStoryContainer(int containerStory) {
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
	
	public void removePassenger(String passengerIdString) {
		int passengerId = -1;
		for (Passenger passenger : passengers) {
			passengerId++;
			if (passenger.getPassengerId().equals(passengerIdString)) {
				this.passengers.remove(passengerId);
				break;
			}
		}
	}
	
	public Passenger getPassenger(int passengerId) {
		return this.passengers.get(passengerId);
	}

}
