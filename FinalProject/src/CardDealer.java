
public class CardDealer {
	private CardHand hand;
	
	// default constructor
	public CardDealer() {
		this.hand = new CardHand();
	}

	// setters and getters
	public CardHand getHand() {
		return hand;
	}

	public void setHand(CardHand hand) {
		this.hand = hand;
	}

}
