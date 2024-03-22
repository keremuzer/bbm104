/**
 * Product class represents a product in the GMM.
 * It has a name, price, protein, carb, fat, and calorie.
 */
public class Product {
    private final String name;
    private final int price;
    private final double protein;
    private final double carb;
    private final double fat;
    private int calorie;

    public Product(String name, int price, double protein, double carb, double fat) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
        this.calorie = (int) Math.round(4 * protein + 4 * carb + 9 * fat);
    }

    public String getName() {
        return name;
    }

    public int getCalorie() {
        return calorie;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarb() {
        return carb;
    }

    public double getFat() {
        return fat;
    }

    public int getPrice() {
        return price;
    }
}
