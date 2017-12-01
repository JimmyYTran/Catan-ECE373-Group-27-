package org.catan.map;

import java.util.ArrayList;

public class Hex {
	//fields
	private int DiceNumber; // this number 2-12 indicates the number to roll to get resources from the hex
	private String ResourceType; // Resource Keywords are: "lumber", "ore", "brick", "grain", "wool", and "nothing" (for desert)
	private boolean RobberStatus; // true indicates robber is on the hex, false indicates it isn't
	private ArrayList<Edge> NearbyEdges; //arrayList of the edges that connect to this edge, used for finding if a player can build on this edge
	private ArrayList<Node> NearbyNodes; //arrayList of the nodes that touch this edge
	
	//constructors
	public Hex() {	//default constructor
		DiceNumber = 1;		//1 is a filler value
		ResourceType = "nothing";	
		RobberStatus = false;	
		NearbyEdges = new ArrayList<Edge>();	
		NearbyNodes = new ArrayList<Node>();
	}
	
	public Hex(int i, String s) {//specific constructor for initializing
		DiceNumber = i;
		ResourceType = s;
		RobberStatus = false;
		NearbyEdges = new ArrayList<Edge>();	
		NearbyNodes = new ArrayList<Node>();
	}
	
	
	//methods
	public void setDiceNumber(int i) {
		DiceNumber = i;
	}
	public int getDiceNumber() {
		return DiceNumber;
	}
	public String getResourceType() {
		return ResourceType;
	}
	public void setResourceType(String resourceType) {
		ResourceType = resourceType;
	}
	public boolean getRobberStatus() {
		return RobberStatus;
	}
	public void setRobberStatus(boolean robberStatus) {
		RobberStatus = robberStatus;
	}
	public void setNearbyEdges(ArrayList<Edge> nearbyEdges) {
		NearbyEdges = nearbyEdges;
	}
	public ArrayList<Node> getNearbyNodes() {
		return(NearbyNodes);
	}
	public void setNearbyNodes(ArrayList<Node> nearbyNodes) {
		NearbyNodes = nearbyNodes;
	}
	public void addNearbyNode(Node n) {
		this.NearbyNodes.add(n);
	}
	public void addNearbyEdge(Edge e) {
		this.NearbyEdges.add(e);
	}

	
}