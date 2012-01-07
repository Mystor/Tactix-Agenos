/**
 * 
 */
package com.agenosworld.iso;

import org.newdawn.slick.SlickException;
import com.agenosworld.basicgame.AgenosImage;
import com.agenosworld.basicgame.Updater;

/**
 * @author Michael
 *
 */
public class Prop {
	
	public boolean ready = false;
	
	public AgenosImage prop;
	
	//Current rendered Y position of the top of the tile
	public int currY;
	
	private int id;
	private boolean forceBlocked;
	
	public Prop(int id, boolean forceBlocked) {
		this.id = id;
		this.forceBlocked = forceBlocked;
	}
	
	public boolean forceBlocked() {
		return forceBlocked;
	}
	
	public void setImg(AgenosImage img) {
		prop = img;
		if (img.isAnimated())
			Updater.addUpdatable(img);
		this.ready = true;
	}
	
	/**
	 * 
	 * @param x The X position at which to render this tile (in pixels)
	 * @param y The Y position at which to render this tile (in pixels)
	 * @param elevation The elevation of the tile in question
	 * @param isoMap The isoMap which this tile is being rendered for
	 * @throws SlickException Thrown if tile is not yet ready to be rendered
	 */
	
	public void render(int x, int y) throws SlickException {
		if (!ready)
			return;
		
		prop.render(x, y);
	}

}
