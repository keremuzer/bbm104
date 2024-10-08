import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * The Analysis class performs various operations on a map, including reading input,
 * calculating the fastest route, generating a barely connected map, and printing results.
 */
public class Analysis {
    private String inputPath;
    private String outputPath;
    private ArrayList<Route> routeList;
    private LinkedHashMap<String, Route> routeMap;
    private String start;
    private String destination;
    private ArrayList<String> roadStrings = new ArrayList<>();

    /**
     * Constructor for the Analysis class.
     *
     * @param inputPath  the path to the input file
     * @param outputPath the path to the output file
     */
    public Analysis(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.routeList = new ArrayList<>();
        this.routeMap = new LinkedHashMap<>();
        this.start = "";
        this.destination = "";
    }

    /**
     * Reads the input, calculates the fastest routes, generates the barely connected map,
     * and prints the results and analysis to the output file.
     */
    public void printOutput() {
        FileIO.writeToFile(outputPath, "", false, false); // Clear the output file
        ArrayList<Road> roads = readInput(inputPath);
        Route route = calculateFastestRoute(roads);
        FileIO.writeToFile(outputPath, "Fastest Route from " + start + " to " + destination + " (" + route.getDistance() + " KM):", true, true);
        printFastestRoute(roads);
        printBarelyConnectedMap();
        ArrayList<Road> barelyConnectedMap = calculateBarelyConnectedMap();
        Route fastestBCMRoute = calculateFastestRoute(barelyConnectedMap);
        FileIO.writeToFile(outputPath, "Fastest Route from " + start + " to " + destination + " on Barely Connected Map (" + fastestBCMRoute.getDistance() + " KM):", true, true);
        printFastestRoute(barelyConnectedMap);
        printAnalysis(roads, barelyConnectedMap);
    }

    /**
     * Calculates the fastest route between the start and destination points.
     *
     * @param roads the list of roads in the map
     * @return the fastest Route object
     */
    private Route calculateFastestRoute(ArrayList<Road> roads) {
        routeMap.clear();
        routeList.clear();
        routeList.add(new Route(0, null, start, new ArrayList<>()));

        HashSet<String> visited = new HashSet<>();

        // Sort the roads connected to each city based on their length and ID
        roads.sort((road1, road2) -> {
            if (road1.getLength() != road2.getLength()) {
                return Integer.compare(road1.getLength(), road2.getLength());
            } else {
                return Integer.compare(road1.getId(), road2.getId());
            }
        });

        while (!routeList.isEmpty()) {
            // Sort the routeList based on the distance and the order they were added
            Collections.sort(routeList, (route1, route2) -> {
                if (route1.getDistance() != route2.getDistance()) {
                    return Integer.compare(route1.getDistance(), route2.getDistance());
                } else {
                    return Integer.compare(routeList.indexOf(route1), routeList.indexOf(route2));
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
                // If the road's starting point is the current point and the destination is not visited
                if (road.getPoint1().equals(currentPoint) && !visited.contains(road.getPoint2())) {
                    int newDistance = currentRoute.getDistance() + road.getLength();
                    Route newRoute = new Route(newDistance, road, road.getPoint2(), currentRoute.getRoadsTaken());
                    routeList.add(newRoute);
                    // If the road's ending point is the current point and the starting point is not visited
                } else if (road.getPoint2().equals(currentPoint) && !visited.contains(road.getPoint1())) {
                    int newDistance = currentRoute.getDistance() + road.getLength();
                    Route newRoute = new Route(newDistance, road, road.getPoint1(), currentRoute.getRoadsTaken());
                    routeList.add(newRoute);
                }
            }
        }
        return null; // return null if no route is found
    }

    /**
     * Calculates the Barely Connected Map from the original map.
     *
     * @return the list of roads in the barely connected map
     */
    private ArrayList<Road> calculateBarelyConnectedMap() {
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
            // Sort the list according to their length use their IDs as a tiebreaker if they have the same length
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

    /**
     * Reads the input file and creates a list of Road objects.
     *
     * @param inputPath the path to the input file
     * @return the list of roads read from the input file
     */
    private ArrayList<Road> readInput(String inputPath) {
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

    /**
     * Prints the fastest route to the output file.
     *
     * @param roads the list of roads in the map
     */
    private void printFastestRoute(ArrayList<Road> roads) {
        Route route = calculateFastestRoute(roads);
        ArrayList<Road> roadsTaken = route.getRoadsTaken();
        printRoad(roadsTaken);
    }

    /**
     * Prints the Barely Connected Map to the output file.
     */
    private void printBarelyConnectedMap() {
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

    /**
     * Prints the given list of roads to the output file.
     *
     * @param roads the list of roads to be printed
     */
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

    /**
     * Prints an analysis comparing the original map and the Barely Connected Map.
     *
     * @param originalRoads the list of roads in the original map
     * @param bcmRoads      the list of roads in the barely connected map
     */
    private void printAnalysis(ArrayList<Road> originalRoads, ArrayList<Road> bcmRoads) {
        // Calculate the total length of the original map
        int originalMapLength = originalRoads.stream().mapToInt(Road::getLength).sum();
        // Calculate the total length of the barely connected map
        int bcmMapLength = bcmRoads.stream().mapToInt(Road::getLength).sum();

        // Calculate the fastest route distance on the original map
        Route originalFastestRoute = calculateFastestRoute(originalRoads);
        int originalFastestRouteDistance = originalFastestRoute.getDistance();

        // Calculate the fastest route distance on the barely connected map
        Route bcmFastestRoute = calculateFastestRoute(bcmRoads);
        int bcmFastestRouteDistance = bcmFastestRoute.getDistance();

        // Calculate the ratios
        double materialUsageRatio = (double) bcmMapLength / originalMapLength * 2;
        double fastestRouteRatio = (double) bcmFastestRouteDistance / originalFastestRouteDistance;

        // Print the analysis
        FileIO.writeToFile(outputPath, "Analysis:", true, true);
        FileIO.writeToFile(outputPath, "Ratio of Construction Material Usage Between Barely Connected and Original Map: " + String.format("%.2f", materialUsageRatio), true, true);
        FileIO.writeToFile(outputPath, "Ratio of Fastest Route Between Barely Connected and Original Map: " + String.format("%.2f", fastestRouteRatio), true, false);
    }
}
