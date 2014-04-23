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
import by.epam.model.core.MyBuilding;

public class AppPresentationPanel extends JComponent implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Image backgroundBottomImg;
//	private Image backgroundMiddleImg;
//	private Image backgroundTopImg;
	private BufferedImage elevatorImg;
//	private BufferedImage elevatorDoorsClosedImg;
//	private BufferedImage elevatorAlmostDoorsClosedImg;
//	private BufferedImage elevatorDownImg;
	private BufferedImage elevatorUpImg;
	private BufferedImage storyImg;
	private BufferedImage passengerImg;
	
	//all input data from main program (core)
	private MyBuilding building;
	
	private List<PassengerAppCoordinates> appPassengers = new ArrayList<PassengerAppCoordinates>();
	private InstanceAppCoordinates appElevator = new InstanceAppCoordinates();
	private InstanceAppCoordinates appElevatorMovePointer = new InstanceAppCoordinates();
	
	private int firstFloorY;
	private int storiesNumberOnFrame = 5;
	
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
//			elevatorDownImg = ImageIO.read(new File(imagePath + "elevator_down.png"));
			elevatorUpImg = ImageIO.read(new File(imagePath + "elevator_up.png"));
			storyImg = ImageIO.read(new File(imagePath + "story.png"));
			passengerImg = ImageIO.read(new File(imagePath + "passenger.png"));
			
			firstFloorY = AppConstants.BASEMENT_FOR_PAINT_STORIES - storyImg.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setBuilding(MyBuilding building) {
		this.building = building;
	}
			
	public void paintComponent(Graphics graphics) {
		graphics.drawImage(backgroundBottomImg, 0, 0, null);
		for (int i = 0; i < storiesNumberOnFrame; i++) {
			graphics.drawImage(storyImg, 0, firstFloorY	- storyImg.getHeight() * i, null);
		}
		graphics.drawImage(elevatorImg, 
				(int) (storyImg.getWidth() - elevatorImg.getWidth()) / 2, firstFloorY, null);
		graphics.drawImage(elevatorUpImg, storyImg.getWidth(), firstFloorY, null);
		
		graphics.drawImage(
				passengerImg, storyImg.getWidth()
						- (passengerImg.getWidth() * AppConstants.PASSENGER_MULTIPLIER_NEAR_BY_WALL),
				AppConstants.BASEMENT_FOR_PAINT_PASSENGERS, null);

//		graphics.drawImage(passengerImg, storyImg.getWidth() 
//				- (passengerImg.getWidth() * 8),
//				AppConstants.BASEMENT_FOR_PAINT_PASSENGERS, null);
		if (building != null) {
			graphics.drawImage(passengerImg, appPassengers.get(1).getX(), appPassengers.get(1).getY(), null);
		}
		
		graphics.setColor(Color.WHITE);
		graphics.drawString("Story: 1", AppConstants.APP_PRESENTATION_AREA_WIDTH - 120, 20);
		graphics.drawString("Moving: up", AppConstants.APP_PRESENTATION_AREA_WIDTH - 120, 40);
		graphics.drawString("Circle: 1", AppConstants.APP_PRESENTATION_AREA_WIDTH - 120, 60);
	}

	public void actionPerformed(ActionEvent e) {
		if (building != null) {
			appPassengers.get(1).setX(appPassengers.get(1).getX() + 2);
		}
		firstFloorY = firstFloorY + 5;
		repaint();
	}
	
	public void createPassengersInApp() {
		int story = 0;
		for (DispatchStoryContainer dsc : building.getDispatchStoriesContainer()) {
			System.out.println(dsc);
			story++;
			for (Passenger passenger : dsc.getPassengers()) {
				appPassengers.add(new PassengerAppCoordinates(passenger, story, 0, AppConstants.BASEMENT_FOR_PAINT_PASSENGERS));
			}
		}		
	}
		
}

