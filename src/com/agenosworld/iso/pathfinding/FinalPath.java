/**
 * 
 */
package com.agenosworld.iso.pathfinding;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

/**
 * @author Mystor
 *
 */
public class FinalPath {
	
	public Vector2f[] commands;
	
	private ArrayList<Vector2f> finalPath;
	private int currentCommand;
	
	public FinalPath(Node destination) {
		Node current = destination;
		boolean atDestination = false;
		
		while(!atDestination) {
			
			finalPath.add(0, current.getVectorFromParent());
			
			current = current.parent;
			
			if (current.parent == null) {
				atDestination = true;
			}
		}
		
		updateCommandsArray();
	}
	
	public Vector2f nextCommand() {
		currentCommand++;
		return commands[currentCommand];
	}
	
	public Vector2f commandAt(int n) {
		return commands[n];
	}
	
	public void resetCount() {
		currentCommand = -1;
	}
	
	protected void updateCommandsArray() {
		commands = new Vector2f[finalPath.size()];
		
		for (int i=0; i<finalPath.size(); i++) {
			commands[i] = finalPath.get(i);
		}
	}

}
