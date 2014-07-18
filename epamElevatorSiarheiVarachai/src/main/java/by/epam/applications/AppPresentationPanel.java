package by.epam.applications;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import by.epam.applications.beans.HealthAppCoordinates;
import by.epam.applications.beans.InstanceAppCoordinates;
import by.epam.applications.beans.PassengerAppCoordinates;
import by.epam.applications.beans.SpriteAnimator;
import by.epam.applications.beans.SpriteSheet;
import by.epam.constants.AppConstants;
import by.epam.model.beans.Passenger;
import by.epam.model.containers.DispatchStoryContainer;
import by.epam.model.core.Controller;
import by.epam.model.core.MyBuilding;

public class AppPresentationPanel extends JComponent implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Image backgroundBottomImg;
	private Image backgroundMiddleImg;
	private Image backgroundTopImg;
	private Image backgroundTop2Img;
	private Image cloudOneImg;
	private Image cloudTwoImg;
	private BufferedImage deadBrainImg;
	private BufferedImage elevatorImg;
	private BufferedImage elevatorDownImg;
	private BufferedImage elevatorUpImg;
	private BufferedImage elevatorNoneImg;
	private BufferedImage storyImg;
	private BufferedImage brainImg;
	private BufferedImage passengerImg;
	private BufferedImage ladderImg;
	private BufferedImage bottomImg;
	
	//all input data from main program (core)
	private MyBuilding building;
	private SpriteAnimator animatorPassenger;
	private SpriteAnimator animatorBrain;
	private SpriteAnimator animatorDeadBrain;
	
	private int timeUntilNextCloud = 1000;
	private int cloudsOnScreen = 1;
	
	private List<PassengerAppCoordinates> appPassengers 
			= new ArrayList<PassengerAppCoordinates>();
	private List<InstanceAppCoordinates> appClouds 
			= new ArrayList<InstanceAppCoordinates>();
	private List<HealthAppCoordinates> appHealths 
			= new ArrayList<HealthAppCoordinates>();
	private InstanceAppCoordinates appElevator;
	private InstanceAppCoordinates appBackground;
	
	private int firstFloorY;
	// permission to the elevator to start moving
	private boolean firstInitialization = false;
	
	private PassengerAppCoordinates drawAppPassenger;
	private List<PassengerAppCoordinates> drawAppPassengersInElevator 
			= new ArrayList<PassengerAppCoordinates>();
	
	
	private Image healthBarImg;
	
	public AppPresentationPanel() {
		Dimension size = getPreferredSize();
		size.width = AppConstants.APP_PRESENTATION_AREA_WIDTH;
		setPreferredSize(size);
		try {
			deadBrainImg = ImageIO.read(getClass()
					.getResource("/images/sprite_dead_brain.png"));
			healthBarImg = ImageIO.read(getClass()
					.getResource("/images/health_bar.png"));
			backgroundBottomImg = ImageIO.read(getClass()
					.getResource("/images/background.png"));
			backgroundMiddleImg = ImageIO.read(getClass()
					.getResource("/images/background_middle.png"));
			backgroundTopImg = ImageIO.read(getClass()
					.getResource("/images/background_top.png"));
			backgroundTop2Img = ImageIO.read(getClass()
					.getResource("/images/background_top2.png"));
			elevatorImg = ImageIO.read(getClass()
					.getResource("/images/elevator.png"));
			elevatorDownImg = ImageIO.read(getClass()
					.getResource("/images/elevator_down.png"));
			elevatorUpImg = ImageIO.read(getClass()
					.getResource("/images/elevator_up.png"));
			elevatorNoneImg = ImageIO.read(getClass()
					.getResource("/images/elevator_none.png"));
			storyImg = ImageIO.read(getClass()
					.getResource("/images/story.png"));
			brainImg = ImageIO.read(getClass()
					.getResource("/images/brain_sprite.png"));
			passengerImg = ImageIO.read(getClass()
					.getResource("/images/sprite_zombie.png"));
			ladderImg = ImageIO.read(getClass()
					.getResource("/images/ladder.png"));
			bottomImg = ImageIO.read(getClass()
					.getResource("/images/bottom.png"));
			cloudOneImg = ImageIO.read(getClass()
					.getResource("/images/cloud1.png"));
			cloudTwoImg = ImageIO.read(getClass()
					.getResource("/images/cloud2.png"));
		
			firstFloorY = AppConstants.GROUND_LINE - storyImg.getHeight();
			
			appClouds.add(new InstanceAppCoordinates(0, 
					AppConstants.APP_PRESENTATION_AREA_WIDTH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		appBackground = new InstanceAppCoordinates(0, 0);
		appElevator = new InstanceAppCoordinates(
				(int) (storyImg.getWidth() - elevatorImg.getWidth()) / 2, firstFloorY);
		
		for (int i = 0; i < cloudsOnScreen; i++) {
			appClouds.add(
					new InstanceAppCoordinates(AppConstants.APP_PRESENTATION_AREA_WIDTH 
							+ (int) Math.random() * 5 + 5, 
							(int) Math.random() * 600 - 500));
		}
	}
	
	public void setBuilding(MyBuilding building) {
		this.building = building;
	}
			
	public void paintComponent(Graphics graphics) {
		// draw background			
		graphics.drawImage(backgroundBottomImg, appBackground.getX(), 
				appBackground.getY(), null);
		
		// draw some more additional elements here or
		// if in each building - use drawBuilding() 
		// TODO
		
		if (building != null) {
			// draw stories and additional elements
			drawBuilding(graphics, building.getStoriesNumber());
		
			// draw elevator
			graphics.drawImage(elevatorImg, appElevator.getX(), 
					appElevator.getY(), null);
			graphics.drawString("Capacity: " + building
					.getElevator().getElevatorCapacity(), 
					appElevator.getX() + 1, appElevator.getY() - 20);
			graphics.drawString("Zombies: " + building.getElevator()
					.getElevatorContainer().getPassengers().size(), 
					appElevator.getX() + 1, appElevator.getY() - 5);

			// draw passengers
			if (building != null) {
//				int i = 0;
				for (PassengerAppCoordinates appPassenger : appPassengers) {
					graphics.drawImage(animatorPassenger.sprite, appPassenger.getX(), 
							appPassenger.getY(), null);
					// for show id passengers. int i = 0 above for need to be uncommented
//					graphics.drawString("id: " 
//					+ appPassenger.getPassenger().getPassengerId(), 
//					appPassenger.getX(), appPassenger.getY() - 9 * i++);
				}
			}
		}
		
		if (animatorPassenger != null) {
			animatorPassenger.update(System.currentTimeMillis());
		}
		if (animatorBrain != null) {
			animatorBrain.update(System.currentTimeMillis());
		}		
		if (animatorDeadBrain != null) {
			animatorDeadBrain.update(System.currentTimeMillis());
		}	
		
		// info block
		drawInfoBlock(graphics);

	}
	
	private void drawHealthBar(Graphics graphics, int story) {
//		for (HealthAppCoordinates appHealth : appHealths) {
		
			if (appHealths.get(story).getHealth() >= 0) {
				if (appHealths.get(story).getTimeToHurt() == 0) {
					appHealths.get(story).setHealth(appHealths.get(story).getHealth() 
							- appHealths.get(story).getMultiplier());
					appHealths.get(story).setTimeToHurt(AppConstants.APP_TIME_TO_HURT);
				} else {
					appHealths.get(story).setTimeToHurt(appHealths.get(story).getTimeToHurt() - 1);
				}
			}
//		}
		
//		for (HealthAppCoordinates appHealth : appHealths) {
//			if (appHealth.getHealth() > 0) {
//				if (appHealth.getTimeToHurt() == 0) {
//					appHealth.setHealth(appHealth.getHealth() 
//							- appHealth.getMultiplier());
//					appHealth.setTimeToHurt(AppConstants.APP_TIME_TO_HURT);
//				} else {
//					appHealth.setTimeToHurt(appHealth.getTimeToHurt() - 1);
//				}
//			}
//		}
		
		// used for the white background of health bar...
		graphics.drawImage(healthBarImg, storyImg.getWidth() - storyImg.getWidth(), 
				firstFloorY - storyImg.getHeight() 
				* story + 40 + appBackground.getY(), null);
		graphics.setColor(Color.WHITE);
		graphics.fillRect(storyImg.getWidth() - storyImg.getWidth() + 3, 
				firstFloorY - storyImg.getHeight() 
				* story + 42 + appBackground.getY(), AppConstants.APP_HEALTH_WIDTH, 4);
		// used for the actual health bar itself...
		if (appHealths.get(story).getHealth() > 0) {
			graphics.setColor(Color.RED);
			graphics.fillRect(storyImg.getWidth() - storyImg.getWidth() + 3, 
					firstFloorY - storyImg.getHeight() 
					* story + 42 + appBackground.getY(), 
					appHealths.get(story).getHealth(), 4);
		}
	}
	
	private void drawInfoBlock(Graphics graphics) {
		graphics.setColor(Color.BLACK);
		int stories = 0;
		if (building != null) {
			stories = building.getStoriesNumber();
		}
		graphics.drawString("Stories: " + stories, 
				AppConstants.APP_INFO_BLOCK_WIDTH, 
				AppConstants.APP_INFO_BLOCK_HEIGHT);
		graphics.drawString("Story: " + Controller.currentStory, 
				AppConstants.APP_INFO_BLOCK_WIDTH, 
				AppConstants.APP_INFO_BLOCK_HEIGHT * 2);
		graphics.drawString("Circle: " + Controller.getCircleMoving(), 
				AppConstants.APP_INFO_BLOCK_WIDTH, 
				AppConstants.APP_INFO_BLOCK_HEIGHT * 3);
		
		if (building != null) {
			if (Controller.upwardMovement == true) {
				graphics.drawString("Moving: up", 
						AppConstants.APP_INFO_BLOCK_WIDTH, 
						AppConstants.APP_INFO_BLOCK_HEIGHT * 4);
				graphics.drawImage(elevatorUpImg, 
						AppConstants.APP_INFO_BLOCK_WIDTH, 
						AppConstants.APP_INFO_BLOCK_HEIGHT * 5, null);
			} else {
				graphics.drawString("Moving: down", 
						AppConstants.APP_INFO_BLOCK_WIDTH, 
						AppConstants.APP_INFO_BLOCK_HEIGHT * 4);
				graphics.drawImage(elevatorDownImg, 
						AppConstants.APP_INFO_BLOCK_WIDTH, 
						AppConstants.APP_INFO_BLOCK_HEIGHT * 5, null);
			}
		} else {
			graphics.drawString("Moving: none", 
					AppConstants.APP_INFO_BLOCK_WIDTH, 
					AppConstants.APP_INFO_BLOCK_HEIGHT * 4);
			graphics.drawImage(elevatorNoneImg, 
					AppConstants.APP_INFO_BLOCK_WIDTH, 
					AppConstants.APP_INFO_BLOCK_HEIGHT * 5, null);
		}
		
		graphics.setColor(Color.BLACK);
		graphics.drawImage(bottomImg, 0, AppConstants.GROUND_LINE, null);
		graphics.drawString("Elevator for <epam> course", 
				bottomImg.getWidth() - 222, AppConstants.GROUND_LINE + 36);
		graphics.drawString("by Siarhei Varachai", 
				bottomImg.getWidth() - 175, AppConstants.GROUND_LINE + 49);
	}
	
	private void drawBuilding(Graphics graphics, int stories) {
		graphics.setColor(Color.WHITE);
		if (stories <= AppConstants.APP_STORIES_ON_SCREEN) {
			// top background
			graphics.drawImage(backgroundTop2Img, 0, 0, null);
			for (InstanceAppCoordinates cloud : appClouds) {
				graphics.drawImage(cloudOneImg, cloud.getX() - 2, cloud.getY(), null);
//				graphics.drawImage(cloudTwoImg, cloud.getX() - 1, cloud.getY(), null);
			}
			for (int i = 0; i < stories; i++) {
				graphics.drawImage(storyImg, 0, 
						firstFloorY	- storyImg.getHeight() * i, null);
				// draw ladder
				graphics.drawImage(ladderImg, storyImg.getWidth(), 
						firstFloorY	- storyImg.getHeight() * i, null);
				// draw info about containers
				graphics.drawString("dispatchStoryContainer: " 
						+ building.getDispatchStoryContainer(i + 1).getPassengers().size(), 
						(int) storyImg.getWidth() / 2 + 65, 
						firstFloorY	- storyImg.getHeight() * i + 9);
				graphics.drawString("arrivalStoryContainer: " 
						+ building.getArrivalStoryContainer(i + 1).getPassengers().size(), 
						(int) storyImg.getWidth() / 2 - 200, 
						firstFloorY - storyImg.getHeight() * i + 9);
				graphics.drawString("Story: " +  (i + 1), 
						storyImg.getWidth() - 75, 
						firstFloorY - storyImg.getHeight() * i + 35);
				// show the brains on each story
				if (appHealths.get(i).getHealth() >= 0) {
					graphics.drawImage(animatorBrain.sprite, 0, 
							firstFloorY - storyImg.getHeight() * i + 50 + appBackground.getY(), null);
				} else {
					graphics.drawImage(animatorDeadBrain.sprite, 2, 
							firstFloorY - storyImg.getHeight() * i + 80 + appBackground.getY(), null);
				}
				drawHealthBar(graphics, i);
				graphics.setColor(Color.WHITE);				
			}
		} else {
			// top background
			graphics.drawImage(backgroundTopImg, 0, 
					firstFloorY - storyImg.getHeight() 
					* (stories + 1) + appBackground.getY(), null);
			for (InstanceAppCoordinates cloud : appClouds) {
//				graphics.drawImage(cloudOneImg, cloud.getX() - 1, cloud.getY(), null);
				graphics.drawImage(cloudTwoImg, cloud.getX() - 1, cloud.getY(), null);
			}
			for (int i = 0; i < stories; i++) {
				if (i >= AppConstants.APP_STORIES_ON_SCREEN) {
					graphics.drawImage(backgroundMiddleImg, 0, 
							firstFloorY - storyImg.getHeight() 
							* i + appBackground.getY(), null);
				}
				graphics.drawImage(storyImg, 0, 
						firstFloorY	- storyImg.getHeight() 
						* i + appBackground.getY(), null);
				// draw ladder
				graphics.drawImage(ladderImg, storyImg.getWidth(), 
						firstFloorY - storyImg.getHeight() 
						* i + appBackground.getY(), null);
				// draw info about containers
				graphics.drawString("dispatchStoryContainer: " 
						+ building.getDispatchStoryContainer(i + 1).getPassengers().size(), 
						(int) storyImg.getWidth() / 2 + 65, 
						firstFloorY	- storyImg.getHeight() 
						* i + 9 + appBackground.getY());
				graphics.drawString("arrivalStoryContainer: " 
						+ building.getArrivalStoryContainer(i + 1).getPassengers().size(), 
						(int) storyImg.getWidth() / 2 - 200, 
						firstFloorY - storyImg.getHeight() * i + 9 + appBackground.getY());
				graphics.drawString("Story: " +  (i + 1), storyImg.getWidth() - 80, 
						firstFloorY - storyImg.getHeight() * i + 35 + appBackground.getY());
				// show the brains on each story
				if (appHealths.get(i).getHealth() >= 0) {
					graphics.drawImage(animatorBrain.sprite, 0, 
							firstFloorY - storyImg.getHeight() * i + 50 + appBackground.getY(), null);
				} else {
					graphics.drawImage(animatorDeadBrain.sprite, 2, 
							firstFloorY - storyImg.getHeight() * i + 80 + appBackground.getY(), null);
				}
				drawHealthBar(graphics, i);
				graphics.setColor(Color.WHITE);
			}
		}
	}

	// update elements before drawing
	public void actionPerformed(ActionEvent e) {
		// additional elements case
		if (timeUntilNextCloud <= 0) {
			for (InstanceAppCoordinates cloud : appClouds) {
				cloud.setX(AppConstants.APP_PRESENTATION_AREA_WIDTH 
						+ (int) Math.random() * 5 - 6 + 600);
				cloud.setY(AppConstants.APP_PRESENTATION_AREA_HEIGHT 
						+ (int) Math.random() * 600 - 500);
			}
			timeUntilNextCloud = 1000;
		} else {
			timeUntilNextCloud = timeUntilNextCloud - 1;
		}
		
		for (InstanceAppCoordinates cloud : appClouds) {
			cloud.setX(cloud.getX() - 2);
		}
		
		// elevator case
		if (ElevatorApp.isRepaintElevator() == true) {
			int heightUp;
			if (Controller.upwardMovement == true) {
				heightUp = AppConstants.APP_MULTIPLIER_ELEVATOR_BOOTS;
			} else {
				heightUp = -1 * AppConstants.APP_MULTIPLIER_ELEVATOR_BOOTS;
			}
			
			// OFF moving for repaint
			if (building.getStoriesNumber() <= AppConstants.APP_STORIES_ON_SCREEN) {
				appElevator.setY(appElevator.getY() - heightUp);
				for (PassengerAppCoordinates appPassenger : drawAppPassengersInElevator) {
					appPassenger.setY(appPassenger.getY() - heightUp);
				}
				if (Controller.upwardMovement == true) {
					if (appElevator.getY() <= (AppConstants
							.APP_STORIES_ON_SCREEN - building.getElevator()
							.getCurrentStory()) * storyImg.getHeight()) {
						for (PassengerAppCoordinates appPassenger : drawAppPassengersInElevator) {
							appPassenger.setOnPosition(true);
						}
						ElevatorApp.setRepaintElevator(false);
						isAllOnPosition();
					}
				} else {
					if (appElevator.getY() >= (AppConstants
							.APP_STORIES_ON_SCREEN - building.getElevator()
							.getCurrentStory()) * storyImg.getHeight()) {
						for (PassengerAppCoordinates appPassenger : drawAppPassengersInElevator) {
							appPassenger.setOnPosition(true);
						}
						ElevatorApp.setRepaintElevator(false);
						isAllOnPosition();
					}
				}
			}
			
			// ON moving for repaint
			if (building.getStoriesNumber() > AppConstants.APP_STORIES_ON_SCREEN) {
				if ((building.getElevator().getCurrentStory() <= 3 
						&& Controller.upwardMovement == true)
						|| (building.getElevator().getCurrentStory() < 3 
						&& Controller.upwardMovement == false)) {
					if (Controller.upwardMovement == true) {
						appElevator.setY(appElevator.getY() - heightUp);
						repaintMyBackground(heightUp);
					
						for (PassengerAppCoordinates appPassenger : drawAppPassengersInElevator) {
							appPassenger.setY(appPassenger.getY() - heightUp);
						}
						if (appElevator.getY() <= (AppConstants
								.APP_STORIES_ON_SCREEN - building.getElevator()
								.getCurrentStory()) * storyImg.getHeight()) {
							for (PassengerAppCoordinates appPassenger : drawAppPassengersInElevator) {
								appPassenger.setOnPosition(true);
							}
							ElevatorApp.setRepaintElevator(false);
							isAllOnPosition();
						}
					} else {
						appElevator.setY(appElevator.getY() - heightUp);
						if (building.getElevator().getCurrentStory() == 3) {
							repaintMyBackground(heightUp);
						} else {	
					
							for (PassengerAppCoordinates appPassenger : drawAppPassengersInElevator) {
								appPassenger.setY(appPassenger.getY() - heightUp);
							}
							if (appElevator.getY() >= (AppConstants
									.APP_STORIES_ON_SCREEN - building.getElevator()
									.getCurrentStory()) * storyImg.getHeight()) {
								for (PassengerAppCoordinates appPassenger : drawAppPassengersInElevator) {
									appPassenger.setOnPosition(true);
								}	
								ElevatorApp.setRepaintElevator(false);
							isAllOnPosition();
							}	
						}
					}
				}
			
				if ((building.getElevator().getCurrentStory() > 3 
						&& Controller.upwardMovement == true)
						|| (building.getElevator().getCurrentStory() >= 3 
						&& Controller.upwardMovement == false)) {
					if (Controller.upwardMovement == true) {
						repaintMyBackground(heightUp);
						if (appBackground.getY() >= storyImg.getHeight() 
								* (building.getElevator().getCurrentStory() - 3)) {
							for (PassengerAppCoordinates appPassenger : drawAppPassengersInElevator) {
								appPassenger.setOnPosition(true);
							}
							ElevatorApp.setRepaintElevator(false);
							isAllOnPosition();
						}
					} else {
						repaintMyBackground(heightUp);
						if (appBackground.getY() <= storyImg.getHeight() 
								* (building.getElevator().getCurrentStory() - 3)) {
							for (PassengerAppCoordinates appPassenger : drawAppPassengersInElevator) {
								appPassenger.setOnPosition(true);
							}	
							ElevatorApp.setRepaintElevator(false);
							isAllOnPosition();
						}
					}
				}
			}
		}
		
		// passengers case
		// first initialization passengers.
		if (building != null && firstInitialization == false) {
			createFirstInitialization();
		}
		if (animatorPassenger != null) {
			animatorPassenger.update(System.currentTimeMillis());
		}
		if (animatorBrain != null) {
			animatorBrain.update(System.currentTimeMillis());
		}
		if (ElevatorApp.isWorking() == true 
				&& ElevatorApp.isRepaintPassenger() == true) {
			switch (drawAppPassenger.getCurrentStep()) {
			case AppConstants.APP_INITIALIZE_STEP_FOUR:
				drawAppPassenger.setX(drawAppPassenger.getX() 
						- (int) (Math.random() 
						* (AppConstants.APP_MULTIPLIER_BOOTS)) 
						- AppConstants.APP_MULTIPLIER_BOOTS);
				if (drawAppPassenger.getX() <= (int) (storyImg.getWidth() / 2) - 20) {
					drawAppPassenger.setOnPosition(true);
					ElevatorApp.setRepaintPassenger(false);
					drawAppPassenger.incCurrentStep();
				}
				break;
			case AppConstants.APP_INITIALIZE_STEP_FIVE:
				drawAppPassenger.setX(drawAppPassenger.getX() 
						- (int) (Math.random() 
						* (AppConstants.APP_MULTIPLIER_BOOTS)) 
						- AppConstants.APP_MULTIPLIER_BOOTS);
				if (drawAppPassenger.getX() <= AppConstants.APP_X_FINISHED_PASSENGER) {
					drawAppPassenger.setOnPosition(true);
					ElevatorApp.setRepaintPassenger(false);
					drawAppPassenger.incCurrentStep();
					// start decrement brain health
					appHealths.get(drawAppPassenger.getPassenger()
							.getDestinationStory() - 1).incMultiplier();
				}
				break;
			}	
			isAllOnPosition();
		}
		
		repaint();
	}
	
	private void repaintMyBackground(int step) {
		// background case
		if (building != null) {
			// repaint background. elevator are moving UP.
			if (Controller.currentStory > AppConstants.APP_STORY_TO_MOVE_BACKGROUND
					|| (Controller.currentStory == AppConstants
							.APP_STORY_TO_MOVE_BACKGROUND 
							&& Controller.upwardMovement == false)) {
				// down our background
				appBackground.setY(appBackground.getY() + step);
				for (PassengerAppCoordinates appPassenger : appPassengers) {
					if (appPassenger.getCurrentStep() != 5) {
						appPassenger.setY(appPassenger.getY() + step);	
					}					
				}
				for (InstanceAppCoordinates cloud : appClouds) {
					cloud.setY(cloud.getY() + step);
				}
			}
		}
	}
	
	public void repaintPassenger(String passengerId) {
		for (PassengerAppCoordinates appPassenger : appPassengers) {
			if (appPassenger.getPassenger()
					.getPassengerId().equals(passengerId)) {
				appPassenger.setOnPosition(false);
				this.drawAppPassenger = appPassenger;
				ElevatorApp.setWorking(true);
				break;
			}
		}
	}
	
	public void repaintElevator(int nextStory) {
		this.drawAppPassengersInElevator.clear();
		for (Passenger elevatorPassenger : building
				.getElevator().getElevatorContainer().getPassengers()) {
			for (PassengerAppCoordinates appPassenger : appPassengers) {
				if (elevatorPassenger.equals(appPassenger.getPassenger())) {
					appPassenger.setOnPosition(false);
					this.drawAppPassengersInElevator.add(appPassenger);
					ElevatorApp.setWorking(true);
					break;
				}
			}
		}
	}
	
	private void createFirstInitialization() {
		// All Threads are waiting correct position on the screen
		// checked method isOnPosition();
		// just show moving our characters to the stories on the screen
		// and + 1 stories - to show that building continue
		ElevatorApp.setWorking(true);
		
		// create sprite for passengers
		SpriteSheet spritePassenger = new SpriteSheet(passengerImg);
		List<BufferedImage> spritesPassenger = new ArrayList<BufferedImage>();
		
		spritesPassenger.add(spritePassenger.grabSprite(10, 163, 32, 42));
		spritesPassenger.add(spritePassenger.grabSprite(60, 163, 33, 42));
		spritesPassenger.add(spritePassenger.grabSprite(114, 163, 35, 42));
		spritesPassenger.add(spritePassenger.grabSprite(158, 163, 32, 42));
		
		animatorPassenger = new SpriteAnimator(spritesPassenger);
		animatorPassenger.setSpeed(200);
		animatorPassenger.start();
		
		// create sprite for brains
		SpriteSheet spriteBrain = new SpriteSheet(brainImg);
		List<BufferedImage> spritesBrain = new ArrayList<BufferedImage>();
		
		spritesBrain.add(spriteBrain.grabSprite(7, 22, 62, 57));
		spritesBrain.add(spriteBrain.grabSprite(86, 21, 62, 57));
		spritesBrain.add(spriteBrain.grabSprite(158, 21, 62, 57));
		spritesBrain.add(spriteBrain.grabSprite(230, 21, 62, 57));
		
		animatorBrain = new SpriteAnimator(spritesBrain);
		animatorBrain.setSpeed(200);
		animatorBrain.start();

		// create sprite for dead brains
		SpriteSheet spriteDeadBrain = new SpriteSheet(deadBrainImg);
		List<BufferedImage> spritesDeadBrain = new ArrayList<BufferedImage>();
		
		spritesDeadBrain.add(spriteDeadBrain.grabSprite(1, 3, 64, 53));
		spritesDeadBrain.add(spriteDeadBrain.grabSprite(91, 2, 64, 53));
		spritesDeadBrain.add(spriteDeadBrain.grabSprite(2, 68, 64, 53));
		spritesDeadBrain.add(spriteDeadBrain.grabSprite(91, 70, 64, 53));
		
		animatorDeadBrain = new SpriteAnimator(spritesDeadBrain);
		animatorDeadBrain.setSpeed(200);
		animatorDeadBrain.start();
		
		for (PassengerAppCoordinates appPassenger : appPassengers) {
			if (appPassenger.isOnPosition() == false) {
				switch (appPassenger.getCurrentStep()) {
				case AppConstants.APP_INITIALIZE_STEP_ONE:
					// moving passengers to building X coordinate to left
					appPassenger.setX(appPassenger.getX() - (int) (Math.random() 
							* (AppConstants.APP_MULTIPLIER_BOOTS)) 
							- AppConstants.APP_MULTIPLIER_BOOTS);
					if (appPassenger.isOnPosition() == false 
							&& appPassenger.getX() <= AppConstants
									.APP_PRESENTATION_AREA_WIDTH 
									- AppConstants.APP_PASSENGER_WIDTH - 160) {
						appPassenger.incCurrentStep();
					}
					break;
				case AppConstants.APP_INITIALIZE_STEP_TWO:
					// moving passengers up. Y to up
					appPassenger.setY(appPassenger.getY() - (int) (Math.random() 
							* AppConstants.APP_MULTIPLIER_BOOTS) 
							- AppConstants.APP_MULTIPLIER_BOOTS);
					if (appPassenger.isOnPosition() == false) {
						if (appPassenger.getCurrentStory() <= AppConstants
								.APP_STORIES_ON_SCREEN 
								&& appPassenger.getY() <= storyImg.getHeight() 
								* (AppConstants.APP_STORIES_ON_SCREEN 
										- appPassenger.getCurrentStory() + 1) 
								- AppConstants.BASEMENT_Y_LINE_FOR_PASSENGER_ON_FLOOR) {
							appPassenger.incCurrentStep();
						}
						// kill unnecessary passengers in moving top
						if (appPassenger.getCurrentStory() > AppConstants
								.APP_STORIES_ON_SCREEN 
								&& appPassenger.getY() < 0 - AppConstants.APP_PASSENGER_HEIGHT) {
							appPassenger.setX(storyImg.getWidth() - (AppConstants.APP_PASSENGER_WIDTH * 2));
							appPassenger.setY(storyImg.getHeight()
									* (AppConstants.APP_STORIES_ON_SCREEN 
											- appPassenger.getCurrentStory() + 1) 
									- AppConstants.BASEMENT_Y_LINE_FOR_PASSENGER_ON_FLOOR);
							appPassenger.setOnPosition(true);
							appPassenger.incCurrentStep();
							appPassenger.incCurrentStep();
						}
					}
					break;
				case AppConstants.APP_INITIALIZE_STEP_THREE:
					// moving into room
					appPassenger.setX(appPassenger.getX() 
							- (int) (Math.random() * (AppConstants
									.APP_MULTIPLIER_BOOTS)) 
							- AppConstants.APP_MULTIPLIER_BOOTS);
					if (appPassenger.getX() <= (storyImg.getWidth() 
							- (AppConstants.APP_PASSENGER_WIDTH * 2))) {
						appPassenger.incCurrentStep();
						appPassenger.setOnPosition(true);
					}
				}
			}
		}
		isAllOnPosition();
	}
	
	private void isAllOnPosition() {
		int numberOnPosition = 0;
		for (PassengerAppCoordinates appPassenger : appPassengers) {
			if (appPassenger.isOnPosition() == true) {
				numberOnPosition++;
			}
		}
		if (appPassengers.size() == numberOnPosition) {
			ElevatorApp.setWorking(false);
			firstInitialization = true;
			synchronized (building) {
				building.notifyAll();
				ElevatorApp.setButtonEnabled(true);
			}
		}		
	}
	
	public void createInstancesInPresentationArea() {
		int story = 0;
		for (DispatchStoryContainer dsc : building.getDispatchStoriesContainer()) {
			story++;
			appHealths.add(new HealthAppCoordinates(storyImg.getWidth() 
					- storyImg.getWidth(), firstFloorY - storyImg.getHeight() 
					* story + 29 + appBackground.getY(), story));
			for (Passenger passenger : dsc.getPassengers()) {
				appPassengers.add(new PassengerAppCoordinates(passenger, story, 
						AppConstants.APP_PRESENTATION_AREA_WIDTH 
						+ AppConstants.APP_PASSENGER_WIDTH, 
						AppConstants.GROUND_LINE - AppConstants.APP_PASSENGER_HEIGHT));
			}
		}		
	}
		
}

