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

    public Slot[][] getSlots() {
        return slots;
    }
}
