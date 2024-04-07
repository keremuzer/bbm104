/**
 * CircularClassroom class that extends Classroom class
 * and implements calculateWallArea and calculateFloorArea methods.
 */
public class CircularClassroom extends Classroom{
    public CircularClassroom(String name, int width, int length, int height) {
        super(name, "Circle", width, length, height);
    }

    @Override
    public double calculateWallArea() {
        return Math.PI * getWidth() * getHeight();
    }

    @Override
    public double calculateFloorArea() {
        return Math.PI * Math.pow((double) getWidth() / 2, 2);
    }
}
