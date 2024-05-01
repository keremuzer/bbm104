import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Random;

public class GameScreen {
    private Scene scene;
    private Group root;
    private Player player;
    private AnimationTimer timer;
    private Rectangle underground;
    private Element[][] elements;
    private int counter = 0;
    private boolean destroyed = false;

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
            Element element = elements[newGridX][newGridY];
            if (isValidPosition(newGridX, newGridY) && element == null) {
                // Update player's grid position
                player.setGridX(newGridX);
                player.setGridY(newGridY);

                // Update player's visual position
                player.setX(player.getX() + deltaX);
                player.setY(player.getY() + deltaY);

                player.setFuel(player.getFuel() - 100);

                // Update the player's image based on the direction
                updatePlayerImage(event.getCode());
            } else if (element instanceof Lava) {
                destroyed = true;
            } else if (element.isDrillable() && event.getCode() != KeyCode.UP) {
                if (element instanceof Mineral) {
                    Mineral mineral = (Mineral) element;
                    player.collect(mineral.getWorth()); // Collect the value of the mineral
                    player.setHaul(player.getHaul() + mineral.getWeight());
                }
                // Update player's grid position
                player.setGridX(newGridX);
                player.setGridY(newGridY);

                // Update player's visual position
                player.setX(player.getX() + deltaX);
                player.setY(player.getY() + deltaY);

                player.setFuel(player.getFuel() - 100);

                // Remove the drilled element
                root.getChildren().remove(element);
                elements[newGridX][newGridY] = null;

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
                counter = 0;
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

    public void displayAttributes() {
        // Display the player's money, fuel, and haul on screen
        root.getChildren().removeIf(node -> node instanceof Text);
        Text fuelText = new Text(10, 20, "Fuel: " + player.getFuel());
        Text haulText = new Text(10, 40, "Haul: " + player.getHaul());
        Text moneyText = new Text(10, 60, "Money: " + player.getMoney());
        fuelText.setFill(javafx.scene.paint.Paint.valueOf("#FFFFFF"));
        haulText.setFill(javafx.scene.paint.Paint.valueOf("#FFFFFF"));
        moneyText.setFill(javafx.scene.paint.Paint.valueOf("#FFFFFF"));
        fuelText.setFont(javafx.scene.text.Font.font(20));
        haulText.setFont(javafx.scene.text.Font.font(20));
        moneyText.setFont(javafx.scene.text.Font.font(20));
        root.getChildren().addAll(moneyText, fuelText, haulText);
    }

    public void startGame() {
        timer = new AnimationTimer() {
            int displayCounter = 0;

            @Override
            public void handle(long now) {
                if (destroyed) {
                    timer.stop();
                    // display game over text with red background
                    Rectangle background = new Rectangle(0, 0, 800, 800);
                    background.setFill(Color.DARKRED);
                    Text gameOver = new Text(260, 400, "GAME OVER");
                    gameOver.setFill(Color.WHITE);
                    gameOver.setFont(javafx.scene.text.Font.font(50));
                    root.getChildren().addAll(background, gameOver);
                    return;
                }
                if (player.getFuel() <= 0) {
                    timer.stop();
                    // display game over text with green background
                    Rectangle background = new Rectangle(0, 0, 800, 800);
                    background.setFill(Color.DARKGREEN);
                    Text gameOver = new Text(260, 400, "GAME OVER");
                    gameOver.setFill(Color.WHITE);
                    gameOver.setFont(javafx.scene.text.Font.font(50));
                    Text money = new Text(180, 450, "Collected Money: " + player.getMoney());
                    money.setFill(Color.WHITE);
                    money.setFont(javafx.scene.text.Font.font(40));
                    root.getChildren().addAll(background, gameOver, money);
                    return;
                }
                displayCounter++;
                if (displayCounter > 10) {
                    player.setFuel(player.getFuel() - 1);
                    displayAttributes();
                    displayCounter = 0;
                }

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
