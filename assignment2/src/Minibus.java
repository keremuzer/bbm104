import java.util.ArrayList;

class Minibus extends Voyage {
    public Minibus(int voyageID, String from, String to, int rows, double seatPrice) {
        this.voyageID = voyageID;
        this.from = from;
        this.to = to;
        this.rows = rows;
        this.seatPrice = seatPrice;
        this.seats = new boolean[rows * 2];
    }

    @Override
    public void sellTicket(ArrayList<Integer> seatNumbers){

    }

    @Override
    public void refundTicket(ArrayList<Integer> seatNumbers) {
        // No refunds allowed, do nothing
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