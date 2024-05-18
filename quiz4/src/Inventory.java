import java.util.LinkedHashMap;

/**
 * Generic class to manage an inventory.
 * Contains methods to add, remove, search, and display items.
 *
 * @param <T> The type of the items in the inventory
 */
public class Inventory<T> {
    // a linked hash map is used to store the items in the order they are added and to search by barcode
    private LinkedHashMap<Integer, T> items;

    public Inventory() {
        items = new LinkedHashMap<>();
    }

    /**
     * Executes the commands in the input file.
     * Reads the commands from the input file.
     * Adds, removes, searches, or displays items according to the commands.
     * Writes the results to the output file.
     *
     * @param inputPath  The path to the input file
     * @param outputPath The path to the output file
     */
    public void executeCommands(String inputPath, String outputPath) {
        String[] commands = FileIO.readFile(inputPath, true, true);
        FileIO.writeToFile(outputPath, "", false, false);

        // process each command
        for (String line : commands) {
            String[] parts = line.split("\t");
            String command = parts[0];
            int barcode;

            switch (command) {
                case "ADD":
                    String type = parts[1];
                    String name = parts[2];
                    barcode = Integer.parseInt(parts[4]);
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

    /**
     * Generic method to add an item to the inventory.
     *
     * @param item The item to be added
     */
    private <E> void addItem(E item) {
        items.put(((Item) item).getBarcode(), (T) item);
    }

    /**
     * Removes an item from the inventory.
     *
     * @param barcode    The barcode of the item to be removed
     * @param outputPath The path to the output file
     */
    private void removeItem(int barcode, String outputPath) {
        if (!items.containsKey(barcode)) {
            FileIO.writeToFile(outputPath, "REMOVE RESULTS:\nItem is not found.\n------------------------------", true, true);
            return;
        }
        items.remove(barcode);
        FileIO.writeToFile(outputPath, "REMOVE RESULTS:\nItem is removed.\n------------------------------", true, true);
    }

    /**
     * Searches for an item by barcode.
     *
     * @param barcode    The barcode of the item to be searched
     * @param outputPath The path to the output file
     */
    private void searchByBarcode(int barcode, String outputPath) {
        if (items.containsKey(barcode)) {
            FileIO.writeToFile(outputPath, "SEARCH RESULTS:\n" + items.get(barcode).toString() + "\n------------------------------", true, true);
        } else {
            FileIO.writeToFile(outputPath, "SEARCH RESULTS:\nItem is not found.\n------------------------------", true, true);
        }
    }

    /**
     * Searches for an item by name.
     *
     * @param searchName The name of the item to be searched
     * @param outputPath The path to the output file
     */
    private void searchByName(String searchName, String outputPath) {
        for (T item : items.values()) {
            if (((Item) item).getName().equals(searchName)) {
                FileIO.writeToFile(outputPath, "SEARCH RESULTS:\n" + item + "\n------------------------------", true, true);
                return;
            }
        }
        FileIO.writeToFile(outputPath, "SEARCH RESULTS:\nItem is not found.\n------------------------------", true, true);
    }

    /**
     * Displays all the items in the inventory.
     *
     * @param outputPath The path to the output file
     */
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
