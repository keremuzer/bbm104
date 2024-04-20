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
        commandLoop:
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
                } else {
                    System.out.println("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!");
                }
            } else if (parts[0].equals("SELL_TICKET")) {
                int voyageID = Integer.parseInt(parts[1]);
                ArrayList<Integer> seatNumbers = new ArrayList<>();
                for (String seatNumber : parts[2].split("_")) {
                    seatNumbers.add(Integer.parseInt(seatNumber));
                }
                for (Voyage voyage : Voyage.voyages) {
                    if (voyage.voyageID == voyageID) {
                        voyage.sellTicket(seatNumbers);
                    }
                }
            } else if (parts[0].equals("REFUND_TICKET")) {
                int voyageID = Integer.parseInt(parts[1]);
                ArrayList<Integer> seatNumbers = new ArrayList<>();
                for (String seatNumber : parts[2].split("_")) {
                    seatNumbers.add(Integer.parseInt(seatNumber));
                }
                for (Voyage voyage : Voyage.voyages) {
                    if (voyage.voyageID == voyageID) {
                        voyage.refundTicket(voyageID, seatNumbers);
                        continue commandLoop;
                    }
                }
                System.out.println("ERROR: There is no voyage with ID of "+ voyageID + "!");
            } else if (parts[0].equals("PRINT_VOYAGE")) {
                try {
                    int voyageID = Integer.parseInt(parts[1]);
                    if (voyageID < 0) {
                        throw new NumberFormatException();
                    }
                    for (Voyage voyage : Voyage.voyages) {
                        if (voyage.voyageID == voyageID) {
                            voyage.printVoyage();
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: -30 is not a positive integer, ID of a voyage must be a positive integer!");
                    return;
                }
            } else {
                System.out.println("ERROR: There is no command namely " + parts[0] + "!");
            }
        }
    }
}