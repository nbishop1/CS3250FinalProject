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
	
	

}
