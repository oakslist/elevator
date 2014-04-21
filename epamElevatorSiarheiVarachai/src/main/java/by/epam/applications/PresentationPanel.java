package by.epam.applications;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import by.epam.AppConstants;
import by.epam.ProgramConstants;

public class PresentationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Image backgroundImg;
	private BufferedImage elevatorImg;
	private BufferedImage elevatorDoorsClosedImg;
	private BufferedImage elevatorAlmostDoorsClosedImg;
	private BufferedImage elevatorDownImg;
	private BufferedImage elevatorUpImg;
	private BufferedImage storyImg;
	private BufferedImage passengerImg;
	
	private int firstFloorY;
	private int storiesNumberOnFrame = 5;
	
	public PresentationPanel() {
		Dimension size = getPreferredSize();
		size.width = AppConstants.APP_PRESENTATION_AREA_WIDTH;
		setPreferredSize(size);
		try {
			String imagePath = ProgramConstants.IMG_DIRECTORY;
			backgroundImg = ImageIO.read(new File(imagePath + "background.png"));
			elevatorImg = ImageIO.read(new File(imagePath + "elevator.png"));
			elevatorDoorsClosedImg = ImageIO.read(new File(imagePath + 
					"elevator_doors_closed.png"));
			elevatorAlmostDoorsClosedImg = ImageIO.read(new File(imagePath + 
					"evelator_doors_almost_closed.png"));
			elevatorDownImg = ImageIO.read(new File(imagePath + "elevator_down.png"));
			elevatorUpImg = ImageIO.read(new File(imagePath + "elevator_up.png"));
			storyImg = ImageIO.read(new File(imagePath + "story.png"));
			passengerImg = ImageIO.read(new File(imagePath + "passenger.png"));
			
			firstFloorY = AppConstants.BASEMENT_FOR_PAINT_STORIES - storyImg.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBorder(BorderFactory.createTitledBorder("Presentation area"));
	}
	
	public void paint(Graphics graphics) {
		graphics.drawImage(backgroundImg, 0, 0, null);
		for (int i = 0; i < storiesNumberOnFrame; i++) {
			graphics.drawImage(storyImg, 0, firstFloorY	- storyImg.getHeight() * i, null);
		}
		graphics.drawImage(elevatorImg, 
				(int) (storyImg.getWidth() - elevatorImg.getWidth()) / 2, firstFloorY, null);
		graphics.drawImage(elevatorUpImg, storyImg.getWidth(), firstFloorY, null);
//		graphics.drawImage(elevatorImg, (int) (storyImg.getWidth() - elevatorImg.getWidth()) / 2, firstFloorY - storyImg.getHeight(), null);
//		graphics.drawImage(elevatorUpImg, storyImg.getWidth(), firstFloorY - storyImg.getHeight(), null);
		
		graphics.drawImage(
				passengerImg, storyImg.getWidth()
						- (passengerImg.getWidth() * AppConstants.PASSENGER_MULTIPLIER_NEAR_BY_WALL),
				AppConstants.BASEMENT_FOR_PAINT_PASSENGERS, null);
		graphics.drawImage(passengerImg, storyImg.getWidth() 
				- (passengerImg.getWidth() * 8),
				AppConstants.BASEMENT_FOR_PAINT_PASSENGERS, null);
		
		graphics.setColor(Color.WHITE);
		graphics.drawString("Story: 1", AppConstants.APP_PRESENTATION_AREA_WIDTH - 120, 20);
		graphics.drawString("Moving: up", AppConstants.APP_PRESENTATION_AREA_WIDTH - 120, 40);
		graphics.drawString("Circle: 1", AppConstants.APP_PRESENTATION_AREA_WIDTH - 120, 60);
	}
		
}

