package by.epam.applications.beans;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	public BufferedImage spriteSheet;
	
	public SpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
	
	public BufferedImage grabSprite(int x, int y, int width, int height) {
		BufferedImage sprite = spriteSheet.getSubimage(x,  y,  width, height);
		return sprite;
	}
	
}
