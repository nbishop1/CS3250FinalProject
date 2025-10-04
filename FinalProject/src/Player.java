import java.util.ArrayList;

public class Player extends FamilyMember {
    private Supplies supplies;
    private ArrayList<Item> inventory;

    // default constructor
    public Player(String name) {
        super(name, 4, 3, 7); // Player: 1 food/4 days, 1 water/3 days, 1 medicine/7 days
        this.supplies = new Supplies();
        this.supplies.setFood(10); // Starting inventory
        this.supplies.setWater(15);
        this.supplies.setMedicine(0);
        this.supplies.setSpareParts(0);
        this.supplies.setAmmo(2);
        this.supplies.setCoin(20);
        this.inventory = new ArrayList<>();
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
        // subtract spent coins and add item into inventory array
    }

    public void sellItem(Item item, int price) {
        // remove item from array and increase coins
    }

    public void eventEffect(Event event) {
        // update health and supplies based on what event occurs
    }
}