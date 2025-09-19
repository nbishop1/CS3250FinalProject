
public class Event {
	private String description;
	private int impact;
	
	// default constructor
	
	public Event(String description, int impact) {
		this.description = description;
		this.impact = impact;
	}
	
	
	// setters and getters
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getImpact() {
		return impact;
	}
	public void setImpact(int impact) {
		this.impact = impact;
	}

}
