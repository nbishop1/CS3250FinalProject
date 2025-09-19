
public class CardDealer {
	private CardHand hand;
	
	// default constructor
	public CardDealer() {
		this.hand = new CardHand();
	}

	// getters (no setters needed)
	public CardHand getHand() {
		return hand;
	}
	
	// method signatures
	public void startDeal(CardDeck deck, CardHand playerHand) {
		// deal two cards to player and dealer at blackjack game start
	}
	
	public void dealersTurn(CardDeck deck) {
		// deal a single card if dealer's hand is < 17
	}


}
