package org.catan.map;

public class Hex {
	//fields
	private int DiceNumber; // this number 2-12 indicates the number to roll to get resources from the hex
	private String ResourceType; // Resource Keywords are: "lumber", "ore", "brick", "grain", "wool", and "nothing" (for desert)
	private boolean RobberStatus; // true indicates robber is on the hex, false indicates it isn't
	
	//constructors
	public Hex() {	//default constructor
		DiceNumber = 1;		//1 is a filler value
		ResourceType = "nothing";	
		RobberStatus = false;	
	}
	
	public Hex(int i, String s) {//specific constructor for initializing
		DiceNumber = i;
		ResourceType = s;
		RobberStatus = false;
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
	
}