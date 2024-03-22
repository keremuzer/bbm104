/**
 * Slot class represents a slot in the vending machine. Each slot can hold up to 10 products.
 */
public class Slot {
    private final Product[] products;
    private int numProducts;

    public Slot() {
        products = new Product[10];
        for (int i = 0; i < 10; i++) {
            products[i] = new Product("", 0, 0, 0, 0);

        }
        numProducts = 0;
    }

    /**
     * Adds the given product to the slot.
     *
     * @param product Product that is going to be added to the slot.
     */
    public void addProduct(Product product) {
        products[numProducts] = product;
        numProducts++;
    }

    /**
     * Removes the last product from the slot.
     */
    public void removeProduct() {
        products[numProducts - 1] = new Product("", 0, 0, 0, 0);
        numProducts--;
    }

    public int getNumProducts() {
        return numProducts;
    }

    public Product[] getProducts() {
        return products;
    }

}
