/**
 * 
 */
package com.agenosworld.iso;

import java.awt.Color;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Michael
 *
 */
public class TileTargeter implements MouseListener {
	
	private IsoMap map;
	private Image mask;
	
	//The currently selected tile
	private Tile current;
	
	//Defines whether the TileTargeter responds to MouseMoved events.
	private boolean active = true;
	
	public TileTargeter(IsoMap map, String maskRes) throws SlickException {
		this.map = map;
		this.mask = new Image(maskRes);
	}
	
	public void setActive() {
		active = true;
	}
	
	public void setInactive() {
		active = false;
	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
	 */
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		int relX = newx - map.getXScroll();
		int relY = newy - map.getYScroll();
		float column = (float)Math.floor((float)relX/((float)TileDef.TILE_WIDTH/2.0f));
		
		if (column < 0)
			return;
		
		if (column > map.getWidth()*2)
			return;
		
		//Get the X values which should be checked for both even and odd tile rows.
		int oddX = (int)Math.floor((column-1)/2.0f);
		int evenX = (int)Math.floor(column/2.0f);
		
		Tile tile;
		
		int tileRelX;
		int tileRelY;
		
		int actX;
		
		for (int y=map.getHeight()-1; y>-1; y--) {
			//Filler values
			tileRelX = -100;
			tileRelY = -100;
			
			if (y%2 != 0) {
				actX = oddX;
				//odd row
				if (!(oddX < 0)) {
					tile = map.getTileAt(oddX, y);
					if (tile.getTileDef()!=null) {
						tileRelX = relX-(oddX*TileDef.TILE_WIDTH)-TileDef.TILE_WIDTH/2;
						tileRelY = relY-(y*TileDef.TILE_HEIGHT/2)+(tile.getElevation()+Tile.BASE_ELEVATION)*TileDef.VERTICAL_DELTA;
					}
				}
				
			} else {
				actX = evenX;
				//even row
				if (!(evenX >= map.getWidth())) {
					tile = map.getTileAt(evenX, y);
					if (tile.getTileDef()!=null) {
						tileRelX = relX-(evenX*TileDef.TILE_WIDTH);
						tileRelY = relY-(y*TileDef.TILE_HEIGHT/2)+(tile.getElevation()+Tile.BASE_ELEVATION)*TileDef.VERTICAL_DELTA;
					}
				}
			}
			
			if (tileRelY >= 0 && tileRelY <= TileDef.TILE_HEIGHT) {
				if (mask.getColor(tileRelX, tileRelY).a == 1) {
					if (current != map.getTileAt(actX, y)) {
						try {
							current.remProp(SelectionIndicator.getIndicator());
						} catch (Exception e) {}
						current = map.getTileAt(actX, y);
						try {
							current.addProp(SelectionIndicator.getIndicator());
						} catch (Exception e) {}
					}
					//System.out.println("Selected tile:" + actX + "x" + y);
					return;
				}
			}
		}
		
		try {
			current.remProp(SelectionIndicator.getIndicator());
			current = null;
		} catch (Exception e) {}
		
	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
	 */
	@Override
	public boolean isAcceptingInput() {
		return active;
	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
	 */
	@Override
	public void setInput(Input input) { }

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
	 */
	@Override
	public void inputEnded() { }

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
	 */
	@Override
	public void inputStarted() { }

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	@Override
	public void mouseWheelMoved(int change) { }

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
	 */
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) { }

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
	 */
	@Override
	public void mousePressed(int button, int x, int y) { }

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
	 */
	@Override
	public void mouseReleased(int button, int x, int y) { }

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
	 */
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) { }
	
}
