public class Mineral extends Element {
    private int worth;
    private int weight;

    public Mineral(String imagePath, int worth, int weight) {
        super(imagePath, true);
        this.worth = worth;
        this.weight = weight;
    }
}
