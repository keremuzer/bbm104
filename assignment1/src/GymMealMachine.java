public class GymMealMachine {
    private final Slot[][] slots;

    public GymMealMachine() {
        slots = new Slot[6][4];
        for (int k = 0; k < 6; k++) {
            for (int l = 0; l < 4; l++) {
                slots[k][l] = new Slot();
            }
        }
    }

    public boolean addToMachine(Product product) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (slots[i][j].getNumProducts() < 10 && slots[i][j].getProducts()[0].getName().equals(product.getName())) {
                    slots[i][j].addProduct(product);

                    return true;
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (slots[i][j].getNumProducts() == 0) {
                    slots[i][j].addProduct(product);

                    return true;
                }
            }
        }
        return false;
    }

    public void removeFromMachine(Slot slot) {
        slot.removeProduct();
    }

    public void printGMM(String path) {
        FileIO.writeToFile(path, "-----Gym Meal Machine-----", true, true);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (slots[i][j].getNumProducts() == 0) {
                    FileIO.writeToFile(path, "___(0, 0)___", true, false);
                    continue;
                }
                FileIO.writeToFile(path, slots[i][j].getProducts()[0].getName() + "(" + slots[i][j].getProducts()[0].getCalorie() + ", " + slots[i][j].getNumProducts() + ")___", true, false);
            }
            FileIO.writeToFile(path, "", true, true);
        }
        FileIO.writeToFile(path, "----------", true, true);
    }

    public boolean isFull() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (slots[i][j].getNumProducts() < 10) {
                    return false;
                }
            }
        }
        return true;
    }

    public Slot[][] getSlots() {
        return slots;
    }
}
