import java.util.List;
import java.util.Map;

/**
 * Main class for executing the BNF string generation.
 * It reads a BNF grammar from input file, processes it, and outputs the possible strings to output file.
 */
public class BNF {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java8 BNF <input_path> <output_path>");
            return;
        }
        Map<String, List<String>> productionRules = GrammarParser.parseGrammar(args[0]);
        StringGenerator generator = new StringGenerator(productionRules);
        FileIO.writeToFile(args[1], generator.generateStrings("S"), false, false);
    }
}
