import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start the game.
     * @param stage The stage to display the game.
     */
    public void start(Stage stage) {
        GameScreen gameScreen = new GameScreen();
        Scene scene = gameScreen.getScene();

        stage.setResizable(false);
        stage.setTitle("HU-Load");

        stage.setScene(scene);
        stage.show();
    }
}