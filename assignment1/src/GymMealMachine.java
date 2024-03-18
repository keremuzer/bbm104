public class GymMealMachine {
    private Slot[][] slots;

    public GymMealMachine() {
        slots = new Slot[6][4];
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
}
