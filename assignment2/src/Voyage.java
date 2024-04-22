import java.util.ArrayList;

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

    public abstract void sellTicket(ArrayList<Integer> seatNumbers, String outputPath);

    public abstract void refundTicket(int voyageID, ArrayList<Integer> seatNumbers, String outputPath);

    public abstract void printVoyage(String outputPath);

    public abstract double calculateRefund(int seatNumber);

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

    public Voyage getVoyage(int voyageID) {
        for (Voyage voyage : voyages) {
            if (voyage.voyageID == voyageID) {
                return voyage;
            }
        }
        return null;
    }

    // Builder static nested class
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
