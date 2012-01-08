/**
 * 
 */
package com.agenosworld.iso;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.MouseListener;
import com.agenosworld.basicgame.*;

/**
 * @author Michael
 *
 */
public class IsoMap implements MouseListener, Updatable, Renderable {
	
	//Map Scrolling Variables
	private int xScroll = 0;
	private int yScroll = 0;
	
	private float baseRate = 0.2f;
	private float yRate = 0, xRate = 0;
	
	private final int SCROLL_BORDER = 60;
	
	//GameBasics Game
	private GameBasics game;
	
	//Map Definition Variables
	private int mapWidth = 7;
	private int mapHeight = 4;
	
	//private int tileWidth = 40;
	//private int tileHeight = 20;
	
	private int[] tileIDArray =
		{1,1,1,2,2,1,1,
		  2,1,1,1,1,1,1,
		 2,2,1,0,1,1,1,
		  2,1,1,1,1,0,0
		};
	
	private int[] tileHeightArray = 
		{1,1,1,3,2,0,0,
		  0,1,2,2,0,0,0,
		 0,0,1,0,1,0,0,
		  0,1,1,1,1,0,0
		};
	
	//Tile Definition Values
	private TileDefs tileDefs;
	
	private Tile[][] tilesArray;
	
	//Create a new IsoMap
	public IsoMap(TileDefs tileDefs, GameBasics game) throws SlickException {
		
		this.tileDefs = tileDefs;
		this.game = game;
		
		xScroll = Math.round(game.getWidth()-(mapWidth+0.5f)*TileDef.TILE_WIDTH)/2;
		yScroll = Math.round(game.getHeight()-(mapHeight+1f)*TileDef.TILE_HEIGHT/2)/2;
		
		tilesArray = new Tile[mapWidth][mapHeight];
		
		for (int y=0; y<mapHeight; y++) {
			
			for (int x=0; x<mapWidth; x++) {
				tilesArray[x][y] = new Tile(x, y, tileHeightArray[x+y*mapWidth], tileDefs.getTileById(tileIDArray[x+y*mapWidth]));
			}
			
		}
	}
	
	public IsoMap(Tile[][] tiles, int mapWidth, int mapHeight, GameBasics game) {
		this.game = game;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		
		xScroll = Math.round(game.getWidth()-(mapWidth+0.5f)*TileDef.TILE_WIDTH)/2;
		yScroll = Math.round(game.getHeight()-(mapHeight+1f)*TileDef.TILE_HEIGHT/2)/2;
		
		tilesArray = tiles;
	}
	
	public Tile getTileAt(int x, int y) {
		return tilesArray[x][y]; 
	}
	
	public TileDefs getTileDefs() {
		return tileDefs;
	}
	
	public int getXScroll() {
		return xScroll;
	}
	public int getYScroll() {
		return yScroll;
	}
	
	public int getWidth() {
		return mapWidth;
	}
	public int getHeight() {
		return mapHeight;
	}
	
	/**
	 * Render the IsoMap at its current offset.
	 * @throws SlickException
	 */
	
	public void render() throws SlickException {
		for (int y=0; y<mapHeight; y++) {
			for (int x=0; x<mapWidth; x++) {
				tilesArray[x][y].render(xScroll, yScroll);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
	 */
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		/*if (newx < SCROLL_BORDER) {
			xRate = baseRate*(1.0f-((float)newx/SCROLL_BORDER));
			//xRate = baseRate-(baseRate/(SCROLL_BORDERf-newx));
		} else if (newx > game.getWidth()-SCROLL_BORDER) {
			xRate = -baseRate*(1.0f-(float)(game.getWidth()-newx)/SCROLL_BORDER);
			//xRate = -(baseRate-(baseRate/(40f-game.getWidth()+newx)));
		} else {
			xRate = 0;
		}
		
		if (newy < SCROLL_BORDER) {
			yRate = baseRate*(1.0f-((float)newy/SCROLL_BORDER));
			//yRate = baseRate-(baseRate/(SCROLL_BORDERf-newy));
		} else if (newy > game.getHeight()-SCROLL_BORDER) {
			yRate = -baseRate*(1.0f-(float)(game.getHeight()-newy)/SCROLL_BORDER);
			//yRate = -(baseRate-(baseRate/(SCROLL_BORDERf-game.getHeight()+newy)));
		} else {
			yRate = 0;
		}*/
	}
	
	/* (non-Javadoc)
	 * @see com.agenosworld.basicgame.Updatable#update(int)
	 */
	@Override
	public void update(int delta) {
		this.xScroll += xRate*delta;
		this.yScroll += yRate*delta;
		
		if (xScroll > game.getWidth()-300) {
			xScroll = game.getWidth()-300;
		} else if (xScroll < -mapWidth*TileDef.TILE_WIDTH+300) {
			xScroll = -mapWidth*TileDef.TILE_WIDTH+300;
		}
		
		if (yScroll > game.getHeight()-300) {
			yScroll = game.getHeight()-300;
		} else if (yScroll < -mapHeight*TileDef.TILE_HEIGHT+300) {
			yScroll = -mapHeight*TileDef.TILE_HEIGHT+300;
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
	 */
	@Override
	public boolean isAcceptingInput() {
		return true;
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
