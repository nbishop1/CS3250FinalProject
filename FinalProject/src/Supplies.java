
public class Supplies {
	private int food;
	private int medicine;
	private int spareParts;
	
	// default constructor
	public Supplies() {
		this.setFood(0);
		this.setMedicine(0);
		this.setSpareParts(0);
	}

	// setters and getters
	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getMedicine() {
		return medicine;
	}

	public void setMedicine(int medicine) {
		this.medicine = medicine;
	}

	public int getSpareParts() {
		return spareParts;
	}

	public void setSpareParts(int spareParts) {
		this.spareParts = spareParts;
	}
	
	// method signatures
	
	public void eatFood(int amount) {
		// decrement food supply
	}
	
	public void addSupplies(int food, int med, int parts) {
		// add amounts
	}
}
