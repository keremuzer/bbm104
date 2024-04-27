import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView{
    public Player() {
        super(new Image("assets/drill/drill_01.png"));
        this.setX(400);
        this.setY(300);
    }
}
