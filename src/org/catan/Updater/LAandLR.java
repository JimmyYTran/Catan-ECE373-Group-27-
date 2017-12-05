package org.catan.Updater;

import org.catan.map.*;
import org.catan.players.*;
import java.util.ArrayList;

public class LAandLR {
	
	public LAandLR() {
		//default constructor
		//note this is a class for static functions, shouldn't be constructed
	}
	
	
	//Assumptions and Notes: 
	//		global variables are set as follows at initialization of game:
	//					LargestArmyOwner = null;	LargestRoadOwner = null;
	//					LargestArmy = 0;			LargestRoad = 0;
	//		also assumes the following global variables exist:
	//					Roads Built, SettlementsBuilt, CardsPlayed, CurrentPlayer
		
	public static Player findLAPlayer(boolean cardsPlayed, Player currentPlayer, Player oldLAOwner) {

		if((cardsPlayed == true)) { //were cards even played?
			
			if(oldLAOwner == null) { //if there hasn't been a LAOwner yet
				if(currentPlayer.getKnights() >= 3) {//have to have at least 3 knights
					return currentPlayer;	//the first LAOwner is set
				}
			}
			else { //if there has been a LAOwner
				if(currentPlayer.getKnights() >= oldLAOwner.getKnights()) { //check Army Size
					return currentPlayer;	//the LAOwner changes
				}
			}
		}
		
		return oldLAOwner;//if cards haven't been played, no changes made
	}
	
	
	
	
	public static Player findLRPlayer(ArrayList<Player> players, Player currentPlayer, Player oldLROwner, int oldLR, boolean settlementsBuilt, boolean roadsBuilt) {		
		int LRP = 5;	//a fictional player with a 0 length longest road.  can't be usurped until a player gets a 5 length LR
		if((settlementsBuilt || roadsBuilt) == true) {//did someone build a settlement or road, so...
			
			//create a list of LR lengths for each potential player (and a fifth one to ensure an initial LRP)
			int[] LRLengths = {0,0,0,0,0};
			
			//determine the LRLength for each player
			for(int ii = 0; ii < players.size(); ii++) {//goes through each player
				for(int iii=0; iii < players.get(ii).getRoads().size(); iii++) {//loops through all of the players roads
					//create a new empty arraylist to be used for the previous edges input (this is the very first edge)
					ArrayList<Edge> prev = new ArrayList<Edge>();
					if(LAandLR.AnalyzeBranch(players.get(ii).getRoads().get(iii), prev, players.get(ii)) > LRLengths[ii]) {
						LRLengths[ii]= LAandLR.AnalyzeBranch(players.get(ii).getRoads().get(iii), prev, players.get(ii));
					}
				}
			}
			
			// find newLROwner using LRP
			for(int iiii =0; iiii < LRLengths.length; iiii++) {//go through the array to check for LR
				if(  (LRLengths[iiii] >= 5) && (LRLengths[iiii] > LRLengths[LRP])  ) {//if its at least 5 and bigger than current Longest Road Player
					LRP = iiii; //sets the current Longest Road Player
				}
			}
			
			//return******************************(need to make sure no one else needs oldLR)
			return players.get(LRP);
			
		}
		else {//nothing new built, no change
			return oldLROwner;
		}
	}
	
	
	
	
	public static int AnalyzeBranch(Edge initialEdge, ArrayList<Edge> previousEdges, Player p) {
		int length = 1;	//at minimum the initial edge is in this branch
		ArrayList<Edge> nextBranches;
		nextBranches = new ArrayList<Edge>(); //list of considered branches
		boolean isPrevEdge = false; //this is used to determine if a nearby edge is a previous edge
		ArrayList<Edge> tempPreviousEdges = (ArrayList<Edge>) previousEdges.clone();	
		
		for(int i = 0; i < initialEdge.getNearbyEdges().size(); i++) {//loop to create nextBranches from nearby edges
			isPrevEdge = false; // reset for new loop run
			
			for(int ii = 0; ii < previousEdges.size(); ii++) {//loops through previous edges to determine if nearbyEdge is a branch
				if(initialEdge.getNearbyEdges().get(i) == previousEdges.get(ii)) {//is the edge a previous edge?
					isPrevEdge = true;
				}
			}
			
			if(isPrevEdge != true) {//if the edge is a new edge
				if(initialEdge.getNearbyEdges().get(i).getOwner() == p) {//if both the nearby edge and initial edge have same owner
					
					//compare the nodes to find the "common node"  then determine is someone else built on top of it to interrupt a road
					for(int i2 = 0; i2 < initialEdge.getNearbyNodes().size(); i2++) {//loop through nodes of initial edge
						for(int i3 = 0; i3 < initialEdge.getNearbyEdges().get(i).getNearbyNodes().size(); i3++) {//loop through nodes of considered edge
							if(initialEdge.getNearbyNodes().get(i2) == initialEdge.getNearbyEdges().get(i).getNearbyNodes().get(i3)) {//if common node is found
								//check  if player owns node or no one does
								if((initialEdge.getNearbyNodes().get(i2).getOwner() == p) || (initialEdge.getNearbyNodes().get(i2).getOwner() == null)){
									nextBranches.add(initialEdge.getNearbyEdges().get(i));	//add it to nextBrancheslist!
									
								}
							}
						}
					}
				}//end of if statement for checking owners of the nearby edge				
			}
		}//end of for loop for creating nextBranches
		
		switch(nextBranches.size()){//this analyzes the nextBranches
			case 0:	//we have no nextbranches.  the initial Edge was the last edge in a branch or alone
				//do nothing, there is no nextBranch to analyze and the length of this branch is still 1
				break;
			case 1: //there is one branch leaving initial edge and we need to analyze it
				tempPreviousEdges.add(initialEdge);//in order to analyze branch, it must know that the initial edge is a previous edge
				length = 1 + LAandLR.AnalyzeBranch(nextBranches.get(0), tempPreviousEdges, p);
				break;
			default://there are multiple branches leaving the initial edge.  we need to analyze each
				int maxLength = 0;//this will be the length of the longest nextBranch
				tempPreviousEdges.add(initialEdge);//in order to analyze branch, it must know that the initial edge is a previous edge

				for(int iii = 0; iii < nextBranches.size(); iii++) {//loop to go through each branch that must be analyzed
					if(LAandLR.AnalyzeBranch(nextBranches.get(iii), tempPreviousEdges, p) > maxLength) {
						maxLength = LAandLR.AnalyzeBranch(nextBranches.get(iii), tempPreviousEdges, p);
					}
				}
		
				length = 1 + maxLength;
				break;
		}
		
		return length;
	}
	
	
}
