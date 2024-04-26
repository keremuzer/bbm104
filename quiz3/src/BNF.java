public class BNF {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java8 BNF <input_path> <output_path>");
            return;
        }
        StringGenerator generator = new StringGenerator(GrammarParser.parseGrammar(args[0]));
        FileIO.writeToFile(args[1], generator.generateStrings("S"), false, false);
    }
}