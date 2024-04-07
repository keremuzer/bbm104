/**
 * Abstract class for a classroom.
 * Contains the name, shape, width, length, and height of the classroom.
 * Contains an abstract method to calculate the wall area of the classroom.
 * Contains an abstract method to calculate the floor area of the classroom.
 * Contains a Builder class to build a classroom object.
 */
public abstract class Classroom {
    private final String name;
    private final String shape;
    private final int width;
    private final int length;
    private final int height;

    public Classroom(String name, String shape, int width, int length, int height) {
        this.name = name;
        this.shape = shape;
        this.width = width;
        this.length = length;
        this.height = height;
    }

    public abstract double calculateWallArea();

    public abstract double calculateFloorArea();

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Builder class to build a classroom object.
     */
    public static class Builder {
        private String name;
        private String shape;
        private int width;
        private int length;
        private int height;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setShape(String shape) {
            this.shape = shape;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setLength(int length) {
            this.length = length;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        /**
         * Builds a classroom object based on the given parameters.
         * @return Classroom object.
         */
        public Classroom build() {
            if (shape.equalsIgnoreCase("rectangle")) {
                return new RectangularClassroom(name, width, length, height);
            } else if (shape.equalsIgnoreCase("circle")) {
                return new CircularClassroom(name, width, length, height);
            } else {
                return null;
            }
        }
    }
}
