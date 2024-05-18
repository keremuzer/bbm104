/**
 * Book class is a subclass of Item class. It has an author attribute.
 * It has a constructor to initialize the attributes.
 * It has a toString method to return the information of the book.
 */
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
