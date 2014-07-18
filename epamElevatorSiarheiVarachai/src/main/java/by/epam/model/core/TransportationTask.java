package by.epam.model.core;

import org.apache.log4j.Logger;

import by.epam.constants.LogConstants;
import by.epam.logs.MyLogWriter;
import by.epam.model.beans.Passenger;
import by.epam.model.beans.TransportationState;

public class TransportationTask implements Runnable {

	private static final Logger LOG = Logger
			.getLogger(TransportationTask.class);
	
	private String threadName;
	private Passenger passenger;
	private Thread thread;
	private boolean wasAborted = false;
	
	public TransportationTask(Passenger passenger) {
		this.threadName = passenger.getPassengerId();
		this.passenger = passenger;
		this.wasAborted = false;
		thread = new Thread(this, this.threadName);
		LOG.info(LogConstants.CREATE_PASSENGER_THREAD + threadName);
		System.out.println(LogConstants.CREATE_PASSENGER_THREAD + threadName);
		MyLogWriter.writeLog(LogConstants
				.CREATE_PASSENGER_THREAD + threadName);
		thread.start();
	}

	public void run() {
		setTransportationState(TransportationState.IN_PROGRESS);

		synchronized (this.passenger) {
			try {
				while ((!Controller.readyToLetIn 
						|| (Controller.isPassengerGoingToUp(passenger) 
								!= Controller.upwardMovement)) 
						&& Controller.isControllerAborted() == false) {
					if (Controller.isControllerAborted() == false) {
						// waiting to set in Elevator
						this.passenger.wait();
						if (Controller.isPassengerGoingToUp(passenger) 
								!= Controller.upwardMovement) {
							passenger.notifyAll();
						}
					} else {
						this.wasAborted = true;
					}
				}
				if (Controller.isControllerAborted() == false) {
					Controller.setInElevator(passenger);
				} else {
					this.wasAborted = true;
				}
				while ((!Controller.readyToLetOut 
						|| (passenger.getDestinationStory() 
								!= Controller.currentStory)) 
						&& Controller.isControllerAborted() == false) {
					passenger.notifyAll();
					if (Controller.isControllerAborted() == false) {
						// waiting to go out from Elevator
						this.passenger.wait();
					} else {
						this.wasAborted = true;
					}
				}
				if (Controller.isControllerAborted() == false) {
					Controller.outFromElevator(passenger);
				} else {
					this.wasAborted = true;
				}
				passenger.notifyAll(); 
				if (Controller.isControllerAborted() == false) {
					// waiting all finished processes
					this.passenger.wait();
				} 				
			} catch (InterruptedException e) {
				setTransportationState(TransportationState.ABORTED);
				LOG.info(passenger + " was interrupted! " + e.toString());
				MyLogWriter.writeLog(passenger + " was interrupted! " 
						+ e.toString());
				e.printStackTrace();
			}
			passenger.notifyAll(); 
		}
		if (this.wasAborted == false) {
			setTransportationState(TransportationState.COMPLETED);
		} else {
			setTransportationState(TransportationState.ABORTED);
		}
		if (Controller.isControllerAborted() == true) {
			LOG.info("passengerId = " + passenger.getPassengerId() 
					+ "; transportationState = " 
					+ passenger.getTransportationState());
			MyLogWriter.writeLog("passengerId = " 
					+ passenger.getPassengerId() 
					+ "; transportationState = " 
					+ passenger.getTransportationState());
		}
	}
	
	private void setTransportationState(TransportationState transportationTask) {
		this.passenger.setTransportationState(transportationTask);
	}

}
