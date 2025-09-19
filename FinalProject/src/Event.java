
public class Event {
	private String description;
	private int impact;
	
	// default constructor
	
	public Event(String description, int impact) {
		this.description = description;
		this.impact = impact;
	}
	
	
	// getters (no setters needed)
	public String getDescription() {
		return description;
	}

	public int getImpact() {
		return impact;
	}
	
	// method signatures
	
	public void applyTo(Player player) {
		// change the state of the player
	}


}
