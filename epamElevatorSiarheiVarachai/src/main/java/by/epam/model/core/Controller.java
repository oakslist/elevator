package by.epam.model.core;

import org.apache.log4j.Logger;

import by.epam.applications.AppButtonListener;
import by.epam.applications.ElevatorApp;
import by.epam.constants.LogConstants;
import by.epam.constants.ProgramConstants;
import by.epam.logs.MyLogWriter;
import by.epam.model.beans.Passenger;
import by.epam.model.beans.TransportationState;
import by.epam.model.containers.ArrivalStoryContainer;
import by.epam.model.containers.DispatchStoryContainer;

public class Controller {
	
	private static final Logger LOG = Logger.getLogger(Controller.class);
	
	public static int numberBookedPlacesInElevator;
	//true = up, false = down
	public static boolean upwardMovement;  
	public static int currentStory;  
	public static boolean readyToSetInContainer = false;
	
	//ready to let passengers out from elevator
	public static boolean readyToLetOut;	
	//ready to let passengers in to elevator
	public static boolean readyToLetIn;	 	
	
	public static boolean isAppTurn = true;
	
	private static boolean isAborted = false;
	// just to show how many circles elevator done in app
	private static int circleMoving = 0;  
	
	private ConfigFile configFile;
	private MyBuilding building;
	//ready to change story
	private boolean readyToMove;	 		
	//ready to close moving
	private boolean readyToExit;	 		 
	
	public Controller(MyBuilding building, ConfigFile configFile) {
		this.building = building;
		this.configFile = configFile;
		Controller.upwardMovement = true;
		Controller.currentStory = building.getElevator().getCurrentStory();
		this.readyToMove = false;
		Controller.readyToLetOut = false;
		Controller.readyToLetIn = true;
		this.readyToExit = false;
		Controller.isAborted = false;
	}
		
