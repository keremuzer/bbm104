/**
 * Wallpaper class that extends Decoration
 */
public class Wallpaper extends Decoration{
    public Wallpaper(String name, int price) {
        super(name, "Wallpaper", price);
    }

    @Override
    int calculateCost(double area) {
        return (int) Math.ceil(area * getPrice());
    }

    public int getTiles(double area) {
        return 0;
    }
}
