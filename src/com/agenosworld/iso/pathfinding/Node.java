/**
 * 
 */
package com.agenosworld.iso.pathfinding;

import org.newdawn.slick.geom.Vector2f;

/**
 * @author Kevin Glass, Mystor
 *
 */
@SuppressWarnings("rawtypes")
public class Node implements Comparable {
	/** The x coordinate of the node */
	public int x;
	/** The y coordinate of the node */
	public int y;
	/** The path cost for this node */
	public float cost;
	/** The parent of this node, how we reached it in the search */
	public Node parent;
	/** In the open list */
	protected boolean open;
	/** In the closed list */
	protected boolean closed;
	
	/**
	 * Create a new node
	 * 
	 * @param x The x coordinate of the node
	 * @param y The y coordinate of the node
	 */
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set the parent of this node
	 * 
	 * @param parent The parent node which lead us to this node
	 * @return The depth we have no reached in searching
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Vector2f getVectorFromParent() {
		try {
			int deltaX = x-parent.x;
			int deltaY = y-parent.y;
			
			return new Vector2f(deltaX, deltaY);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	/*public int getDirFromParent() {
		int nextCommand;
		
		if (parent == null) {
			return DONE;
		}
		
		
		switch (parent.x-x) {
			case 1:
				nextCommand = EAST;
				break;
			case 0:
				nextCommand = DONE;
				break;
			case -1:
				nextCommand = WEST;
				break;
			default:
				nextCommand = DONE;
				break;
		}
	
		switch (parent.y-y) {
			case 1:
				if (nextCommand == EAST) {
					nextCommand = SOUTHEAST;
				} else if (nextCommand == WEST) {
					nextCommand = SOUTHWEST;
				} else if (nextCommand == DONE) {
					nextCommand = SOUTH;
				}
				break;
			case 0:
				break;
			case -1:
				if (nextCommand == EAST) {
					nextCommand = NORTHEAST;
				} else if (nextCommand == WEST) {
					nextCommand = NORTHWEST;
				} else if (nextCommand == DONE) {
					nextCommand = NORTH;
				}
				break;
			default:
				break;
		}
		
		return nextCommand;
	}*/
	
	/**
	 * @see Comparable#compareTo(Object)
	 */
	public int compareTo(Object other) {
		Node o = (Node) other;
		
		if (cost < o.cost) {
			return -1;
		} else if (cost > o.cost) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * Indicate whether the node is in the open list
	 * 
	 * @param open True if the node is in the open list
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	/**
	 * Check if the node is in the open list
	 * 
	 * @return True if the node is in the open list
	 */
	public boolean isOpen() {
		return open;
	}
	
	/**
	 * Indicate whether the node is in the closed list
	 * 
	 * @param closed True if the node is in the closed list
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	/**
	 * Check if the node is in the closed list
	 * 
	 * @return True if the node is in the closed list
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * Reset the state of this node
	 */
	public void reset() {
		closed = false;
		open = false;
		cost = 0;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Node "+x+","+y+"]";
	}
}
