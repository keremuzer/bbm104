import java.util.ArrayList;

class StandardBus extends Voyage {
    private double refundCut;

    public StandardBus(int voyageID, String from, String to, int rows, double seatPrice, double refundCut) {
        super(voyageID, from, to, rows, seatPrice);
        this.seats = new boolean[rows * 4];
        this.refundCut = refundCut;
    }

    @Override
    public void sellTicket(ArrayList<Integer> seatNumbers) {
        for (int seatNumber : seatNumbers) {
            if (seatNumber <= 0) {
                System.out.println("ERROR: " + seatNumber + " is not a positive integer, seat number must be a positive integer!");
                return;
            }
            if (seatNumber > seats.length) {
                System.out.println("ERROR: There is no such a seat!");
                return;
            }
            if (seats[seatNumber - 1]) {
                System.out.println("ERROR: One or more seats already sold!");
                return;
            }
        }

        for (int seatNumber : seatNumbers) {
            seats[seatNumber - 1] = true;
            setRevenue(getRevenue() + getSeatPrice());
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
            if (!seats[seatNumber - 1]) {
                System.out.println("ERROR: One or more seats are not sold!");
                return;
            }
        }

        for (int seatNumber : seatNumbers) {
            seats[seatNumber - 1] = false;
            setRevenue(getRevenue() - calculateRefund(seatNumber));
        }
    }

    @Override
    public void printVoyage() {
        System.out.println("Voyage " + getVoyageID() + "\n" + getFrom() + "-" + getTo());
        for (int i = 0; i < seats.length; i++) {
            if (i % 4 == 0) {
                if (seats[i]) {
                    System.out.print("X ");
                } else {
                    System.out.print("* ");
                }
            } else if (i % 4 == 1) {
                if (seats[i]) {
                    System.out.print("X | ");
                } else {
                    System.out.print("* | ");
                }
            } else if (i % 4 == 2) {
                if (seats[i]) {
                    System.out.print("X ");
                } else {
                    System.out.print("* ");
                }
            } else {
                if (seats[i]) {
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
        return getSeatPrice() * (100 - refundCut) / 100;
    }
}