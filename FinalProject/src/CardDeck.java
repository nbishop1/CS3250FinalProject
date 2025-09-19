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

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

}
