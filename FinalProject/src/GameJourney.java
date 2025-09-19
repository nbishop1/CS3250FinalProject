
public class GameJourney {
	private Player player;
	private List<Town> towns;
	private int day;
	private boolean gameOver;
	
	public GameJourney(Player player) {
		this.player = player;
		this.towns = new ArrayList<>();
		this.day = 0;
		this.gameOver = false;
	}
	
	// Getters and setters here
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public List<Town> getTowns() {
		return towns;
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	public boolean gameOver() {
		return gameOver;
	}
	
	// Methods here
	
	public void travelToNextTown() {
		// TODO Advance the day with a possible event
		// After possible event or no event enter new
		// town and start BlackJack game
	}
	
	public void startJourney() {
		// TODO Start the loop for the journey
	}
	
	public void endJourney() {
		gameOver = true;
	}

}
