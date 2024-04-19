class PremiumBus extends StandardBus {
    private double premiumFee;

    public PremiumBus(int voyageID, String from, String to, int rows, double seatPrice, double refundCut, double premiumFee) {
        super(voyageID, from, to, rows, seatPrice, refundCut);
        this.premiumFee = premiumFee;
        this.seats = new boolean[rows * 3];
    }

    @Override
    public double calculateRefund(int seatNumber) {
        // Additional logic for premium seats
        return super.calculateRefund(seatNumber) - premiumFee;
    }
}