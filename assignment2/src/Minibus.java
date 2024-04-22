import java.util.ArrayList;

class Minibus extends Voyage {
    public Minibus(int voyageID, String from, String to, int rows, double seatPrice) {
        super(voyageID, from, to, rows, seatPrice);
        this.seats = new boolean[rows * 2];
    }

    @Override
    public double refundTicket(int voyageID, ArrayList<Integer> seatNumbers, String outputPath) {
        // Refund is not available for minibuses
        return 0;
    }

    /**
     * Prints the voyage to the output file.
     * Prints the voyage ID, departure point, and arrival point.
     * Prints the seats of the voyage.
     * X represents a sold seat, * represents an empty seat.
     *
     * @param outputPath The path to the output file
     */
    @Override
    public void printVoyage(String outputPath) {
        FileIO.writeToFile(outputPath, "Voyage " + getVoyageID() + "\n" + getFrom() + "-" + getTo(), true, true);
        for (int i = 0; i < seats.length; i++) {
            if (i % 2 == 0) {
                if (seats[i]) {
                    FileIO.writeToFile(outputPath, "X ", true, false);
                } else {
                    FileIO.writeToFile(outputPath, "* ", true, false);
                }
            } else {
                if (seats[i]) {
                    FileIO.writeToFile(outputPath, "X", true, true);
                } else {
                    FileIO.writeToFile(outputPath, "*", true, true);
                }
            }
        }
    }

    @Override
    public double calculateRefund(int seatNumber) {
        return 0;  // No refund available for minibuses
    }
}