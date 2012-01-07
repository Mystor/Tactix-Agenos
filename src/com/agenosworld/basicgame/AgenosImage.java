/**
 * 
 */
package com.agenosworld.basicgame;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.xml.sax.Attributes;

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
	
	private int xOffset;
	private int yOffset;

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
		
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public AgenosImage(String ref, int xOffset, int yOffset) throws SlickException {
		super(ref, 1, 1);
		animated = false;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public static AgenosImage fromImageDef(Attributes attributes, String imgRes) throws SlickException {
		boolean anim;
		try {
			anim = (Boolean.parseBoolean(attributes.getValue("animated")));
		} catch (NullPointerException e) {
			anim = false;
		}
		
		int tw = 0, th = 0, frameRate = 0, frameCount = 0;
		
		if (anim) {
			try {
				tw = Integer.parseInt(attributes.getValue("tw"));
				th = Integer.parseInt(attributes.getValue("th"));
				
				frameRate = Integer.parseInt(attributes.getValue("frameRate"));
				frameCount = Integer.parseInt(attributes.getValue("frameCnt"));
			} catch (NumberFormatException e) {
				anim = false;
			}
		}
		
		int xOffset = 0, yOffset = 0;
		
		String ref = imgRes + "/" + attributes.getValue("src");
		try {
			xOffset = Integer.parseInt(attributes.getValue("xOffset"));
			yOffset = Integer.parseInt(attributes.getValue("yOffset"));
		} catch (NumberFormatException e) {
			throw new SlickException(e.getMessage());
		}
		
		if (anim)
			return new AgenosImage(ref, xOffset, yOffset, tw, th, frameRate, frameCount);
		
		return new AgenosImage(ref, xOffset, yOffset);
	}
	
	public boolean isAnimated() {
		return animated;
	}
	
	public void update(int delta) {
		if (!animated)
			return;
		
		currDelta += delta;
		if (currDelta >= frameDelta) {
			this.currFrame++;
			
			if (currFrame >=frameCount) {	
				currFrame = 0;
			}
			
			currDelta-=frameDelta;
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
		
		getSprite(frameX, frameY).draw(x-xOffset, y-yOffset);
		
	}

}
