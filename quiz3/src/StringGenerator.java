import java.util.ArrayList;
import java.util.HashMap;

public class StringGenerator {
    private final HashMap<String, ArrayList<String>> productionRules;

    public StringGenerator(HashMap<String, ArrayList<String>> productionRules) {
        this.productionRules = productionRules;
    }

    public String generateStrings(String startSymbol) {
        ArrayList<String> results = new ArrayList<>();
        if (!productionRules.containsKey(startSymbol)) {
            return startSymbol;
        }

        for(String expansion : productionRules.get(startSymbol)) {
            String[] symbols = expansion.split("");
            StringBuilder result = new StringBuilder();
            for (String symbol : symbols) {
                result.append(generateStrings(symbol));
            }
            results.add(result.toString());
        }
        return "(" + String.join("|", results) + ")";
    }
}
