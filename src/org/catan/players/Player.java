package org.catan.players;

import org.catan.cards.*;
import org.catan.map.*;

import java.util.ArrayList;

public class Player {
	private String name;
	private int points;
	private ArrayList<Node> ownedNodes;
	private ArrayList<Edge> roads;
	private int brick;
	private int lumber;
	private int grain;
	private int wool;
	private int ore;
	private int knights;
	private Hand hand;
	private int totalSettlements;
	private int totalCities;
	
	// Constructor
	
	public Player() {
		name = "";
		points = 0;
		ownedNodes = new ArrayList<Node>();
		roads = new ArrayList<Edge>();
		brick = 0;
		lumber = 0;
		grain = 0;
		wool = 0;
		ore = 0;
		knights = 0;
		hand = new Hand();
		totalSettlements = 0;
		totalCities = 0;
	}
	
	
	// Getters and Setters
	
	public void setName(String s) {
		name = s;
	}
	
	public String getName() {
		return(name);
	}
	
	public ArrayList<Node> getNodes() {
		return(ownedNodes);
	}
	
	public ArrayList<Edge> getRoads() {
		return(roads);
	}
	
	public int getResources(String s) {
		if (s.compareTo("brick") == 0) {				// Return amount of brick
			return(brick);
		}
		else if (s.compareTo("lumber") == 0) {			// Return amount of lumber
			return(lumber);
		}
		else if (s.compareTo("grain") == 0) {			// Return amount of grain
			return(grain);
		}
		else if (s.compareTo("wool") == 0) {			// Return amount of wool
			return(wool);
		}
		else if (s.compareTo("ore") == 0) {				// Return amount of ore
			return(ore);
		}
		else {
			return(0);
		}
	}
	
	public int getKnights() {
		return(knights);
	}
	
	public Hand getHand() {
		return(hand);
	}
	
	public int getPoints() {
		return(points);
	}
	
	public int getTotalSettlements() {
		return(totalSettlements);
	}
	
	public int getTotalCities() {
		return(totalCities);
	}
	
	// Other Functions
	
	public void addResources(String s, int i){
		if (s.compareTo("brick") == 0) {				// Add to amount of brick
			brick += i;
		}
		else if (s.compareTo("lumber") == 0) {			// Add to amount of lumber
			lumber += i;
		}
		else if (s.compareTo("grain") == 0) {			// Add to amount of grain
			grain += i;
		}
		else if (s.compareTo("wool") == 0) {			// Add to amount of wool
			wool += i;
		}
		else if (s.compareTo("ore") == 0) {				// Add to amount of ore
			ore += i;
		}
	}
	
	public void addKnights(int i) {
		knights += i;									// Add i number of points to the player's knight total
	}
	
	public void addPoints(int i) {
		points += i;									// Add i number of points to the player's current point total
	}
	
