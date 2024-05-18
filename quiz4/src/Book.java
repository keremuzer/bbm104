public class Book extends Item {
    private String author;

    public Book(String name, String author, int barcode, double price) {
        super(name, barcode, price);
        this.author = author;
    }

    @Override
    public String toString() {
        return "Author of the " + getName() + " is " + author + ". Its barcode is " + getBarcode() + " and its price is " + getPrice();
    }
}
