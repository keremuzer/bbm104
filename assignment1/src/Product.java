
public class Product {
    private final String name;
    private final double price;
    private final double protein;
    private final double carb;
    private final double fat;
    private double calorie;

    public Product(String name, double price, double protein, double carb, double fat) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
        this.calorie = 4 * protein + 4 * carb + 9 * fat;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "" + name;
    }
}
