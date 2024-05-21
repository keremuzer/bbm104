import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

    public Route calculateFastestRoute() {
        ArrayList<Road> roads = readInput(inputPath);
        routeList.add(new Route(0, null, start, new ArrayList<>()));

        while (!routeMap.containsKey(destination)) {
            routeList.sort((road1, road2) -> {
                if (road1.getDistance() != road2.getDistance()) {
                    return Integer.compare(road1.getDistance(), road2.getDistance());
                } else {
                    return Integer.compare(road1.getRoad().getId(), road2.getRoad().getId());
                }
            });

            Route currentRoute = routeList.remove(0);

            for (Road road : roads) {
                if ((road.getPoint1().equals(currentRoute.getPoint()) || road.getPoint2().equals(currentRoute.getPoint())) && !routeMap.containsKey(road.getPoint2())) {
                    int newDistance = currentRoute.getDistance() + road.getLength();
                    Route newRoute = new Route(newDistance, road, road.getPoint2(), currentRoute.getRoadsTaken());
                    routeList.add(newRoute);
                }
            }
            routeMap.put(currentRoute.getPoint(), currentRoute);
        }
        return routeMap.get(destination);
    }

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

    public void printFastestRoute() {
        calculateFastestRoute();
        Route route = routeMap.get(destination);
        ArrayList<Road> roadsTaken = route.getRoadsTaken();
        FileIO.writeToFile(outputPath, "Fastest Route from " + start + " to " + destination + " (" + route.getDistance() + " KM)" + ":", false, true);
        for (Road road : roadsTaken) {
            String roadStr = road.getPoint1() + "\t" + road.getPoint2() + "\t" + road.getLength() + "\t" + road.getId();
            if (roadStrings.contains(roadStr)) {
                FileIO.writeToFile(outputPath, roadStr, true, true);
            } else {
                FileIO.writeToFile(outputPath, road.getPoint2() + "\t" + road.getPoint1() + "\t" + road.getLength() + "\t" + road.getId(), true, true);
            }
        }
    }
}