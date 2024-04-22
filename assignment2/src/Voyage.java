import java.util.ArrayList;

/**
 * Abstract class for voyages.
 * Contains the voyageID, departure point, arrival point, number of rows, seat price, seats, and revenue of the voyage.
 * Contains an abstract method to refund a ticket.
 * Contains an abstract method to print the voyage.
 * Contains an abstract method to calculate the refund amount of a seat.
 * Contains a Builder class to build a voyage object.
 */
public abstract class Voyage {
    static ArrayList<Voyage> voyages = new ArrayList<>();
    private final int voyageID;
    private final String from;
    private final String to;
    private final int rows;
    private final double seatPrice;
    boolean[] seats;
    private double revenue;

    public Voyage(int voyageID, String from, String to, int rows, double seatPrice) {
        this.voyageID = voyageID;
        this.from = from;
        this.to = to;
        this.rows = rows;
        this.seatPrice = seatPrice;
        this.revenue = 0;
        this.seats = new boolean[rows * 4];
        voyages.add(this);
    }


    public abstract double refundTicket(int voyageID, ArrayList<Integer> seatNumbers, String outputPath);

    public abstract void printVoyage(String outputPath);

    public abstract double calculateRefund(int seatNumber);

    /**
     * Sells a ticket for the voyage.
     * Checks if the seat number is positive, if the seat number is valid, and if the seat is already sold.
     * If the seat is valid and not sold, sells the ticket and increases the revenue.
     *
     * @param seatNumbers The seat numbers to be sold
     * @param outputPath  The path to the output file
     * @return The total price of the sold tickets
     */
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
            setRevenue(getRevenue() + getSeatPrice());
            price += getSeatPrice();
        }
        return price;
    }

    /**
     * Cancels the voyage.
     * Sets all the seats to empty and decreases the revenue.
     */
    public void cancelVoyage() {
        for (int i = 0; i < seats.length; i++) {
            if (seats[i]) {
                seats[i] = false;
                setRevenue(getRevenue() - getSeatPrice());
            }
        }
    }

    public double setRevenue(double revenue) {
        return this.revenue = revenue;
    }

    // Getters for common properties
    public int getVoyageID() {
        return voyageID;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getRows() {
        return rows;
    }

    public double getSeatPrice() {
        return seatPrice;
    }

    public double getRevenue() {
        return revenue;
    }

    /**
     * Builder class to build a voyage object.
     */
    public static class Builder {
        private String type;
        private int voyageID;
        private String from;
        private String to;
        private int rows;
        private double seatPrice;
        private Double refundCut = null;
        private Double premiumFee = null;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setVoyageID(int voyageID) {
            this.voyageID = voyageID;
            return this;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setTo(String to) {
            this.to = to;
            return this;
        }

        public Builder setRows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder setSeatPrice(double seatPrice) {
            this.seatPrice = seatPrice;
            return this;
        }

        public Builder setRefundCut(Double refundCut) {
            this.refundCut = refundCut;
            return this;
        }

        public Builder setPremiumFee(Double premiumFee) {
            this.premiumFee = premiumFee;
            return this;
        }

        /**
         * Builds a voyage object according to the type.
         *
         * @return The built voyage object
         */
        public Voyage build() {
            switch (type.toLowerCase()) {
                case "standard":
                    return new StandardBus(voyageID, from, to, rows, seatPrice, refundCut);
                case "premium":
                    return new PremiumBus(voyageID, from, to, rows, seatPrice, refundCut, premiumFee);
                case "minibus":
                    return new Minibus(voyageID, from, to, rows, seatPrice);
                default:
                    System.out.println("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!");
                    return null;
            }
        }
    }
}
