import javafx.scene.Group;
import javafx.scene.Scene;

public class GameScreen {
    private Scene scene;
    private Group root;

    public GameScreen() {
        this.root = new Group();
        this.scene = new Scene(root, 800, 600);
        scene.setFill(javafx.scene.paint.Paint.valueOf("#004873"));
    }

    public Scene getScene() {
        return this.scene;
    }
}
