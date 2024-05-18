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
                    inventory.removeItem(barcode, outputPath);
                    break;
                case "SEARCHBYBARCODE":
                    barcode = Integer.parseInt(parts[1]);
                    searchByBarcode(barcode, outputPath);
                    break;
                case "SEARCHBYNAME":
                    String searchName = parts[1];
                    searchByName(searchName, outputPath);
            }
        }
    }

    private void addItem(T item) {
        items.put(((Item) item).getBarcode(), item);
    }

    private void removeItem(int barcode, String outputPath) {
        items.remove(barcode);
        FileIO.writeToFile(outputPath, "REMOVE RESULTS:\nItem is removed.\n------------------------------", true, true);
    }

    private void searchByBarcode(int barcode, String outputPath) {
        if (items.containsKey(barcode)) {
            FileIO.writeToFile(outputPath, "SEARCH RESULTS:\n" + items.get(barcode).toString() + "\n------------------------------", true, true);
        } else {
            FileIO.writeToFile(outputPath, "SEARCH RESULTS:\nItem is not found.\n------------------------------", true, true);
        }
    }

    private void searchByName(String searchName, String outputPath) {
        for (T item : items.values()) {
            if (((Item) item).getName().equals(searchName)) {
                FileIO.writeToFile(outputPath, "SEARCH RESULTS:\n" + item.toString() + "\n------------------------------", true, true);
                return;
            }
        }
        FileIO.writeToFile(outputPath, "SEARCH RESULTS:\nItem is not found.\n------------------------------", true, true);
    }
}
