import java.util.ArrayList;

class Minibus extends Voyage {
    public Minibus(int voyageID, String from, String to, int rows, double seatPrice) {
        this.voyageID = voyageID;
        this.from = from;
        this.to = to;
        this.rows = rows;
        this.seatPrice = seatPrice;
        this.seats = new boolean[rows * 2];
        this.revenue = 0;
    }

    @Override
    public void sellTicket(ArrayList<Integer> seatNumbers){
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
    public void refundTicket(int voyageID, ArrayList<Integer> seatNumbers) {
        // Refund is not available for minibuses, do nothing
    }

    @Override
    public void printVoyage() {
        // Implementation
    }

    @Override
    public double calculateRefund(int seatNumber) {
        return 0;  // No refund available for minibuses
    }
}