	public String buildSettlement(Node n) {
		/* Build a settlement on a node.
		 * Parameter: n, the node that the player wants to build on
		 * Returns a string that can be printed using the GUI.
		 * 
		 * When a settlement is built, n's owner becomes the current player.
		 * The player uses one brick, lumber, wool, and grain.
		 * Also, adjacent nodes have their statuses set to "unavailable".
		 * 
		 * A settlement can only be built on n if n meets the following criteria:
		 * 
		 * 1. If the node already has a settlement or city, or the adjacent nodes have a
		 *    settlement or city, then this method returns false. Else, it returns true.
		 * 2. The node connects to at least one of the current players nodes.
		 * 3. The player must have at least one of each of these resources:
		 * 	  brick, lumber, wool, grain
		 */
		
		if (totalSettlements == 5) {
			return("You have already built the maximum number of settlements! (5)");			
		}
		else if (n.getStatus().compareTo("na") == 0) {
			return("Node is too close to other settlements or cities!");
		}
		else if ((n.getStatus().compareTo("s") == 0) || (n.getStatus().compareTo("c") == 0)) {
			return("There is already a settlement or city on this node.");
		}
		else if (n.getOwner() != null) {
			return("You cannot build in between another player's roads.");
		}
		else if ((brick < 1) || (lumber < 1) || (wool < 1) || (grain < 1)) {
			return("You do not have enough resources to build a settlement!");
		}
		else {	
			int flag = 0;									// flag checks if node touches a road owned by current player		
			for (Edge adjEdge : n.getNearbyEdges()) {
				if (adjEdge.getOwner() == this) {
					flag += 1;
				}
			}
			
			if (flag == 0) {								// If flag remains zero, it means no roads owned by the current player were found touching the node
				return("This node does not touch any roads that you own!");
			}
			else {
				n.setOwner(this);							// If criteria 2 and 3 are met...
				n.setStatus("s");							// Set owner to current player and status to "settlement"
				brick -= 1;									// Subtract resources from player
				lumber -= 1;
				wool -= 1;
				grain -= 1;
				totalSettlements += 1;
				ownedNodes.add(n);
				
				for (Node adjNode : n.getNearbyNodes()) {
					adjNode.setStatus("na");				// Set adjacent nodes to "unavailable" (Distance Rule)
				}
				
				return("Settlement successfully built!");	// Since settlement has been built, return true						
			}
		}
	}
	
	public String buildCity(Node n) {
		/* Upgrade a settlement to a city.
		 * Parameter: n, the node that the player wants to build on
		 * Returns a string that can be printed using the GUI.
		 * 
		 * When a city is built, n's status changes from "settlement" to "city".
		 * The player uses three ore, two grain.
		 * 
		 * A settlement can only be built on n if n meets the following criteria:
		 * 
		 * 1. n must have a settlement whose owner is the current player
		 * 2. The player must have at least three ore and two grain.
		 */
		
		if (totalCities == 4) {
			return("You have already built the maximum number of cities! (4)");			
		}
		else if (n.getStatus().compareTo("c") == 0) {
			return("There is already a city on this node.");
		}
		else if (n.getOwner() != this) {
			return("You do not currently own this node!");
		}
		else if((ore < 3) && (grain < 2)) {
			return("Not enough resources to build a city!");
		}
		else {
			n.setStatus("city");
			ore -= 3;
			grain -= 2;
			totalCities += 1;
			totalSettlements -= 1;				// Same node, but not a settlement anymore
			
			return("City successfully built!");
		}
	}
	
	public String buildRoad(Edge e) {
		/* Build a road on an edge.
		 * Parameter: e, the edge that the player wants to build on
		 * Returns a string that can be printed using the GUI.
		 * 
		 * When a road is built, e's owner changes to the current player.
		 * The player uses one brick, one lumber.
		 * 
		 * A road can only be built on e if e meets the following criteria:
		 * 
		 * 1. e must be adjacent to either a settlement or another road.
		 * 2. The player must have at least one brick and one lumber.
		 */

		if (ownedNodes.size() == 15) {
			return("You have already built the maximum number of roads! (15)");			
		}
		if (e.getOwner() != null) {
			return("Another road has already been built on this edge!");
		}
		if (lumber < 1 || brick < 1) {
			return("You do not have enough resources to build a road!");
		}
		
		int flag = 0;									// If the flags both remains zero (no adjacent settlements, cities, or roads), return false
		for (Node adjNode : e.getNearbyNodes()) {
			if ((adjNode.getOwner() == this) &&
					(adjNode.getStatus().compareTo("s") == 0 || 
					adjNode.getStatus().compareTo("c") == 0)) {
				flag += 1;
			}
		}

		for (Edge adjEdge : e.getNearbyEdges()) {
			if (adjEdge.getOwner() == this) {
				flag += 1;
			}
		}
		
		if (flag == 0) {
			return("The edge is not adjacent to another road or owned node.");		
		}
		else {
			e.setOwner(this);
			brick -= 1;
			lumber -= 1;
			roads.add(e);
			
			return("Road successfully built!");
		}
	}
	
