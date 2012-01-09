/**
 * 
 */
package com.agenosworld.iso;

import java.util.ArrayList;
import org.newdawn.slick.SlickException;

/**
 * @author Michael
 *
 */
public class Tile {
	
	public static final int BASE_ELEVATION = 2;
	
	//Tile Property variables
	private int x, y;
	private int elevation;
	private boolean blocked;
	
	//Tile rendering variables
	private int xOffset;
	private TileDef tileDef;
	
	//Prop variables
	private ArrayList<Prop> props;
	
	private final int TILE_WIDTH = 40, TILE_HEIGHT = 20;
	
	public Tile(int x, int y, int elevation, TileDef tileDef) {
		this.x = x;
		this.y = y;
		this.elevation = elevation;
		
		props = new ArrayList<Prop>();
		
		if (tileDef == null) {
			this.elevation = -BASE_ELEVATION;
		} else {
			this.blocked = tileDef.blocked;
		}
		
		
		this.tileDef = tileDef;
		
		if (y%2 == 0) {
			xOffset = 0;
		} else {
			xOffset = TILE_WIDTH/2;
		}
	}
	
	public Prop addProp(Prop prop) {
		props.add(prop);
		return prop;
	}
	
	public boolean remProp(Prop prop) {
		return props.remove(prop);
	}
	
	public boolean hasProp(Prop prop) {
		return props.contains(prop);
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

	public void render(int xScroll, int yScroll, IsoMap map) throws SlickException {
		if (tileDef == null)
			return;
		
		int xRender = 0;
		int yRender = 0;
		
		xRender = xOffset + x*TILE_WIDTH;
		yRender = y*TILE_HEIGHT/2;
		
		int minElevation = Integer.MAX_VALUE;
		
		Tile tileDR = map.getTileAt(x + (int)IsoMap.DIR_DR.x+y%2, y + (int)IsoMap.DIR_DR.y);
		Tile tileDL = map.getTileAt(x + (int)IsoMap.DIR_DL.x+y%2, y + (int)IsoMap.DIR_DL.y);
		
		if (tileDR != null) {
			minElevation = tileDR.getElevation()+BASE_ELEVATION;
		} else {
			minElevation = 0;
		}
		if (tileDL != null) {
			if (minElevation > tileDL.getElevation()+BASE_ELEVATION)
				minElevation = tileDL.getElevation()+BASE_ELEVATION;
		} else {
			minElevation = 0;
		}
		
		if (minElevation == Integer.MAX_VALUE)
			minElevation = 0;
		
		if (minElevation > elevation+BASE_ELEVATION)
			minElevation = elevation+BASE_ELEVATION; 
		
		int elevationDelta = TileDef.VERTICAL_DELTA*minElevation;
		
		tileDef.render(xRender+xScroll, yRender+yScroll-elevationDelta, elevation+BASE_ELEVATION-minElevation, props);
	}

}
