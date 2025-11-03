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
		hand.clear();
		playerHand.clear();
		// Deal two cards to player and dealer
		playerHand.addCard(deck.draw());
		hand.addCard(deck.draw());
		playerHand.addCard(deck.draw());
		hand.addCard(deck.draw());
	}

	public void dealersTurn(CardDeck deck) {
		// Dealer draws until hand value is 17 or more
		while (hand.getBestValue() < 17) {
			hand.addCard(deck.draw());
		}
	}
}