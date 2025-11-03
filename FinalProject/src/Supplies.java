public class Supplies {
	private int food;
	private int medicine;
	private int spareParts;
	private int water;
	private int ammo;
	private int coin;
	
	// default constructor
	public Supplies() {
		this.setFood(0);
		this.setMedicine(0);
		this.setSpareParts(0);
		this.setWater(0);
		this.setAmmo(0);
		this.setCoin(0);
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

	public int getWater() { return water; }
	public void setWater(int water) { this.water = water; }
	public int getAmmo() { return ammo; }
	public void setAmmo(int ammo) { this.ammo = ammo; }
	public int getCoin() { return coin; }
	public void setCoin(int coin) { this.coin = coin; }
	
	// method signatures
	
	public void eatFood(int amount) {
		food = Math.max(0, food - amount);
	}
	public void drinkWater(int amount) {
		water = Math.max(0, water - amount);
	}
	public void useMedicine(int amount) {
		medicine = Math.max(0, medicine - amount);
	}
	public void useAmmo(int amount) {
		ammo = Math.max(0, ammo - amount);
	}
	public void spendCoin(int amount) {
		coin = Math.max(0, coin - amount);
	}
	public void addSupplies(int food, int med, int parts, int water, int ammo, int coin) {
		this.food += food;
		this.medicine += med;
		this.spareParts += parts;
		this.water += water;
		this.ammo += ammo;
		this.coin += coin;
	}
	public void addCoin(int amount) {
	    this.coin += amount;
	}
}