	// start elevator
	public void move() {
		Controller.circleMoving++;
		while(!this.readyToExit) {
			if (isAborted) {
				break;
			}
			//ready to change story
			if (this.readyToMove) {
				nextStory();
				this.readyToMove = false;
				Controller.readyToLetOut = true;
				Controller.readyToLetIn = true;
			}
			if (isAborted) {
				break;
			} else {
				try {
					Thread.sleep(ProgramConstants.DEFAULT_SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//ready to let passengers out from elevator
			if (Controller.readyToLetOut) {
				while(Controller.readyToLetOut) {
					letPassengerOut();
				}
			}
			if (isAborted) {
				break;
			} else {
				try {
					Thread.sleep(ProgramConstants.DEFAULT_SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//ready to let passengers in to elevator
			if (Controller.readyToLetIn) {
				while(Controller.readyToLetIn) {
					letPassengerIn();	
				}
				this.readyToMove = true;
			}
			if (isAborted) {
				break;
			} else {
				try {
					Thread.sleep(ProgramConstants.DEFAULT_SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				checkReadyToExit();
			}
		}
		//ready to close moving 
		stopMoving();
	}
	
	private void letPassengerIn() {
		int currentNumberCheckedPeoplesOnStory;
		boolean isWorkingInMethod = true;
		while (building.getDispatchStoryContainer(Controller.currentStory)
				.getPassengers().size() > 0 && isWorkingInMethod && !isAborted) {
			Controller.readyToSetInContainer = false;
			currentNumberCheckedPeoplesOnStory = building
					.getDispatchStoryContainer(Controller.currentStory)
					.getPassengers().size();
			for (Passenger passenger : building
					.getDispatchStoryContainer(Controller.currentStory)
					.getPassengers()) {
				currentNumberCheckedPeoplesOnStory--;
				Controller.numberBookedPlacesInElevator = building
						.getElevator().getElevatorContainer()
						.getPassengers().size();
				// just must be == but and >= is correct too
				if (Controller.numberBookedPlacesInElevator >= building
						.getElevator().getElevatorCapacity()) {
					isWorkingInMethod = false;
					break;
				}
				synchronized (passenger) {
					passenger.notify();
					try {
						if (isAborted == false) {
							passenger.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (Controller.readyToSetInContainer && !isAborted) {
					LOG.info(LogConstants.getLogBoadingOfPassenger(passenger
							.getPassengerId(), Controller.currentStory));
					MyLogWriter.writeLog(LogConstants
							.getLogBoadingOfPassenger(passenger
									.getPassengerId(), Controller.currentStory));
					building.getElevator().getElevatorContainer()
							.setPassenger(passenger);
					building.getDispatchStoryContainer(Controller.currentStory)
							.removePassenger(passenger.getPassengerId());
					Controller.readyToSetInContainer = false;
					if (configFile.getAnimationBoost() != 0) {
						synchronized (building) {
							ElevatorApp.setRepaintPassenger(true, 
									passenger.getPassengerId());
							try {
								while (ElevatorApp.isRepaintPassenger() == true) {
									building.wait();
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					break;
				}
			}
			if (currentNumberCheckedPeoplesOnStory == 0) {
				isWorkingInMethod = false;
			}
		}
		Controller.readyToSetInContainer = false;
		Controller.readyToLetIn = false;
	}
	
	private void letPassengerOut() {
		int currentNumberCheckedPeoplesInElevator;
		boolean isWorkingInMethod = true;
		while(isWorkingInMethod && !isAborted) {
			Controller.readyToSetInContainer = false;
			currentNumberCheckedPeoplesInElevator = building
					.getElevator().getElevatorContainer().getPassengers().size();
			for (Passenger passenger : building.getElevator()
					.getElevatorContainer().getPassengers()) {
				currentNumberCheckedPeoplesInElevator--;
				synchronized (passenger) {
					passenger.notify();
					try {
						if (isAborted == false) {
							passenger.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (Controller.readyToSetInContainer && !isAborted) {
					LOG.info(LogConstants
							.getLogDeboadingOfPassenger(passenger.getPassengerId(), 
							Controller.currentStory));
					MyLogWriter.writeLog(LogConstants
							.getLogDeboadingOfPassenger(passenger.getPassengerId(), 
							Controller.currentStory));
					building.getArrivalStoryContainer(Controller.currentStory)
					.setPassenger(passenger);
					building.getElevator().getElevatorContainer()
					.removePassenger(passenger.getPassengerId());
					Controller.readyToSetInContainer = false;
					if (configFile.getAnimationBoost() != 0) {
						synchronized (building) {
							ElevatorApp.setRepaintPassenger(true, 
									passenger.getPassengerId());
							try {
								while (ElevatorApp.isRepaintPassenger() == true) {
									building.wait();
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					break;
				}
			}
			if (currentNumberCheckedPeoplesInElevator == 0) {
				isWorkingInMethod = false;
			}
		}
		Controller.readyToSetInContainer = false;
		Controller.readyToLetOut = false;
		
	}
	
	private void checkReadyToExit() {
		for (DispatchStoryContainer dispatchStoryContainer : building
				.getDispatchStoriesContainer()) {
			if (dispatchStoryContainer.getPassengers().size() > 0) {
				return;
			}
		}
		if (building.getElevator().getElevatorContainer()
				.getPassengers().size() > 0) {
			return;
		}
		this.readyToExit = true;
	}
	
	private void stopMoving() {
		
		// finish all aborted passengers thread
		if (Controller.isAborted == true) {
			
			LOG.info(LogConstants.SEPARATING_LINE);
			LOG.info(LogConstants.ABORTED);
			LOG.info(LogConstants.SEPARATING_LINE);
			MyLogWriter.writeLog(LogConstants.ABORTED_SYSTEM);
			
			// from dispatch stories
			for (DispatchStoryContainer dsc : building
					.getDispatchStoriesContainer()) {
				for (int i = 0; i < dsc.getPassengers().size(); i++) {
					synchronized (dsc.getPassengers().get(i)) {
						dsc.getPassengers().get(i).notify();
						LOG.info("passengerID = " + dsc.getPassengers()
								.get(i).getPassengerId() + 
								" in dispatchStoryContainer now");
						MyLogWriter.writeLog("passengerID = " + 
								dsc.getPassengers().get(i).getPassengerId() + 
								" in dispatchStoryContainer now");
						try {
							dsc.getPassengers().get(i).wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			// from elevator story
			for (Passenger passenger : building.getElevator()
					.getElevatorContainer().getPassengers()) {
				synchronized (passenger) {
					passenger.notify();
					LOG.info("passengerID = " + passenger.getPassengerId() + 
							" in elevatorContainer now");
					MyLogWriter.writeLog("passengerID = " + passenger.getPassengerId() + 
							" in elevatorContainer now");
					try {
						passenger.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		// finish all passengers thread which are waiting for controller end
		for (ArrivalStoryContainer asc : building.getArrivalStoriesContainer()) {
			for (int i = 0; i < asc.getPassengers().size(); i++) {
				synchronized (asc.getPassengers().get(i)) {
					asc.getPassengers().get(i).notify();
					if (Controller.isAborted == true) {
						LOG.info("passengerID = " + asc.getPassengers()
								.get(i).getPassengerId() 
								+ " in arrivalStoryContainer now");
						MyLogWriter.writeLog("passengerID = " + asc.getPassengers()
								.get(i).getPassengerId() 
								+ " in arrivalStoryContainer now");
						try {
							asc.getPassengers().get(i).wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		// waiting when all tasks will be finished
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AppButtonListener.setButtonViewLogName();
	}
	
	private void nextStory() {
		// elevator going to move up or down
		if (Controller.upwardMovement == true) {
			this.building.getElevator().incrementCurrentStory();
		} else {
			this.building.getElevator().decrementCurrentStory();
		}
		LOG.info(LogConstants.getLogMovingElevator(Controller.currentStory,
				building.getElevator().getCurrentStory()));
		MyLogWriter.writeLog("\n" + LogConstants
				.getLogMovingElevator(Controller.currentStory, 
				building.getElevator().getCurrentStory()));
		Controller.currentStory = building.getElevator().getCurrentStory();
		if (configFile.getAnimationBoost() != 0) { 
			synchronized (building) {
				ElevatorApp.setRepaintElevator(true, Controller.currentStory);
				while(ElevatorApp.isRepaintElevator() == true) {
					try {
						building.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if (Controller.currentStory == ProgramConstants.INITIAL_STORY) {
			setCircleMoving(getCircleMoving() + 1);
			Controller.upwardMovement = true;
		}
		if (Controller.currentStory == this.building.getStoriesNumber()) {
			Controller.upwardMovement = false;
		}
	}
	
	public static void setInElevator(Passenger passenger) {
		// elevator are moving up and passenger direction is up 
		if (isPassengerGoingToUp(passenger) == true 
				&& Controller.upwardMovement == true) {
			Controller.readyToSetInContainer = true;
		}
		// elevator are moving down and passenger direction is down
		if (isPassengerGoingToUp(passenger) == false 
				&& Controller.upwardMovement == false) {
			Controller.readyToSetInContainer = true;
		}
	}
	
	public static boolean isPassengerGoingToUp(Passenger passenger) {
		int up = Controller.currentStory - passenger.getDestinationStory();
		if (up < 0) {
			return true;
		}
		return false;
	}
	
	public static void outFromElevator(Passenger passenger) {
		// passenger in the necessary story
		if (Controller.currentStory == passenger.getDestinationStory()) {
			Controller.readyToSetInContainer = true;
		}
	}
	
	public void validateTransportation() {
		LOG.info(LogConstants.VALIDATION);
		MyLogWriter.writeLog(LogConstants.VALIDATION);
		
		//check are empty dispatch stories
		int passengersNumberInDispatchStories = 0;
		for (DispatchStoryContainer dsc : building
				.getDispatchStoriesContainer()) {
			passengersNumberInDispatchStories += dsc.getPassengers().size();
		}		
		if (passengersNumberInDispatchStories == 0) {
			LOG.info(LogConstants.VALIDATION_OK_DISPATCH_STORIES_ARE_EMPTY);
			MyLogWriter.writeLog(LogConstants
					.VALIDATION_OK_DISPATCH_STORIES_ARE_EMPTY);
		} else {
			LOG.error(LogConstants.VALIDATION_ERR_DISPATCH_STORIES_DO_NOT_EMPTY);
			MyLogWriter.writeLog(LogConstants
					.VALIDATION_ERR_DISPATCH_STORIES_DO_NOT_EMPTY);
		}
		
		//check is empty elevator
		int passengersNumberInElevator = building.getElevator()
				.getElevatorContainer().getPassengers().size();
		if (passengersNumberInElevator == 0) {
			LOG.error(LogConstants.VALIDATION_OK_ELEVATOR_IS_EMPTY);
			MyLogWriter.writeLog(LogConstants.VALIDATION_OK_ELEVATOR_IS_EMPTY);
		} else {
			LOG.error(LogConstants.VALIDATION_ERR_ELEVATOR_DOES_NOT_EMPTY);
			MyLogWriter.writeLog(LogConstants
					.VALIDATION_ERR_ELEVATOR_DOES_NOT_EMPTY);
		}
		
		//check passenger's state and destination
		int stateProblem = 0;
		int destinationProblem = 0;
		int passengersNumberInArrivalStories = 0;
		int story = 0;
		for (ArrivalStoryContainer asc : building.getArrivalStoriesContainer()) {
			story++;
			passengersNumberInArrivalStories += asc.getPassengers().size();
			for (Passenger passenger : asc.getPassengers()) {
				if (!passenger.getTransportationState()
						.equals(TransportationState
								.COMPLETED.getTransportationState())) {
					stateProblem++;
					LOG.error(LogConstants.VALIDATION_ERR_PROBLEM_WITH_STATE + 
							" Passenger ID = " + passenger.getPassengerId());
					MyLogWriter.writeLog(LogConstants
							.VALIDATION_ERR_PROBLEM_WITH_STATE + 
							" Passenger ID = " + passenger.getPassengerId());
				}
				if (passenger.getDestinationStory() != story) {
					destinationProblem++;
					LOG.error(LogConstants.VALIDATION_ERR_PROBLEM_WITH_DESTINATION + 
							" Destination = " + passenger.getDestinationStory() + 
							" current story = " + story);
					MyLogWriter.writeLog(LogConstants
							.VALIDATION_ERR_PROBLEM_WITH_DESTINATION  + 
							" Destination = " + passenger.getDestinationStory() + 
							" current story = " + story);
				}
			}
		}
		if (stateProblem == 0) {
			LOG.error(LogConstants.VALIDATION_OK_PASSENGERS_STATE);
			MyLogWriter.writeLog(LogConstants.VALIDATION_OK_PASSENGERS_STATE);
		}
		if (destinationProblem == 0) {
			LOG.error(LogConstants.VALIDATION_OK_PASSENGERS_DESTINATION);
			MyLogWriter.writeLog(LogConstants.VALIDATION_OK_PASSENGERS_DESTINATION);
		}
		
		//check are passengers on arrival stories are equal passengersNumber
		if (passengersNumberInArrivalStories == building.getPassengersNumber()) {
			LOG.error(LogConstants
					.VALIDATION_OK_ARRIVAL_STORIES_ARE_EQUAL_PASSENGERS_NUMBER);
			MyLogWriter.writeLog(LogConstants
					.VALIDATION_OK_ARRIVAL_STORIES_ARE_EQUAL_PASSENGERS_NUMBER);
		} else {
			LOG.error(LogConstants
					.VALIDATION_ERR_ARRIVAL_STORIES_DO_NOT_EQUAL_PASSENGERS_NUMBER);
			MyLogWriter.writeLog(LogConstants
					.VALIDATION_ERR_ARRIVAL_STORIES_DO_NOT_EQUAL_PASSENGERS_NUMBER);
		}
	}

	public static boolean isAborted() {
		return Controller.isAborted;
	}
	
	public static void setAborted(boolean aborted) {
		Controller.isAborted = aborted;
	}

	public static int getCircleMoving() {
		return circleMoving;
	}

	public void setCircleMoving(int circleMoving) {
		Controller.circleMoving = circleMoving;
	}

}
