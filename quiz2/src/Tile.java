public class Tile extends Decoration{
    private final int area;
    public Tile(String name, int price, int area) {
        super(name, "Tile", price);
        this.area = area;
    }

    @Override
    int calculateCost(double area) {
        return getTiles(area) * getPrice();
    }

    public int getTiles(double area) {
        return (int) Math.ceil(area / this.area);
    }
}
