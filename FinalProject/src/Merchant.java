import java.util.HashMap;

public class Merchant {
    private HashMap<String, Integer> itemStock; // item name -> quantity
    private HashMap<String, Integer> itemPrices; // item name -> price

    public Merchant() {
        itemStock = new HashMap<>();
        itemPrices = new HashMap<>();
    }

    public void addItem(String name, int quantity, int price) {
        itemStock.put(name, quantity);
        itemPrices.put(name, price);
    }

    public boolean buyItem(Player player, String name, int quantity) {
        if (itemStock.containsKey(name) && itemStock.get(name) >= quantity) {
            int totalPrice = itemPrices.get(name) * quantity;
            if (player.getSupplies().getCoin() >= totalPrice) {
                player.getSupplies().spendCoin(totalPrice);
                player.getInventory().add(new Item(name, quantity));
                itemStock.put(name, itemStock.get(name) - quantity);
                return true;
            }
        }
        return false;
    }

    public HashMap<String, Integer> getItemStock() {
        return itemStock;
    }

    public HashMap<String, Integer> getItemPrices() {
        return itemPrices;
    }
}
