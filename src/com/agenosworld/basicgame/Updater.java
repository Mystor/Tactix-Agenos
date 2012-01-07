/**
 * 
 */
package com.agenosworld.basicgame;

import java.util.ArrayList;
import org.newdawn.slick.SlickException;

/**
 * @author Michael
 *
 */
public class Updater {

	private static ArrayList<Updatable> updatables = new ArrayList<Updatable>();
	
	public static void update(int delta) throws SlickException {
		for (Updatable u : updatables) {
			u.update(delta);
		}
	}
	
	public static void addUpdatable(Updatable u) {
		updatables.add(u);
	}
	
	public static boolean removeUpdatable(Updatable u) {
		return updatables.remove(u);
	}

}
