package by.epam.model.beans;

public enum TransportationState {
	NOT_STARTED("NOT_STARTED"), 
	IN_PROGRESS("IN_PROGRESS"), 
	COMPLETED("COMPLETED"), 
	ABORTED("ABORTED");
	
	private final String transportationState;
	
	TransportationState(String transportationState) {
		this.transportationState = transportationState;
	}
	
	public String getTransportationState() {
		return transportationState;
	}
	
	
}
