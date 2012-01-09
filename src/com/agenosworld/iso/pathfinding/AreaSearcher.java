/**
 * 
 */
package com.agenosworld.iso.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import com.agenosworld.iso.*;

/**
 * @author Kevin Glass, Mystor 
 *
 */
public class AreaSearcher {
	
	/** Set of Node which have been searched already */
	private ArrayList<Node> closed = new ArrayList<Node>();
	/** Nodes which are currently being searched */
	private PriorityList open = new PriorityList();
	
	/** map to search */
	protected IsoMap map;
	
	/** Complete set of nodes across the entire map */
	private Node[][] nodes;
	/** Current node */
	private Node current;
	
	/** The current Mover passing along the path */
	private Pather pather;
	/** Source X and Y */
	private int sourceX, sourceY;
	/** The range which may be travelled by the mover */
	private float range;
	
	/**
	 * 
	 * @param map
	 * @param allowDiagMvmt Currently does nothing
	 */
	
	public AreaSearcher(IsoMap map) {
		this.map = map;
		
		nodes = new Node[map.getWidth()][map.getHeight()];
		for (int x=0; x<map.getWidth(); x++) {
			for (int y=0; y<map.getHeight(); y++) {
				nodes[x][y] = new Node(x,y);
			}
		}
	}
	
	public NodeList search(Pather pather, int sx, int sy, float range) {
		current = null;
		this.range = range;
		
		// Reset the nodes to their state before the search began
		for (int x=0; x<map.getWidth(); x++) {
			for (int y=0; y<map.getHeight(); y++) {
				nodes[x][y].reset();
			}
		}
		
		nodes[sx][sy].cost = 0;
		closed.clear();
		open.clear();
		addToOpen(nodes[sx][sy]);
		
		while(open.size() != 0) {
			
			/*int lx = sx;
			int ly = sy;
			if (current != null) {
				lx = current.x;
				ly = current.y;
			}*/
			
			current = getFirstInOpen();
			
			removeFromOpen(current);
			addToClosed(current);
			
			Vector2f[] possibleDestinations = {IsoMap.DIR_DL, IsoMap.DIR_DR, IsoMap.DIR_UL, IsoMap.DIR_UR};
			
			for (Vector2f dest : possibleDestinations) {
				
				// determine the location of the neighbour and evaluate it
				int xp = (int)(dest.x+(dest.y%2)) + current.x;
				int yp = (int)dest.y + current.y;
				
				if (isValidLocation(pather,current.x,current.y,xp,yp)) {
					// the cost to get to this node is cost the current plus the movement
					// cost to reach this node. 
					float nextStepCost = current.cost + getMovementCost(pather, current.x, current.y, xp, yp);
					
					if (nextStepCost > this.range) {
						continue;
					}
					
					Node neighbour = nodes[xp][yp];
					//map.pathFinderVisited(xp, yp);
					
					// if the new cost we've determined for this node is lower than 
					// it has been previously makes sure the node hasn't been discarded. We've
					// determined that there might have been a better path to get to
					// this node so it needs to be re-evaluated
					if (nextStepCost < neighbour.cost) {
						if (inOpenList(neighbour)) {
							removeFromOpen(neighbour);
						}
						if (inClosedList(neighbour)) {
							removeFromClosed(neighbour);
						}
					}
					
					// if the node hasn't already been processed and discarded then
					// reset it's cost to our current cost and add it as a next possible
					// step (i.e. to the open list)
					if (!inOpenList(neighbour) && !(inClosedList(neighbour))) {
						neighbour.cost = nextStepCost;
						neighbour.setParent(current);
						addToOpen(neighbour);
					} 
				}
			}
		}
		
		// All points within range have had their paths calculated - return the NodeList
		return new NodeList(closed);
	}
	
	/**
	 * Get the X coordinate of the node currently being evaluated
	 * 
	 * @return The X coordinate of the node currently being evaluated
	 */
	public int getCurrentX() {
		if (current == null) {
			return -1;
		}
		
		return current.x;
	}

	/**
	 * Get the Y coordinate of the node currently being evaluated
	 * 
	 * @return The Y coordinate of the node currently being evaluated
	 */
	public int getCurrentY() {
		if (current == null) {
			return -1;
		}
		
		return current.y;
	}
	
	/**
	 * Get the first element from the open list. This is the next
	 * one to be searched.
	 * 
	 * @return The first element in the open list
	 */
	protected Node getFirstInOpen() {
		return (Node) open.first();
	}
	
	/**
	 * Add a node to the open list
	 * 
	 * @param node The node to be added to the open list
	 */
	protected void addToOpen(Node node) {
		node.setOpen(true);
		open.add(node);
	}
	
