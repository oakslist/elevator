package by.epam.applications.beans;

import java.awt.image.BufferedImage;
import java.util.List;

public class SpriteAnimator {

private List<BufferedImage> frames;
	
	public BufferedImage sprite;
	
	private volatile boolean running = false;
	
	private long previousTime;
	private int speed;
	private int frameAtPause, currentFrame;
	
	public SpriteAnimator(List<BufferedImage> frames) {
		this.frames = frames;
	}
	
	public void update(long time) {
		if(running) {
			if(time - previousTime >= speed) {
				//update the animation
				currentFrame++;
				try {
					sprite = frames.get(currentFrame);
				} catch (IndexOutOfBoundsException e) {
					currentFrame = 0;
					sprite = frames.get(currentFrame);
				}
				previousTime = time;
			}
		}
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void start() {
		running = true;
		previousTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
	}

	public void stop() {
		running = false;
		previousTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
	}

	public void pause() {
		frameAtPause = currentFrame;
		running = false;
	}

	public void resume() {
		currentFrame = frameAtPause;
		running = true;
	}
}
