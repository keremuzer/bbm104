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
        for (int seatNumber : seatNumbers) {
            if (seatNumber < 0) {
                System.out.println("ERROR: " + seatNumber + " is not a positive integer, seat number must be a positive integer!");
                return;
            }
            if (seatNumber > seats.length) {
                System.out.println("ERROR: There is no such a seat!");
                return;
            }
            if (!seats[seatNumber]) {
                System.out.println("ERROR: One or more seats are not sold!");
                return;
            }
        }

        for (int seatNumber : seatNumbers) {
            seats[seatNumber] = false;
            double refund = calculateRefund(seatNumber);
            revenue -= refund;
        }
    }

    @Override
    public void printVoyage() {
        System.out.println("Voyage " + voyageID + "\n" + from + "-" + to);
        for (int i = 0; i < seats.length; i++){
            if (i % 4 == 0){
                if (seats[i]){
                    System.out.print("X " );
                } else {
                    System.out.print("* " );
                }
            } else if (i % 4 == 1){
                if (seats[i]){
                    System.out.print("X | ");
                } else {
                    System.out.print("* | ");
                }
            } else if (i % 4 == 2){
                if (seats[i]){
                    System.out.print("X ");
                } else {
                    System.out.print("* ");
                }
            } else {
                if (seats[i]){
                    System.out.print("X\n");
                } else {
                    System.out.print("*\n");
                }

            }
        }
    }

    @Override
    public double calculateRefund(int seatNumber) {
        // Calculation based on refundCut
        return seatPrice * refundCut / 100;
    }
}