package org.catan.cards;

public class Card {
	protected String rule;
	
	public Card() {
        rule = "";
    }
	
	public String getRule() {return this.rule;}
	
	public void setRule(String rule) {
		this.rule = rule;
	}	
}
