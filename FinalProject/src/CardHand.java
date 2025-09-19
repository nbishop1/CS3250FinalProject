import java.util.ArrayList;

public class CardHand {
	private ArrayList<Card> cards;
	
	
	// default constructor
	public CardHand() {
		cards = new ArrayList<>();
	}
	
	//getters (no setters needed)
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	// method signatures
	
	public void addCard(Card c) {
		cards.add(c);
	}
	
	public int getBestValue() {
		// compute blackjack best value such as ace = 1 or 11
		return 0;
	}
	
	public boolean isBuse() {
		return getBestValue() > 21;
	}
	
	public void clear() {
		cards.clear();
	}
	

}
