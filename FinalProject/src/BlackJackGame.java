
public class BlackJackGame {
	private CardDeck deck;
	private CardDealer dealer;
	
	// default constructor
	public BlackJackGame() {
		this.deck = new CardDeck();
		this.dealer = new CardDealer();
	}
	
	// getters and setters
	public CardDeck getDeck() {
		return deck;
	}
	public void setDeck(CardDeck deck) {
		this.deck = deck;
	}
	public CardDealer getDealer() {
		return dealer;
	}
	public void setDealer(CardDealer dealer) {
		this.dealer = dealer;
	}
	

}
