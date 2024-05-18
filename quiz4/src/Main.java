public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Main input_file output_file");
            return;
        }
        Inventory<Item> inventory = new Inventory<>();
        inventory.executeCommands(args[0], args[1]);
    }
}