import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Parses BNF grammars from input file and stores them in a map.
 */
public class GrammarParser {
    /**
     * Parses the BNF grammar from the provided file path.
     *
     * @param inputPath the path to the file containing the BNF grammar
     * @return a map of production rules where each key is a non-terminal symbol and the value is a list of expansions
     * <p>
     * I used a HashMap to store the grammar rules because HashMap provides O(1) average-time complexity for put and get operations,
     * which is efficient for recursive functions.
     * <p>
     * I used ArrayList for storing the expansion rules because the dynamic structure of ArrayList.
     */
    public static Map<String, List<String>> parseGrammar(String inputPath) {
        String[] lines = FileIO.readFile(inputPath, true, true);
        Map<String, List<String>> productionRules = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split("->");
            String key = parts[0].trim();
            String[] expansions = parts[1].trim().split("\\|");
            productionRules.put(key, new ArrayList<>(Arrays.asList(expansions)));
        }
        return productionRules;
    }
}
