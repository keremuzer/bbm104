public class Mineral extends Element {
    private final int worth;
    private final int weight;

    /**
     * Creates a new mineral.
     *
     * @param imagePath The path to the image file.
     * @param worth     The worth of the mineral.
     * @param weight    The weight of the mineral.
     */
    public Mineral(String imagePath, int worth, int weight) {
        super(imagePath, true);
        this.worth = worth;
        this.weight = weight;
    }

    public int getWorth() {
        return worth;
    }

    public int getWeight() {
        return weight;
    }
}