	/**
	 * Check if a node is in the open list
	 * 
	 * @param node The node to check for
	 * @return True if the node given is in the open list
	 */
	protected boolean inOpenList(Node node) {
		return node.isOpen();
	}
	
	/**
	 * Remove a node from the open list
	 * 
	 * @param node The node to remove from the open list
	 */
	protected void removeFromOpen(Node node) {
		node.setOpen(false);
		open.remove(node);
	}
	
	/**
	 * Add a node to the closed list
	 * 
	 * @param node The node to add to the closed list
	 */
	protected void addToClosed(Node node) {
		node.setClosed(true);
		closed.add(node);
	}
	
	/**
	 * Check if the node supplied is in the closed list
	 * 
	 * @param node The node to search for
	 * @return True if the node specified is in the closed list
	 */
	protected boolean inClosedList(Node node) {
		return node.isClosed();
	}
	
	/**
	 * Remove a node from the closed list
	 * 
	 * @param node The node to remove from the closed list
	 */
	protected void removeFromClosed(Node node) {
		node.setClosed(false);
		closed.remove(node);
	}
	
	/**
	 * Check if a given location is valid for the supplied mover
	 * 
	 * @param mover The mover that would hold a given location
	 * @param sx The starting x coordinate
	 * @param sy The starting y coordinate
	 * @param x The x coordinate of the location to check
	 * @param y The y coordinate of the location to check
	 * @return True if the location is valid for the given mover
	 */
	protected boolean isValidLocation(Pather pather, int sx, int sy, int x, int y) {
		boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidth()) || (y >= map.getHeight());
		
		if ((!invalid) && ((sx != x) || (sy != y))) {
			this.pather = pather;
			this.sourceX = sx;
			this.sourceY = sy;
			invalid = map.blocked(this, x, y);
		}
		
		return !invalid;
	}
	
	/**
	 * Get the cost to move through a given location
	 * 
	 * @param pather The entity that is being moved
	 * @param sx The x coordinate of the tile whose cost is being determined
	 * @param sy The y coordinate of the tile whose cost is being determined
	 * @param tx The x coordinate of the target location
	 * @param ty The y coordinate of the target location
	 * @return The cost of movement through the given tile
	 */
	public float getMovementCost(Pather pather, int sx, int sy, int tx, int ty) {
		this.pather = pather;
		this.sourceX = sx;
		this.sourceY = sy;
		
		return map.getCost(this, tx, ty);
	}
	
	private class PriorityList {
		/** The list of elements */
		private List list = new LinkedList();
		
		/**
		 * Retrieve the first element from the list
		 *  
		 * @return The first element from the list
		 */
		public Object first() {
			return list.get(0);
		}
		
		/**
		 * Empty the list
		 */
		public void clear() {
			list.clear();
		}
		
		/**
		 * Add an element to the list - causes sorting
		 * 
		 * @param o The element to add
		 */
		public void add(Object o) {
			// float the new entry 
			for (int i=0;i<list.size();i++) {
				if (((Comparable) list.get(i)).compareTo(o) > 0) {
					list.add(i, o);
					break;
				}
			}
			if (!list.contains(o)) {
				list.add(o);
			}
			//Collections.sort(list);
		}
		
		/**
		 * Remove an element from the list
		 * 
		 * @param o The element to remove
		 */
		public void remove(Object o) {
			list.remove(o);
		}
	
		/**
		 * Get the number of elements in the list
		 * 
		 * @return The number of element in the list
 		 */
		public int size() {
			return list.size();
		}
		
		/**
		 * Check if an element is in the list
		 * 
		 * @param o The element to search for
		 * @return True if the element is in the list
		 */
		public boolean contains(Object o) {
			return list.contains(o);
		}
		
		public String toString() {
			String temp = "{";
			for (int i=0;i<size();i++) {
				temp += list.get(i).toString()+",";
			}
			temp += "}";
			
			return temp;
		}
	}
	
	/**
	 * @see org.newdawn.slick.util.pathfinding.PathFindingContext#getMover()
	 */
	public Pather getPather() {
		return pather;
	}

	/**
	 * @see org.newdawn.slick.util.pathfinding.PathFindingContext#getSearchDistance()
	 */
	public int getSearchDistance() {
		return -1;
	}

	/**
	 * @see org.newdawn.slick.util.pathfinding.PathFindingContext#getSourceX()
	 */
	public int getSourceX() {
		return sourceX;
	}

	/**
	 * @see org.newdawn.slick.util.pathfinding.PathFindingContext#getSourceY()
	 */
	public int getSourceY() {
		return sourceY;
	}

}
