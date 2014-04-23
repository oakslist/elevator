package by.epam.applications;

import by.epam.AppConstants;
import by.epam.model.core.Controller;

public class AppButtonListener {

	private boolean startButtonClicked;
	
	public AppButtonListener() {
		this.startButtonClicked = false;
		this.setAbortButtonClicked(false);
	}

	public void startButtonlistener(boolean bool) {
		startButtonClicked = bool;
		synchronized (this) {
			try {
				while (!startButtonClicked) {
					this.wait();
				}
				this.notifyAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setAbortButtonClicked(boolean abortButtonClicked) {
		ElevatorApp.stopApp();
		Controller.setAborted(abortButtonClicked);
	}
	
	public static void setButtonViewLogName() {
		ElevatorApp.setButtonText(AppConstants.APP_BUTTON_VIEW_LOG_NAME);
	}
	
}
