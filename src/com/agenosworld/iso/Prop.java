/**
 * 
 */
package com.agenosworld.iso;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Michael
 *
 */
public class Prop {
	
	public static final int TILE_WIDTH = 40;
	public static final int TILE_HEIGHT = 20;
	
	public static final int VERTICAL_DELTA = 5;
	public static final int VERTICAL_HEIGHT = 14;
	
	public boolean ready = false;
	
	public Image tile;
	public Image mainVert;
	public Image topVert;
	public boolean blocked;
	
	//Current rendered Y position of the top of the tile
	public int currY;
	
	private String imgSrc;
	private int id;
	
	public Prop(int id, String imgSrc) {
		this.imgSrc = imgSrc;
		this.id = id;
	}
	
	public void setTileImg(String res) {
		try {
			tile = new Image(imgSrc+"/"+res);
		} catch (SlickException e) {
			System.out.println("ERROR: Unable to load image for tile ID: "+id);
		}
		checkRdy();
	}
	public void setMainVert(String res) {
		try {	
			mainVert = new Image(imgSrc+"/"+res);
		} catch (SlickException e) {
			System.out.println("ERROR: Unable to load image for tile ID: "+id);
		}
		checkRdy();
	}
	public void setTopVert(String res) {
		try {	
			topVert = new Image(imgSrc+"/"+res);
		} catch (SlickException e) {
			System.out.println("ERROR: Unable to load image for tile ID: "+id);
		}
		checkRdy();
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	/**
	 * 
	 * @param x The X position at which to render this tile (in pixels)
	 * @param y The Y position at which to render this tile (in pixels)
	 * @param elevation The elevation of the tile in question
	 * @param isoMap The isoMap which this tile is being rendered for
	 * @throws SlickException Thrown if tile is not yet ready to be rendered
	 */
	
	public void render(int x, int y, int elevation) throws SlickException {
		if (!ready) {
			//throw new SlickException("Tile not yet ready");
			return;
		}
		
		currY = y;
		
		for (int i=0; i<elevation; i++) {
			
			int renderY = currY+(Prop.TILE_HEIGHT/2-Prop.VERTICAL_DELTA);
			
			if (i==(elevation - 1)) {
				topVert.draw(x, renderY);
			} else {
				mainVert.draw(x, renderY);
			}
			
			currY -= Prop.VERTICAL_DELTA;
		}
		
		tile.draw(x, currY);
	}
	
	private void checkRdy() {
		if (tile!=null && topVert!=null && mainVert!=null) {
			this.ready = true;
		}
	}

}
