package by.epam;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import by.epam.applications.ElevatorApp;
import by.epam.constants.LogConstants;
import by.epam.constants.ProgramConstants;
import by.epam.logs.MyLogWriter;
import by.epam.model.beans.Passenger;
import by.epam.model.containers.DispatchStoryContainer;
import by.epam.model.core.ConfigFile;
import by.epam.model.core.Controller;
import by.epam.model.core.MyBuilding;
import by.epam.model.core.TransportationTask;
import by.epam.model.impl.ConfigFileImpl;


public class Runner {
	
	private static final Logger LOG = Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {
		
		LOG.info(LogConstants.START_APP);
		MyLogWriter.writeLog(LogConstants.START_APP);
		
		LOG.info(LogConstants.SEPARATING_LINE);
		MyLogWriter.writeLog(LogConstants.SEPARATING_LINE);
		
		ConfigFileImpl configFileImp = new ConfigFileImpl();
		
		ConfigFile configFile = configFileImp.getConfigParametrs();
		
		if (configFile.getAnimationBoost() != 0) {
			MyLogWriter.ANIMATION_BOOTS = configFile.getAnimationBoost();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ElevatorApp.startElevatorAppFrame();
				}
			});
			ProgramConstants.appButtonListener.startButtonlistener(false);
		}
		
		MyBuilding building = new MyBuilding(configFile.getStoriesNumber(), 
				configFile.getElevatorCapacity(), configFile.getPassengersNumber());

		setPassengersOnStories(building);
		
		//start app and set all input data about passengers
		if (configFile.getAnimationBoost() != 0) {
			ElevatorApp.setInitialDataToApp(building);
			synchronized (building) {
				ElevatorApp.setTimer(configFile.getAnimationBoost());
				ElevatorApp.startApp();
				try {
					building.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		int story = 0;
		for (DispatchStoryContainer dsc : building.getDispatchStoriesContainer()) {
			story++;
			System.out.println("On the " + story + " story: " + 
					dsc.getPassengers().size() + " passengers");
		}
		
		LOG.info(LogConstants.SEPARATING_LINE);
		LOG.info(LogConstants.STARTING_TRANSPORTATION);
		LOG.info(LogConstants.SEPARATING_LINE);
		MyLogWriter.writeLog(LogConstants.STARTING_TRANSPORTATION_SYSTEM);
		
		LOG.info(LogConstants.CREATE_PASSENGER_TRANSPORTATION_TASKS);
		MyLogWriter.writeLog(LogConstants.CREATE_PASSENGER_TRANSPORTATION_TASKS);
		for (DispatchStoryContainer ds : building.getDispatchStoriesContainer()) {
			for (int i = 0; i < ds.getPassengers().size(); i++) {
				new TransportationTask(ds.getPassengers().get(i));
			}
		}
		
		// waiting to finish passengers thread
		try {
			Thread.sleep(ProgramConstants.DEFAULT_SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		LOG.info(LogConstants.SEPARATING_LINE);
		MyLogWriter.writeLog(LogConstants.SEPARATING_LINE);
		LOG.info(LogConstants.ELEVATOR_STARTING);
		MyLogWriter.writeLog(LogConstants.ELEVATOR_STARTING);
		Controller controller = new Controller(building, configFile);
		controller.move();
		
		LOG.info(LogConstants.SEPARATING_LINE);
		LOG.info(LogConstants.COMPLETION_TRANSPORTATION);
		LOG.info(LogConstants.SEPARATING_LINE);
		MyLogWriter.writeLog(LogConstants.COMPLETION_TRANSPORTATION_SYSTEM);
		
		if (Controller.isAborted() == false) {
			controller.validateTransportation();
			LOG.info(LogConstants.SEPARATING_LINE);
			MyLogWriter.writeLog(LogConstants.SEPARATING_LINE);
		}
		
		LOG.info(LogConstants.FINISH_APP);
		MyLogWriter.writeLog(LogConstants.FINISH_APP);
	
	}
	
	private static void setPassengersOnStories(MyBuilding building) {
		LOG.info(LogConstants.CREATE_PASSENGERS_AND_SET_TO_STORY_CONTAINERS);
		MyLogWriter.writeLog(LogConstants.CREATE_PASSENGERS_AND_SET_TO_STORY_CONTAINERS);
		
		//find out how many passengers on story we need
		int modPassenger = building.getPassengersNumber() % building.getStoriesNumber();
		int passengerOnStory = (building.getPassengersNumber() - modPassenger) 
				/ building.getStoriesNumber();
		int originalPartId = ProgramConstants.INITIAL_ORIGINAL_ID_PART;
				
		// add passengers in story containers and save that containers in
		// stories ArrayList<DispatchStoryContainer>
		for (int currentStory = 1; currentStory <= building.getStoriesNumber(); currentStory++) {
			for (int passenger = 0; passenger < passengerOnStory; passenger++) {
				addPassengerToDispatchStoryContainer(building, currentStory, originalPartId);
				originalPartId++;
			}
			if (modPassenger != 0) {
				addPassengerToDispatchStoryContainer(building, currentStory, originalPartId);
				modPassenger--;
				originalPartId++;
			}
		}
	}
	
	private static void addPassengerToDispatchStoryContainer(MyBuilding building, 
			int currentStory, int originalPartId) {
		building.getDispatchStoryContainer(currentStory)
				.setPassenger(new Passenger(building.getStoriesNumber(), 
						originalPartId, currentStory));
	}
	
}
