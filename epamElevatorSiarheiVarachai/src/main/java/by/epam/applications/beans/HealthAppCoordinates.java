package by.epam.applications.beans;

import by.epam.constants.AppConstants;

public class HealthAppCoordinates extends InstanceAppCoordinates {
	
	private int health;
	private int story;
	private int multiplier;
	private int timeToHurt;
		
	public HealthAppCoordinates(int xCoordinate, int yCoordinate, int story) {
		super(xCoordinate, yCoordinate);
		this.story = story;
		this.health = AppConstants.APP_HEALTH_WIDTH;
		this.setTimeToHurt(AppConstants.APP_TIME_TO_HURT);
		this.multiplier = 0;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getStory() {
		return story;
	}

	public int getTimeToHurt() {
		return timeToHurt;
	}

	public void setTimeToHurt(int timeToHurt) {
		this.timeToHurt = timeToHurt;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void incMultiplier() {
		this.multiplier++;
	}
}
