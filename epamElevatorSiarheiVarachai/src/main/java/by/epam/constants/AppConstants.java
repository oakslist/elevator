package by.epam.constants;

public class AppConstants {
	
	public static final int APP_MAIN_WIDTH = 1200;
	public static final int APP_MAIN_HEIGHT = 800;
	
	public static final int APP_MULTIPLIER_BOOTS = 2;
	public static final int APP_MULTIPLIER_ELEVATOR_BOOTS = 2;
	
	public static final int GROUND_LINE = 690;
	public static final int BASEMENT_Y_LINE_FOR_PASSENGER_ON_FLOOR = 54;
	public static final int PASSENGER_MULTIPLIER_NEAR_BY_WALL = 2;
	public static final int APP_X_FINISHED_PASSENGER = 55;
	
	
	public static final int APP_MESSAGES_AREA_WIDTH = (int) (APP_MAIN_WIDTH / 7) * 3;
	public static final int APP_MESSAGES_AREA_HEIGHT = APP_MAIN_HEIGHT;
	public static final int APP_PRESENTATION_AREA_WIDTH = APP_MAIN_WIDTH 
			- APP_MESSAGES_AREA_WIDTH;
	public static final int APP_PRESENTATION_AREA_HEIGHT = APP_MAIN_HEIGHT;
	
	public static final int APP_INFO_BLOCK_WIDTH = APP_PRESENTATION_AREA_WIDTH - 120;
	public static final int APP_INFO_BLOCK_HEIGHT = 20;
	
	public static final int APP_PASSENGER_HEIGHT = 42;
	public static final int APP_PASSENGER_WIDTH = 35;
	
	public static final String APP_BUTTON_START_NAME = "START";
	public static final String APP_BUTTON_ABORT_NAME = "ABORT";
	public static final String APP_BUTTON_VIEW_LOG_NAME = "VIEW LOG FILE";
	
	public static final int APP_STORIES_ON_SCREEN = 5;
	public static final int APP_STORY_TO_MOVE_BACKGROUND = 3;
	
	// moving to building
	public static final int APP_INITIALIZE_STEP_ONE = 1;
	// moving up to correct story
	public static final int APP_INITIALIZE_STEP_TWO = 2;
	// moving to the room
	public static final int APP_INITIALIZE_STEP_THREE = 3;
	// moving to the elevator
	public static final int APP_INITIALIZE_STEP_FOUR = 4;
	// moving out from elevator
	public static final int APP_INITIALIZE_STEP_FIVE = 5;

}
