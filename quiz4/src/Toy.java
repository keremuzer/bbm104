public class Toy extends Item {
    private String color;

    public Toy(String name, String color, int barcode, double price) {
        super(name, barcode, price);
        this.color = color;
    }

    @Override
    public String toString() {
        return "Color of the " + getName() + " is " + color + ". Its barcode is " + getBarcode() + " and its price is " + getPrice() + ".";
    }
}
