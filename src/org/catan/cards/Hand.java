package org.catan.cards;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cards;
	private int KnightsUsed;
	private Deck deck;
    
	public Hand() {
        cards = new ArrayList<Card>();
        KnightsUsed = 0;
        deck = new Deck();
    }
	
	public void getDeck(Deck deck) {
		this.deck = deck;
	}
	
	public String getCardRule(int i) {
		//The function will select ith card in array 
		//then read the string
	if (this.cards.size() > i) {// if the card index is in the hand
		String rule = this.cards.get(i).getRule();
		return rule;
	}
	return "There is no card";
	}
	
	public String useCardAndDiscard(int i) {
		//The function will show string
		//Then it will get rid of card
	if(this.cards.size() > i) {
		String rule = this.cards.get(i).getRule();
		this.cards.remove(i);	
		return rule;
	}
	return "There is no card";
	}		
	
	public void addCard () {
		
		this.cards.add(deck.getCard());
		deck.removeCard();
	}
	
	
}