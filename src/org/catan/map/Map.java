package org.catan.map;
import java.util.ArrayList; // Needed for the ArrayList class
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Map {
	//fields
	private int RobberHex;				//notes the index for the current location of the robber
	private ArrayList<Hex> Hexes;		//list of hexes in the game, total of 19
	private ArrayList<Node> Nodes;		//list of nodes in the game, total of 54
	private ArrayList<Edge> Edges;		//list of all edges in the game, a total of 72
	//the harbors, with 1 being the top left and going clockwise around the map
	private String H1Resource = "any";	// keywords are "lumber", "grain", "brick", "wool", "ore" and "any"
	private String H2Resource = "grain";
	private String H3Resource = "ore";
	private String H4Resource = "any";
	private String H5Resource = "wool";
	private String H6Resource = "any";
	private String H7Resource = "any";
	private String H8Resource = "brick";
	private String H9Resource = "lumber";
	private int H1Rate = 3;			//all Harbor Rates are relative to 1.  so H1Rate 3 means it is 3:1
	private int H2Rate = 2;
	private int H3Rate = 2;
	private int H4Rate = 3;
	private int H5Rate = 2;
	private int H6Rate = 3;
	private int H7Rate = 3;
	private int H8Rate = 2;
	private int H9Rate = 2;
	private ArrayList<Node> H1Nodes;
	private ArrayList<Node> H2Nodes;
	private ArrayList<Node> H3Nodes;
	private ArrayList<Node> H4Nodes;
	private ArrayList<Node> H5Nodes;
	private ArrayList<Node> H6Nodes;
	private ArrayList<Node> H7Nodes;
	private ArrayList<Node> H8Nodes;
	private ArrayList<Node> H9Nodes;
	private ArrayList<String> HexResources;//No desert
	//[] = {"lumber", "lumber", "lumber", "lumber", "grain", "grain", "grain", "grain", "ore", "ore", "ore", "brick", "brick", "brick", "wool", "wool", "wool", "wool"  };
	private ArrayList<Integer> HexNumbers;//No desert
	
	//constructors
	public Map(){
		//create arrayList objects
		Hexes = new ArrayList<Hex>(19);
		Edges = new ArrayList<Edge>(72);
		Nodes = new ArrayList<Node>(54);
		H1Nodes = new ArrayList<Node>(2);
		H2Nodes = new ArrayList<Node>(2);
		H3Nodes = new ArrayList<Node>(2);
		H4Nodes = new ArrayList<Node>(2);
		H5Nodes = new ArrayList<Node>(2);
		H6Nodes = new ArrayList<Node>(2);
		H7Nodes = new ArrayList<Node>(2);
		H8Nodes = new ArrayList<Node>(2);
		H9Nodes = new ArrayList<Node>(2);
		HexResources = new ArrayList<String>(Arrays.asList("lumber", "lumber", "lumber", "lumber", "grain", "grain", "grain", "grain", "ore", "ore", "ore", "brick", "brick", "brick", "wool", "wool", "wool", "wool"  ));
		Collections.shuffle(HexResources);
		HexNumbers = new ArrayList<Integer>(Arrays.asList(2, 3, 3, 4, 4, 5, 5, 6, 6,8,8,9,9,10,10, 11, 11, 12));
		Collections.shuffle(HexNumbers);
		
		//create RobberHex
		RobberHex = 20; //this is a dummy value since we don't know which hex has the robber yet
		//temporary objects/variables used to initialize the list members
		Hex h = new Hex();
		Random rand = new Random();
		int pickedNum = 18;
		boolean go = false;
		for (int i = 0; i < 18; i++) {
			Hexes.add(new Hex(HexNumbers.get(0), HexResources.get(0)));
			//System.out.println(HexResources.get(0) + " " + HexResources.size()); to check randomizer
			HexNumbers.remove(0);
			HexResources.remove(0);
		}
		h.setRobberStatus(true); //after randomizing resources with numbers the desert is added
		Hexes.add(h);
		Collections.shuffle(Hexes);//shuffle after because I realized desert was always going to be 18 if I added it last
		
		//counters for initializing hexes
		/*int Forest = 0;		
		int Pasture = 0;
		int Mountain = 0;
		int Field = 0;
		int Hill = 0;
		int Desert = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		int five = 0;
		int six = 0;
		int eight = 0;
		int nine =0;
		int ten = 0;
		int eleven = 0;
		int twelve = 0;
		
		//initialize the members of the Hexes ArrayList//////////////////////////////////////////
		//notes: there are 1 desert, 3 mountains, 4 forests, 3 hills, 4 fields, and 4 pastures, one 12&2, and two of 3-11 (excluding 7)
		/*for(int i =0; i < 19; i++) {//loop that creates 19 hexes
			h = new Hex();	//creates a new hex object to be edited and then added to the list
			
			while(go == false) {//this loop assigns the hex a resource type randomly
				pickedNum = rand.nextInt(6);//this is to determine resource type, 0-5 
				switch(pickedNum){
					case 0:	//mountain
						if(Mountain < 3) { //if we haven't already made too many mountains, make this hex a mountain
							h.setResourceType("ore");  
							Mountain++;
						}
						break;
					case 1:	//field
						if(Field < 4) { //if we haven't already made too many fields, make this hex a mountain
							h.setResourceType("grain");  
							Field++;
						}
						break;
					case 2:	//pasture
						if(Pasture < 4) { //if we haven't already made too many pastures, make this hex a mountain
							h.setResourceType("wool");  
							Pasture++;
						}
						break;
					case 3:	//forest
						if(Forest < 4) { //if we haven't already made too many Forests, make this hex a mountain
							h.setResourceType("lumber");  
							Forest++;
						}
						break;
					case 4:	//hill
						if(Hill < 3) { //if we haven't already made too many hills, make this hex a mountain
							h.setResourceType("brick");  
							Hill++;
						}
						break;
					case 5: //desert
						if(Desert < 1) { //if we haven't already made too many deserts, make this hex a mountain
							h.setResourceType("nothing");  
							Desert++;
							h.setRobberStatus(true);
							RobberHex = i;//this notes that the robberhex is the desert hex, which is index i in the List
						}
						break;
					default:	//this can be used for errors
						break;
					
				}//end of switch statement
			}//end of while loop for assigning resources
			
			go = false;//reset for next loop to assign numbers
			
			while(go == false) {//this loop assigns the hex a dice number randomly
				if(h.getResourceType().compareTo("nothing") == 0) {
					//if the currently being made hex is the desert don't assign a dice number, it will have "1" which is impossible to roll
				}
				else {
					pickedNum = (rand.nextInt(11)+2);//this is to determine a dice number 2-12
					switch(pickedNum){
						case 2:	//dice number 2
							if(two < 1) { //if we don't have a two already
								h.setDiceNumber(2);  
								two++;
							}
							break;
						case 3:	//dice number 3
							if(three < 2) { //if we don't have two threes already
								h.setDiceNumber(3);  
								three++;
							}
							break;
						case 4:	//dice number 4
							if(four < 2) { //if we don't have two fours already
								h.setDiceNumber(4);  
								four++;
							}
							break;
						case 5:	//dice number 5
							if(five < 2) { //if we don't have two 5s already
								h.setDiceNumber(5);  
								five++;
							}
							break;
						case 6:	//dice number 6
							if(six < 2) { //if we don't have two 6s already
								h.setDiceNumber(6);  
								six++;
							}
							break;
						case 8:	//dice number 8
							if(eight < 2) { //if we don't have two 8s already
								h.setDiceNumber(8);  
								eight++;
							}
							break;
						case 9:	//dice number 9
							if(nine < 2) { //if we don't have two 9s already
								h.setDiceNumber(9);  
								eight++;
							}
							break;
						case 10:	//dice number 10
							if(ten < 2) { //if we don't have two 10s already
								h.setDiceNumber(10);  
								ten++;
							}
							break;
						case 11:	//dice number 11
							if(eleven < 2) { //if we don't have two 11s already
								h.setDiceNumber(11);  
								eleven++;
							}
							break;
						case 12:	//dice number 12
							if(twelve < 1) { //if we don't have a 12s already
								h.setDiceNumber(12);  
								twelve++;
							}
							break;
						default:	//this is for the case of 7, don't assign a number, try again
							break;
							
					}//end of switch statement
				}//end of else statement
			}//end of while loop for assigning dice numbers
			
			this.addHex(h);//adds the just created hex to the list
		}//end of loop creating hexes 
		*/
		//initialize the members of the Edges ArrayList//////////////////////////////////////////		
		for(int i =0; i < 72; i++) {//loop that creates 72 edges
			Edges.add(new Edge());
		}//end of for loop for creating edges
		
		//initialize the members of the Nodes ArrayList//////////////////////////////////////////
		for(int i =0; i < 54; i++) {//loop that creates 54 nodes
			Nodes.add(new Node());
		}//end of for loop for creating nodes
	
		//the following code sets the Nearby Edges and Nodes for the Edges of the Map////////////
		//0
		Edges.get(0).addNearbyEdge(Edges.get(2));
		Edges.get(0).addNearbyEdge(Edges.get(6));
		Edges.get(0).addNearbyNode(Nodes.get(0));
		Edges.get(0).addNearbyNode(Nodes.get(3));
		//1
		Edges.get(1).addNearbyEdge(Edges.get(0));
		Edges.get(1).addNearbyEdge(Edges.get(7));
		Edges.get(1).addNearbyEdge(Edges.get(2));
		Edges.get(1).addNearbyNode(Nodes.get(0));
		Edges.get(1).addNearbyNode(Nodes.get(4));
		//2
		Edges.get(2).addNearbyEdge(Edges.get(1));
		Edges.get(2).addNearbyEdge(Edges.get(3));
		Edges.get(2).addNearbyEdge(Edges.get(7));
		Edges.get(2).addNearbyNode(Nodes.get(1));
		Edges.get(2).addNearbyNode(Nodes.get(4));
		//3
		Edges.get(3).addNearbyEdge(Edges.get(2));
		Edges.get(3).addNearbyEdge(Edges.get(4));
		Edges.get(3).addNearbyEdge(Edges.get(8));
		Edges.get(3).addNearbyNode(Nodes.get(1));
		Edges.get(3).addNearbyNode(Nodes.get(5));
		//4
		Edges.get(4).addNearbyEdge(Edges.get(3));
		Edges.get(4).addNearbyEdge(Edges.get(5));
		Edges.get(4).addNearbyEdge(Edges.get(8));
		Edges.get(4).addNearbyNode(Nodes.get(5));
		Edges.get(4).addNearbyNode(Nodes.get(2));
		//5
		Edges.get(5).addNearbyEdge(Edges.get(4));
		Edges.get(5).addNearbyEdge(Edges.get(9));
		Edges.get(5).addNearbyNode(Nodes.get(2));
		Edges.get(5).addNearbyNode(Nodes.get(6));
		//6
		Edges.get(6).addNearbyEdge(Edges.get(0));
		Edges.get(6).addNearbyEdge(Edges.get(10));
		Edges.get(6).addNearbyEdge(Edges.get(11));
		Edges.get(6).addNearbyNode(Nodes.get(3));
		Edges.get(6).addNearbyNode(Nodes.get(7));
		//7
		Edges.get(7).addNearbyEdge(Edges.get(1));
		Edges.get(7).addNearbyEdge(Edges.get(2));
		Edges.get(7).addNearbyEdge(Edges.get(12));
		Edges.get(7).addNearbyEdge(Edges.get(13));
		Edges.get(7).addNearbyNode(Nodes.get(4));
		Edges.get(7).addNearbyNode(Nodes.get(8));
		//8
		Edges.get(8).addNearbyEdge(Edges.get(3));
		Edges.get(8).addNearbyEdge(Edges.get(4));
		Edges.get(8).addNearbyEdge(Edges.get(14));
		Edges.get(8).addNearbyEdge(Edges.get(15));
		Edges.get(8).addNearbyNode(Nodes.get(5));
		Edges.get(8).addNearbyNode(Nodes.get(9));
		//9
		Edges.get(9).addNearbyEdge(Edges.get(5));
		Edges.get(9).addNearbyEdge(Edges.get(16));
		Edges.get(9).addNearbyEdge(Edges.get(17));
		Edges.get(9).addNearbyNode(Nodes.get(6));
		Edges.get(9).addNearbyNode(Nodes.get(10));
		//10
		Edges.get(10).addNearbyEdge(Edges.get(6));
		Edges.get(10).addNearbyEdge(Edges.get(11));
		Edges.get(10).addNearbyEdge(Edges.get(18));
		Edges.get(10).addNearbyNode(Nodes.get(7));
		Edges.get(10).addNearbyNode(Nodes.get(11));
		//11
		Edges.get(11).addNearbyEdge(Edges.get(10));
		Edges.get(11).addNearbyEdge(Edges.get(6));
		Edges.get(11).addNearbyEdge(Edges.get(19));
		Edges.get(11).addNearbyEdge(Edges.get(12));
		Edges.get(11).addNearbyNode(Nodes.get(7));
		Edges.get(11).addNearbyNode(Nodes.get(12));
		//12
		Edges.get(12).addNearbyEdge(Edges.get(11));
		Edges.get(12).addNearbyEdge(Edges.get(13));
		Edges.get(12).addNearbyEdge(Edges.get(7));
		Edges.get(12).addNearbyEdge(Edges.get(19));
		Edges.get(12).addNearbyNode(Nodes.get(8));
		Edges.get(12).addNearbyNode(Nodes.get(12));
		//13
		Edges.get(13).addNearbyEdge(Edges.get(7));
		Edges.get(13).addNearbyEdge(Edges.get(12));
		Edges.get(13).addNearbyEdge(Edges.get(15));
		Edges.get(13).addNearbyEdge(Edges.get(20));
		Edges.get(13).addNearbyNode(Nodes.get(8));
		Edges.get(13).addNearbyNode(Nodes.get(13));
		//14
		Edges.get(14).addNearbyEdge(Edges.get(8));
		Edges.get(14).addNearbyEdge(Edges.get(13));
		Edges.get(14).addNearbyEdge(Edges.get(15));
		Edges.get(14).addNearbyEdge(Edges.get(20));
		Edges.get(14).addNearbyNode(Nodes.get(9));
		Edges.get(14).addNearbyNode(Nodes.get(13));
		//15
		Edges.get(15).addNearbyEdge(Edges.get(8));
		Edges.get(15).addNearbyEdge(Edges.get(14));
		Edges.get(15).addNearbyEdge(Edges.get(16));
		Edges.get(15).addNearbyEdge(Edges.get(21));
		Edges.get(15).addNearbyNode(Nodes.get(9));
		Edges.get(15).addNearbyNode(Nodes.get(14));
		//16
		Edges.get(16).addNearbyEdge(Edges.get(9));
		Edges.get(16).addNearbyEdge(Edges.get(15));
		Edges.get(16).addNearbyEdge(Edges.get(17));
		Edges.get(16).addNearbyEdge(Edges.get(21));
		Edges.get(16).addNearbyNode(Nodes.get(10));
		Edges.get(16).addNearbyNode(Nodes.get(14));
		//17
		Edges.get(17).addNearbyEdge(Edges.get(9));
		Edges.get(17).addNearbyEdge(Edges.get(16));
		Edges.get(17).addNearbyEdge(Edges.get(22));
		Edges.get(17).addNearbyNode(Nodes.get(10));
		Edges.get(17).addNearbyNode(Nodes.get(15));
		//18
		Edges.get(18).addNearbyEdge(Edges.get(10));
		Edges.get(18).addNearbyEdge(Edges.get(23));
		Edges.get(18).addNearbyEdge(Edges.get(24));
		Edges.get(18).addNearbyNode(Nodes.get(11));
		Edges.get(18).addNearbyNode(Nodes.get(16));
		//19
		Edges.get(19).addNearbyEdge(Edges.get(11));
		Edges.get(19).addNearbyEdge(Edges.get(12));
		Edges.get(19).addNearbyEdge(Edges.get(25));
		Edges.get(19).addNearbyEdge(Edges.get(26));
		Edges.get(19).addNearbyNode(Nodes.get(12));
		Edges.get(19).addNearbyNode(Nodes.get(17));
		//20
		Edges.get(20).addNearbyEdge(Edges.get(13));
		Edges.get(20).addNearbyEdge(Edges.get(14));
		Edges.get(20).addNearbyEdge(Edges.get(27));
		Edges.get(20).addNearbyEdge(Edges.get(28));
		Edges.get(20).addNearbyNode(Nodes.get(13));
		Edges.get(20).addNearbyNode(Nodes.get(18));
		//21
		Edges.get(21).addNearbyEdge(Edges.get(15));
		Edges.get(21).addNearbyEdge(Edges.get(16));
		Edges.get(21).addNearbyEdge(Edges.get(29));
		Edges.get(21).addNearbyEdge(Edges.get(30));
		Edges.get(21).addNearbyNode(Nodes.get(14));
		Edges.get(21).addNearbyNode(Nodes.get(19));
		//22
		Edges.get(22).addNearbyEdge(Edges.get(17));
		Edges.get(22).addNearbyEdge(Edges.get(31));
		Edges.get(22).addNearbyEdge(Edges.get(32));
		Edges.get(22).addNearbyNode(Nodes.get(15));
		Edges.get(22).addNearbyNode(Nodes.get(20));
		//23
		Edges.get(23).addNearbyEdge(Edges.get(18));
		Edges.get(23).addNearbyEdge(Edges.get(24));
		Edges.get(23).addNearbyEdge(Edges.get(33));
		Edges.get(23).addNearbyNode(Nodes.get(16));
		Edges.get(23).addNearbyNode(Nodes.get(21));
		//24
		Edges.get(24).addNearbyEdge(Edges.get(18));
		Edges.get(24).addNearbyEdge(Edges.get(23));
		Edges.get(24).addNearbyEdge(Edges.get(25));
		Edges.get(24).addNearbyEdge(Edges.get(34));
		Edges.get(24).addNearbyNode(Nodes.get(16));
		Edges.get(24).addNearbyNode(Nodes.get(22));
		//25
		Edges.get(25).addNearbyEdge(Edges.get(19));
		Edges.get(25).addNearbyEdge(Edges.get(24));
		Edges.get(25).addNearbyEdge(Edges.get(26));
		Edges.get(25).addNearbyEdge(Edges.get(34));
		Edges.get(25).addNearbyNode(Nodes.get(17));
		Edges.get(25).addNearbyNode(Nodes.get(22));
		//26
		Edges.get(26).addNearbyEdge(Edges.get(19));
		Edges.get(26).addNearbyEdge(Edges.get(25));
		Edges.get(26).addNearbyEdge(Edges.get(27));
		Edges.get(26).addNearbyEdge(Edges.get(35));
		Edges.get(26).addNearbyNode(Nodes.get(17));
		Edges.get(26).addNearbyNode(Nodes.get(23));
		//27
		Edges.get(27).addNearbyEdge(Edges.get(20));
		Edges.get(27).addNearbyEdge(Edges.get(26));
		Edges.get(27).addNearbyEdge(Edges.get(28));
		Edges.get(27).addNearbyEdge(Edges.get(35));
		Edges.get(27).addNearbyNode(Nodes.get(18));
		Edges.get(27).addNearbyNode(Nodes.get(23));
		//28
		Edges.get(28).addNearbyEdge(Edges.get(20));
		Edges.get(28).addNearbyEdge(Edges.get(27));
		Edges.get(28).addNearbyEdge(Edges.get(29));
		Edges.get(28).addNearbyEdge(Edges.get(36));
		Edges.get(28).addNearbyNode(Nodes.get(18));
		Edges.get(28).addNearbyNode(Nodes.get(24));
		//29
		Edges.get(29).addNearbyEdge(Edges.get(21));
		Edges.get(29).addNearbyEdge(Edges.get(28));
		Edges.get(29).addNearbyEdge(Edges.get(30));
		Edges.get(29).addNearbyEdge(Edges.get(36));
		Edges.get(29).addNearbyNode(Nodes.get(19));
		Edges.get(29).addNearbyNode(Nodes.get(24));
		//30
		Edges.get(30).addNearbyEdge(Edges.get(21));
		Edges.get(30).addNearbyEdge(Edges.get(29));
		Edges.get(30).addNearbyEdge(Edges.get(31));
		Edges.get(30).addNearbyEdge(Edges.get(37));
		Edges.get(30).addNearbyNode(Nodes.get(19));
		Edges.get(30).addNearbyNode(Nodes.get(25));
		//31
		Edges.get(31).addNearbyEdge(Edges.get(21));
		Edges.get(31).addNearbyEdge(Edges.get(30));
		Edges.get(31).addNearbyEdge(Edges.get(32));
		Edges.get(31).addNearbyEdge(Edges.get(37));
		Edges.get(31).addNearbyNode(Nodes.get(20));
		Edges.get(31).addNearbyNode(Nodes.get(25));
		//32
		Edges.get(32).addNearbyEdge(Edges.get(22));
		Edges.get(32).addNearbyEdge(Edges.get(31));
		Edges.get(32).addNearbyEdge(Edges.get(38));
		Edges.get(32).addNearbyNode(Nodes.get(20));
		Edges.get(32).addNearbyNode(Nodes.get(26));
		//33
		Edges.get(33).addNearbyEdge(Edges.get(23));
		Edges.get(33).addNearbyEdge(Edges.get(39));
		Edges.get(33).addNearbyNode(Nodes.get(21));
		Edges.get(33).addNearbyNode(Nodes.get(27));
		//34
		Edges.get(34).addNearbyEdge(Edges.get(24));
		Edges.get(34).addNearbyEdge(Edges.get(25));
		Edges.get(34).addNearbyEdge(Edges.get(40));
		Edges.get(34).addNearbyEdge(Edges.get(41));
		Edges.get(34).addNearbyNode(Nodes.get(22));
		Edges.get(34).addNearbyNode(Nodes.get(28));
		//35
		Edges.get(35).addNearbyEdge(Edges.get(26));
		Edges.get(35).addNearbyEdge(Edges.get(27));
		Edges.get(35).addNearbyEdge(Edges.get(42));
		Edges.get(35).addNearbyEdge(Edges.get(43));
		Edges.get(35).addNearbyNode(Nodes.get(23));
		Edges.get(35).addNearbyNode(Nodes.get(29));
		//36
		Edges.get(36).addNearbyEdge(Edges.get(28));
		Edges.get(36).addNearbyEdge(Edges.get(29));
		Edges.get(36).addNearbyEdge(Edges.get(44));
		Edges.get(36).addNearbyEdge(Edges.get(45));
		Edges.get(36).addNearbyNode(Nodes.get(24));
		Edges.get(36).addNearbyNode(Nodes.get(30));
		//37
		Edges.get(37).addNearbyEdge(Edges.get(30));
		Edges.get(37).addNearbyEdge(Edges.get(31));
		Edges.get(37).addNearbyEdge(Edges.get(46));
		Edges.get(37).addNearbyEdge(Edges.get(47));
		Edges.get(37).addNearbyNode(Nodes.get(25));
		Edges.get(37).addNearbyNode(Nodes.get(31));
		//38
		Edges.get(38).addNearbyEdge(Edges.get(32));
		Edges.get(38).addNearbyEdge(Edges.get(48));
		Edges.get(38).addNearbyNode(Nodes.get(26));
		Edges.get(38).addNearbyNode(Nodes.get(32));
		//39
		Edges.get(39).addNearbyEdge(Edges.get(33));
		Edges.get(39).addNearbyEdge(Edges.get(49));
		Edges.get(39).addNearbyEdge(Edges.get(40));
		Edges.get(39).addNearbyNode(Nodes.get(27));
		Edges.get(39).addNearbyNode(Nodes.get(33));
		//40
		Edges.get(40).addNearbyEdge(Edges.get(39));
		Edges.get(40).addNearbyEdge(Edges.get(41));
		Edges.get(40).addNearbyEdge(Edges.get(34));
		Edges.get(40).addNearbyEdge(Edges.get(49));
		Edges.get(40).addNearbyNode(Nodes.get(28));
		Edges.get(40).addNearbyNode(Nodes.get(33));
		//41
		Edges.get(41).addNearbyEdge(Edges.get(34));
		Edges.get(41).addNearbyEdge(Edges.get(40));
		Edges.get(41).addNearbyEdge(Edges.get(42));
		Edges.get(41).addNearbyEdge(Edges.get(50));
		Edges.get(41).addNearbyNode(Nodes.get(28));
		Edges.get(41).addNearbyNode(Nodes.get(34));
		//42
		Edges.get(42).addNearbyEdge(Edges.get(35));
		Edges.get(42).addNearbyEdge(Edges.get(41));
		Edges.get(42).addNearbyEdge(Edges.get(43));
		Edges.get(42).addNearbyEdge(Edges.get(50));
		Edges.get(42).addNearbyNode(Nodes.get(29));
		Edges.get(42).addNearbyNode(Nodes.get(34));
		//43
		Edges.get(43).addNearbyEdge(Edges.get(35));
		Edges.get(43).addNearbyEdge(Edges.get(42));
		Edges.get(43).addNearbyEdge(Edges.get(44));
		Edges.get(43).addNearbyEdge(Edges.get(51));
		Edges.get(43).addNearbyNode(Nodes.get(29));
		Edges.get(43).addNearbyNode(Nodes.get(35));
		//44
		Edges.get(44).addNearbyEdge(Edges.get(36));
		Edges.get(44).addNearbyEdge(Edges.get(43));
		Edges.get(44).addNearbyEdge(Edges.get(45));
		Edges.get(44).addNearbyEdge(Edges.get(51));
		Edges.get(44).addNearbyNode(Nodes.get(30));
		Edges.get(44).addNearbyNode(Nodes.get(35));
		//45
		Edges.get(45).addNearbyEdge(Edges.get(36));
		Edges.get(45).addNearbyEdge(Edges.get(44));
		Edges.get(45).addNearbyEdge(Edges.get(46));
		Edges.get(45).addNearbyEdge(Edges.get(52));
		Edges.get(45).addNearbyNode(Nodes.get(30));
		Edges.get(45).addNearbyNode(Nodes.get(36));
		//46
		Edges.get(46).addNearbyEdge(Edges.get(37));
		Edges.get(46).addNearbyEdge(Edges.get(45));
		Edges.get(46).addNearbyEdge(Edges.get(47));
		Edges.get(46).addNearbyEdge(Edges.get(52));
		Edges.get(46).addNearbyNode(Nodes.get(31));
		Edges.get(46).addNearbyNode(Nodes.get(36));
		//47
		Edges.get(47).addNearbyEdge(Edges.get(37));
		Edges.get(47).addNearbyEdge(Edges.get(46));
		Edges.get(47).addNearbyEdge(Edges.get(48));
		Edges.get(47).addNearbyEdge(Edges.get(53));
		Edges.get(47).addNearbyNode(Nodes.get(31));
		Edges.get(47).addNearbyNode(Nodes.get(37));
		//48
		Edges.get(48).addNearbyEdge(Edges.get(47));
		Edges.get(48).addNearbyEdge(Edges.get(38));
		Edges.get(48).addNearbyEdge(Edges.get(53));
		Edges.get(48).addNearbyNode(Nodes.get(32));
		Edges.get(48).addNearbyNode(Nodes.get(37));
		//49
		Edges.get(49).addNearbyEdge(Edges.get(39));
		Edges.get(49).addNearbyEdge(Edges.get(40));
		Edges.get(49).addNearbyEdge(Edges.get(54));
		Edges.get(49).addNearbyNode(Nodes.get(33));
		Edges.get(49).addNearbyNode(Nodes.get(38));
		//50
		Edges.get(50).addNearbyEdge(Edges.get(42));
		Edges.get(50).addNearbyEdge(Edges.get(41));
		Edges.get(50).addNearbyEdge(Edges.get(55));
		Edges.get(50).addNearbyEdge(Edges.get(56));
		Edges.get(50).addNearbyNode(Nodes.get(34));
		Edges.get(50).addNearbyNode(Nodes.get(39));
		//51
		Edges.get(51).addNearbyEdge(Edges.get(43));
		Edges.get(51).addNearbyEdge(Edges.get(44));
		Edges.get(51).addNearbyEdge(Edges.get(57));
		Edges.get(51).addNearbyEdge(Edges.get(58));
		Edges.get(51).addNearbyNode(Nodes.get(35));
		Edges.get(51).addNearbyNode(Nodes.get(40));
		//52
		Edges.get(52).addNearbyEdge(Edges.get(45));
		Edges.get(52).addNearbyEdge(Edges.get(46));
		Edges.get(52).addNearbyEdge(Edges.get(59));
		Edges.get(52).addNearbyEdge(Edges.get(60));
		Edges.get(52).addNearbyNode(Nodes.get(36));
		Edges.get(52).addNearbyNode(Nodes.get(41));
		//53
		Edges.get(53).addNearbyEdge(Edges.get(47));
		Edges.get(53).addNearbyEdge(Edges.get(48));
		Edges.get(53).addNearbyEdge(Edges.get(61));
		Edges.get(53).addNearbyNode(Nodes.get(37));
		Edges.get(53).addNearbyNode(Nodes.get(42));
		//54
		Edges.get(54).addNearbyEdge(Edges.get(49));
		Edges.get(54).addNearbyEdge(Edges.get(55));
		Edges.get(54).addNearbyEdge(Edges.get(62));
		Edges.get(54).addNearbyNode(Nodes.get(38));
		Edges.get(54).addNearbyNode(Nodes.get(43));
		//55
		Edges.get(55).addNearbyEdge(Edges.get(50));
		Edges.get(55).addNearbyEdge(Edges.get(54));
		Edges.get(55).addNearbyEdge(Edges.get(56));
		Edges.get(55).addNearbyEdge(Edges.get(63));
		Edges.get(55).addNearbyNode(Nodes.get(39));
		Edges.get(55).addNearbyNode(Nodes.get(43));
		//56
		Edges.get(56).addNearbyEdge(Edges.get(50));
		Edges.get(56).addNearbyEdge(Edges.get(55));
		Edges.get(56).addNearbyEdge(Edges.get(57));
		Edges.get(56).addNearbyEdge(Edges.get(63));
		Edges.get(56).addNearbyNode(Nodes.get(39));
		Edges.get(56).addNearbyNode(Nodes.get(44));
		//57
		Edges.get(57).addNearbyEdge(Edges.get(51));
		Edges.get(57).addNearbyEdge(Edges.get(56));
		Edges.get(57).addNearbyEdge(Edges.get(58));
		Edges.get(57).addNearbyEdge(Edges.get(63));
		Edges.get(57).addNearbyNode(Nodes.get(40));
		Edges.get(57).addNearbyNode(Nodes.get(44));
		//58
		Edges.get(58).addNearbyEdge(Edges.get(51));
		Edges.get(58).addNearbyEdge(Edges.get(57));
		Edges.get(58).addNearbyEdge(Edges.get(59));
		Edges.get(58).addNearbyEdge(Edges.get(64));
		Edges.get(58).addNearbyNode(Nodes.get(40));
		Edges.get(58).addNearbyNode(Nodes.get(45));
		//59
		Edges.get(59).addNearbyEdge(Edges.get(52));
		Edges.get(59).addNearbyEdge(Edges.get(58));
		Edges.get(59).addNearbyEdge(Edges.get(60));
		Edges.get(59).addNearbyEdge(Edges.get(64));
		Edges.get(59).addNearbyNode(Nodes.get(41));
		Edges.get(59).addNearbyNode(Nodes.get(45));
		//60
		Edges.get(60).addNearbyEdge(Edges.get(52));
		Edges.get(60).addNearbyEdge(Edges.get(59));
		Edges.get(60).addNearbyEdge(Edges.get(61));
		Edges.get(60).addNearbyEdge(Edges.get(65));
		Edges.get(60).addNearbyNode(Nodes.get(41));
		Edges.get(60).addNearbyNode(Nodes.get(46));
		//61
		Edges.get(61).addNearbyEdge(Edges.get(53));
		Edges.get(61).addNearbyEdge(Edges.get(60));
		Edges.get(61).addNearbyEdge(Edges.get(65));
		Edges.get(61).addNearbyNode(Nodes.get(42));
		Edges.get(61).addNearbyNode(Nodes.get(46));
		//62
		Edges.get(62).addNearbyEdge(Edges.get(54));
		Edges.get(62).addNearbyEdge(Edges.get(55));
		Edges.get(62).addNearbyEdge(Edges.get(66));
		Edges.get(62).addNearbyNode(Nodes.get(43));
		Edges.get(62).addNearbyNode(Nodes.get(47));
		//63
		Edges.get(63).addNearbyEdge(Edges.get(56));
		Edges.get(63).addNearbyEdge(Edges.get(57));
		Edges.get(63).addNearbyEdge(Edges.get(67));
		Edges.get(63).addNearbyEdge(Edges.get(68));
		Edges.get(63).addNearbyNode(Nodes.get(44));
		Edges.get(63).addNearbyNode(Nodes.get(48));
		//64
		Edges.get(64).addNearbyEdge(Edges.get(58));
		Edges.get(64).addNearbyEdge(Edges.get(59));
		Edges.get(64).addNearbyEdge(Edges.get(69));
		Edges.get(64).addNearbyEdge(Edges.get(70));
		Edges.get(64).addNearbyNode(Nodes.get(45));
		Edges.get(64).addNearbyNode(Nodes.get(49));
		//65 
		Edges.get(65).addNearbyEdge(Edges.get(60));
		Edges.get(65).addNearbyEdge(Edges.get(61));
		Edges.get(65).addNearbyEdge(Edges.get(71));
		Edges.get(65).addNearbyNode(Nodes.get(46));
		Edges.get(65).addNearbyNode(Nodes.get(50));
		//66
		Edges.get(66).addNearbyEdge(Edges.get(62));
		Edges.get(66).addNearbyEdge(Edges.get(67));
		Edges.get(66).addNearbyNode(Nodes.get(47));
		Edges.get(66).addNearbyNode(Nodes.get(51));
		//67
		Edges.get(67).addNearbyEdge(Edges.get(63));
		Edges.get(67).addNearbyEdge(Edges.get(66));
		Edges.get(67).addNearbyEdge(Edges.get(68));
		Edges.get(67).addNearbyNode(Nodes.get(48));
		Edges.get(67).addNearbyNode(Nodes.get(51));
		//68
		Edges.get(68).addNearbyEdge(Edges.get(63));
		Edges.get(68).addNearbyEdge(Edges.get(67));
		Edges.get(68).addNearbyEdge(Edges.get(69));
		Edges.get(68).addNearbyNode(Nodes.get(48));
		Edges.get(68).addNearbyNode(Nodes.get(52));
		//69
		Edges.get(69).addNearbyEdge(Edges.get(64));
		Edges.get(69).addNearbyEdge(Edges.get(68));
		Edges.get(69).addNearbyEdge(Edges.get(70));
		Edges.get(69).addNearbyNode(Nodes.get(49));
		Edges.get(69).addNearbyNode(Nodes.get(52));
		//70
		Edges.get(70).addNearbyEdge(Edges.get(64));
		Edges.get(70).addNearbyEdge(Edges.get(69));
		Edges.get(70).addNearbyEdge(Edges.get(71));
		Edges.get(70).addNearbyNode(Nodes.get(49));
		Edges.get(70).addNearbyNode(Nodes.get(53));
		//71
		Edges.get(71).addNearbyEdge(Edges.get(70));
		Edges.get(71).addNearbyEdge(Edges.get(65));
		Edges.get(71).addNearbyNode(Nodes.get(50));
		Edges.get(71).addNearbyNode(Nodes.get(53));
		
		
		//the following code sets the Nearby Edges and Nodes and Hexes for the Nodes of the Map////////////
		//0
		Nodes.get(0).addNearbyEdge(Edges.get(0));
		Nodes.get(0).addNearbyEdge(Edges.get(1));
		Nodes.get(0).addNearbyNode(Nodes.get(3));
		Nodes.get(0).addNearbyNode(Nodes.get(4));
		Nodes.get(0).addNearbyHex(Hexes.get(0));
		//1
		Nodes.get(1).addNearbyEdge(Edges.get(2));
		Nodes.get(1).addNearbyEdge(Edges.get(3));
		Nodes.get(1).addNearbyNode(Nodes.get(4));
		Nodes.get(1).addNearbyNode(Nodes.get(5));
		Nodes.get(1).addNearbyHex(Hexes.get(1));
		//2
		Nodes.get(2).addNearbyEdge(Edges.get(4));
		Nodes.get(2).addNearbyEdge(Edges.get(5));
		Nodes.get(2).addNearbyNode(Nodes.get(5));
		Nodes.get(2).addNearbyNode(Nodes.get(6));
		Nodes.get(2).addNearbyHex(Hexes.get(2));
		//3
		Nodes.get(3).addNearbyEdge(Edges.get(0));
		Nodes.get(3).addNearbyEdge(Edges.get(6));
		Nodes.get(3).addNearbyNode(Nodes.get(0));
		Nodes.get(3).addNearbyNode(Nodes.get(7));
		Nodes.get(3).addNearbyHex(Hexes.get(0));
		//4
		Nodes.get(4).addNearbyEdge(Edges.get(1));
		Nodes.get(4).addNearbyEdge(Edges.get(2));
		Nodes.get(4).addNearbyEdge(Edges.get(7));
		Nodes.get(4).addNearbyNode(Nodes.get(0));
		Nodes.get(4).addNearbyNode(Nodes.get(1));
		Nodes.get(4).addNearbyNode(Nodes.get(8));
		Nodes.get(4).addNearbyHex(Hexes.get(0));
		Nodes.get(4).addNearbyHex(Hexes.get(1));
		//5
		Nodes.get(5).addNearbyEdge(Edges.get(3));
		Nodes.get(5).addNearbyEdge(Edges.get(4));
		Nodes.get(5).addNearbyEdge(Edges.get(8));
		Nodes.get(5).addNearbyNode(Nodes.get(1));
		Nodes.get(5).addNearbyNode(Nodes.get(2));
		Nodes.get(5).addNearbyNode(Nodes.get(9));
		Nodes.get(5).addNearbyHex(Hexes.get(1));
		Nodes.get(5).addNearbyHex(Hexes.get(2));
		//6
		Nodes.get(6).addNearbyEdge(Edges.get(5));
		Nodes.get(6).addNearbyEdge(Edges.get(9));
		Nodes.get(6).addNearbyNode(Nodes.get(2));
		Nodes.get(6).addNearbyNode(Nodes.get(10));
		Nodes.get(6).addNearbyHex(Hexes.get(2));
		//7
		Nodes.get(7).addNearbyEdge(Edges.get(10));
		Nodes.get(7).addNearbyEdge(Edges.get(11));
		Nodes.get(7).addNearbyEdge(Edges.get(6));
		Nodes.get(7).addNearbyNode(Nodes.get(3));
		Nodes.get(7).addNearbyNode(Nodes.get(11));
		Nodes.get(7).addNearbyNode(Nodes.get(12));
		Nodes.get(7).addNearbyHex(Hexes.get(0));
		Nodes.get(7).addNearbyHex(Hexes.get(3));
		//8
		Nodes.get(8).addNearbyEdge(Edges.get(7));
		Nodes.get(8).addNearbyEdge(Edges.get(12));
		Nodes.get(8).addNearbyEdge(Edges.get(13));
		Nodes.get(8).addNearbyNode(Nodes.get(4));
		Nodes.get(8).addNearbyNode(Nodes.get(12));
		Nodes.get(8).addNearbyNode(Nodes.get(13));
		Nodes.get(8).addNearbyHex(Hexes.get(0));
		Nodes.get(8).addNearbyHex(Hexes.get(4));
		Nodes.get(8).addNearbyHex(Hexes.get(1));
		//9
		Nodes.get(9).addNearbyEdge(Edges.get(8));
		Nodes.get(9).addNearbyEdge(Edges.get(14));
		Nodes.get(9).addNearbyEdge(Edges.get(15));
		Nodes.get(9).addNearbyNode(Nodes.get(5));
		Nodes.get(9).addNearbyNode(Nodes.get(13));
		Nodes.get(9).addNearbyNode(Nodes.get(14));
		Nodes.get(9).addNearbyHex(Hexes.get(5));
		Nodes.get(9).addNearbyHex(Hexes.get(1));
		Nodes.get(9).addNearbyHex(Hexes.get(2));
		//10
		Nodes.get(10).addNearbyEdge(Edges.get(9));
		Nodes.get(10).addNearbyEdge(Edges.get(16));
		Nodes.get(10).addNearbyEdge(Edges.get(17));
		Nodes.get(10).addNearbyNode(Nodes.get(6));
		Nodes.get(10).addNearbyNode(Nodes.get(14));
		Nodes.get(10).addNearbyNode(Nodes.get(15));
		Nodes.get(10).addNearbyHex(Hexes.get(2));
		Nodes.get(10).addNearbyHex(Hexes.get(6));
		//11
		Nodes.get(11).addNearbyEdge(Edges.get(10));
		Nodes.get(11).addNearbyEdge(Edges.get(18));
		Nodes.get(11).addNearbyNode(Nodes.get(7));
		Nodes.get(11).addNearbyNode(Nodes.get(16));
		Nodes.get(11).addNearbyHex(Hexes.get(3));
		//12
		Nodes.get(12).addNearbyEdge(Edges.get(11));
		Nodes.get(12).addNearbyEdge(Edges.get(12));
		Nodes.get(12).addNearbyEdge(Edges.get(19));
		Nodes.get(12).addNearbyNode(Nodes.get(7));
		Nodes.get(12).addNearbyNode(Nodes.get(8));
		Nodes.get(12).addNearbyNode(Nodes.get(17));
		Nodes.get(12).addNearbyHex(Hexes.get(0));
		Nodes.get(12).addNearbyHex(Hexes.get(3));
		Nodes.get(12).addNearbyHex(Hexes.get(4));
		//13
		Nodes.get(13).addNearbyEdge(Edges.get(13));
		Nodes.get(13).addNearbyEdge(Edges.get(14));
		Nodes.get(13).addNearbyEdge(Edges.get(20));
		Nodes.get(13).addNearbyNode(Nodes.get(8));
		Nodes.get(13).addNearbyNode(Nodes.get(9));
		Nodes.get(13).addNearbyNode(Nodes.get(18));
		Nodes.get(13).addNearbyHex(Hexes.get(5));
		Nodes.get(13).addNearbyHex(Hexes.get(4));
		Nodes.get(13).addNearbyHex(Hexes.get(1));
		//14
		Nodes.get(14).addNearbyEdge(Edges.get(15));
		Nodes.get(14).addNearbyEdge(Edges.get(16));
		Nodes.get(14).addNearbyEdge(Edges.get(21));
		Nodes.get(14).addNearbyNode(Nodes.get(9));
		Nodes.get(14).addNearbyNode(Nodes.get(10));
		Nodes.get(14).addNearbyNode(Nodes.get(19));
		Nodes.get(14).addNearbyHex(Hexes.get(2));
		Nodes.get(14).addNearbyHex(Hexes.get(5));
		Nodes.get(14).addNearbyHex(Hexes.get(6));
		//15
		Nodes.get(15).addNearbyEdge(Edges.get(17));
		Nodes.get(15).addNearbyEdge(Edges.get(22));
		Nodes.get(15).addNearbyNode(Nodes.get(10));
		Nodes.get(15).addNearbyNode(Nodes.get(20));
		Nodes.get(15).addNearbyHex(Hexes.get(6));
		//16
		Nodes.get(16).addNearbyEdge(Edges.get(18));
		Nodes.get(16).addNearbyEdge(Edges.get(23));
		Nodes.get(16).addNearbyEdge(Edges.get(24));
		Nodes.get(16).addNearbyNode(Nodes.get(11));
		Nodes.get(16).addNearbyNode(Nodes.get(21));
		Nodes.get(16).addNearbyNode(Nodes.get(22));
		Nodes.get(16).addNearbyHex(Hexes.get(3));
		Nodes.get(16).addNearbyHex(Hexes.get(7));
		//17
		Nodes.get(17).addNearbyEdge(Edges.get(19));
		Nodes.get(17).addNearbyEdge(Edges.get(25));
		Nodes.get(17).addNearbyEdge(Edges.get(26));
		Nodes.get(17).addNearbyNode(Nodes.get(12));
		Nodes.get(17).addNearbyNode(Nodes.get(22));
		Nodes.get(17).addNearbyNode(Nodes.get(23));
		Nodes.get(17).addNearbyHex(Hexes.get(3));
		Nodes.get(17).addNearbyHex(Hexes.get(4));
		Nodes.get(17).addNearbyHex(Hexes.get(8));
		//18
		Nodes.get(18).addNearbyEdge(Edges.get(20));
		Nodes.get(18).addNearbyEdge(Edges.get(27));
		Nodes.get(18).addNearbyEdge(Edges.get(28));
		Nodes.get(18).addNearbyNode(Nodes.get(13));
		Nodes.get(18).addNearbyNode(Nodes.get(23));
		Nodes.get(18).addNearbyNode(Nodes.get(24));
		Nodes.get(18).addNearbyHex(Hexes.get(4));
		Nodes.get(18).addNearbyHex(Hexes.get(5));
		Nodes.get(18).addNearbyHex(Hexes.get(9));
		//19
		Nodes.get(19).addNearbyEdge(Edges.get(21));
		Nodes.get(19).addNearbyEdge(Edges.get(29));
		Nodes.get(19).addNearbyEdge(Edges.get(30));
		Nodes.get(19).addNearbyNode(Nodes.get(14));
		Nodes.get(19).addNearbyNode(Nodes.get(24));
		Nodes.get(19).addNearbyNode(Nodes.get(25));
		Nodes.get(19).addNearbyHex(Hexes.get(5));
		Nodes.get(19).addNearbyHex(Hexes.get(6));
		Nodes.get(19).addNearbyHex(Hexes.get(10));
		//20
		Nodes.get(20).addNearbyEdge(Edges.get(22));
		Nodes.get(20).addNearbyEdge(Edges.get(31));
		Nodes.get(20).addNearbyEdge(Edges.get(32));
		Nodes.get(20).addNearbyNode(Nodes.get(15));
		Nodes.get(20).addNearbyNode(Nodes.get(25));
		Nodes.get(20).addNearbyNode(Nodes.get(26));
		Nodes.get(20).addNearbyHex(Hexes.get(6));
		Nodes.get(20).addNearbyHex(Hexes.get(11));
		//21
		Nodes.get(21).addNearbyEdge(Edges.get(23));
		Nodes.get(21).addNearbyEdge(Edges.get(33));
		Nodes.get(21).addNearbyNode(Nodes.get(16));
		Nodes.get(21).addNearbyNode(Nodes.get(27));
		Nodes.get(21).addNearbyHex(Hexes.get(7));
		//22
		Nodes.get(22).addNearbyEdge(Edges.get(24));
		Nodes.get(22).addNearbyEdge(Edges.get(25));
		Nodes.get(22).addNearbyEdge(Edges.get(34));
		Nodes.get(22).addNearbyNode(Nodes.get(16));
		Nodes.get(22).addNearbyNode(Nodes.get(17));
		Nodes.get(22).addNearbyNode(Nodes.get(28));
		Nodes.get(22).addNearbyHex(Hexes.get(3));
		Nodes.get(22).addNearbyHex(Hexes.get(7));
		Nodes.get(22).addNearbyHex(Hexes.get(8));
		//23
		Nodes.get(23).addNearbyEdge(Edges.get(26));
		Nodes.get(23).addNearbyEdge(Edges.get(27));
		Nodes.get(23).addNearbyEdge(Edges.get(35));
		Nodes.get(23).addNearbyNode(Nodes.get(17));
		Nodes.get(23).addNearbyNode(Nodes.get(18));
		Nodes.get(23).addNearbyNode(Nodes.get(29));
		Nodes.get(23).addNearbyHex(Hexes.get(4));
		Nodes.get(23).addNearbyHex(Hexes.get(8));
		Nodes.get(23).addNearbyHex(Hexes.get(9));
		//24
		Nodes.get(24).addNearbyEdge(Edges.get(28));
		Nodes.get(24).addNearbyEdge(Edges.get(29));
		Nodes.get(24).addNearbyEdge(Edges.get(36));
		Nodes.get(24).addNearbyNode(Nodes.get(18));
		Nodes.get(24).addNearbyNode(Nodes.get(19));
		Nodes.get(24).addNearbyNode(Nodes.get(30));
		Nodes.get(24).addNearbyHex(Hexes.get(9));
		Nodes.get(24).addNearbyHex(Hexes.get(10));
		Nodes.get(24).addNearbyHex(Hexes.get(5));
		//25
		Nodes.get(25).addNearbyEdge(Edges.get(30));
		Nodes.get(25).addNearbyEdge(Edges.get(31));
		Nodes.get(25).addNearbyEdge(Edges.get(37));
		Nodes.get(25).addNearbyNode(Nodes.get(19));
		Nodes.get(25).addNearbyNode(Nodes.get(20));
		Nodes.get(25).addNearbyNode(Nodes.get(31));
		Nodes.get(25).addNearbyHex(Hexes.get(6));
		Nodes.get(25).addNearbyHex(Hexes.get(10));
		Nodes.get(25).addNearbyHex(Hexes.get(11));
		//26
		Nodes.get(26).addNearbyEdge(Edges.get(32));
		Nodes.get(26).addNearbyEdge(Edges.get(38));
		Nodes.get(26).addNearbyNode(Nodes.get(20));
		Nodes.get(26).addNearbyNode(Nodes.get(32));
		Nodes.get(26).addNearbyHex(Hexes.get(11));
		//27
		Nodes.get(27).addNearbyEdge(Edges.get(39));
		Nodes.get(27).addNearbyEdge(Edges.get(33));
		Nodes.get(27).addNearbyNode(Nodes.get(21));
		Nodes.get(27).addNearbyNode(Nodes.get(33));
		Nodes.get(27).addNearbyHex(Hexes.get(7));
		//28
		Nodes.get(28).addNearbyEdge(Edges.get(34));
		Nodes.get(28).addNearbyEdge(Edges.get(40));
		Nodes.get(28).addNearbyEdge(Edges.get(41));
		Nodes.get(28).addNearbyNode(Nodes.get(22));
		Nodes.get(28).addNearbyNode(Nodes.get(33));
		Nodes.get(28).addNearbyNode(Nodes.get(34));
		Nodes.get(28).addNearbyHex(Hexes.get(7));
		Nodes.get(28).addNearbyHex(Hexes.get(8));
		Nodes.get(28).addNearbyHex(Hexes.get(12));
		//29
		Nodes.get(29).addNearbyEdge(Edges.get(35));
		Nodes.get(29).addNearbyEdge(Edges.get(42));
		Nodes.get(29).addNearbyEdge(Edges.get(43));
		Nodes.get(29).addNearbyNode(Nodes.get(23));
		Nodes.get(29).addNearbyNode(Nodes.get(34));
		Nodes.get(29).addNearbyNode(Nodes.get(35));
		Nodes.get(29).addNearbyHex(Hexes.get(8));
		Nodes.get(29).addNearbyHex(Hexes.get(9));
		Nodes.get(29).addNearbyHex(Hexes.get(13));
		//30
		Nodes.get(30).addNearbyEdge(Edges.get(36));
		Nodes.get(30).addNearbyEdge(Edges.get(44));
		Nodes.get(30).addNearbyEdge(Edges.get(45));
		Nodes.get(30).addNearbyNode(Nodes.get(24));
		Nodes.get(30).addNearbyNode(Nodes.get(35));
		Nodes.get(30).addNearbyNode(Nodes.get(36));
		Nodes.get(30).addNearbyHex(Hexes.get(9));
		Nodes.get(30).addNearbyHex(Hexes.get(10));
		Nodes.get(30).addNearbyHex(Hexes.get(14));
		//31
		Nodes.get(31).addNearbyEdge(Edges.get(37));
		Nodes.get(31).addNearbyEdge(Edges.get(46));
		Nodes.get(31).addNearbyEdge(Edges.get(47));
		Nodes.get(31).addNearbyNode(Nodes.get(24));
		Nodes.get(31).addNearbyNode(Nodes.get(35));
		Nodes.get(31).addNearbyNode(Nodes.get(36));
		Nodes.get(31).addNearbyHex(Hexes.get(10));
		Nodes.get(31).addNearbyHex(Hexes.get(11));
		Nodes.get(31).addNearbyHex(Hexes.get(15));
		//32
		Nodes.get(32).addNearbyEdge(Edges.get(38));
		Nodes.get(32).addNearbyEdge(Edges.get(48));
		Nodes.get(32).addNearbyNode(Nodes.get(26));
		Nodes.get(32).addNearbyNode(Nodes.get(37));
		Nodes.get(32).addNearbyHex(Hexes.get(11));
		//33
		Nodes.get(33).addNearbyEdge(Edges.get(39));
		Nodes.get(33).addNearbyEdge(Edges.get(40));
		Nodes.get(33).addNearbyEdge(Edges.get(49));
		Nodes.get(33).addNearbyNode(Nodes.get(27));
		Nodes.get(33).addNearbyNode(Nodes.get(28));
		Nodes.get(33).addNearbyNode(Nodes.get(38));
		Nodes.get(33).addNearbyHex(Hexes.get(7));
		Nodes.get(33).addNearbyHex(Hexes.get(12));
		//34
		Nodes.get(34).addNearbyEdge(Edges.get(41));
		Nodes.get(34).addNearbyEdge(Edges.get(42));
		Nodes.get(34).addNearbyEdge(Edges.get(50));
		Nodes.get(34).addNearbyNode(Nodes.get(28));
		Nodes.get(34).addNearbyNode(Nodes.get(29));
		Nodes.get(34).addNearbyNode(Nodes.get(39));
		Nodes.get(34).addNearbyHex(Hexes.get(8));
		Nodes.get(34).addNearbyHex(Hexes.get(12));
		Nodes.get(34).addNearbyHex(Hexes.get(13));
		//35
		Nodes.get(35).addNearbyEdge(Edges.get(43));
		Nodes.get(35).addNearbyEdge(Edges.get(44));
		Nodes.get(35).addNearbyEdge(Edges.get(51));
		Nodes.get(35).addNearbyNode(Nodes.get(29));
		Nodes.get(35).addNearbyNode(Nodes.get(30));
		Nodes.get(35).addNearbyNode(Nodes.get(40));
		Nodes.get(35).addNearbyHex(Hexes.get(9));
		Nodes.get(35).addNearbyHex(Hexes.get(13));
		Nodes.get(35).addNearbyHex(Hexes.get(14));
		//36
		Nodes.get(36).addNearbyEdge(Edges.get(45));
		Nodes.get(36).addNearbyEdge(Edges.get(46));
		Nodes.get(36).addNearbyEdge(Edges.get(52));
		Nodes.get(36).addNearbyNode(Nodes.get(30));
		Nodes.get(36).addNearbyNode(Nodes.get(31));
		Nodes.get(36).addNearbyNode(Nodes.get(41));
		Nodes.get(36).addNearbyHex(Hexes.get(10));
		Nodes.get(36).addNearbyHex(Hexes.get(14));
		Nodes.get(36).addNearbyHex(Hexes.get(15));
		//37
		Nodes.get(37).addNearbyEdge(Edges.get(47));
		Nodes.get(37).addNearbyEdge(Edges.get(48));
		Nodes.get(37).addNearbyEdge(Edges.get(53));
		Nodes.get(37).addNearbyNode(Nodes.get(31));
		Nodes.get(37).addNearbyNode(Nodes.get(32));
		Nodes.get(37).addNearbyNode(Nodes.get(42));
		Nodes.get(37).addNearbyHex(Hexes.get(11));
		Nodes.get(37).addNearbyHex(Hexes.get(15));
		//38
		Nodes.get(38).addNearbyEdge(Edges.get(49));
		Nodes.get(38).addNearbyEdge(Edges.get(54));
		Nodes.get(38).addNearbyNode(Nodes.get(33));
		Nodes.get(38).addNearbyNode(Nodes.get(43));
		Nodes.get(38).addNearbyHex(Hexes.get(12));
		//39
		Nodes.get(39).addNearbyEdge(Edges.get(50));
		Nodes.get(39).addNearbyEdge(Edges.get(55));
		Nodes.get(39).addNearbyEdge(Edges.get(56));
		Nodes.get(39).addNearbyNode(Nodes.get(34));
		Nodes.get(39).addNearbyNode(Nodes.get(43));
		Nodes.get(39).addNearbyNode(Nodes.get(44));
		Nodes.get(39).addNearbyHex(Hexes.get(12));
		Nodes.get(39).addNearbyHex(Hexes.get(13));
		Nodes.get(39).addNearbyHex(Hexes.get(16));
		//40
		Nodes.get(40).addNearbyEdge(Edges.get(51));
		Nodes.get(40).addNearbyEdge(Edges.get(57));
		Nodes.get(40).addNearbyEdge(Edges.get(58));
		Nodes.get(40).addNearbyNode(Nodes.get(35));
		Nodes.get(40).addNearbyNode(Nodes.get(44));
		Nodes.get(40).addNearbyNode(Nodes.get(45));
		Nodes.get(40).addNearbyHex(Hexes.get(13));
		Nodes.get(40).addNearbyHex(Hexes.get(14));
		Nodes.get(40).addNearbyHex(Hexes.get(17));
		//41
		Nodes.get(41).addNearbyEdge(Edges.get(52));
		Nodes.get(41).addNearbyEdge(Edges.get(59));
		Nodes.get(41).addNearbyEdge(Edges.get(60));
		Nodes.get(41).addNearbyNode(Nodes.get(36));
		Nodes.get(41).addNearbyNode(Nodes.get(45));
		Nodes.get(41).addNearbyNode(Nodes.get(46));
		Nodes.get(41).addNearbyHex(Hexes.get(14));
		Nodes.get(41).addNearbyHex(Hexes.get(15));
		Nodes.get(41).addNearbyHex(Hexes.get(18));
		//42
		Nodes.get(42).addNearbyEdge(Edges.get(53));
		Nodes.get(42).addNearbyEdge(Edges.get(61));
		Nodes.get(42).addNearbyNode(Nodes.get(37));
		Nodes.get(42).addNearbyNode(Nodes.get(46));
		Nodes.get(42).addNearbyHex(Hexes.get(15));
		//43
		Nodes.get(43).addNearbyEdge(Edges.get(54));
		Nodes.get(43).addNearbyEdge(Edges.get(55));
		Nodes.get(43).addNearbyEdge(Edges.get(62));
		Nodes.get(43).addNearbyNode(Nodes.get(38));
		Nodes.get(43).addNearbyNode(Nodes.get(39));
		Nodes.get(43).addNearbyNode(Nodes.get(47));
		Nodes.get(43).addNearbyHex(Hexes.get(12));
		Nodes.get(43).addNearbyHex(Hexes.get(16));
		//44
		Nodes.get(44).addNearbyEdge(Edges.get(56));
		Nodes.get(44).addNearbyEdge(Edges.get(57));
		Nodes.get(44).addNearbyEdge(Edges.get(63));
		Nodes.get(44).addNearbyNode(Nodes.get(39));
		Nodes.get(44).addNearbyNode(Nodes.get(40));
		Nodes.get(44).addNearbyNode(Nodes.get(48));
		Nodes.get(44).addNearbyHex(Hexes.get(13));
		Nodes.get(44).addNearbyHex(Hexes.get(16));
		Nodes.get(44).addNearbyHex(Hexes.get(17));
		//45
		Nodes.get(45).addNearbyEdge(Edges.get(58));
		Nodes.get(45).addNearbyEdge(Edges.get(59));
		Nodes.get(45).addNearbyEdge(Edges.get(64));
		Nodes.get(45).addNearbyNode(Nodes.get(40));
		Nodes.get(45).addNearbyNode(Nodes.get(41));
		Nodes.get(45).addNearbyNode(Nodes.get(49));
		Nodes.get(45).addNearbyHex(Hexes.get(14));
		Nodes.get(45).addNearbyHex(Hexes.get(17));
		Nodes.get(45).addNearbyHex(Hexes.get(18));
		//46
		Nodes.get(46).addNearbyEdge(Edges.get(60));
		Nodes.get(46).addNearbyEdge(Edges.get(61));
		Nodes.get(46).addNearbyEdge(Edges.get(65));
		Nodes.get(46).addNearbyNode(Nodes.get(41));
		Nodes.get(46).addNearbyNode(Nodes.get(42));
		Nodes.get(46).addNearbyNode(Nodes.get(50));
		Nodes.get(46).addNearbyHex(Hexes.get(15));
		Nodes.get(46).addNearbyHex(Hexes.get(18));
		//47
		Nodes.get(47).addNearbyEdge(Edges.get(62));
		Nodes.get(47).addNearbyEdge(Edges.get(66));
		Nodes.get(47).addNearbyNode(Nodes.get(43));
		Nodes.get(47).addNearbyNode(Nodes.get(51));
		Nodes.get(47).addNearbyHex(Hexes.get(16));
		//48
		Nodes.get(48).addNearbyEdge(Edges.get(63));
		Nodes.get(48).addNearbyEdge(Edges.get(67));
		Nodes.get(48).addNearbyEdge(Edges.get(68));
		Nodes.get(48).addNearbyNode(Nodes.get(44));
		Nodes.get(48).addNearbyNode(Nodes.get(51));
		Nodes.get(48).addNearbyNode(Nodes.get(52));
		Nodes.get(48).addNearbyHex(Hexes.get(16));
		Nodes.get(48).addNearbyHex(Hexes.get(17));
		//49
		Nodes.get(49).addNearbyEdge(Edges.get(64));
		Nodes.get(49).addNearbyEdge(Edges.get(69));
		Nodes.get(49).addNearbyEdge(Edges.get(70));
		Nodes.get(49).addNearbyNode(Nodes.get(45));
		Nodes.get(49).addNearbyNode(Nodes.get(52));
		Nodes.get(49).addNearbyNode(Nodes.get(53));
		Nodes.get(49).addNearbyHex(Hexes.get(18));
		Nodes.get(49).addNearbyHex(Hexes.get(17));
		//50
		Nodes.get(50).addNearbyEdge(Edges.get(65));
		Nodes.get(50).addNearbyEdge(Edges.get(71));
		Nodes.get(50).addNearbyNode(Nodes.get(46));
		Nodes.get(50).addNearbyNode(Nodes.get(53));
		Nodes.get(50).addNearbyHex(Hexes.get(18));
		//51
		Nodes.get(51).addNearbyEdge(Edges.get(67));
		Nodes.get(51).addNearbyEdge(Edges.get(66));
		Nodes.get(51).addNearbyNode(Nodes.get(47));
		Nodes.get(51).addNearbyNode(Nodes.get(48));
		Nodes.get(51).addNearbyHex(Hexes.get(16));
		//52
		Nodes.get(52).addNearbyEdge(Edges.get(68));
		Nodes.get(52).addNearbyEdge(Edges.get(69));
		Nodes.get(52).addNearbyNode(Nodes.get(48));
		Nodes.get(52).addNearbyNode(Nodes.get(49));
		Nodes.get(52).addNearbyHex(Hexes.get(17));
		//53
		Nodes.get(53).addNearbyEdge(Edges.get(70));
		Nodes.get(53).addNearbyEdge(Edges.get(71));
		Nodes.get(53).addNearbyNode(Nodes.get(49));
		Nodes.get(53).addNearbyNode(Nodes.get(50));
		Nodes.get(53).addNearbyHex(Hexes.get(18));
		
		//0
		Hexes.get(0).addNearbyEdge(Edges.get(0));
		Hexes.get(0).addNearbyEdge(Edges.get(1));
		Hexes.get(0).addNearbyEdge(Edges.get(6));
		Hexes.get(0).addNearbyEdge(Edges.get(7));
		Hexes.get(0).addNearbyEdge(Edges.get(11));
		Hexes.get(0).addNearbyEdge(Edges.get(12));
		//1
		Hexes.get(1).addNearbyEdge(Edges.get(2));
		Hexes.get(1).addNearbyEdge(Edges.get(3));
		Hexes.get(1).addNearbyEdge(Edges.get(7));
		Hexes.get(1).addNearbyEdge(Edges.get(8));
		Hexes.get(1).addNearbyEdge(Edges.get(13));
		Hexes.get(1).addNearbyEdge(Edges.get(14));
		//2
		Hexes.get(2).addNearbyEdge(Edges.get(4));
		Hexes.get(2).addNearbyEdge(Edges.get(5));
		Hexes.get(2).addNearbyEdge(Edges.get(8));
		Hexes.get(2).addNearbyEdge(Edges.get(9));
		Hexes.get(2).addNearbyEdge(Edges.get(15));
		Hexes.get(2).addNearbyEdge(Edges.get(16));
		//3
		Hexes.get(3).addNearbyEdge(Edges.get(10));
		Hexes.get(3).addNearbyEdge(Edges.get(11));
		Hexes.get(3).addNearbyEdge(Edges.get(18));
		Hexes.get(3).addNearbyEdge(Edges.get(19));
		Hexes.get(3).addNearbyEdge(Edges.get(24));
		Hexes.get(3).addNearbyEdge(Edges.get(25));
		//4
		Hexes.get(4).addNearbyEdge(Edges.get(12));
		Hexes.get(4).addNearbyEdge(Edges.get(13));
		Hexes.get(4).addNearbyEdge(Edges.get(19));
		Hexes.get(4).addNearbyEdge(Edges.get(20));
		Hexes.get(4).addNearbyEdge(Edges.get(26));
		Hexes.get(4).addNearbyEdge(Edges.get(27));
		//5
		Hexes.get(5).addNearbyEdge(Edges.get(14));
		Hexes.get(5).addNearbyEdge(Edges.get(15));
		Hexes.get(5).addNearbyEdge(Edges.get(20));
		Hexes.get(5).addNearbyEdge(Edges.get(21));
		Hexes.get(5).addNearbyEdge(Edges.get(28));
		Hexes.get(5).addNearbyEdge(Edges.get(29));
		//6
		Hexes.get(6).addNearbyEdge(Edges.get(16));
		Hexes.get(6).addNearbyEdge(Edges.get(17));
		Hexes.get(6).addNearbyEdge(Edges.get(21));
		Hexes.get(6).addNearbyEdge(Edges.get(22));
		Hexes.get(6).addNearbyEdge(Edges.get(30));
		Hexes.get(6).addNearbyEdge(Edges.get(31));
		//7
		Hexes.get(7).addNearbyEdge(Edges.get(23));
		Hexes.get(7).addNearbyEdge(Edges.get(24));
		Hexes.get(7).addNearbyEdge(Edges.get(33));
		Hexes.get(7).addNearbyEdge(Edges.get(34));
		Hexes.get(7).addNearbyEdge(Edges.get(39));
		Hexes.get(7).addNearbyEdge(Edges.get(40));
		//8
		Hexes.get(8).addNearbyEdge(Edges.get(25));
		Hexes.get(8).addNearbyEdge(Edges.get(26));
		Hexes.get(8).addNearbyEdge(Edges.get(34));
		Hexes.get(8).addNearbyEdge(Edges.get(35));
		Hexes.get(8).addNearbyEdge(Edges.get(41));
		Hexes.get(8).addNearbyEdge(Edges.get(42));
		//9
		Hexes.get(9).addNearbyEdge(Edges.get(27));
		Hexes.get(9).addNearbyEdge(Edges.get(28));
		Hexes.get(9).addNearbyEdge(Edges.get(35));
		Hexes.get(9).addNearbyEdge(Edges.get(36));
		Hexes.get(9).addNearbyEdge(Edges.get(43));
		Hexes.get(9).addNearbyEdge(Edges.get(44));
		//10
		Hexes.get(10).addNearbyEdge(Edges.get(29));
		Hexes.get(10).addNearbyEdge(Edges.get(30));
		Hexes.get(10).addNearbyEdge(Edges.get(36));
		Hexes.get(10).addNearbyEdge(Edges.get(37));
		Hexes.get(10).addNearbyEdge(Edges.get(45));
		Hexes.get(10).addNearbyEdge(Edges.get(46));
		//11
		Hexes.get(11).addNearbyEdge(Edges.get(31));
		Hexes.get(11).addNearbyEdge(Edges.get(32));
		Hexes.get(11).addNearbyEdge(Edges.get(37));
		Hexes.get(11).addNearbyEdge(Edges.get(38));
		Hexes.get(11).addNearbyEdge(Edges.get(47));
		Hexes.get(11).addNearbyEdge(Edges.get(48));
		//12
		Hexes.get(12).addNearbyEdge(Edges.get(40));
		Hexes.get(12).addNearbyEdge(Edges.get(41));
		Hexes.get(12).addNearbyEdge(Edges.get(49));
		Hexes.get(12).addNearbyEdge(Edges.get(50));
		Hexes.get(12).addNearbyEdge(Edges.get(54));
		Hexes.get(12).addNearbyEdge(Edges.get(55));
		//13
		Hexes.get(13).addNearbyEdge(Edges.get(42));
		Hexes.get(13).addNearbyEdge(Edges.get(43));
		Hexes.get(13).addNearbyEdge(Edges.get(50));
		Hexes.get(13).addNearbyEdge(Edges.get(51));
		Hexes.get(13).addNearbyEdge(Edges.get(56));
		Hexes.get(13).addNearbyEdge(Edges.get(57));
		//14
		Hexes.get(14).addNearbyEdge(Edges.get(44));
		Hexes.get(14).addNearbyEdge(Edges.get(45));
		Hexes.get(14).addNearbyEdge(Edges.get(51));
		Hexes.get(14).addNearbyEdge(Edges.get(52));
		Hexes.get(14).addNearbyEdge(Edges.get(58));
		Hexes.get(14).addNearbyEdge(Edges.get(59));
		//15
		Hexes.get(15).addNearbyEdge(Edges.get(46));
		Hexes.get(15).addNearbyEdge(Edges.get(47));
		Hexes.get(15).addNearbyEdge(Edges.get(52));
		Hexes.get(15).addNearbyEdge(Edges.get(53));
		Hexes.get(15).addNearbyEdge(Edges.get(60));
		Hexes.get(15).addNearbyEdge(Edges.get(61));
		//16
		Hexes.get(16).addNearbyEdge(Edges.get(55));
		Hexes.get(16).addNearbyEdge(Edges.get(56));
		Hexes.get(16).addNearbyEdge(Edges.get(62));
		Hexes.get(16).addNearbyEdge(Edges.get(63));
		Hexes.get(16).addNearbyEdge(Edges.get(66));
		Hexes.get(16).addNearbyEdge(Edges.get(67));
		//17
		Hexes.get(17).addNearbyEdge(Edges.get(57));
		Hexes.get(17).addNearbyEdge(Edges.get(58));
		Hexes.get(17).addNearbyEdge(Edges.get(63));
		Hexes.get(17).addNearbyEdge(Edges.get(64));
		Hexes.get(17).addNearbyEdge(Edges.get(68));
		Hexes.get(17).addNearbyEdge(Edges.get(69));
		//18
		Hexes.get(18).addNearbyEdge(Edges.get(59));
		Hexes.get(18).addNearbyEdge(Edges.get(60));
		Hexes.get(18).addNearbyEdge(Edges.get(64));
		Hexes.get(18).addNearbyEdge(Edges.get(65));
		Hexes.get(18).addNearbyEdge(Edges.get(70));
		Hexes.get(18).addNearbyEdge(Edges.get(71));
		
		// The following code adds the corresponding nodes to the Harbor Nodes Lists
		this.H1Nodes.add(this.getNode(0));		
		this.H1Nodes.add(this.getNode(3));
		this.H2Nodes.add(this.getNode(1));
		this.H2Nodes.add(this.getNode(5));
		this.H3Nodes.add(this.getNode(10));
		this.H3Nodes.add(this.getNode(15));
		this.H4Nodes.add(this.getNode(26));
		this.H4Nodes.add(this.getNode(32));
		this.H5Nodes.add(this.getNode(42));
		this.H5Nodes.add(this.getNode(46));
		this.H6Nodes.add(this.getNode(49));
		this.H6Nodes.add(this.getNode(52));
		this.H7Nodes.add(this.getNode(51));
		this.H7Nodes.add(this.getNode(47));
		this.H8Nodes.add(this.getNode(33));
		this.H8Nodes.add(this.getNode(38));
		this.H9Nodes.add(this.getNode(11));
		this.H9Nodes.add(this.getNode(16));
	}// end of constructor.  Entire game map should have been randomly generated

	//methods
	public Node getNode(int i) {	//note, there are 54 nodes, but the index is 0-53
		return this.Nodes.get(i);
	}
	
	public Edge getEdge(int i) {	//note, there are 72 edges, but the index is 0-71
		return this.Edges.get(i);
	}
	public Hex getHex(int i){		//note there are 19 hexes, but the index is 0-18
		return this.Hexes.get(i);
	}
	
	public void addHex(Hex h) {
		this.Hexes.add(h);
	}
	
	public void addEdge(Edge e) {
		this.Edges.add(e);
	}
	
	public void addNode(Node n) {
		this.Nodes.add(n);
	}
	
	public void addH1Node(Node n) {
		this.H1Nodes.add(n);
	}
	
	public void addH2Node(Node n) {
		this.H1Nodes.add(n);
	}
	
	public void addH3Node(Node n) {
		this.H1Nodes.add(n);
	}
	
	public void addH4Node(Node n) {
		this.H1Nodes.add(n);
	}
	
	public void addH5Node(Node n) {
		this.H1Nodes.add(n);
	}
	
	public void addH6Node(Node n) {
		this.H1Nodes.add(n);
	}
	
	public void addH7Node(Node n) {
		this.H1Nodes.add(n);
	}
	
	public void addH8Node(Node n) {
		this.H1Nodes.add(n);
	}
	
	public void addH9Node(Node n) {
		this.H1Nodes.add(n);
	}
	
	public ArrayList<Node> getH1Nodes() {
		return H1Nodes;
	}
	
	public ArrayList<Node> getH2Nodes() {
		return H2Nodes;
	}
	
	public ArrayList<Node> getH3Nodes() {
		return H3Nodes;
	}
	
	public ArrayList<Node> getH4Nodes() {
		return H4Nodes;
	}
	
	public ArrayList<Node> getH5Nodes() {
		return H5Nodes;
	}
	
	public ArrayList<Node> getH6Nodes() {
		return H6Nodes;
	}
	
	public ArrayList<Node> getH7Nodes() {
		return H7Nodes;
	}
	
	public ArrayList<Node> getH8Nodes() {
		return H8Nodes;
	}
	
	public ArrayList<Node> getH9Nodes() {
		return H9Nodes;
	}
	
	public ArrayList<Node> getNodes() {
		return Nodes;
	}
	
	public ArrayList<Edge> getEdges() {
		return Edges;
	}
	
	public ArrayList<Hex> getHexes() {
		return Hexes;
	}
	
	public int getRobberHex() {
		return RobberHex;
	}
	
	public void setRobberHex (int i) {//i is the hex that the robber is being moved to
		this.Hexes.get(RobberHex).setRobberStatus(false);//removes robber from original hex
		this.Hexes.get(i).setRobberStatus(true); //sets the robber status of the hex at i to true
		this.RobberHex = i;//sets the new hex as the current robberHex
	}
	
	public int getH1Rate() {
		return H1Rate;
	}
	
	public int getH2Rate() {
		return H2Rate;
	}
	
	public int getH3Rate() {
		return H3Rate;
	}
	
	public int getH4Rate() {
		return H4Rate;
	}
	
	public int getH5Rate() {
		return H5Rate;
	}
	
	public int getH6Rate() {
		return H6Rate;
	}
	
	public int getH7Rate() {
		return H7Rate;
	}
	
	public int getH8Rate() {
		return H8Rate;
	}
	
	public int getH9Rate() {
		return H9Rate;
	}
	
	public String getH1Resource() {
		return H1Resource;
	}
	
	public String getH2Resource() {
		return H2Resource;
	}
	
	public String getH3Resource() {
		return H3Resource;
	}
	
	public String getH4Resource() {
		return H4Resource;
	}
	
	public String getH5Resource() {
		return H5Resource;
	}
	
	public String getH6Resource() {
		return H6Resource;
	}
	
	public String getH7Resource() {
		return H7Resource;
	}
	
	public String getH8Resource() {
		return H8Resource;
	}
	
	public String getH9Resource() {
		return H9Resource;
	}
	
	
}
