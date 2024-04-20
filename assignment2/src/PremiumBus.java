import java.util.ArrayList;

class PremiumBus extends StandardBus {
    private double premiumFee;

    public PremiumBus(int voyageID, String from, String to, int rows, double seatPrice, double refundCut, double premiumFee) {
        super(voyageID, from, to, rows, seatPrice, refundCut);
        this.premiumFee = premiumFee;
        this.seats = new boolean[rows * 3];
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
            if (seatNumber % 3 == 1){
                revenue += seatPrice + premiumFee;
            } else {
                revenue += seatPrice;
            }
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

    public void printVoyage(){
        System.out.println("Voyage " + voyageID + "\n" + from + "-" + to);
        for (int i = 0; i < seats.length; i++){
            if (i % 3 == 0){
                if (seats[i]){
                    System.out.print("X | ");
                } else {
                    System.out.print("* | ");
                }
            } else if (i % 3 == 1) {
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
        // Additional logic for premium seats
        if (seatNumber % 3 == 1){
            return super.calculateRefund(seatNumber) - premiumFee;
        } else {
            return super.calculateRefund(seatNumber);
        }
    }
}