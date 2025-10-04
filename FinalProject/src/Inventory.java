import java.util.HashMap;

public class Inventory {
    private HashMap<String, Item> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(String name, int quantity) {
        if (items.containsKey(name)) {
            items.get(name).setQuantity(items.get(name).getQuantity() + quantity);
        } else {
            items.put(name, new Item(name, quantity));
        }
    }

    public boolean removeItem(String name, int quantity) {
        if (items.containsKey(name)) {
            Item item = items.get(name);
            if (item.getQuantity() >= quantity) {
                item.setQuantity(item.getQuantity() - quantity);
                if (item.getQuantity() == 0) items.remove(name);
                return true;
            }
        }
        return false;
    }

    public int getQuantity(String name) {
        if (items.containsKey(name)) {
            return items.get(name).getQuantity();
        }
        return 0;
    }

    public boolean hasItem(String name, int quantity) {
        return getQuantity(name) >= quantity;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }
}
