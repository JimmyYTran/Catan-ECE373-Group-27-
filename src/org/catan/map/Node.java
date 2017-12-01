package org.catan.map;

import org.catan.players.*;
import java.util.ArrayList; // Needed for the ArrayList class

public class Node {
	//fields
	private String Status;//status keywords are "a" for available, "s" for settlement, and "c" for city "na" for not available (distance rule)
	private ArrayList<Hex> NearbyHexes; //arraylist of all hexes near the node
	private ArrayList<Edge> NearbyEdges; //arraylist of all edges near the node
	private ArrayList<Node> NearbyNodes; //arraylist of all nodes near the node
	private Player Owner; // player that owns the node
	
	//constructors
	public Node() {	//default constructor
		Status = "a";	//default is available
		NearbyHexes = new ArrayList<Hex>();		
		NearbyEdges = new ArrayList<Edge>();	
		NearbyNodes = new ArrayList<Node>();
		Owner = null;
	}
	
	//methods
	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public ArrayList<Hex> getNearbyHexes() {
		return NearbyHexes;
	}

	public void setNearbyHexes(ArrayList<Hex> nearbyHexes) {
		NearbyHexes = nearbyHexes;
	}

	public ArrayList<Edge> getNearbyEdges() {
		return NearbyEdges;
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

	public Player getOwner() {
		return Owner;
	}

	public void setOwner(Player owner) {
		Owner = owner;
	}
	
	public void addNearbyNode(Node n) {
		this.NearbyNodes.add(n);
	}
	
	public void addNearbyEdge(Edge e) {
		this.NearbyEdges.add(e);
	}
	
	public void addNearbyHex(Hex h) {
		this.NearbyHexes.add(h);
		h.addNearbyNode(this);
	}
}
