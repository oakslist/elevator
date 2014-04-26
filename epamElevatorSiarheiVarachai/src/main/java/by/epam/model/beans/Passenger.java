package by.epam.model.beans;

import org.apache.log4j.Logger;

import by.epam.LogConstants;
import by.epam.ProgramConstants;
import by.epam.logs.MyLogWriter;

public class Passenger {

	private static final Logger LOG = Logger.getLogger(Passenger.class);

	private String passengerId;
	private int destinationStory;
	private String transportationState;

	public Passenger(int storiesNumber, int originalPartId, int currentStory) {
		this.passengerId = createRandomPassengerId(originalPartId);
		this.destinationStory = createRandomDestinationStory(storiesNumber, 
				currentStory);
		this.transportationState = TransportationState.NOT_STARTED
				.getTransportationState();
		LOG.info(LogConstants.CREATE_NEW_PASSENGER + ": " + toString());
		MyLogWriter.writeLog(LogConstants
				.CREATE_NEW_PASSENGER + ": " + toString());
	}

	public Passenger(String passengerId, int destinationStory) {
		this.passengerId = passengerId;
		this.destinationStory = destinationStory;
		this.transportationState = TransportationState.NOT_STARTED
				.getTransportationState();
		LOG.info("Create new passenger: passengerId = " + passengerId
				+ "; destinationStory = " + destinationStory + ";");
		MyLogWriter.writeLog("Create new passenger: passengerId = " + passengerId
				+ "; destinationStory = " + destinationStory + ";");
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public int getDestinationStory() {
		return destinationStory;
	}

	public void setDestinationStory(int destinationStory) {
		this.destinationStory = destinationStory;
	}

	public String getTransportationState() {
		return transportationState;
	}

	public void setTransportationState(TransportationState transportationState) {
		this.transportationState = transportationState.getTransportationState();
	}

	public void setTransportationState(String transportationState) {
		this.transportationState = transportationState;
	}

	@Override
	public String toString() {
		return "passengerId=" + passengerId + "; destinationStory="
				+ destinationStory + "; transportationState="
				+ transportationState;
	}

	private String createRandomPassengerId(int originalPartId) {
		StringBuilder sb = new StringBuilder();
		sb.append("ID");
		for (int i = 0; i < ProgramConstants.PASSENGER_ID_SIZE; i++) {
			sb.append(getRandomChar());
		}
		sb.append("-").append(originalPartId);
		return sb.toString();
	}

	// Min + (int)(Math.random() * ((Max - Min) + 1)) to create [min;max]
	// Create [min;currentStory) and (currentStory;max]
	private int createRandomDestinationStory(int StoriesNumber, int currentStory) {
		int story;
		do { 
			story = ProgramConstants.INITIAL_STORY + 
					(int) (Math.random() 
					* ((StoriesNumber - ProgramConstants.INITIAL_STORY) + 1)); 
		} while (story == currentStory); 
		return story;
	}

	private String getRandomChar() {
		return ProgramConstants.alphaNumList
				.get((int) (Math.random() * ProgramConstants.alphaNumList.size()));
	}

}
