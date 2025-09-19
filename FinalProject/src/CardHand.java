import java.util.ArrayList;

public class CardHand {
	private ArrayList<Card> cards;
	
	
	// default constructor
	public CardHand() {
		cards = new ArrayList<>();
	}
	
	//getters and setters
	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	

}
