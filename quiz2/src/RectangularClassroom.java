public class RectangularClassroom extends Classroom{
    public RectangularClassroom(String name, int width, int length, int height) {
        super(name, "Rectangle", width, length, height);
    }

    @Override
    public double calculateWallArea() {
        return 2 * (getWidth() * getHeight() + getLength() * getHeight());
    }

    @Override
    public double calculateFloorArea() {
        return getWidth() * getLength();
    }
}
