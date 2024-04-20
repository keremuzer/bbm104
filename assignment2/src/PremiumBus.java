class PremiumBus extends StandardBus {
    private double premiumFee;

    public PremiumBus(int voyageID, String from, String to, int rows, double seatPrice, double refundCut, double premiumFee) {
        super(voyageID, from, to, rows, seatPrice, refundCut);
        this.premiumFee = premiumFee;
        this.seats = new boolean[rows * 3];
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
            if (seatNumber % 3 == 1){
                revenue += seatPrice + premiumFee;
            } else {
                revenue += seatPrice;
            }
        }
    }

    @Override
    public double calculateRefund(int seatNumber) {
        // Additional logic for premium seats
        return super.calculateRefund(seatNumber) - premiumFee;
    }
}