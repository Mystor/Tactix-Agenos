/**
 * 
 */
package com.agenosworld.tactix;

import com.agenosworld.iso.*;
import com.agenosworld.basicgame.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Michael
 *
 */
public class TactixAgenos extends BasicGame implements GameBasics {
	
	IsoMap isoMap;
	TileDefs tileDefs;
	Input input;
	
	TileTargeter tileTargeter;
	
	public TactixAgenos() {
		super("TactixAgenos");
	}

	

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		tileDefs = new TileDefs("res/tiles.xml", "res/sprites/tiles");
		isoMap = new IsoMap(tileDefs, this);
		Updater.addUpdatable(isoMap);

		tileTargeter = new TileTargeter(isoMap, "res/sprites/hitmap-tile.png");
		
		input = new Input(this.getHeight());
		input.addMouseListener(isoMap);
		input.addMouseListener(tileTargeter);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		input.poll(this.getWidth(), this.getHeight());
		
		/*if (isoMap != null)
			isoMap.update(delta);*/
		
		Updater.update(delta);

	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		if (isoMap != null)
			isoMap.render();
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new TactixAgenos());
			app.setDisplayMode(800, 600, false);
			//app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.agenosworld.basicgame.GameBasics#getWidth()
	 */
	@Override
	public int getWidth() {
		return 800;
	}

	/* (non-Javadoc)
	 * @see com.agenosworld.basicgame.GameBasics#getHeight()
	 */
	@Override
	public int getHeight() {
		return 600;
	}



	/* (non-Javadoc)
	 * @see com.agenosworld.basicgame.GameBasics#getInput()
	 */
	@Override
	public Input getInput() {
		return input;
	}

	

}
