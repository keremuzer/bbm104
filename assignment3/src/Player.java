import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    private Image leftImg;
    private Image upImg;
    private Image downImg;
    private int gridX;
    private int gridY;

    public Player() {
        this.leftImg = new Image("assets/drill/drill_01.png");
        this.upImg = new Image("assets/drill/drill_24.png");
        this.downImg = new Image("assets/drill/drill_44.png");
        setImage(leftImg);
        this.setX(431);
        this.setY(-8);
        this.gridX = 9;
        this.gridY = 0;
    }

    public void move(double x, double y) {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }

    public Image getDownImg() {
        return downImg;
    }

    public Image getUpImg() {
        return upImg;
    }

    public Image getLeftImg() {
        return leftImg;
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
}
