
public class GameJourney {
	private int day;
	private boolean gameOver;
	
	// default constructor
	public GameJourney() {
		this.day = 1;
		this.gameOver = false;
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

}
