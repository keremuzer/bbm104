import java.util.ArrayList;

class Minibus extends Voyage {
    public Minibus(int voyageID, String from, String to, int rows, double seatPrice) {
        super(voyageID, from, to, rows, seatPrice);
        this.seats = new boolean[rows * 2];
    }

    @Override
    public void sellTicket(ArrayList<Integer> seatNumbers, String outputPath) {
        for (int seatNumber : seatNumbers) {
            if (seatNumber <= 0) {
                throw new IllegalArgumentException("ERROR: " + seatNumber + " is not a positive integer, seat number must be a positive integer!");
            }
            if (seatNumber > seats.length) {
                throw new IllegalArgumentException("ERROR: There is no such a seat!");
            }
            if (seats[seatNumber - 1]) {
                throw new IllegalArgumentException("ERROR: One or more seats already sold!");
            }
        }

        for (int seatNumber : seatNumbers) {
            seats[seatNumber - 1] = true;
            setRevenue(getRevenue() + getSeatPrice());
        }
    }

    @Override
    public void refundTicket(int voyageID, ArrayList<Integer> seatNumbers, String outputPath) {
        // Refund is not available for minibuses
    }

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