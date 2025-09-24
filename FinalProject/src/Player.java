import java.util.ArrayList;

public class Player {
	private String name;
	private int coin;
	private int health;
	private Supplies supplies;
	private ArrayList<Item> inventory;
	
	// default constructor
	public Player(String name) {
		this.name = name;
		this.coin = 20;
		this.health = 100;
		this.supplies = new Supplies();
		this.inventory = new ArrayList<>();
	}
	
	// getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	public Supplies getSupplies() {
		return supplies;
	}

	public void setSupplies(Supplies supplies) {
		this.supplies = supplies;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
	// method signatures
	public void buyItem(Item item, int cost) {
		// subtract spent coins and add append item into inventory array
	}
	
	public void sellItem(Item item, int price) {
		// remove item from array and increase coins
	}
	
	public void eventEffect(Event event) {
		// update health and supplies based on what event occurs
	}

}
