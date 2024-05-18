public class Main {
    /**
     * Main method to run the program.
     *
     * @param args input file path and output file path
     */
    public static void main(String[] args) {
        // Check the number of arguments
        if (args.length != 2) {
            System.out.println("Usage: java Main input_file output_file");
            return;
        }
        // Create an inventory of items and execute the commands
        Inventory<Item> inventory = new Inventory<>();
        inventory.executeCommands(args[0], args[1]);
    }
}