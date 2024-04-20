import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        executeCommands();
    }

    private static String[] readCommands(String path) {
        return FileIO.readFile(path, true, true);
    }

    private static void executeCommands(){
        String[] commands = readCommands("src/i2.txt");
        for (String command : commands) {
            String[] parts = command.split("\t");
            if (parts[0].equals("Z_REPORT")) {
                System.out.println("printing z report");
            } else if (parts[0].equals("INIT_VOYAGE")) {
                if (parts[1].equals("Minibus")) {
                    Voyage voyage = new Voyage.Builder()
                            .setType(parts[1])
                            .setVoyageID(Integer.parseInt(parts[2]))
                            .setFrom(parts[3])
                            .setTo(parts[4])
                            .setRows(Integer.parseInt(parts[5]))
                            .setSeatPrice(Double.parseDouble(parts[6]))
                            .build();
                    Voyage.voyages.add(voyage);
                } else if (parts[1].equals("Standard")) {
                    Voyage voyage = new Voyage.Builder()
                            .setType(parts[1])
                            .setVoyageID(Integer.parseInt(parts[2]))
                            .setFrom(parts[3])
                            .setTo(parts[4])
                            .setRows(Integer.parseInt(parts[5]))
                            .setSeatPrice(Double.parseDouble(parts[6]))
                            .setRefundCut(Double.parseDouble(parts[7]))
                            .build();
                    Voyage.voyages.add(voyage);
                } else if (parts[1].equals("Premium")) {
                    Voyage voyage = new Voyage.Builder()
                            .setType(parts[1])
                            .setVoyageID(Integer.parseInt(parts[2]))
                            .setFrom(parts[3])
                            .setTo(parts[4])
                            .setRows(Integer.parseInt(parts[5]))
                            .setSeatPrice(Double.parseDouble(parts[6]))
                            .setRefundCut(Double.parseDouble(parts[7]))
                            .setPremiumFee(Double.parseDouble(parts[8]))
                            .build();
                    Voyage.voyages.add(voyage);
                }
            } else if (parts[0].equals("SELL_TICKET")) {
                int voyageID = Integer.parseInt(parts[1]);
                int[] seatNumbers = Arrays.stream(parts[2].split("_")).mapToInt(Integer::parseInt).toArray();
                for (Voyage voyage : Voyage.voyages) {
                    if (voyage.voyageID == voyageID) {
                        voyage.sellTicket(seatNumbers);
                    }
                }
            }
        }
    }
}