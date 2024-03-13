public class Product {
    private String name;
    private double price;
    private double protein;
    private double carb;
    private double fat;
    private double calorie;

    public Product(String name, double price, double protein, double carb, double fat) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
        this.calorie = 4 * protein + 4 * carb + 9 * fat;
    }
}
