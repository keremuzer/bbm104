public abstract class Decoration {
    private final String name;
    private final String type;
    private final int price;

    abstract int calculateCost(double area);

    public Decoration(String name, String type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public abstract int getTiles(double area);

    public static class Builder{
        private String name;
        private String type;
        private int price;
        private int area;


        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setArea(int area) {
            this.area = area;
            return this;
        }

        public Decoration build() {
            if (type.equalsIgnoreCase("paint")) {
                return new Paint(name, price);
            } else if (type.equalsIgnoreCase("wallpaper")) {
                return new Wallpaper(name, price);
            } else if (type.equalsIgnoreCase("tile")) {
                return new Tile(name, price, area);
            } else {
                return null;
            }
        }
    }
}
