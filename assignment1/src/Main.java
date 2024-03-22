import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        GymMealMachine machine = new GymMealMachine();
        fill(machine, args[0], args[2]);
        machine.printGMM(args[2]);
        purchase(machine, args[1], args[2]);
        machine.printGMM(args[2]);
    }

    private static ArrayList<Product> readInput(String path) {
        String[] lines = FileIO.readFile(path, true, true);
        ArrayList<Product> products = new ArrayList<>();

        assert lines != null;
        for (String line : lines) {
            String[] parts = line.split("\t");
            String name = parts[0];
            int price = Integer.parseInt(parts[1]);
            double protein = Double.parseDouble(parts[2].split(" ")[0]);
            double carb = Double.parseDouble(parts[2].split(" ")[1]);
            double fat = Double.parseDouble(parts[2].split(" ")[2]);
            Product product = new Product(name, price, protein, carb, fat);
            products.add(product);
        }
        return products;
    }

    private static int fill(GymMealMachine machine, String inputPath, String outputPath) {
        ArrayList<Product> products = readInput(inputPath);
        for (Product product : products) {

            if (!machine.addToMachine(product)) {
                FileIO.writeToFile(outputPath, "INFO: There is no available place to put " + product.getName(), true, true);
                if (machine.isFull()) {
                    FileIO.writeToFile(outputPath, "INFO: The machine is full!", true, true);
                    return -1;
                }
            }
        }
        return 0;
    }

    private static void purchase(GymMealMachine machine, String inputPath, String outputPath) {
        String[] lines = FileIO.readFile(inputPath, true, true);

        assert lines != null;
        for (String line : lines) {
            String[] parts = line.split("\t");
            int money = Arrays.stream(parts[1].split(" ")).mapToInt(Integer::parseInt).sum();
            String choice = parts[2];
            int value = Integer.parseInt(parts[3]);
            FileIO.writeToFile(outputPath, "INPUT: " + line, true, true);

            makePurchase(machine, choice, value, money, outputPath);
        }
    }

    private static int makePurchase(GymMealMachine machine, String choice, int value, int money, String path) {
        switch (choice) {
            case "NUMBER":
                if (value < 0 || value > 23) {
                    FileIO.writeToFile(path, "INFO: Number cannot be accepted. Please try again with another number.", true, true);
                    FileIO.writeToFile(path, "RETURN: Returning your change: " + money + " TL", true, true);
                    return -1;
                }
                if (machine.getSlots()[value / 4][value % 4].getNumProducts() == 0) {
                    FileIO.writeToFile(path, "INFO: This slot is empty, your money will be returned.", true, true);
                    FileIO.writeToFile(path, "RETURN: Returning your change: " + money + " TL", true, true);
                    return -1;
                }
                if (machine.getSlots()[value / 4][value % 4].getNumProducts() == 0) {
                    FileIO.writeToFile(path, "INFO: Product not found, your money will be returned.", true, true);
                    FileIO.writeToFile(path, "RETURN: Returning your change: " + money + " TL", true, true);
                    return -1;
                }
                if (machine.getSlots()[value / 4][value % 4].getProducts()[0].getPrice() > money) {
                    FileIO.writeToFile(path, "INFO: Insufficient money, try again with more money.", true, true);
                    FileIO.writeToFile(path, "RETURN: Returning your change: " + money + " TL", true, true);
                    return -1;
                }
                FileIO.writeToFile(path, "PURCHASE: You have bought one " + machine.getSlots()[value / 4][value % 4].getProducts()[0].getName(), true, true);
                FileIO.writeToFile(path, "RETURN: Returning your change: " + (money - machine.getSlots()[value / 4][value % 4].getProducts()[0].getPrice()) + " TL", true, true);
                machine.removeFromMachine(machine.getSlots()[value / 4][value % 4]);
                return 0;
            case "PROTEIN":
                for (int j = 0; j < 6; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (machine.getSlots()[j][k].getProducts()[0].getProtein() >= value - 5 && machine.getSlots()[j][k].getProducts()[0].getProtein() <= value + 5) {
                            return printInfo(machine, money, path, j, k);
                        }
                    }
                }
                FileIO.writeToFile(path, "INFO: Product not found, your money will be returned.", true, true);
                FileIO.writeToFile(path, "RETURN: Returning your change: " + money + " TL", true, true);
                return -1;
            case "CARB":
                for (int j = 0; j < 6; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (machine.getSlots()[j][k].getProducts()[0].getCarb() >= value - 5 && machine.getSlots()[j][k].getProducts()[0].getCarb() <= value + 5) {
                            return printInfo(machine, money, path, j, k);
                        }
                    }
                }
                FileIO.writeToFile(path, "INFO: Product not found, your money will be returned.", true, true);
                FileIO.writeToFile(path, "RETURN: Returning your change: " + money + " TL", true, true);
                return -1;
            case "FAT":
                for (int j = 0; j < 6; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (machine.getSlots()[j][k].getProducts()[0].getFat() >= value - 5 && machine.getSlots()[j][k].getProducts()[0].getFat() <= value + 5) {
                            return printInfo(machine, money, path, j, k);
                        }
                    }
                }
                FileIO.writeToFile(path, "INFO: Product not found, your money will be returned.", true, true);
                FileIO.writeToFile(path, "RETURN: Returning your change: " + money + " TL", true, true);
                return -1;
            case "CALORIE":
                for (int j = 0; j < 6; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (machine.getSlots()[j][k].getNumProducts() > 0 && machine.getSlots()[j][k].getProducts()[0].getCalorie() >= value - 5 && machine.getSlots()[j][k].getProducts()[0].getCalorie() <= value + 5) {
                            return printInfo(machine, money, path, j, k);
                        }
                    }
                }
                FileIO.writeToFile(path, "INFO: Product not found, your money will be returned.", true, true);
                FileIO.writeToFile(path, "RETURN: Returning your change: " + money + " TL", true, true);
                return -1;
        }
        return -1;
    }

    private static int printInfo(GymMealMachine machine, int money, String path, int j, int k) {
        if (machine.getSlots()[j][k].getProducts()[0].getPrice() > money) {
            FileIO.writeToFile(path, "INFO: Insufficient money, try again with more money.", true, true);
            FileIO.writeToFile(path, "RETURN: Returning your change: " + money + " TL", true, true);
            return -1;
        }
        FileIO.writeToFile(path, "PURCHASE: You have bought one " + machine.getSlots()[j][k].getProducts()[0].getName(), true, true);
        FileIO.writeToFile(path, "RETURN: Returning your change: " + (money - machine.getSlots()[j][k].getProducts()[0].getPrice()) + " TL", true, true);
        machine.removeFromMachine(machine.getSlots()[j][k]);
        return 0;
    }
}