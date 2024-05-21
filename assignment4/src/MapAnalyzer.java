import java.util.Locale;

public class MapAnalyzer {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Analysis analysis = new Analysis("src/i1.txt", "src/output.txt");
        analysis.printFastestRoute();
    }
}