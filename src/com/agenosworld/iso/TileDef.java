/**
 * 
 */
package com.agenosworld.iso;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import com.agenosworld.basicgame.AgenosImage;
import com.agenosworld.basicgame.Updater;

/**
 * @author Michael
 *
 */
public class TileDef {
	
	public static final int TILE_WIDTH = 40;
	public static final int TILE_HEIGHT = 20;
	
	public static final int VERTICAL_DELTA = 5;
	public static final int VERTICAL_HEIGHT = 14;
	
	public boolean ready = false;
	
	public AgenosImage tile;
	public AgenosImage mainVert;
	public AgenosImage topVert;
	public boolean blocked;
	
	//Current rendered Y position of the top of the tile
	public int currY;
	
	private int id;
	
	public TileDef(int id) {
		this.id = id;
	}
	
	public void setTileImg(AgenosImage img) {
		tile = img;
		if (img.isAnimated())
			Updater.addUpdatable(img);
		checkRdy();
	}
	public void setMainVert(AgenosImage img) {
		mainVert = img;
		if (img.isAnimated())
			Updater.addUpdatable(img);
		checkRdy();
	}
	public void setTopVert(AgenosImage img) {
		topVert = img;
		if (img.isAnimated())
			Updater.addUpdatable(img);
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
	
	public void render(int x, int y, int elevation, ArrayList<Prop> props) throws SlickException {
		if (!ready) {
			//throw new SlickException("Tile not yet ready");
			return;
		}
		
		currY = y;
		
		for (int i=0; i<elevation; i++) {
			
			int renderY = currY+(TileDef.TILE_HEIGHT/2-TileDef.VERTICAL_DELTA);
			
			if (i==(elevation - 1)) {
				topVert.render(x, renderY);
			} else {
				mainVert.render(x, renderY);
			}
			
			currY -= TileDef.VERTICAL_DELTA;
		}
		
		tile.render(x, currY);
		
		for (Prop p : props) {
			p.render(x, currY);
		}
	}
	
	private void checkRdy() {
		if (tile!=null && topVert!=null && mainVert!=null) {
			this.ready = true;
		}
	}

}
