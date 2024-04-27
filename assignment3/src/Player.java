import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    private double x;
    private double y;
    private String leftImg;
    private String rightImg;
    private String upImg;
    private String downImg;

    public Player() {
        this.leftImg = "assets/drill/drill_01.png";
        this.rightImg = "assets/drill/drill_02.png";
        this.upImg = "assets/drill/drill_03.png";
        this.downImg = "assets/drill/drill_04.png";
        setImage(new Image(leftImg));
        this.setX(400);
        this.setY(300);
    }

    public void move() {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }

    public void setVelocity(double x, double y) {
        this.x = x;
        this.y = y;

    }
}
