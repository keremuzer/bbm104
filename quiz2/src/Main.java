import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Main <items> <decorate> <output>");
            return;
        }
        decorate(readClassrooms(args[0]), readDecorations(args[0]), args[1], args[2]);
    }

    private static ArrayList<Classroom> readClassrooms(String path) {
        String[] lines = FileIO.readFile(path, true, true);
        ArrayList<Classroom> classes = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\t");
            if (parts[0].equals("CLASSROOM")) {
                Classroom classroom = new Classroom.Builder()
                        .setName(parts[1])
                        .setShape(parts[2])
                        .setWidth(Integer.parseInt(parts[3]))
                        .setLength(Integer.parseInt(parts[4]))
                        .setHeight(Integer.parseInt(parts[5]))
                        .build();
                classes.add(classroom);
            }
        }
        return classes;
    }

    private static ArrayList<Decoration> readDecorations(String path) {
        String[] lines = FileIO.readFile(path, true, true);
        ArrayList<Decoration> decorations = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\t");
            if (parts[0].equals("DECORATION")) {
                Decoration decoration;
                if (parts[2].equals("Tile")) {
                    decoration = new Decoration.Builder()
                            .setName(parts[1])
                            .setType(parts[2])
                            .setPrice(Integer.parseInt(parts[3]))
                            .setArea(Integer.parseInt(parts[4]))
                            .build();
                } else {
                    decoration = new Decoration.Builder()
                            .setName(parts[1])
                            .setType(parts[2])
                            .setPrice(Integer.parseInt(parts[3]))
                            .build();
                }
                decorations.add(decoration);
            }
        }
        return decorations;
    }

    public static void decorate(ArrayList<Classroom> classes, ArrayList<Decoration> decorations, String inputPath, String outputPath) {
        String[] lines = FileIO.readFile(inputPath, true, true);
        int totalCost = 0;
        for (String line : lines) {
            String[] parts = line.split("\t");
            for (Classroom classroom : classes) {
                if (classroom.getName().equals(parts[0])){
                    int wallCost = 0;
                    int floorCost = 0;
                    String decorationType = "";
                    int tilesForFlooring = 0;
                    int tilesForWalls = 0;
                    for (Decoration decoration : decorations) {
                        if (decoration.getName().equals(parts[1])) {
                            decorationType = decoration.getType();
                            wallCost = decoration.calculateCost(classroom.calculateWallArea());
                            totalCost += wallCost;
                            tilesForWalls = decoration.getTiles(classroom.calculateWallArea());
                        }
                    }
                    for (Decoration decoration : decorations) {
                        if (decoration.getName().equals(parts[2])) {
                            floorCost = decoration.calculateCost(classroom.calculateFloorArea());
                            totalCost += floorCost;
                            tilesForFlooring = decoration.getTiles(classroom.calculateFloorArea());
                        }
                    }
                    if (decorationType.equals("Tile")) {
                        FileIO.writeToFile(outputPath,
                                "Classroom " + classroom.getName() + " used " + tilesForWalls + " Tiles for walls and used " + tilesForFlooring + " Tiles for flooring, these costed " + (wallCost + floorCost) + "TL.",
                                true,
                                true);
                    } else {
                        FileIO.writeToFile(outputPath,
                                "Classroom " + classroom.getName() + " used " + (int) Math.ceil(classroom.calculateWallArea()) + "m2 of " + decorationType + " for walls and used " + tilesForFlooring + " Tiles for flooring, these costed " + (wallCost + floorCost) + "TL.",
                                true,
                                true);
                    }
                }
            }
        }
        FileIO.writeToFile(outputPath,
                "Total price is: " + totalCost + "TL.",
                true,
                false);
    }
}