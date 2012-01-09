/**
 * 
 */
package com.agenosworld.iso;

import org.newdawn.slick.SlickException;

import com.agenosworld.basicgame.AgenosImage;

/**
 * @author Michael
 *
 */
public class SelectionIndicator extends Prop {
	
	private static SelectionIndicator indicator;
	
	public SelectionIndicator() throws SlickException {
		super(-1, false);
		
		this.setImg(new AgenosImage("res/sprites/props/tile_select.png", 0, 0));
	}
	
	public static SelectionIndicator getIndicator() throws SlickException {
		if (indicator != null)
			return indicator;
		
		return indicator = new SelectionIndicator();
	}
	
}
