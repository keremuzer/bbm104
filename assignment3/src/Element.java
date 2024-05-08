import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Element extends ImageView {
    private final boolean isDrillable;

    /**
     * Creates a new element.
     *
     * @param imagePath   The path to the image file.
     * @param isDrillable Whether the element can be drilled.
     */
    public Element(String imagePath, boolean isDrillable) {
        setImage(new Image(imagePath));
        this.isDrillable = isDrillable;
    }

    public boolean isDrillable() {
        return isDrillable;
    }
}
