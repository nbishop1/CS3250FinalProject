
public class Card {
	public enum Suit { HEARTS, DIAMONDS, CLUBS, SPADES }
	private Suit suit;
	private String cardRank;
	private int value;
	
	// default constructor
	public Card(Suit suit, String cardRank, int value) {
		this.suit = suit;
		this.cardRank = cardRank;
		this.value = value;
	}
	
	// setters and getters
	public Suit getSuit() {
		return suit;
	}
	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	public String getCardRank() {
		return cardRank;
	}
	public void setCardRank(String cardRank) {
		this.cardRank = cardRank;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
