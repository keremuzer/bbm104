/**
 * Item class is an abstract class that represents an item in the store.
 * It has three attributes: name, barcode, and price.
 * It has a constructor that initializes the attributes.
 * It has three getter methods to get the name, barcode, and price of the item.
 */
public abstract class Item {
    private String name;
    private int barcode;
    private double price;

    public Item(String name, int barcode, double price) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }
}
