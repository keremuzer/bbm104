import java.util.ArrayList;

class PremiumBus extends StandardBus {
    private double premiumFee;

    public PremiumBus(int voyageID, String from, String to, int rows, double seatPrice, double refundCut, double premiumFee) {
        super(voyageID, from, to, rows, seatPrice, refundCut);
        this.premiumFee = premiumFee;
        this.seats = new boolean[rows * 3];
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
            if (seatNumber % 3 == 1) {
                setRevenue(getRevenue() + getSeatPrice() + premiumFee);
            } else {
                setRevenue(getRevenue() + getSeatPrice());
            }
        }
    }

    @Override
    public void refundTicket(int voyageID, ArrayList<Integer> seatNumbers, String outputPath) {
        for (int seatNumber : seatNumbers) {
            if (seatNumber < 0) {
                throw new IllegalArgumentException("ERROR: " + seatNumber + " is not a positive integer, seat number must be a positive integer!");
            }
            if (seatNumber > seats.length) {
                throw new IllegalArgumentException("ERROR: There is no such a seat!");
            }
            if (!seats[seatNumber - 1]) {
                throw new IllegalArgumentException("ERROR: One or more seats are not sold!");
            }
        }

        for (int seatNumber : seatNumbers) {
            seats[seatNumber - 1] = false;
            double refund = calculateRefund(seatNumber);
            setRevenue(getRevenue() - refund);
        }
    }

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

    @Override
    public double calculateRefund(int seatNumber) {
        if (seatNumber % 3 == 1) {
            return (getSeatPrice() + premiumFee) * (100 - getRefundCut()) / 100;
        } else {
            return getSeatPrice() * (100 - getRefundCut()) / 100;
        }
    }
}