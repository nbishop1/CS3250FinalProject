import java.util.ArrayList;

public class GeneralStore {
	private ArrayList<String> itemNames;
	private ArrayList<Integer> itemPrices;
	
	// default constructor
	public GeneralStore() {
		setItemNames(new ArrayList<>());
		setItemPrices(new ArrayList<>());
		
		// Can use add item function to add default items
	}
	
	
	// setters and getters
	public ArrayList<String> getItemNames() {
		return itemNames;
	}

	public void setItemNames(ArrayList<String> itemNames) {
		this.itemNames = itemNames;
	}

	public ArrayList<Integer> getItemPrices() {
		return itemPrices;
	}

	public void setItemPrices(ArrayList<Integer> itemPrices) {
		this.itemPrices = itemPrices;
	}
	
	// methods
	
	public void addItem(String name, int price) {
		itemNames.add(name);
		itemPrices.add(price);
	}
	
	public int getPrice(String name) {
		// use for loop to get price of given item, return price if found
		return -1; // if item isn't found
	}
	
	public boolean buy(Player player, String itemName, int quantity) {
		return false; // if item not found or not enough coins, otherwise return true
	}
	
	public void displayItems() {
		// display the available items using a for loop
	}

}
