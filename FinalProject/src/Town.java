import java.util.ArrayList;

public class Town {
	private String name;
	private GeneralStore store;
	private BlackJackGame blackjackGame;
	private ArrayList<Event> events;
	
	// default constructor
	public Town(String name, GeneralStore store, BlackJackGame blackjackGame) {
		this.setName(name);
		this.store = store;
		this.blackjackGame = blackjackGame;
	}
	
	
	// setters and getters
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public GeneralStore getStore() {
		return store;
	}

	public BlackJackGame getBlackjackGame() {
		return blackjackGame;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}
	
	// method signatures
	
	public void enterTown(Player player) {
		// show store and allow new blackjack game
	}
	
	public void visitStore(Player player) {
		// open the store for the player
	}
	
	public void playBlackjack(Player player) {
		// play the blackjack game for the player
	}

}
