import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GeneralStore {
    private ArrayList<Item> items;
    private HashMap<String, Integer> itemPrices;
    private int storeId;

    public GeneralStore(int storeId) {
        this.storeId = storeId;
        items = new ArrayList<>();
        itemPrices = new HashMap<>();
        Random rand = new Random();
        // Food: 2 coins, 5-20 units
        items.add(new Item("Food", rand.nextInt(16) + 5));
        itemPrices.put("Food", 2);
        // Water: 2 coins, 5-20 units
        items.add(new Item("Water", rand.nextInt(16) + 5));
        itemPrices.put("Water", 2);
        // Ammo: 10 + 10*storeId coins, 2 units
        items.add(new Item("Ammo", 2));
        itemPrices.put("Ammo", 10 + 10 * storeId);
        // Medicine: 5 + 5*storeId coins, 2 units
        items.add(new Item("Medicine", 2));
        itemPrices.put("Medicine", 5 + 5 * storeId);
        // SpareParts: 20 + 20*storeId coins, 1 unit
        items.add(new Item("SpareParts", 1));
        itemPrices.put("SpareParts", 20 + 20 * storeId);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public int getPrice(String name) {
        return itemPrices.getOrDefault(name, -1);
    }

    public int getStock(String name) {
        Item item = getItem(name);
        return item != null ? item.getQuantity() : 0;
    }

    // Buy logic: checks stock and coins, updates player supplies and store stock
    public boolean buy(Player player, String itemName, int quantity) {
        Item item = getItem(itemName);
        int price = getPrice(itemName);
        if (item == null || item.getQuantity() < quantity) return false;
        int totalCost = price * quantity;
        if (player.getSupplies().getCoin() < totalCost) return false;
        // Update player supplies
        switch (itemName.toLowerCase()) {
            case "food":
                player.getSupplies().setFood(player.getSupplies().getFood() + quantity);
                break;
            case "water":
                player.getSupplies().setWater(player.getSupplies().getWater() + quantity);
                break;
            case "ammo":
                player.getSupplies().setAmmo(player.getSupplies().getAmmo() + quantity);
                break;
            case "medicine":
                player.getSupplies().setMedicine(player.getSupplies().getMedicine() + quantity);
                break;
            case "spareparts":
                player.getSupplies().setSpareParts(player.getSupplies().getSpareParts() + quantity);
                break;
            default:
                return false;
        }
        // Deduct coins and decrease store stock
        player.getSupplies().spendCoin(totalCost);
        item.setQuantity(item.getQuantity() - quantity);
        return true;
    }

    // Display items, prices, and stock
    public void displayItems() {
        System.out.println("General Store Inventory:");
        for (Item item : items) {
            int price = getPrice(item.getName());
            System.out.printf("%s - Price: %d coins, Stock: %d\n", item.getName(), price, item.getQuantity());
        }
    }
}