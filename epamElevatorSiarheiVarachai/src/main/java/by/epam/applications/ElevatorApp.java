package by.epam.applications;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import by.epam.constants.AppConstants;
import by.epam.constants.ProgramConstants;
import by.epam.logs.OpenLogFile;
import by.epam.model.core.MyBuilding;

public class ElevatorApp extends JFrame {

	private static final long serialVersionUID = 1L;

	private final static AppPresentationPanel PRESENTATION_AREA = new AppPresentationPanel();
	private final static AppMessagesPanel MESSAGE_AREA = new AppMessagesPanel();
	private final static int timerMillis = 50;
	private static JButton button = new JButton(AppConstants.APP_BUTTON_START_NAME);
	private static Timer timer = new Timer(timerMillis, PRESENTATION_AREA);
	private static boolean isWorking = false;
	private static boolean repaintPassenger = false;
	private static boolean repaintElevator = false;
	
	public ElevatorApp(String title) {
		super(title);
		
		// set layout manager
		setLayout(new BorderLayout());

		// set swing component to JFrame
		super.add(MESSAGE_AREA, BorderLayout.CENTER);
		super.add(button, BorderLayout.SOUTH);
		super.add(PRESENTATION_AREA, BorderLayout.WEST);
		
		// add behavior
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (button.getText().equals(
						AppConstants.APP_BUTTON_VIEW_LOG_NAME)) {
					// open log file
					new OpenLogFile();
				}
				if (button.getText().equals(AppConstants.APP_BUTTON_ABORT_NAME)) {
					ProgramConstants.appButtonListener
							.setAbortButtonClicked(true);
					button.setText(AppConstants.APP_BUTTON_VIEW_LOG_NAME);
				}
				if (button.getText().equals(AppConstants.APP_BUTTON_START_NAME)) {
					ProgramConstants.appButtonListener
							.startButtonlistener(true);
					button.setText(AppConstants.APP_BUTTON_ABORT_NAME);
				}
			}
		});
	
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(AppConstants.APP_MAIN_WIDTH, 
				AppConstants.APP_MAIN_HEIGHT);
	}
	
	public static void startElevatorAppFrame() {
		JFrame frame = new ElevatorApp("Elevator");
		frame.setSize(AppConstants.APP_MAIN_WIDTH, 
				AppConstants.APP_MAIN_HEIGHT);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
	
	public static void startApp() {
		timer.start();
	}
	
	public static void stopApp() {
		timer.stop();
	}
	
	public static void setTimer(int boost) {
		timer.setDelay((int) timerMillis / boost);
	}
	
	public static void setButtonText(String text) {
		ElevatorApp.button.setText(text);
	}
	
	public static void setInitialDataToApp(MyBuilding building) {
		ElevatorApp.PRESENTATION_AREA.setBuilding(building);
		ElevatorApp.PRESENTATION_AREA.createInstancesInPresentationArea();
	}

	public static boolean isWorking() {
		return isWorking;
	}

	public static void setWorking(boolean isWorking) {
		ElevatorApp.isWorking = isWorking;
	}

	public static boolean isRepaintPassenger() {
		return repaintPassenger;
	}

	public static void setRepaintPassenger(boolean repaintPassenger, 
			String passengerId) {
		ElevatorApp.repaintPassenger = repaintPassenger;
		ElevatorApp.PRESENTATION_AREA.repaintPassenger(passengerId);
	}
	
	public static void setRepaintPassenger(boolean repaintPassenger) {
		ElevatorApp.repaintPassenger = repaintPassenger;
	}

	public static boolean isRepaintElevator() {
		return repaintElevator;
	}
	
	public static void setRepaintElevator(boolean repaintElevator) {
		ElevatorApp.repaintElevator = repaintElevator;
	}

	public static void setRepaintElevator(boolean repaintElevator, 
			int nextStory) {
		ElevatorApp.repaintElevator = repaintElevator;
		ElevatorApp.PRESENTATION_AREA.repaintElevator(nextStory);
	}

}

