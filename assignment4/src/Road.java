/**
 * Road class represents a road between two points in the map.
 */
public class Road {
    private String point1;
    private String point2;
    private int length;
    private int id;

    /**
     * Constructor for the Road class.
     *
     * @param point1 The first point of the road
     * @param point2 The second point of the road
     * @param length The length of the road
     * @param id     The id of the road
     */
    public Road(String point1, String point2, int length, int id) {
        this.point1 = point1;
        this.point2 = point2;
        this.length = length;
        this.id = id;
    }

    public String getPoint1() {
        return point1;
    }

    public String getPoint2() {
        return point2;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }
}