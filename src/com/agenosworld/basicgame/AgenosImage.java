/**
 * 
 */
package com.agenosworld.basicgame;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Michael
 *
 */
public class AgenosImage extends SpriteSheet implements Updatable {
	
	private int frameDelta;
	private int currDelta = 0;
	
	private int currFrame;
	private int frameCount;
	
	private boolean animated;

	/**
	 * @param ref
	 * @param tw
	 * @param th
	 * @throws SlickException
	 */
	public AgenosImage(String ref, int xOffset, int yOffset, int tw, int th, int frameRate, int frameCount) throws SlickException {
		super(ref, tw, th);
		
		animated = true;
		currFrame = 0;
		this.frameCount = frameCount;
		
		frameDelta = 1000/frameRate;
	}
	
	public AgenosImage(String ref, int xOffset, int yOffset) throws SlickException {
		super(ref, 1, 1);
		animated = false;
	}
	
	public void update(int delta) {
		if (!animated)
			return;
		
		currDelta += delta;
		if (currDelta >= frameDelta) {
			this.currFrame++;
			
			if (currFrame >=frameCount)
				currFrame = 0;
		}
	}
	
	public void render(int x, int y) {
		if (!animated) {
			this.draw(x, y);
			return;
		}
		
		int frameX = currFrame;
		int frameY = 0;
		
		while (frameX > getHorizontalCount()) {
			frameY++;
			frameX -= getHorizontalCount();
		}
		
		getSprite(frameX, frameY).draw(x, y);
		
	}

}
