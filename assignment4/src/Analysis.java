import java.util.*;

public class Analysis {
    private String inputPath;
    private String outputPath;
    private ArrayList<Route> routeList;
    private LinkedHashMap<String, Route> routeMap;
    private String start;
    private String destination;
    private ArrayList<String> roadStrings = new ArrayList<>();

    public Analysis(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        routeList = new ArrayList<>();
        routeMap = new LinkedHashMap<>();
        start = "";
        destination = "";
    }

    public Route calculateFastestRoute(ArrayList<Road> roads) {
        routeMap.clear();
        routeList.clear();
        routeList.add(new Route(0, null, start, new ArrayList<>()));

        HashSet<String> visited = new HashSet<>();

        while (!routeList.isEmpty()) {
            // Sort the routeList to get the route with the smallest distance
            routeList.sort((route1, route2) -> {
                if (route1.getDistance() != route2.getDistance()) {
                    return Integer.compare(route1.getDistance(), route2.getDistance());
                } else {
                    return Integer.compare(route1.getRoad().getId(), route2.getRoad().getId());
                }
            });

            // Get the route with the smallest distance
            Route currentRoute = routeList.remove(0);
            String currentPoint = currentRoute.getPoint();

            if (visited.contains(currentPoint)) {
                continue;
            }
            visited.add(currentPoint);

            // If we reached the destination, return the route
            if (currentPoint.equals(destination)) {
                return currentRoute;
            }

            // Add new routes to the routeList
            for (Road road : roads) {
                if (road.getPoint1().equals(currentPoint) && !visited.contains(road.getPoint2())) {
                    int newDistance = currentRoute.getDistance() + road.getLength();
                    Route newRoute = new Route(newDistance, road, road.getPoint2(), currentRoute.getRoadsTaken());
                    routeList.add(newRoute);
                } else if (road.getPoint2().equals(currentPoint) && !visited.contains(road.getPoint1())) {
                    int newDistance = currentRoute.getDistance() + road.getLength();
                    Route newRoute = new Route(newDistance, road, road.getPoint1(), currentRoute.getRoadsTaken());
                    routeList.add(newRoute);
                }
            }
        }
        return null; // If no route is found
    }

    public ArrayList<Road> calculateBarelyConnectedMap() {
        ArrayList<Road> roads = readInput(inputPath);
        ArrayList<Road> barelyConnectedMap = new ArrayList<>();

        // Create a list of points from the roads and sort it alphabetically
        ArrayList<String> points = new ArrayList<>();
        for (Road road : roads) {
            if (!points.contains(road.getPoint1())) {
                points.add(road.getPoint1());
            }
            if (!points.contains(road.getPoint2())) {
                points.add(road.getPoint2());
            }
        }
        Collections.sort(points);

        // Create a list to hold roads
        ArrayList<Road> roadList = new ArrayList<>();

        // Start with the point that is smallest in alphabetical order and add all of its roads to the list
        String startPoint = points.get(0);
        for (Road road : roads) {
            if (road.getPoint1().equals(startPoint) || road.getPoint2().equals(startPoint)) {
                roadList.add(road);
            }
        }

        // Create a set to hold the points that are already appended to the barely connected map
        HashSet<String> appendedPoints = new HashSet<>();
        appendedPoints.add(startPoint);

        while (!roadList.isEmpty()) {
            // Sort the list in increasing order according to their length (use their IDs for determining which one is smaller if the lengths are equal)
            roadList.sort((road1, road2) -> {
                if (road1.getLength() != road2.getLength()) {
                    return Integer.compare(road1.getLength(), road2.getLength());
                } else {
                    return Integer.compare(road1.getId(), road2.getId());
                }
            });

            // Select the smallest road
            Road road = roadList.remove(0);

            // If both ends of the road are already appended, it means that appending it causes a cycle
            if (appendedPoints.contains(road.getPoint1()) && appendedPoints.contains(road.getPoint2())) {
                continue;
            }

            // Append the road to the barely connected map
            barelyConnectedMap.add(road);

            // Append the point that is not already appended to the appendedPoints set
            String appendedPoint;
            if (!appendedPoints.contains(road.getPoint1())) {
                appendedPoint = road.getPoint1();
                appendedPoints.add(appendedPoint);
            } else {
                appendedPoint = road.getPoint2();
                appendedPoints.add(appendedPoint);
            }

            // Append all the roads that are connected to any point that has been appended to the Barely Connected Map
            for (Road r : roads) {
                if ((appendedPoints.contains(r.getPoint1()) || appendedPoints.contains(r.getPoint2())) && !roadList.contains(r)) {
                    roadList.add(r);
                }
            }
        }
        return barelyConnectedMap;
    }

    public ArrayList<Road> readInput(String inputPath) {
        ArrayList<Road> roads = new ArrayList<>();
        String[] lines = FileIO.readFile(inputPath, true, true);
        start = lines[0].split("\t")[0];
        destination = lines[0].split("\t")[1];
        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].split("\t");
            // Store the road as a string
            roadStrings.add(lines[i]);
            // Create a road for each direction
            roads.add(new Road(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
            roads.add(new Road(parts[1], parts[0], Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
        }
        return roads;
    }

    public void printOutput() {
        FileIO.writeToFile("src/output.txt", "", false, false);
        ArrayList<Road> roads = readInput(inputPath);
        Route route = calculateFastestRoute(roads);
        FileIO.writeToFile(outputPath, "Fastest Route from " + start + " to " + destination + " (" + route.getDistance() + " KM)" + ":", true, true);
        printFastestRoute(roads);
        printBarelyConnectedMap();
        ArrayList<Road> barelyConnectedMap = calculateBarelyConnectedMap();
        Route fastestBCMRoute = calculateFastestRoute(barelyConnectedMap);
        FileIO.writeToFile(outputPath, "Fastest Route from " + start + " to " + destination + " on Barely Connected Map (" + fastestBCMRoute.getDistance() + " KM)" + ":", true, true);
        printFastestRoute(barelyConnectedMap);
    }

    public void printFastestRoute(ArrayList<Road> roads) {
        Route route = calculateFastestRoute(roads);
        ArrayList<Road> roadsTaken = route.getRoadsTaken();
        printRoad(roadsTaken);
    }

    public void printBarelyConnectedMap() {
        ArrayList<Road> barelyConnectedMap = calculateBarelyConnectedMap();
        // Sort the roads in barelyConnectedMap according to their length, and if they are equal in length, use their IDs as a tiebreaker
        barelyConnectedMap.sort((road1, road2) -> {
            if (road1.getLength() != road2.getLength()) {
                return Integer.compare(road1.getLength(), road2.getLength());
            } else {
                return Integer.compare(road1.getId(), road2.getId());
            }
        });
        FileIO.writeToFile(outputPath, "Roads of Barely Connected Map is:", true, true);
        printRoad(barelyConnectedMap);
    }

    private void printRoad(ArrayList<Road> roads) {
        for (Road road : roads) {
            String roadStr = road.getPoint1() + "\t" + road.getPoint2() + "\t" + road.getLength() + "\t" + road.getId();
            if (roadStrings.contains(roadStr)) {
                FileIO.writeToFile(outputPath, roadStr, true, true);
            } else {
                FileIO.writeToFile(outputPath, road.getPoint2() + "\t" + road.getPoint1() + "\t" + road.getLength() + "\t" + road.getId(), true, true);
            }
        }
    }


}