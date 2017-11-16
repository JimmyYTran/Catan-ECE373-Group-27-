package org.catan.cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deck;	
	
	public Deck(){
        deck = new ArrayList<Card>();
    }
	
	public void initializeDeck() {
		for(int i = 0; i < 14; i++) {// Adds 14 Knight cards
			this.deck.add(new Knight());
		}
		for(int i = 0; i < 2; i++) { // Road Building 2
			this.deck.add(new RoadBuilding());
		}
		for(int i = 0; i < 2; i++) { 	// Monopoly 2
			this.deck.add(new Monopoly());
		}
		for(int i = 0; i < 2; i++) { // Year of Plenty 2
			this.deck.add(new YearofPlenty());
		}
		for(int i = 0; i < 5; i++) { // Victory Point 5
			this.deck.add(new VictoryPoint());
		}
		
		Collections.shuffle(deck); //shuffle deck
	}
	public Card getCard(){		
		return this.deck.get(0);
	}
	
	public void removeCard(){
		this.deck.remove(0);
	}
	public ArrayList<Card> getDeck() {
		return this.deck;
	}
}
