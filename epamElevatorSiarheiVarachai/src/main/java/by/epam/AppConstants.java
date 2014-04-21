package by.epam;

public class AppConstants {
	
	public static int APP_MAIN_WIDTH = 1200;
	public static int APP_MAIN_HEIGHT = 800;
	
	public static int BASEMENT_FOR_PAINT_STORIES = 690;
	public static int BASEMENT_FOR_PAINT_PASSENGERS = BASEMENT_FOR_PAINT_STORIES - 54;
	public static int PASSENGER_MULTIPLIER_NEAR_BY_WALL = 2;

	
	public static int APP_MESSAGES_AREA_WIDTH = (int) (APP_MAIN_WIDTH / 7) * 3;
	public static int APP_MESSAGES_AREA_HEIGHT = APP_MAIN_HEIGHT;
	public static int APP_PRESENTATION_AREA_WIDTH = APP_MAIN_WIDTH - APP_MESSAGES_AREA_WIDTH;
	public static int APP_PRESENTATION_AREA_HEIGHT = APP_MAIN_HEIGHT;
	
	public static String APP_BUTTON_START_NAME = "START";
	public static String APP_BUTTON_ABORT_NAME = "ABORT";
	public static String APP_BUTTON_VIEW_LOG_NAME = "VIEW LOG FILE";

}
