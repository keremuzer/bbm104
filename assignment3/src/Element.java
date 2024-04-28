import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Element extends ImageView {
    public Element(String imagePath) {
        setImage(new Image(imagePath));
    }
}
