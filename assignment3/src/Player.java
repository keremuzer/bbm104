import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    private double xVelocity;
    private double yVelocity;
    private Image leftImg;
    private Image upImg;
    private Image downImg;

    public Player() {
        this.leftImg = new Image("assets/drill/drill_01.png");
        this.upImg = new Image("assets/drill/drill_24.png");
        this.downImg = new Image("assets/drill/drill_44.png");
        setImage(leftImg);
        this.setX(400);
        this.setY(10);
    }

    public void move() {
        this.setX(this.getX() + xVelocity);
        this.setY(this.getY() + yVelocity);
    }

    public void setVelocity(double x, double y) {
        this.xVelocity = x;
        this.yVelocity = y;
        if (x > 0) {
            setImage(leftImg);
            this.setScaleX(-0.9);
        } else if (x < 0) {
            setImage(leftImg);
            this.setScaleX(0.9);
        } else if (y < 0) {
            setImage(upImg);
        }
    }

    public Image getDownImg() {
        return downImg;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }
}
