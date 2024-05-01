import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
        this.scene = new Scene(root, 800, 800);
        scene.setFill(javafx.scene.paint.Paint.valueOf("#004873"));
        createUnderground();
        player = new Player();
        player.setScaleX(0.9);
        player.setScaleY(0.9);
        root.getChildren().add(player);
        handleKeyPress();
        startGame();
    }

    public void createUnderground() {
        underground = new Rectangle(800, 700);
        underground.setFill(javafx.scene.paint.Paint.valueOf("#8B4513"));
        underground.setY(103);
        root.getChildren().add(underground);
        elements = new Element[16][16]; // Corresponds to 16 rows and columns in a 800x800 screen

        Random random = new Random();
        Element element;
        for (int i = 0; i < 800; i += 50) {
            for (int j = 100; j < 800; j += 50) { // Start from 100 to align with the underground Y
                int gridX = i / 50;
                int gridY = (j) / 50; // Adjust gridY to start from 0 at Y=100

                if (j == 100) {
                    element = new Dirt("assets/underground/top_01.png");
                } else if (i == 0 || i == 750 || j == 750) {
                    element = new Obstacle("assets/underground/obstacle_01.png");
                } else {
                    int rand = random.nextInt(100);
                    if (rand > 95 && j >= 400) { // Adjusting depth checks to grid system
                        element = new Mineral("assets/underground/valuable_emerald.png", 5000, 60);
                    } else if (rand > 88 && j >= 300) {
                        element = new Mineral("assets/underground/valuable_goldium.png", 250, 20);
                    } else if (rand > 80 && j >= 200) {
                        element = new Mineral("assets/underground/valuable_silverium.png", 100, 10);
                    } else if (rand > 75 && j >= 200) {
                        element = new Lava("assets/underground/lava_01.png");
                    } else {
                        element = new Dirt("assets/underground/soil_01.png");
                    }
                }
                element.setX(i);
                element.setY(j);
                elements[gridX][gridY] = element; // Properly assign to grid
                root.getChildren().add(element);
            }
        }
    }

    public void handleKeyPress() {
        scene.setOnKeyPressed(event -> {
            int deltaX = 0;
            int deltaY = 0;
            int newGridX = player.getGridX();
            int newGridY = player.getGridY();

            switch (event.getCode()) {
                case UP:
                    deltaY = -50;
                    newGridY--;
                    break;
                case DOWN:
                    deltaY = 50;
                    newGridY++;
                    break;
                case LEFT:
                    deltaX = player.getScaleX() == -0.9 ? -70 : -50;
                    newGridX--;
                    break;
                case RIGHT:
                    deltaX = player.getScaleX() == 0.9 ? 70 : 50;
                    newGridX++;
                    break;
            }
            if (elements[newGridX][newGridY] == null) {
                // Update player's grid position
                player.setGridX(newGridX);
                player.setGridY(newGridY);

                // Update player's visual position
                player.setX(player.getX() + deltaX);
                player.setY(player.getY() + deltaY);

                // Update the player's image based on the direction
                updatePlayerImage(event.getCode());
            }
            // Check if the new grid position is valid and if the element is drillable
            if (isValidPosition(newGridX, newGridY) && elements[newGridX][newGridY].isDrillable()) {
                // Update player's grid position
                player.setGridX(newGridX);
                player.setGridY(newGridY);

                // Update player's visual position
                player.setX(player.getX() + deltaX);
                player.setY(player.getY() + deltaY);

                // Update the player's image based on the direction
                updatePlayerImage(event.getCode());
            }
        });
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < elements.length && y >= 0 && y < elements[0].length;
    }

    private void updatePlayerImage(KeyCode code) {
        switch (code) {
            case UP:
                player.setImage(player.getUpImg());
                break;
            case DOWN:
                player.setImage(player.getDownImg());
                break;
            case LEFT:
                player.setScaleX(0.9);  // Ensure player faces left
                player.setImage(player.getLeftImg());
                break;
            case RIGHT:
                player.setScaleX(-0.9); // Ensure player faces right
                player.setImage(player.getLeftImg());
                break;
        }
    }


    public void startGame() {
        timer = new AnimationTimer() {
            int counter = 0;

            @Override
            public void handle(long now) {
                counter++;
                if (counter > 50 && player.getGridY() + 1 < elements[0].length && elements[player.getGridX()][player.getGridY() + 1] == null) {
                    player.move(0, 50); // Move down one grid cell in pixel terms
                    player.setGridY(player.getGridY() + 1); // Increment the gridY to reflect the new position
                    counter = 0;
                }
            }
        };
        timer.start();
    }

    public Scene getScene() {
        return this.scene;
    }
}
