package by.epam;

public class AppConstants {
	
	public static final int APP_MAIN_WIDTH = 1200;
	public static final int APP_MAIN_HEIGHT = 800;
	
	public static final int APP_MULTIPLIER_BOOTS = 2;
	
	public static final int GROUND_LINE = 690;
	public static final int BASEMENT_FOR_PAINT_STORIES = GROUND_LINE - 54;
	public static final int BASEMENT_FOR_PAINT_PASSENGERS = GROUND_LINE - 54;
	public static final int PASSENGER_MULTIPLIER_NEAR_BY_WALL = 2;

	
	public static final int APP_MESSAGES_AREA_WIDTH = (int) (APP_MAIN_WIDTH / 7) * 3;
	public static final int APP_MESSAGES_AREA_HEIGHT = APP_MAIN_HEIGHT;
	public static final int APP_PRESENTATION_AREA_WIDTH = APP_MAIN_WIDTH - APP_MESSAGES_AREA_WIDTH;
	public static final int APP_PRESENTATION_AREA_HEIGHT = APP_MAIN_HEIGHT;
	
	public static final int APP_INFO_BLOCK_WIDTH = APP_PRESENTATION_AREA_WIDTH - 120;
	public static final int APP_INFO_BLOCK_HEIGHT = 20;
	
	public static final String APP_BUTTON_START_NAME = "START";
	public static final String APP_BUTTON_ABORT_NAME = "ABORT";
	public static final String APP_BUTTON_VIEW_LOG_NAME = "VIEW LOG FILE";
	
	public static final int APP_STORIES_ON_SCREEN = 5;
	public static final int APP_STORY_TO_MOVE_BACKGROUND = 3;

}
