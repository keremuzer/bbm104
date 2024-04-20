import java.util.ArrayList;

class StandardBus extends Voyage {
    private double refundCut;

    public StandardBus(int voyageID, String from, String to, int rows, double seatPrice, double refundCut) {
        this.voyageID = voyageID;
        this.from = from;
        this.to = to;
        this.rows = rows;
        this.seatPrice = seatPrice;
        this.refundCut = refundCut;
        this.seats = new boolean[rows * 4];
    }

    @Override
    public void sellTicket(int[] seatNumbers) {
        for (int seatNumber : seatNumbers) {
            if (seatNumber < 0) {
                System.out.println("ERROR: " + seatNumber + " is not a positive integer, seat number must be a positive integer!");
                return;
            }
            if (seatNumber > seats.length) {
                System.out.println("ERROR: There is no such a seat!");
                return;
            }
            if (seats[seatNumber]) {
                System.out.println("ERROR: One or more seats already sold!");
                return;
            }
        }

        for (int seatNumber : seatNumbers) {
            seats[seatNumber] = true;
            revenue += seatPrice;
        }
    }

    @Override
    public void refundTicket(ArrayList<Integer> seatNumbers) {
        // Implementation
    }

    @Override
    public void printVoyage() {
        // Implementation
    }

    @Override
    public double calculateRefund(int seatNumber) {
        // Calculation based on refundCut
        return 0;
    }
}