package by.epam.model.beans;

import org.apache.log4j.Logger;

import by.epam.LogConstants;
import by.epam.logs.MyLogWriter;


public class Building {
	
	private static final Logger LOG = Logger.getLogger(Building.class);
	
	private final int STORIES_NUMBER;
	
	public Building(int storiesNumber) {
		LOG.info(LogConstants.SEPARATING_LINE);
		LOG.info(LogConstants.CREATE_BUILDING);
		LOG.info(LogConstants.SEPARATING_LINE);
		MyLogWriter.writeLog(LogConstants.CREATE_BUILDING_SYSTEM);
		this.STORIES_NUMBER = storiesNumber;
	}

	public int getStoriesNumber() {
		return STORIES_NUMBER;
	}
		
}