	public String buildStartingSettlement(Node n) {
		/* Build a settlement on a node.
		 * Parameter: n, the node that the player wants to build on
		 * Returns a string that can be printed using the GUI.
		 * 
		 * When a settlement is built, n's owner becomes the current player.
		 * The player uses one brick, lumber, wool, and grain.
		 * Also, adjacent nodes have their statuses set to "unavailable".
		 * 
		 * A settlement can only be built on n if n meets the following criteria:
		 * 
		 * 1. If the node already has a settlement or city, or the adjacent nodes have a
		 *    settlement or city, then this method returns false. Else, it returns true.
		 * 2. The node connects to at least one of the current players nodes.
		 * 3. The player must have at least one of each of these resources:
		 * 	  brick, lumber, wool, grain
		 */
		
		if (totalSettlements == 5) {
			return("You have already built the maximum number of settlements! (5)");			
		}
		else if (n.getStatus().compareTo("na") == 0) {
			return("Node is too close to other settlements or cities!");
		}
		else if ((n.getStatus().compareTo("s") == 0) || (n.getStatus().compareTo("c") == 0)) {
			return("There is already a settlement or city on this node.");
		}
		else if (n.getOwner() != null) {
			return("You cannot build in between another player's roads.");
		}
		else {	
			int flag = 0;									// flag checks if node touches a road owned by current player		
			for (Edge adjEdge : n.getNearbyEdges()) {
				if (adjEdge.getOwner() == this) {
					flag += 1;
				}
			}
			
			if (flag == 0) {								// If flag remains zero, it means no roads owned by the current player were found touching the node
				return("This node does not touch any roads that you own!");
			}
			else {
				n.setOwner(this);							// If criteria 2 and 3 are met...
				n.setStatus("s");							// Set owner to current player and status to "settlement"
				//brick -= 1;									// Subtract resources from player
				//lumber -= 1;
				//wool -= 1;
				//grain -= 1;
				totalSettlements += 1;
				ownedNodes.add(n);
				
				for (Node adjNode : n.getNearbyNodes()) {
					adjNode.setStatus("na");				// Set adjacent nodes to "unavailable" (Distance Rule)
				}
				
				return("Settlement successfully built!");	// Since settlement has been built, return true						
			}
		}
	}
	
	public String buildStartingRoad(Edge e) {
		/* Build a road on an edge.
		 * Parameter: e, the edge that the player wants to build on
		 * Returns a string that can be printed using the GUI.
		 * 
		 * When a road is built, e's owner changes to the current player.
		 * The player uses one brick, one lumber.
		 * 
		 * A road can only be built on e if e meets the following criteria:
		 * 
		 * 1. e must be adjacent to either a settlement or another road.
		 * 2. The player must have at least one brick and one lumber.
		 */

		if (ownedNodes.size() == 15) {
			return("You have already built the maximum number of roads! (15)");			
		}
		if (e.getOwner() != null) {
			return("Another road has already been built on this edge!");
		}
		
		int flag = 0;									// If the flags both remains zero (no adjacent settlements, cities, or roads), return false
		for (Node adjNode : e.getNearbyNodes()) {
			if ((adjNode.getOwner() == this) &&
					(adjNode.getStatus().compareTo("s") == 0 || 
					adjNode.getStatus().compareTo("c") == 0)) {
				flag += 1;
			}
		}

		for (Edge adjEdge : e.getNearbyEdges()) {
			if (adjEdge.getOwner() == this) {
				flag += 1;
			}
		}
		
		if (flag == 0) {
			return("The edge is not adjacent to another road or owned node.");		
		}
		else {
			e.setOwner(this);
			roads.add(e);
			
			return("Road successfully built!");
		}
	}
	
	
	
