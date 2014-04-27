package by.epam.constants;

public class LogConstants {

	// initial info
	public final static String SEPARATING_LINE = "==================================";

	public final static String START_APP = "START_APPLICATION";
	public final static String FINISH_APP = "FINISH_APPLICATION";
	public final static String READ_CONFIG_PROPERTY = "READ_CONFIG_PROPERTY";
	public final static String IMPL_CONFIG_FILE = "IMPLEMENT_THE_CONFIG_FILE";
	public final static String CREATE_BUILDING_SYSTEM = SEPARATING_LINE
			+ "\nCREATED_BUILDING\n" + SEPARATING_LINE;
	public final static String CREATE_BUILDING = "CREATED_BUILDING";
	public final static String CREATE_PASSENGERS_AND_SET_TO_STORY_CONTAINERS 
			= "CREATE_PASSENGERS_AND_SET_TO_STORY_CONTAINERS";
	public final static String CREATE_NEW_PASSENGER = "CREATED_NEW_PASSENGER";
	public final static String CREATE_PASSENGER_TRANSPORTATION_TASKS 
			= "CREATE_PASSENGER_TRANSPORTATION_TASKS";
	public final static String CREATE_PASSENGER_THREAD = "created passenger thread ";
	public final static String ELEVATOR_STARTING = "ELEVATOR_STARTING";

	public final static String STARTING_TRANSPORTATION_SYSTEM = SEPARATING_LINE
			+ "\nSTARTING_TRANSPORTATION\n" + SEPARATING_LINE;
	public final static String STARTING_TRANSPORTATION = "STARTING_TRANSPORTATION";
	public final static String COMPLETION_TRANSPORTATION_SYSTEM = SEPARATING_LINE
			+ "\nCOMPLETION_TRANSPORTATION\n" + SEPARATING_LINE;
	public final static String COMPLETION_TRANSPORTATION = "COMPLETION_TRANSPORTATION";
	public final static String ABORTING_TRANSPORTATION = "ABORTING_TRANSPORTATION";
	public final static String MOVING_ELEVATOR = "MOVING_ELEVATOR";
	public final static String BOADING_OF_PASSENGER = "BOADING_OF_PASSENGER";
	public final static String DEBOADING_OF_PASSENGER = "DEBOADING_OF_PASSENGER";

	// configuration parameters to log
	public final static String STORIES_NUMBER = "storiesNumber = ";
	public final static String ELEVATOR_CAPACITY = "elevatorCapasity = ";
	public final static String PASSENGERS_NUMBER = "passengersNumber = ";
	public final static String ANIMATION_BOOST = "animationBoost = ";

	public final static String ABORTED = "ABORTED";
	public final static String ABORTED_SYSTEM = SEPARATING_LINE + "\nABORTED\n"
			+ SEPARATING_LINE;

	// validation section
	public final static String VALIDATION = "VALIDATION";
	public final static String PASSENGERS_NUMBER_IN_DISPATCH_STORIES 
			= "PASSENGERS_NUMBER_IN_DISPATCH_STORIES = ";
	public final static String PASSENGERS_NUMBER_IN_ELEVATOR 
			= "PASSENGERS_NUMBER_IN_ELEVATOR = ";
	public final static String PASSENGERS_NUMBER_IN_ARRIVAL_STORIES 
			= "PASSENGERS_NUMBER_IN_ARRIVAL_STORIES = ";
	// validation error messages
	public final static String VALIDATION_ERR_DISPATCH_STORIES_DO_NOT_EMPTY 
			= "dispatch stories aren't empty.";
	public final static String VALIDATION_ERR_ELEVATOR_DOES_NOT_EMPTY 
			= "elevator doesn't empty.";
	public final static String VALIDATION_ERR_ARRIVAL_STORIES_DO_NOT_EQUAL_PASSENGERS_NUMBER 
			= "arrival stories aren't equal passengersNumber.";
	public final static String VALIDATION_ERR_PROBLEM_WITH_STATE 
			= "problem with transportationState.COMPLETED.";
	public final static String VALIDATION_ERR_PROBLEM_WITH_DESTINATION 
			= "problem with destination.";
	// validation OK messages
	public final static String VALIDATION_OK_DISPATCH_STORIES_ARE_EMPTY 
			= "dispatch stories are empty. VALIDATION - OK";
	public final static String VALIDATION_OK_ELEVATOR_IS_EMPTY 
			= "elevator is empty. VALIDATION - OK";
	public final static String VALIDATION_OK_ARRIVAL_STORIES_ARE_EQUAL_PASSENGERS_NUMBER 
			= "arrival stories are equal passengersNumber. VALIDATION - OK";
	public final static String VALIDATION_OK_PASSENGERS_STATE 
			= "all passengers have transportationState.COMPLETED. VALIDATION - OK";
	public final static String VALIDATION_OK_PASSENGERS_DESTINATION 
			= "all passengers got correct destination. VALIDATION - OK";
	
	
	public static String getLogMovingElevator(int initialStory, int nextStory) {
		return MOVING_ELEVATOR + " (from " + initialStory + " to " + nextStory + ")";
	}
	
	public static String getLogBoadingOfPassenger(String passangerID, int passengerStory) {
		return BOADING_OF_PASSENGER + " (" + passangerID + " on " + passengerStory + ")";
	}
	
	public static String getLogDeboadingOfPassenger(String passangerID, int passengerStory) {
		return DEBOADING_OF_PASSENGER + " (" + passangerID + " on " + passengerStory + ")";
	}

}
