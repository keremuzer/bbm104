public class Road {
    private String point1;
    private String point2;
    private int length;
    private int id;

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