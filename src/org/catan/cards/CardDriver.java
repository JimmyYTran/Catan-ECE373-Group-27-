package org.catan.cards;

public class CardDriver {
	public static void main (String[] args) {
		Deck d1 = new Deck();
		Hand h1 = new Hand();
		Knight k1 = new Knight();//this is the first type of card put in the deck
		d1.initializeDeck();//adds all the cards then shuffles
		System.out.println("The deck size starts at: " + d1.getDeck().size());//shows the deck of size 25
		System.out.println("Rule for Knight Card: " + k1.getRule());// show rule of knight for compare
		System.out.println("Rule for a random Card: " + d1.getDeck().get(0).getRule());// should change every time run test
		System.out.println("\n");
		
		int i = 0;
		for (i = 0; i < 25; ++i) {
			h1.getDeck(d1);//add deck to hand
			h1.addCard();//add a card
			System.out.println("Drawing card from deck and using it...");
			System.out.println(h1.useCardAndDiscard(0));//use a card and show rule
			System.out.println(h1.getCardRule(0));//show that the used card is gone
			System.out.println("Cards remaining: " + d1.getDeck().size());//should decrease to zero
			System.out.println("\n");
		}
	}
}
