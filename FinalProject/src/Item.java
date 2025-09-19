
public class Item {
	private String name;
	private int quantity;
	
	// default constructors
	public Item( String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	
	// setters and getters
	public String getName() {
		return name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

}
