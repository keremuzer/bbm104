/**
 * GymMealMachine class is a class that represents the gym meal machine in the gym.
 * It has a 6x4 array of slots. Each slot can hold up to 10 products.
 */
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

    /**
     * Adds the given product to the machine.
     * If there is a slot that has the same product, it adds the product to that slot.
     * If there is no slot that has the same product, it adds the product to the first empty slot.
     *
     * @param product Product that is going to be added to the machine.
     * @return True if the product is added successfully, false if the machine is full.
     */
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

    /**
     * Removes the product from the given slot.
     *
     * @param slot Slot that the product is going to be removed from.
     */
    public void removeFromMachine(Slot slot) {
        slot.removeProduct();
    }

    /**
     * Prints the products in the machine to the given path.
     *
     * @param path Path of the output file.
     */
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

    /**
     * Checks if the machine is full.
     *
     * @return True if the machine is full, false if there is at least one empty slot.
     */
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
