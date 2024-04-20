import java.util.ArrayList;

public abstract class Voyage {
    int voyageID;
    String from;
    String to;
    int rows;
    double seatPrice;
    double revenue;
    boolean[] seats;
    static ArrayList<Voyage> voyages = new ArrayList<>();

    public abstract void sellTicket(ArrayList<Integer> seatNumbers);
    public abstract void refundTicket(int voyageID, ArrayList<Integer> seatNumbers);
    public abstract void printVoyage();
    public abstract double calculateRefund(int seatNumber);

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
                    throw new IllegalArgumentException("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!");
            }
        }
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
        return 0;
    }

    public Voyage getVoyage(int voyageID) {
        for (Voyage voyage : voyages) {
            if (voyage.voyageID == voyageID) {
                return voyage;
            }
        }
        return null;
    }
}
