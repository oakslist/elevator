package by.epam.model.core;

import org.apache.log4j.Logger;

import by.epam.constants.LogConstants;
import by.epam.ifaces.IConfigFile;
import by.epam.logs.MyLogWriter;

public class ConfigFile implements IConfigFile {

	private static final Logger LOG = Logger.getLogger(ConfigFile.class);

	private final int storiesNumber;
	private final int elevatorCapasity;
	private final int passengersNumber;
	private final int animationBoost;

	public ConfigFile(int storiesNumber, int elevatorCapasity,
			int passengersNumber, int animationBoost) {
		this.storiesNumber = storiesNumber;
		this.elevatorCapasity = elevatorCapasity;
		this.passengersNumber = passengersNumber;
		this.animationBoost = animationBoost;
		LOG.info(LogConstants.STORIES_NUMBER + storiesNumber);
		MyLogWriter.writeLog(LogConstants.STORIES_NUMBER + storiesNumber);
		LOG.info(LogConstants.ELEVATOR_CAPACITY + elevatorCapasity);
		MyLogWriter.writeLog(LogConstants.ELEVATOR_CAPACITY + elevatorCapasity);
		LOG.info(LogConstants.PASSENGERS_NUMBER + passengersNumber);
		MyLogWriter.writeLog(LogConstants.PASSENGERS_NUMBER + passengersNumber);
		LOG.info(LogConstants.ANIMATION_BOOST + animationBoost);
		MyLogWriter.writeLog(LogConstants.ANIMATION_BOOST + animationBoost);
	}

	public int getStoriesNumber() {
		return this.storiesNumber;
	}

	public int getElevatorCapacity() {
		return this.elevatorCapasity;
	}

	public int getPassengersNumber() {
		return this.passengersNumber;
	}

	public int getAnimationBoost() {
		return this.animationBoost;
	}

}
