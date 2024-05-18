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
