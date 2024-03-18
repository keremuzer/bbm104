public class Slot {
    private Product[] products;
    private int numProducts;

    public Slot() {
        products = new Product[10];
    }

    public void addProduct(Product product) {
        products[numProducts] = product;
        numProducts++;
    }

    public int getNumProducts() {
        return numProducts;
    }

    public Product[] getProducts() {
        return products;
    }
}
