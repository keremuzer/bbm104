import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Element extends ImageView {
    private boolean isDrillable;

    public Element(String imagePath, boolean isDrillable) {
        setImage(new Image(imagePath));
        this.isDrillable = isDrillable;
    }

    public boolean isDrillable() {
        return isDrillable;
    }
}
