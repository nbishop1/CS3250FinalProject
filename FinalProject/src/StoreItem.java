public class StoreItem {
    private String name;
    private int price;
    private int stock;

    public StoreItem(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public void decreaseStock(int amount) { this.stock = Math.max(0, this.stock - amount); }
}
