
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
		// start turn loop
		// 	1. dealer deals hand
		// 2. player hits or stands
		// 3. player calls or increases bet
		// 4. repeat
	}
	
	public int placeBet(Player player, int amount) {
		// player validates their bet and coins are subtracted from playable amount
		return 0;
	}
	
	public void roundEnd(Player player, CardHand playerHand, CardHand dealerHand, int bet) {
		// payout to the player or the house 
	}
	

}
