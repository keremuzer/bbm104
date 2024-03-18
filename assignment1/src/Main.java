

public class Main {
    public static void main(String[] args) {
        String[] lines = FileIO.readFile("src/Sample_IO_Files_Pack_1/Product_1.txt", true, true);

        assert lines != null;
        for (String line : lines) {
            String[] parts = line.split("\t");
            String name = parts[0];
            double price = Double.parseDouble(parts[1]);
            double protein = Double.parseDouble(parts[2].split(" ")[0]);
            double carb = Double.parseDouble(parts[2].split(" ")[1]);
            double fat = Double.parseDouble(parts[2].split(" ")[2]);
            Product product = new Product(name, price, protein, carb, fat);
            System.out.println(product);
        }
    }
    
}