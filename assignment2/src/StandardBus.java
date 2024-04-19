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
    public void sellTicket(ArrayList<Integer> seatNumbers) {
        // Implementation
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