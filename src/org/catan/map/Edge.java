package org.catan.map;

import org.catan.players.*;
import java.util.ArrayList; // Needed for the ArrayList class

public class Edge {
	//fields
	private String Status; //keywords for Status are "a" for available and "na" for not available
	private Player Owner; //Player object that owns the edge if owned
	private ArrayList<Edge> NearbyEdges; //arrayList of the edges that connect to this edge, used for finding if a player can build on this edge
	private ArrayList<Node> NearbyNodes; //arrayList of the nodes that touch this edge
	
	//constructors
	public Edge(){				//default constructor
		Status = "a";	//default roads are available
		Owner = null;	//no player owns the edge
		NearbyEdges = new ArrayList<Edge>();	
		NearbyNodes = new ArrayList<Node>();
	}
	
	//methods
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Player getOwner() {
		return Owner;
	}
	public void setOwner(Player owner) {
		Owner = owner;
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
	public void addNearbyNode(Node n) {
		this.NearbyNodes.add(n);
	}
	public void addNearbyEdge(Edge e) {
		this.NearbyEdges.add(e);
	}

}
