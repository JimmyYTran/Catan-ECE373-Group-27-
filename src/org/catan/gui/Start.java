package org.catan.gui;
import java.util.ArrayList;

import org.catan.cards.*;
import org.catan.map.*;
import org.catan.players.*;


public class Start {
	//private ArrayList<Player> players;
	
	public Start() {
		// players = new ArrayList<Player>();
	}
	
	//When implemented into the GUI this will ask for number of players and then names for players
	//Those will be used for inputs
	public static ArrayList<Player> createPlayers (int number, ArrayList<String> names) {
		ArrayList<Player>  players = new ArrayList<Player>();
		for (int i = 0; i < number; i++ ) {
			players.add(new Player());
			players.get(i).setName(names.get(i));
		}
		return players;
	}
	
	
	
	
	
	
	
}