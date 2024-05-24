import java.util.Locale;

/**
 * Main class for the MapAnalyzer program.
 */
public class MapAnalyzer {
    /**
     * Main method for the MapAnalyzer program.
     *
     * @param args Command line arguments:
     *             args[0] is the input file path
     *             args[1] is the output file path
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Analysis analysis = new Analysis(args[0], args[1]);
        analysis.printOutput();
    }
}