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
		System.out.println("StartUpdate");
		for (Updatable u : updatables) {
			System.out.println("Updating: "+u);
			u.update(delta);
		}
		System.out.println("EndUpdate");
	}
	
	public static void addUpdatable(Updatable u) {
		updatables.add(u);
		System.out.println("Adding to updatables"+u);
	}
	
	public static boolean removeUpdatable(Updatable u) {
		return updatables.remove(u);
	}

}
