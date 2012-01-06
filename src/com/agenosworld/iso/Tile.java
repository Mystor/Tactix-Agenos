/**
 * 
 */
package com.agenosworld.iso;

import org.newdawn.slick.SlickException;

/**
 * @author Michael
 *
 */
public class Tile {
	
	public static final int BASE_ELEVATION = 2;
	
	//private boolean selected;
	private boolean hover = false;
	
	//Tile Property variables
	private int x, y;
	private int elevation;
	private boolean blocked;
	
	//Tile rendering variables
	private int xOffset;
	private TileDef tileDef;
	
	private final int TILE_WIDTH = 40, TILE_HEIGHT = 20;
	
	public Tile(int x, int y, int elevation, TileDef tileDef) {
		this.x = x;
		this.y = y;
		this.elevation = elevation;
		if (tileDef != null)
			this.blocked = tileDef.blocked;
		this.tileDef = tileDef;
		
		if (y%2 == 0) {
			xOffset = 0;
		} else {
			xOffset = TILE_WIDTH/2;
		}
	}
	
	public void setHover(boolean hover) {
		this.hover = hover;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public int getElevation() {
		return elevation;
	}
	
	public TileDef getTileDef() {
		return tileDef;
	}

	public void render(int xScroll, int yScroll) throws SlickException {
		if (tileDef == null)
			return;
		
		int xRender = 0;
		int yRender = 0;
		
		/*xRender = TILE_WIDTH*x/2-TILE_WIDTH*y/2;
		yRender = TILE_HEIGHT*y/2+TILE_HEIGHT*x/2;*/
		
		xRender = xOffset + x*TILE_WIDTH;
		yRender = y*TILE_HEIGHT/2;
				
		tileDef.render(xRender+xScroll, yRender+yScroll, elevation+BASE_ELEVATION);
	}

}
