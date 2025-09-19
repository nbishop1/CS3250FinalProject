import java.util.ArrayList;

public class GameJourney {
	private int day;
	private boolean gameOver;
	private Player player;
	private ArrayList<Town> towns;
	
	// default constructor
	public GameJourney(Player player) {
		this.day = 1;
		this.gameOver = false;
		this.player = player;
		this.towns = new ArrayList<>();
		
	}
	// Getters and setters here
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	public boolean gameOver() {
		return gameOver;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Town> getTowns() {
		return towns;
	}

	public void setTowns(ArrayList<Town> towns) {
		this.towns = towns;
	}

}