	public String tradeResources(Player other, int thisTradeList[], int otherTradeList[]) {
		/* Trade resources with another player.
		 * Parameters:
		 * 		other: The Player that this Player wants to trade with
		 * 		thisTradeList: An array of 5 ints, representing resources this Player wants
		 * 						to give to the other Player
		 * 		otherTradeList: An array of 5 ints, representing resources the other Player
		 * 						wants to give to this Player
		 * Returns a string that can be printed using the GUI.
		 * 
		 * Both ...TradeLists must be arrays of 5 given in this order:
		 * 		c
		 */		
		
		if ((brick - thisTradeList[0] < 0) ||
				(lumber - thisTradeList[1] < 0) ||
				(grain - thisTradeList[2] < 0) ||
				(wool - thisTradeList[3] < 0) ||
				(ore - thisTradeList[4] < 0) ||
				(other.getResources("brick") - otherTradeList[0] < 0) ||
				(other.getResources("lumber") - otherTradeList[1] < 0) ||
				(other.getResources("grain") - otherTradeList[2] < 0) ||
				(other.getResources("wool") - otherTradeList[3] < 0) ||
				(other.getResources("ore") - otherTradeList[4] < 0)) 
		{
			
			return("One or both player(s) does not have enough resources to complete the trade.");
		}
		else 
		{
			this.addResources("brick", otherTradeList[0] - thisTradeList[0]);
			this.addResources("lumber", otherTradeList[1] - thisTradeList[1]);
			this.addResources("grain", otherTradeList[2] - thisTradeList[2]);
			this.addResources("wool", otherTradeList[3] - thisTradeList[3]);
			this.addResources("ore", otherTradeList[4] - thisTradeList[4]);
			other.addResources("brick", thisTradeList[0] - otherTradeList[0]);
			other.addResources("lumber", thisTradeList[1] - otherTradeList[1]);
			other.addResources("grain", thisTradeList[2] - otherTradeList[2]);
			other.addResources("wool", thisTradeList[3] - otherTradeList[3]);
			other.addResources("ore", thisTradeList[4] - otherTradeList[4]);
			
			return("Trade has been completed!");
		}
	}
	
	/*
	public String tradeResources(String s, Node port) {
		if (port.getOwner() != this) {
			return("Not implemented yet");
		}
		return("Not implemented yet");
	}
	*/
	
	public String tradeResources(String resourceIn, String resourceWant) {
		if (resourceIn.compareTo("brick") == 0) {
			if (brick < 4) {
				return("You don't have enough brick for a 4:1 trade.");
			}
			else {
				brick -= 4;
				this.addResources(resourceWant, 1);
				
				return("Traded 4 brick for 1 " + resourceWant);
			}
		}
		else if (resourceIn.compareTo("lumber") == 0) {	
			if (lumber < 4) {
				return("You don't have enough lumber for a 4:1 trade.");
			}
			else {
				lumber -= 4;
				this.addResources(resourceWant, 1);
				
				return("Traded 4 lumber for 1 " + resourceWant);
			}
		}
		else if (resourceIn.compareTo("grain") == 0) {
			if (grain < 4) {
				return("You don't have enough grain for a 4:1 trade.");
			}
			else {
				grain -= 4;
				this.addResources(resourceWant, 1);
				
				return("Traded 4 grain for 1 " + resourceWant);
			}
		}
		else if (resourceIn.compareTo("wool") == 0) {
			if (wool < 4) {
				return("You don't have enough wool for a 4:1 trade.");
			}
			else {
				wool -= 4;
				this.addResources(resourceWant, 1);
				
				return("Traded 4 wool for 1 " + resourceWant);
			}
		}
		else if (resourceIn.compareTo("ore") == 0) {
			if (ore < 4) {
				return("You don't have enough ore for a 4:1 trade.");
			}
			else {
				ore -= 4;
				this.addResources(resourceWant, 1);
				
				return("Traded 4 ore for 1 " + resourceWant);
			}
		}
		else {
			return("String requested is not the name of a resource?");
		}
	}
	
	public String addDevCard() {
		if ((grain < 1) && (wool < 1) && (ore < 1)) {
			return("Not enough resources to buy a development card!");
		}
		else {
			grain -= 1;
			wool -= 1;
			ore -= 1;
			hand.addCard();
			
			return("Card has been bought!");
		}

	}
}
