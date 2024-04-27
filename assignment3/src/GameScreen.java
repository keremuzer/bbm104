import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class GameScreen {
    private Scene scene;
    private Group root;
    private Player player;
    private AnimationTimer timer;

    public GameScreen() {
        this.root = new Group();
        this.scene = new Scene(root, 800, 600);
        scene.setFill(javafx.scene.paint.Paint.valueOf("#004873"));
        player = new Player();
        root.getChildren().add(player);
        handleKeyPress();
        handleKeyRelease();
        startGame();
    }

    public void handleKeyPress() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    player.setVelocity(0, -1.5);
                    break;
                case DOWN:
                    player.setVelocity(0, 1.5);
                    break;
                case LEFT:
                    player.setVelocity(-1.5, 0);
                    break;
                case RIGHT:
                    player.setVelocity(1.5, 0);
                    break;
            }
        });
    }

    public void handleKeyRelease() {
        scene.setOnKeyReleased(e -> {
            player.setVelocity(0, 0);
        });
    }

    public void startGame() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.move();
            }
        };
        timer.start();
    }

    public Scene getScene() {
        return this.scene;
    }
}
