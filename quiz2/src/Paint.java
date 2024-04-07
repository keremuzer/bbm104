public class Paint extends Decoration{
    public Paint(String name, int price) {
        super(name, "Paint", price);
    }

    @Override
    int calculateCost(double area) {
        return (int) Math.ceil(area * getPrice());
    }

    public int getTiles(double area) {
        return 0;
    }
}
