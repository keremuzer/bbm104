import java.util.HashMap;
import java.util.LinkedHashMap;

public class Inventory<T> {
    private LinkedHashMap<Integer, T> items;

    public Inventory() {
        items = new LinkedHashMap<>();
    }

    public void executeCommands(String inputPath, String outputPath) {
        String[] commands = FileIO.readFile(inputPath, true, true);
        FileIO.writeToFile(outputPath, "", false, false);

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
                            this.addItem(new Book(name, author, barcode, price));
                            break;
                        case "Toy":
                            String color = parts[3];
                            this.addItem(new Toy(name, color, barcode, price));
                            break;
                        case "Stationery":
                            String kind = parts[3];
                            this.addItem(new Stationery(name, kind, barcode, price));
                            break;
                    }
                    break;
                case "REMOVE":
                    barcode = Integer.parseInt(parts[1]);
                    this.removeItem(barcode, outputPath);
                    break;
                case "SEARCHBYBARCODE":
                    barcode = Integer.parseInt(parts[1]);
                    this.searchByBarcode(barcode, outputPath);
                    break;
                case "SEARCHBYNAME":
                    String searchName = parts[1];
                    this.searchByName(searchName, outputPath);
                    break;
                case "DISPLAY":
                    this.displayItems(outputPath);
                    break;
            }
        }
    }

    private <E> void addItem(E item) {
        items.put(((Item) item).getBarcode(), (T) item);
    }

    private void removeItem(int barcode, String outputPath) {
        if (!items.containsKey(barcode)) {
            FileIO.writeToFile(outputPath, "REMOVE RESULTS:\nItem is not found.\n------------------------------", true, true);
            return;
        }
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
                FileIO.writeToFile(outputPath, "SEARCH RESULTS:\n" + item + "\n------------------------------", true, true);
                return;
            }
        }
        FileIO.writeToFile(outputPath, "SEARCH RESULTS:\nItem is not found.\n------------------------------", true, true);
    }

    private void displayItems(String outputPath) {
        FileIO.writeToFile(outputPath, "INVENTORY:", true, true);
        for (T item : items.values()) {
            if (item instanceof Book) {
                FileIO.writeToFile(outputPath, item.toString(), true, true);
            }
        }
        for (T item : items.values()) {
            if (item instanceof Toy) {
                FileIO.writeToFile(outputPath, item.toString(), true, true);
            }
        }
        for (T item : items.values()) {
            if (item instanceof Stationery) {
                FileIO.writeToFile(outputPath, item.toString(), true, true);
            }
        }

        FileIO.writeToFile(outputPath, "------------------------------", true, true);
    }
}
