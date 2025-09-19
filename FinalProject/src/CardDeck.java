import java.util.ArrayList;

public class CardDeck {
	private ArrayList<Card> cards;
	
	// default constructor
	public CardDeck() {
		this.cards = new ArrayList<>();
		
		// set up standard 52 card deck later
	}
	
	// setters and getters
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	// method signatures
	
	public void shuffle() {
		// implement shuffling the card deck
	}
	
	public Card draw() {
		return null;// if there are no more cards
	}
	
	public void reset() {
		// recreate a card deck
	}

}
