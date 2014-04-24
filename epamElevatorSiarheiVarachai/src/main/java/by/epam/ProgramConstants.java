package by.epam;

import java.io.File;
import java.util.ArrayList;

import by.epam.applications.AppButtonListener;

public class ProgramConstants {
	
	private ProgramConstants() {
		
	}

	// config.property constants
	public final static String CONFIG_FILE_NAME = "config.property";
	public final static String LOG_FILE_NAME = "myLog.log";
	public final static String OPEN_LOG_FILE_PROGRAM = "notepad";
	public final static String PACKAGE = "src" + File.separator + "main" + 
			File.separator + "resources" + File.separator;
	public final static String WORKING_DIRECTORY = System.getProperty("user.dir") +  
			File.separator;
	public final static String IMG_DIRECTORY = WORKING_DIRECTORY + 
			PACKAGE + "images" + File.separator;
		
		
	public final static String CONFIG_STORIES_NUMBER = "storiesNumber";
	public final static String CONFIG_EVEVATOR_CAPACITY = "elevatorCapacity";
	public final static String CONFIG_PASSENGERS_NUMBER = "passengersNumber";
	public final static String CONFIG_ANIMATION_BOOTS = "animationBoots";
		
	// config default	
	public final static String CONFIG_DEFAULT_STORIES_NUMBER = "10";
	public final static String CONFIG_DEFAULT_EVEVATOR_CAPACITY = "5";
	public final static String CONFIG_DEFAULT_PASSENGERS_NUMBER = "10";
	public final static String CONFIG_DEFAULT_ANIMATION_BOOTS = "1";
		
	public final static int DEFAULT_SLEEP_TIME = 200;
	public final static int SLEEP_TIME_ANIMATION_BOOTS = 500;
		
	public final static int INITIAL_STORY = 1;
	public final static int INITIAL_ORIGINAL_ID_PART = 1;
		
			
	// create list of char for random passengerId
	public final static int PASSENGER_ID_SIZE = 5;
	public static ArrayList<String> alphaNumList = new ArrayList<String>();
	
	public static AppButtonListener appButtonListener = new AppButtonListener();
			
	static {
		for (char c = 'A'; c <= 'z'; c++) {
			String s = new String();
	        s += c;
	        alphaNumList.add(s);
	        if (c == 'Z') {
	        	c = 'a' - 1;
	        }
	    }
	    for (int c = 0; c < 10; c++) {
	    	String s = new String();
	        s += c;
	        alphaNumList.add(s);
	    }
	}
		
		
}
