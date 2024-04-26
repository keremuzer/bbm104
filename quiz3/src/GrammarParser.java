import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GrammarParser {
    public static HashMap<String, ArrayList<String>> parseGrammar(String inputPath) {
        String[] lines = FileIO.readFile(inputPath, true, true);
        HashMap<String, ArrayList<String>> productionRules = new HashMap<>();
        assert lines != null;
        for (String line : lines) {
            String[] parts = line.split("->");
            String key = parts[0];
            String[] expansions = parts[1].split("\\|");
            productionRules.put(key, new ArrayList<>(Arrays.asList(expansions)));
        }
        return productionRules;
    }
}