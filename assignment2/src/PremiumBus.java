import java.util.ArrayList;

class PremiumBus extends StandardBus {
    private final double premiumFee;

    public PremiumBus(int voyageID, String from, String to, int rows, double seatPrice, double refundCut, double premiumFee) {
        super(voyageID, from, to, rows, seatPrice, refundCut);
        this.premiumFee = premiumFee;
        this.seats = new boolean[rows * 3];
    }

    /**
     * Sells the tickets with the given seat numbers and returns the total price.
     * Checks if the seat number is positive, if the seat number is valid, and if the seat is already sold.
     * If the seat is valid and not sold, sells the ticket and increases the revenue.
     *
     * @param seatNumbers The seat numbers to be sold
     * @param outputPath  The path to the output file
     * @return The total price of the tickets
     */
    @Override
    public double sellTicket(ArrayList<Integer> seatNumbers, String outputPath) {
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
        double price = 0;
        for (int seatNumber : seatNumbers) {
            seats[seatNumber - 1] = true;
            if (seatNumber % 3 == 1) {
                setRevenue(getRevenue() + getSeatPrice() * (premiumFee / 100 + 1));
                price += getSeatPrice() * (premiumFee / 100 + 1);
            } else {
                setRevenue(getRevenue() + getSeatPrice());
                price += getSeatPrice();
            }
        }
        return price;
    }

    /**
     * Prints the voyage to the output file.
     * Prints the voyage ID, departure point, and arrival point.
     * Prints the seats of the voyage.
     * X represents a sold seat, * represents an empty seat. | represents the aisle.
     *
     * @param outputPath The path to the output file
     */
    public void printVoyage(String outputPath) {
        FileIO.writeToFile(outputPath, "Voyage " + getVoyageID() + "\n" + getFrom() + "-" + getTo(), true, true);
        for (int i = 0; i < seats.length; i++) {
            if (i % 3 == 0) {
                if (seats[i]) {
                    FileIO.writeToFile(outputPath, "X | ", true, false);
                } else {
                    FileIO.writeToFile(outputPath, "* | ", true, false);
                }
            } else if (i % 3 == 1) {
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

    /**
     * Cancels the voyage.
     * Sets all the seats to empty and decreases the revenue.
     */
    @Override
    public void cancelVoyage() {
        for (int i = 1; i <= seats.length; i++) {
            if (seats[i - 1]) {
                seats[i - 1] = false;
                if (i % 3 == 1) {
                    setRevenue(getRevenue() - getSeatPrice() * (premiumFee / 100 + 1));
                } else {
                    setRevenue(getRevenue() - getSeatPrice());
                }
            }
        }
    }

    /**
     * Calculates the refund amount for the given seat number.
     *
     * @param seatNumber The seat number
     * @return The refund amount
     */
    @Override
    public double calculateRefund(int seatNumber) {
        if (seatNumber % 3 == 1) {
            return getSeatPrice() * (premiumFee / 100 + 1) * (1 - getRefundCut() / 100);
        } else {
            return getSeatPrice() * (1 - getRefundCut() / 100);
        }
    }
}