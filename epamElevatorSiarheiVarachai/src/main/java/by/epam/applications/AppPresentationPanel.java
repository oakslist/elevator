package by.epam.applications;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.imageio.ImageIO;

import by.epam.AppConstants;
import by.epam.ProgramConstants;
import by.epam.model.beans.Passenger;
import by.epam.model.containers.DispatchStoryContainer;
import by.epam.model.core.Controller;
import by.epam.model.core.MyBuilding;

public class AppPresentationPanel extends JComponent implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Image backgroundBottomImg;
//	private Image backgroundMiddleImg;
//	private Image backgroundTopImg;
	private BufferedImage elevatorImg;
//	private BufferedImage elevatorDoorsClosedImg;
//	private BufferedImage elevatorAlmostDoorsClosedImg;
	private BufferedImage elevatorDownImg;
	private BufferedImage elevatorUpImg;
	private BufferedImage elevatorNoneImg;
	private BufferedImage storyImg;
	private BufferedImage passengerImg;
	
	//all input data from main program (core)
	private MyBuilding building;
	
	private List<PassengerAppCoordinates> appPassengers = new ArrayList<PassengerAppCoordinates>();
	private InstanceAppCoordinates appElevator;
//	private InstanceAppCoordinates appElevatorMovePointer;
	
	private int firstFloorY;
	// permission to the elevator to start moving
	private boolean elevatorCanMove = false;
	private boolean firstInitialization = false;
	
	public AppPresentationPanel() {
		Dimension size = getPreferredSize();
		size.width = AppConstants.APP_PRESENTATION_AREA_WIDTH;
		setPreferredSize(size);
		try {
			String imagePath = ProgramConstants.IMG_DIRECTORY;
			backgroundBottomImg = ImageIO.read(new File(imagePath + "background.png"));
			elevatorImg = ImageIO.read(new File(imagePath + "elevator.png"));
//			elevatorDoorsClosedImg = ImageIO.read(new File(imagePath + 
//					"elevator_doors_closed.png"));
//			elevatorAlmostDoorsClosedImg = ImageIO.read(new File(imagePath + 
//					"evelator_doors_almost_closed.png"));
			elevatorDownImg = ImageIO.read(new File(imagePath + "elevator_down.png"));
			elevatorUpImg = ImageIO.read(new File(imagePath + "elevator_up.png"));
			elevatorNoneImg = ImageIO.read(new File(imagePath + "elevator_none.png"));
			storyImg = ImageIO.read(new File(imagePath + "story.png"));
			passengerImg = ImageIO.read(new File(imagePath + "passenger.png"));
			
			firstFloorY = AppConstants.GROUND_LINE - storyImg.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		appElevator = new InstanceAppCoordinates((int) (storyImg.getWidth() - elevatorImg.getWidth()) / 2, firstFloorY);
//		appElevatorMovePointer = new InstanceAppCoordinates(storyImg.getWidth(), firstFloorY);
	}
	
	public void setBuilding(MyBuilding building) {
		this.building = building;
	}
			
	public void paintComponent(Graphics graphics) {
		// draw background			
		graphics.drawImage(backgroundBottomImg, 0, 0, null);
		
		// draw stories
		for (int i = 0; i < AppConstants.APP_STORIES_ON_SCREEN; i++) {
			graphics.drawImage(storyImg, 0, firstFloorY	- storyImg.getHeight() * i, null);
		}
		
		// draw ladder
		// TODO
		
		// draw some additional elements
		// TODO
		
		// draw elevator
		graphics.drawImage(elevatorImg, appElevator.getX(), appElevator.getY(), null);

		// draw passengers
		if (building != null) {
			for (PassengerAppCoordinates appPassenger : appPassengers) {
				graphics.drawImage(passengerImg, appPassenger.getX(), appPassenger.getY(), null);
			}
		}
		
		// info block
		graphics.setColor(Color.WHITE);
		graphics.drawString("Story: " + Controller.currentStory, AppConstants.APP_INFO_BLOCK_WIDTH, AppConstants.APP_INFO_BLOCK_HEIGHT);
		graphics.drawString("Circle: " + Controller.getCircleMoving(), AppConstants.APP_INFO_BLOCK_WIDTH, AppConstants.APP_INFO_BLOCK_HEIGHT * 2);
		
		if (building != null) {
			if (Controller.upwardMovement == true) {
				graphics.drawString("Moving: up", AppConstants.APP_INFO_BLOCK_WIDTH, AppConstants.APP_INFO_BLOCK_HEIGHT * 3);
				graphics.drawImage(elevatorUpImg, AppConstants.APP_INFO_BLOCK_WIDTH, AppConstants.APP_INFO_BLOCK_HEIGHT * 4, null);
			} else {
				graphics.drawString("Moving: down", AppConstants.APP_INFO_BLOCK_WIDTH, AppConstants.APP_INFO_BLOCK_HEIGHT * 3);
				graphics.drawImage(elevatorDownImg, AppConstants.APP_INFO_BLOCK_WIDTH, AppConstants.APP_INFO_BLOCK_HEIGHT * 4, null);
			}
		} else {
			graphics.drawString("Moving: none", AppConstants.APP_INFO_BLOCK_WIDTH, AppConstants.APP_INFO_BLOCK_HEIGHT * 3);
			graphics.drawImage(elevatorNoneImg, AppConstants.APP_INFO_BLOCK_WIDTH, AppConstants.APP_INFO_BLOCK_HEIGHT * 4, null);
		}

	}

	public void actionPerformed(ActionEvent e) {
		
		// background case
		if (building != null) {
			// repaint background. elevator are moving UP.
			if (Controller.upwardMovement == true && Controller.currentStory >= AppConstants.APP_STORY_TO_MOVE_BACKGROUND) {
				// TODO up our background
			}
			
			// repaint background. elevator are moving DOWN.
			if (Controller.upwardMovement == false && Controller.currentStory >= AppConstants.APP_STORY_TO_MOVE_BACKGROUND) {
				// TODO down our background. On first and second stories background doesn't move
			}
		}
		
		// stories case
		
		
		// additional elements case
		
		
		// elevator case
		if (elevatorCanMove == false) {
			appElevator.setY(appElevator.getY() - 1 * AppConstants.APP_MULTIPLIER_BOOTS);
//			if (appElevator.getY() % storyImg.getHeight() == 0) {
//				appElevatorMovePointer.setY(appElevatorMovePointer.getY() - storyImg.getHeight());
//			}
		}
		
		// passengers case
		// first initialization passengers. 
		if (building != null && firstInitialization == false) {
			createFirstInitialization();
		}
		

	
		repaint();
	}
	
	private void createFirstInitialization() {
		// All Threads are waiting correct position on the screen
		// checked method isOnPosition();
		// just show moving our characters to the stories on the screen and + 1 stories - to show that building continue
		ElevatorApp.setWorking(true);
		for (PassengerAppCoordinates appPassenger : appPassengers) {
			if (appPassenger.isOnPosition() == false) {
				switch (appPassenger.getCurrentStep()) {
				case AppConstants.APP_INITIALIZE_STEP_ONE:
					// moving passengers to building X coordinate to left
					appPassenger.setX(appPassenger.getX() - (int) (Math.random() * (3 + AppConstants.APP_MULTIPLIER_BOOTS)) - AppConstants.APP_MULTIPLIER_BOOTS);
					if (appPassenger.isOnPosition() == false && appPassenger.getX() <= AppConstants.APP_PRESENTATION_AREA_WIDTH - passengerImg.getWidth() - 160) {
						appPassenger.incCurrentStep();
					}
					break;
				case AppConstants.APP_INITIALIZE_STEP_TWO:
					// moving passengers up. Y to up
					appPassenger.setY(appPassenger.getY() - (int) (Math.random() * AppConstants.APP_MULTIPLIER_BOOTS) - AppConstants.APP_MULTIPLIER_BOOTS);
					if (appPassenger.isOnPosition() == false) {
						if (appPassenger.getCurrentStory() <= AppConstants.APP_STORIES_ON_SCREEN && appPassenger.getY() <= storyImg.getHeight() * appPassenger.getCurrentStory() - AppConstants.BASEMENT_Y_LINE_FOR_PASSENGER_ON_FLOOR) {
							appPassenger.incCurrentStep();
						}
						// kill unnecessary passengers in moving top
						if (appPassenger.getCurrentStory() > AppConstants.APP_STORIES_ON_SCREEN && appPassenger.getY() < 0 - passengerImg.getHeight()) {
							appPassenger.setOnPosition(true);
						}
					}
					break;
				case AppConstants.APP_INITIALIZE_STEP_THREE:
					// moving into room
					appPassenger.setX(appPassenger.getX() - (int) (Math.random() * (3 + AppConstants.APP_MULTIPLIER_BOOTS)) - AppConstants.APP_MULTIPLIER_BOOTS);
					if (appPassenger.getX() <= (storyImg.getWidth() - (passengerImg.getWidth() * 2))) {
						appPassenger.incCurrentStep();
						appPassenger.setOnPosition(true);
					}
				}
			}
		}

		int numberOnPosition = 0;
		for (PassengerAppCoordinates appPassenger : appPassengers) {
			if (appPassenger.isOnPosition() == true) {
				numberOnPosition++;
			}
		}
		if (appPassengers.size() == numberOnPosition) {
			ElevatorApp.setWorking(false);
			firstInitialization = true;
			for (PassengerAppCoordinates appPassenger : appPassengers) {
				synchronized (appPassenger.getPassenger()) {
					appPassenger.getPassenger().notifyAll();
				}
			}
		}

	}
	
	public void createInstancesInPresentationArea() {
		int story = 0;
		for (DispatchStoryContainer dsc : building.getDispatchStoriesContainer()) {
			System.out.println(dsc);
			story++;
			for (Passenger passenger : dsc.getPassengers()) {
				appPassengers.add(new PassengerAppCoordinates(passenger, story, 
						AppConstants.APP_PRESENTATION_AREA_WIDTH + passengerImg.getWidth(), 
						AppConstants.GROUND_LINE - passengerImg.getHeight()));
			}
		}
	}
		
}

