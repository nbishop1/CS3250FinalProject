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


	public void setStore(GeneralStore store) {
		this.store = store;
	}


	public BlackJackGame getBlackjackGame() {
		return blackjackGame;
	}


	public void setBlackjackGame(BlackJackGame blackjackGame) {
		this.blackjackGame = blackjackGame;
	}


	public ArrayList<Event> getEvents() {
		return events;
	}


	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

}
