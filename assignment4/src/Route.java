import java.util.ArrayList;

public class Route {
    private int distance;
    private Road road;
    private String point;
    private ArrayList<Road> roadsTaken;

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

    public Road getRoad() {
        return road;
    }

    public String getPoint() {
        return point;
    }

    public ArrayList<Road> getRoadsTaken() {
        return roadsTaken;
    }
}