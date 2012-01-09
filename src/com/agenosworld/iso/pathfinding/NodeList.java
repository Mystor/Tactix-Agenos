/**
 * 
 */
package com.agenosworld.iso.pathfinding;

import java.util.ArrayList;

/**
 * @author Mystor
 *
 */
public class NodeList {
	
	private ArrayList<Node> nodes;
	
	private int currentNode = -1;
	
	public NodeList(ArrayList<Node> nodeList) {
		nodes = nodeList;
	}
	
	public FinalPath getPathTo(Node node) {
		return new FinalPath(node);
	}
	
	public Node nextNode() {
		currentNode++;
		return nodes.get(currentNode);
	}
	
	public int getLength() {
		return nodes.size();
	}
	
	public Node nodeAt(int n) {
		return nodes.get(n);
	}
	
	public void resetCount() {
		currentNode = -1;
	}

}
