import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Player extends ImageView {
    private final Image leftImg;
    private final Image upImg;
    private final Image downImg;
    private int money;
    private int haul;
    private double fuel;
    private int gridX;
    private int gridY;

    /**
     * Create a new player.
     */
    public Player() {
        this.money = 0;
        this.fuel = 10000;
        this.leftImg = new Image("assets/drill/drill_01.png");
        this.upImg = new Image("assets/drill/drill_24.png");
        this.downImg = new Image("assets/drill/drill_44.png");
        setImage(leftImg);
        this.setX(431);
        this.setY(-8);
        this.gridX = 9;
        this.gridY = 0;
        this.setScaleX(0.9);
        this.setScaleY(0.9);
    }

    /**
     * Collect money.
     *
     * @param worth The worth of the mineral.
     */
    public void collect(int worth) {
        money += worth;
    }

    /**
     * Move the player.
     *
     * @param x The x coordinate to move.
     * @param y The y coordinate to move.
     */
    public void move(double x, double y) {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }

    /**
     * Update the player's image based on the key pressed.
     *
     * @param code The key code.
     */
    public void updatePlayerImage(KeyCode code) {
        switch (code) {
            case UP:
                setImage(upImg);
                break;
            case DOWN:
                setImage(downImg);
                break;
            case LEFT:
                setScaleX(0.9);  // make the player face left
                setImage(leftImg);
                break;
            case RIGHT:
                setScaleX(-0.9); // make the player face right
                setImage(leftImg);
                break;
        }
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public double getMoney() {
        return money;
    }

    public int getHaul() {
        return haul;
    }

    public void setHaul(int haul) {
        this.haul = haul;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
}
