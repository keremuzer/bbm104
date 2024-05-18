/**
 * Stationery class is a subclass of Item class.
 * It has a constructor to initialize the attributes.
 * It has a toString method to return the attributes.
 */
public class Stationery extends Item {
    private String kind;

    public Stationery(String name, String kind, int barcode, double price) {
        super(name, barcode, price);
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Kind of the " + getName() + " is " + kind + ". Its barcode is " + getBarcode() + " and its price is " + getPrice();
    }
}
