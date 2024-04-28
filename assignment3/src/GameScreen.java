import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class GameScreen {
    private Scene scene;
    private Group root;
    private Player player;
    private AnimationTimer timer;
    private Rectangle underground;
    private Element[][] elements;

    public GameScreen() {
        this.root = new Group();
        this.scene = new Scene(root, 800, 600);
        scene.setFill(javafx.scene.paint.Paint.valueOf("#004873"));
        createUnderground();
        player = new Player();
        root.getChildren().add(player);
        handleKeyPress();
        handleKeyRelease();
        startGame();
    }

    public void createUnderground() {
        underground = new Rectangle(800, 500);
        underground.setFill(javafx.scene.paint.Paint.valueOf("#8B4513"));
        underground.setY(103);
        root.getChildren().add(underground);
        elements = new Element[16][10];
        Random random = new Random();
        Element element;
        for (int i = 0; i < 800; i += 50) {
            for (int j = 0; j < 500; j += 50) {
                if (j == 0) {
                    element = new Dirt("assets/underground/top_01.png");
                } else if (i == 0 || i == 750 || j == 450) {
                    element = new Obstacle("assets/underground/obstacle_01.png");
                } else {
                    int rand = random.nextInt(100);
                    if (rand < 85) {
                        element = new Dirt("assets/underground/soil_01.png");
                    } else if (rand < 90) {
                        element = new Lava("assets/underground/lava_01.png");
                    } else if (rand < 95) {
                        element = new Mineral("assets/underground/valuable_silverium.png", 100, 10);
                    } else if (rand < 98) {
                        element = new Mineral("assets/underground/valuable_goldium.png", 250, 20);
                    } else {
                        element = new Mineral("assets/underground/valuable_emerald.png", 5000, 60);
                    }
                }
                element.setX(i);
                element.setY(j + 100);
                elements[i / 50][j / 50] = element;
                root.getChildren().add(element);
            }
        }
    }

    public void handleKeyPress() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    player.setVelocity(0, -2.5);
                    break;
                case DOWN:
                    player.setVelocity(0, 2.5);
                    player.setImage(player.getDownImg());
                    break;
                case LEFT:
                    player.setVelocity(-2.5, 0);
                    break;
                case RIGHT:
                    player.setVelocity(2.5, 0);
                    break;
            }
        });
    }

    public void handleKeyRelease() {
        scene.setOnKeyReleased(e -> player.setVelocity(0, 1));
    }

    public void startGame() {
        player.setVelocity(0, 1);
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
