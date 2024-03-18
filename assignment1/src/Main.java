import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Product> products = readInput("Product_1.txt");
        GymMealMachine machine = new GymMealMachine();
        fill(products, machine);

        for (int i = 0; i < 6; i++) {

            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 10; k++) {
                    System.out.println(machine.getSlots()[i][j].getProducts()[k]);
                }
            }
        }
    }


    public static ArrayList<Product> readInput(String path) {
        String[] lines = FileIO.readFile(path, true, true);
        ArrayList<Product> products = new ArrayList<>();

        assert lines != null;
        for (String line : lines) {
            String[] parts = line.split("\t");
            String name = parts[0];
            double price = Double.parseDouble(parts[1]);
            double protein = Double.parseDouble(parts[2].split(" ")[0]);
            double carb = Double.parseDouble(parts[2].split(" ")[1]);
            double fat = Double.parseDouble(parts[2].split(" ")[2]);
            Product product = new Product(name, price, protein, carb, fat);
            products.add(product);
        }
        return products;
    }

    public static int fill(ArrayList<Product> products, GymMealMachine machine) {
        for (Product product : products) {

            if (!machine.addToMachine(product)) {
                System.out.println("INFO: The machine is full!");
                return -1;
            }
        }
        return 0;
    }
}