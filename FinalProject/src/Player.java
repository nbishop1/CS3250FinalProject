import java.util.ArrayList;

public class Player extends FamilyMember {
    private Supplies supplies;
    private ArrayList<Item> inventory;

    // default constructor
    public Player(String name) {
        super(name, 4, 3, 7); // Player: 1 food/4 days, 1 water/3 days
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

    // methods
    public boolean buyItem(Item item, int cost) {
        if (supplies.getCoin() < cost) {
            return false; // Not enough coins
        }
        supplies.spendCoin(cost);
        // Check if item already exists in inventory
        for (Item invItem : inventory) {
            if (invItem.getName().equalsIgnoreCase(item.getName())) {
                invItem.setQuantity(invItem.getQuantity() + item.getQuantity());
                return true;
            }
        }
        // Item not found, add new
        inventory.add(new Item(item.getName(), item.getQuantity()));
        return true;
    }

    public boolean sellItem(Item item, int price) {
        for (int i = 0; i < inventory.size(); i++) {
            Item invItem = inventory.get(i);
            if (invItem.getName().equalsIgnoreCase(item.getName())) {
                if (invItem.getQuantity() >= item.getQuantity()) {
                    invItem.setQuantity(invItem.getQuantity() - item.getQuantity());
                    supplies.setCoin(supplies.getCoin() + price);
                    if (invItem.getQuantity() == 0) {
                        inventory.remove(i);
                    }
                    return true;
                }
            }
        }
        return false; // Item not found or not enough quantity
    }
}