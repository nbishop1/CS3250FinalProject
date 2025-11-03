public class BlackJackGame {
	private CardDeck deck;
	private CardDealer dealer;
	
	// default constructor
	public BlackJackGame() {
		this.deck = new CardDeck();
		this.dealer = new CardDealer();
	}
	
	// getters (no setters needed)
	public CardDeck getDeck() {
		return deck;
	}
	
	public CardDealer getDealer() {
		return dealer;
	}

	// method signatures
	
	public void playFor(Player player) {
		deck.reset();
		CardHand playerHand = new CardHand();
		int bet = placeBet(player, 1); // For now, always bet 1 coin
		if (bet == 0) return; // Not enough coins
		dealer.startDeal(deck, playerHand);
		// Player turn: hit until 17 or higher (auto for now)
		while (playerHand.getBestValue() < 17 && !playerHand.isBust()) {
			playerHand.addCard(deck.draw());
		}
		// Dealer turn
		dealer.dealersTurn(deck);
		// End round
		roundEnd(player, playerHand, dealer.getHand(), bet);
	}
	
	public int placeBet(Player player, int amount) {
		if (player.getSupplies().getCoin() < amount) return 0;
		player.getSupplies().spendCoin(amount);
		return amount;
	}
	
	public void roundEnd(Player player, CardHand playerHand, CardHand dealerHand, int bet) {
		int playerValue = playerHand.getBestValue();
		int dealerValue = dealerHand.getBestValue();
		if (playerHand.isBust()) {
			// Player busts, loses bet
			return;
		} else if (dealerHand.isBust() || playerValue > dealerValue) {
			// Player wins, pays 2x bet
			player.getSupplies().addCoin(bet * 2);
		} else if (playerValue == dealerValue) {
			// Push, return bet
			player.getSupplies().addCoin(bet);
		} // else: dealer wins, player loses bet
	}
}