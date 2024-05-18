import java.util.HashMap;

public class Inventory<T> {
    private HashMap<Integer, T> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void executeCommands(String inputPath, String outputPath) {
        Inventory<Item> inventory = new Inventory<>();
        String[] commands = FileIO.readFile(inputPath, true, true);

        for (String line : commands) {
            String[] parts = line.split("\t");
            String command = parts[0];

            switch (command) {
                case "ADD":
                    String type = parts[1];
                    String name = parts[2];
                    int barcode = Integer.parseInt(parts[4]);
                    double price = Double.parseDouble(parts[5]);
                    switch (type) {
                        case "Book":
                            String author = parts[3];
                            inventory.addItem(new Book(name, author, barcode, price));
                            break;
                        case "Toy":
                            String color = parts[3];
                            inventory.addItem(new Toy(name, color, barcode, price));
                            break;
                        case "Stationery":
                            String kind = parts[3];
                            inventory.addItem(new Stationery(name, kind, barcode, price));
                            break;
                    }
                    break;
                case "REMOVE":
                    barcode = Integer.parseInt(parts[1]);
                    inventory.removeItem(barcode);
                    FileIO.writeToFile(outputPath, "REMOVE RESULTS:\nItem is removed.\n------------------------------", true, true);
                    break;
            }
        }
    }

    private void addItem(T item) {
        items.put(((Item) item).getBarcode(), item);
    }

    private void removeItem(int barcode) {
        items.remove(barcode);
    }
}
