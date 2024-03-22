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

    public void addProduct(Product product) {
        products[numProducts] = product;
        numProducts++;
    }

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
