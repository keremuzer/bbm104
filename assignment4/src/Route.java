import java.util.ArrayList;

/**
 * Represents a route in the map.
 */
public class Route {
    private int distance;
    private Road road;
    private String point;
    private ArrayList<Road> roadsTaken;

    /**
     * Constructor for the Route class.
     *
     * @param distance   The distance from the starting point
     * @param road       The last road taken
     * @param point      The current point
     * @param roadsTaken The roads taken in the route
     */
    public Route(int distance, Road road, String point, ArrayList<Road> roadsTaken) {
        this.distance = distance;
        this.road = road;
        this.point = point;
        this.roadsTaken = new ArrayList<>(roadsTaken);
        if (road != null) {
            this.roadsTaken.add(road);
        }
    }

    public int getDistance() {
        return distance;
    }

    public String getPoint() {
        return point;
    }

    public ArrayList<Road> getRoadsTaken() {
        return roadsTaken;
    }
}