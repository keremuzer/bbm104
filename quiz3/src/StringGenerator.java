import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class generates strings based on the BNF grammar rules provided.
 */
public class StringGenerator {
    private final Map<String, List<String>> productionRules;

    /**
     * Constructor for StringGenerator.
     *
     * @param productionRules the parsed grammar rules from GrammarParser
     */
    public StringGenerator(Map<String, List<String>> productionRules) {
        this.productionRules = productionRules;
    }

    /**
     * Generates all possible strings from a given starting symbol.
     * This method uses recursion to expand each non-terminal until only terminals remain.
     *
     * @param startSymbol the non-terminal symbol from which to start generating strings
     * @return a list of all possible strings that can be generated from the startSymbol
     */
    public String generateStrings(String startSymbol) {
        if (!productionRules.containsKey(startSymbol)) {
            return startSymbol;
        }
        List<String> results = new ArrayList<>();
        for (String expansion : productionRules.get(startSymbol)) {
            StringBuilder result = new StringBuilder();
            String[] symbols = expansion.split("");
            for (String symbol : symbols) {
                result.append(generateStrings(symbol));
            }
            results.add(result.toString());
        }
        return "(" + String.join("|", results) + ")";
    }
}
