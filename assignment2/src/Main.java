import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        executeCommands("src/output.txt");
    }

    private static String[] readCommands(String path) {
        return FileIO.readFile(path, true, true);
    }

    private static void executeCommands(String outputPath) {
        FileIO.writeToFile(outputPath, "", false, false);
        String[] commands = readCommands("src/i2.txt");
        commandLoop:
        for (String command : commands) {
            FileIO.writeToFile(outputPath, "COMMAND: " + command, true, true);
            String[] parts = command.split("\t");
            switch (parts[0]) {
                case "Z_REPORT":
                    try {
                        if (parts.length != 1) {
                            throw new Exception();
                        }
                        FileIO.writeToFile(outputPath, "Z Report:", true, true);
                        FileIO.writeToFile(outputPath, "----------------", true, true);
                        if (Voyage.voyages.isEmpty()) {
                            FileIO.writeToFile(outputPath, "No Voyages Available!", true, true);
                            FileIO.writeToFile(outputPath, "----------------", true, true);
                            break;
                        }
                        Voyage.voyages.sort(Comparator.comparing(Voyage::getVoyageID));
                        for (Voyage voyage : Voyage.voyages) {
                            voyage.printVoyage(outputPath);
                            FileIO.writeToFile(outputPath, "Revenue: " + String.format(Locale.US, "%.2f", voyage.getRevenue()), true, true);
                            FileIO.writeToFile(outputPath, "----------------", true, true);
                        }
                    } catch (Exception e) {
                        FileIO.writeToFile(outputPath, "ERROR: Erroneous usage of \"Z_REPORT\" command!", true, true);
                    }
                    break;
                case "INIT_VOYAGE":
                    try {
                        if (Integer.parseInt(parts[2]) <= 0) {
                            throw new IllegalArgumentException("ERROR: " + Integer.parseInt(parts[2]) + " is not a positive integer, ID of a voyage must be a positive integer!");
                        }
                        if (Integer.parseInt(parts[5]) <= 0) {
                            throw new IllegalArgumentException("ERROR: " + Integer.parseInt(parts[5]) + " is not a positive integer, number of seat rows of a voyage must be a positive integer!");
                        }
                        if (Double.parseDouble(parts[6]) <= 0) {
                            throw new IllegalArgumentException("ERROR: " + parts[6] + " is not a positive number, price must be a positive number!");
                        }
                        for (Voyage voyage : Voyage.voyages) {
                            if (voyage.getVoyageID() == Integer.parseInt(parts[2])) {
                                throw new IllegalArgumentException("ERROR: There is already a voyage with ID of " + Integer.parseInt(parts[2]) + "!");
                            }
                        }
                        if (parts[1].equals("Minibus")) {
                            Voyage voyage = new Voyage.Builder()
                                    .setType(parts[1])
                                    .setVoyageID(Integer.parseInt(parts[2]))
                                    .setFrom(parts[3])
                                    .setTo(parts[4])
                                    .setRows(Integer.parseInt(parts[5]))
                                    .setSeatPrice(Double.parseDouble(parts[6]))
                                    .build();
                            FileIO.writeToFile(outputPath, "Voyage " + voyage.getVoyageID() + " was initialized as a minibus (2) voyage from " + voyage.getFrom() + " to " + voyage.getTo() + " with " + String.format(Locale.US, "%.2f", voyage.getSeatPrice()) + " TL priced " + voyage.getRows() * 2 + " regular seats. Note that minibus tickets are not refundable.", true, true);
                        } else if (parts[1].equals("Standard")) {
                            if (Double.parseDouble(parts[7]) < 0 || Double.parseDouble(parts[7]) > 100) {
                                throw new IllegalArgumentException("ERROR: " + parts[7] + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!");
                            }
                            Voyage voyage = new Voyage.Builder()
                                    .setType(parts[1])
                                    .setVoyageID(Integer.parseInt(parts[2]))
                                    .setFrom(parts[3])
                                    .setTo(parts[4])
                                    .setRows(Integer.parseInt(parts[5]))
                                    .setSeatPrice(Double.parseDouble(parts[6]))
                                    .setRefundCut(Double.parseDouble(parts[7]))
                                    .build();
                            FileIO.writeToFile(outputPath, "Voyage " + voyage.getVoyageID() + " was initialized as a standard (2+2) voyage from " + voyage.getFrom() + " to " + voyage.getTo() + " with " + String.format(Locale.US, "%.2f", voyage.getSeatPrice()) + " TL priced " + voyage.getRows() * 4 + " regular seats. Note that refunds will be " + parts[7] + "% less than the paid amount.", true, true);
                        } else if (parts[1].equals("Premium")) {
                            if (Double.parseDouble(parts[7]) < 0 || Double.parseDouble(parts[7]) > 100) {
                                throw new IllegalArgumentException("ERROR: " + parts[7] + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!");
                            }
                            if (Double.parseDouble(parts[8]) < 0) {
                                throw new IllegalArgumentException("ERROR: " + parts[8] + " is not a non-negative integer, premium fee must be a non-negative integer!");
                            }
                            Voyage voyage = new Voyage.Builder()
                                    .setType(parts[1])
                                    .setVoyageID(Integer.parseInt(parts[2]))
                                    .setFrom(parts[3])
                                    .setTo(parts[4])
                                    .setRows(Integer.parseInt(parts[5]))
                                    .setSeatPrice(Double.parseDouble(parts[6]))
                                    .setRefundCut(Double.parseDouble(parts[7]))
                                    .setPremiumFee(Double.parseDouble(parts[8]))
                                    .build();
                            FileIO.writeToFile(outputPath, "Voyage " + voyage.getVoyageID() + " was initialized as a premium (1+2) voyage from " + voyage.getFrom() + " to " + voyage.getTo() + " with " + String.format(Locale.US, "%.2f", voyage.getSeatPrice()) + " TL priced " + voyage.getRows() * 2 + " regular seats and " + String.format(Locale.US, "%.2f", voyage.getSeatPrice() * (100 + Double.parseDouble(parts[8])) / 100) + " TL priced " + voyage.getRows() + " premium seats. Note that refunds will be " + parts[7] + "% less than the paid amount.", true, true);
                        } else {
                            FileIO.writeToFile(outputPath, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
                        }
                    } catch (IllegalArgumentException e) {
                        FileIO.writeToFile(outputPath, e.getMessage(), true, true);
                    } catch (Exception e) {
                        FileIO.writeToFile(outputPath, "ERROR: An error occurred while processing the INIT_VOYAGE command.", true, true);
                    }
                    break;
                case "SELL_TICKET":
                    try {
                        if (parts.length != 3) {
                            throw new Exception();
                        }
                        int voyageID = Integer.parseInt(parts[1]);
                        boolean found = false;
                        for (Voyage voyage : Voyage.voyages) {
                            if (voyage.getVoyageID() == voyageID) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            throw new IllegalArgumentException("ERROR: There is no voyage with ID of " + voyageID + "!");
                        }
                        Voyage currentVoyage = null;
                        ArrayList<Integer> seatNumbers = new ArrayList<>();
                        for (String seatNumber : parts[2].split("_")) {
                            seatNumbers.add(Integer.parseInt(seatNumber));
                        }
                        for (Voyage voyage : Voyage.voyages) {
                            if (voyage.getVoyageID() == voyageID) {
                                voyage.sellTicket(seatNumbers, outputPath);
                                currentVoyage = voyage;
                            }
                        }
                        FileIO.writeToFile(outputPath, "Seat " + parts[2].replace("_", "-") + " of the Voyage " + voyageID + " from " + currentVoyage.getFrom() + " to " + currentVoyage.getTo() + " was successfully sold for " + String.format(Locale.US, "%.2f", seatNumbers.size() * currentVoyage.getSeatPrice()) + " TL.", true, true);
                    } catch (IllegalArgumentException e) {
                        FileIO.writeToFile(outputPath, e.getMessage(), true, true);
                    } catch (Exception e) {
                        FileIO.writeToFile(outputPath, "ERROR: Erroneous usage of \"SELL_TICKET\" command!", true, true);
                    }
                    break;
                case "REFUND_TICKET": {
                    try {
                        int voyageID = Integer.parseInt(parts[1]);
                        ArrayList<Integer> seatNumbers = new ArrayList<>();
                        for (String seatNumber : parts[2].split("_")) {
                            seatNumbers.add(Integer.parseInt(seatNumber));
                        }
                        for (Voyage voyage : Voyage.voyages) {
                            if (voyage.getVoyageID() == voyageID) {
                                if (voyage instanceof Minibus) {
                                    FileIO.writeToFile(outputPath, "ERROR: Minibus tickets are not refundable!", true, true);
                                    continue commandLoop;
                                }
                                voyage.refundTicket(voyageID, seatNumbers, outputPath);
                                FileIO.writeToFile(outputPath, "Seat " + parts[2].replace("_", "-") + " of the Voyage " + voyageID + " from " + voyage.getFrom() + " to " + voyage.getTo() + " was successfully refunded for " + String.format(Locale.US, "%.2f", seatNumbers.size() * voyage.calculateRefund(seatNumbers.get(0))) + " TL.", true, true);
                                continue commandLoop;
                            }
                        }
                        FileIO.writeToFile(outputPath, "ERROR: There is no voyage with ID of " + voyageID + "!", true, true);
                    } catch (IllegalArgumentException e) {
                        FileIO.writeToFile(outputPath, e.getMessage(), true, true);
                    } catch (Exception e) {
                        FileIO.writeToFile(outputPath, "ERROR: Erroneous usage of \"REFUND_TICKET\" command!", true, true);
                    }
                    break;
                }
                case "PRINT_VOYAGE":
                    try {
                        int voyageID = Integer.parseInt(parts[1]);
                        if (voyageID <= 0) {
                            throw new NumberFormatException(voyageID + "");
                        }
                        for (Voyage voyage : Voyage.voyages) {
                            if (voyage.getVoyageID() == voyageID) {
                                voyage.printVoyage(outputPath);
                                FileIO.writeToFile(outputPath, "Revenue: " + String.format(Locale.US, "%.2f", voyage.getRevenue()), true, true);
                            }
                        }
                    } catch (NumberFormatException e) {
                        FileIO.writeToFile(outputPath, "ERROR: " + e.getMessage() + " is not a positive integer, ID of a voyage must be a positive integer!", true, true);
                        break;
                    } catch (Exception e) {
                        FileIO.writeToFile(outputPath, "ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!", true, true);
                    }
                    break;
                case "CANCEL_VOYAGE": {
                    try {
                        if (parts.length != 2) {
                            throw new Exception();
                        }
                        int voyageID = Integer.parseInt(parts[1]);
                        for (Voyage voyage : Voyage.voyages) {
                            if (voyage.getVoyageID() == voyageID) {
                                FileIO.writeToFile(outputPath, "Voyage " + voyageID + " was successfully cancelled!", true, true);
                                FileIO.writeToFile(outputPath, "Voyage details can be found below:", true, true);
                                voyage.printVoyage(outputPath);
                                ArrayList<Integer> seatNumbers = new ArrayList<>();
                                for (int i = 0; i < voyage.seats.length; i++) {
                                    if (voyage.seats[i]) {
                                        seatNumbers.add(i + 1);
                                    }
                                }
                                voyage.refundTicket(voyageID, seatNumbers, outputPath);
                                FileIO.writeToFile(outputPath, "Revenue: " + String.format(Locale.US, "%.2f", voyage.getRevenue()), true, true);
                                Voyage.voyages.remove(voyage);
                                continue commandLoop;
                            }
                        }
                        FileIO.writeToFile(outputPath, "ERROR: There is no voyage with ID of " + voyageID + "!", true, true);
                        break;
                    } catch (Exception e) {
                        FileIO.writeToFile(outputPath, "ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!", true, true);
                        break;
                    }
                }
                default:
                    FileIO.writeToFile(outputPath, "ERROR: There is no command namely " + parts[0] + "!", true, true);
                    break;
            }
        }
        // print z report if the last command is not z report
        if (!commands[commands.length - 1].equals("Z_REPORT")) {
            FileIO.writeToFile(outputPath, "Z Report:", true, true);
            FileIO.writeToFile(outputPath, "----------------", true, true);
            if (Voyage.voyages.isEmpty()) {
                FileIO.writeToFile(outputPath, "No Voyages Available!", true, true);
                FileIO.writeToFile(outputPath, "----------------", true, true);
                return;
            }
            Voyage.voyages.sort(Comparator.comparing(Voyage::getVoyageID));
            for (Voyage voyage : Voyage.voyages) {
                voyage.printVoyage(outputPath);
                FileIO.writeToFile(outputPath, "Revenue: " + String.format(Locale.US, "%.2f", voyage.getRevenue()), true, true);
                FileIO.writeToFile(outputPath, "----------------", true, true);
            }
        }
    }
}