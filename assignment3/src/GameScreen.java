import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Random;

public class GameScreen {
    private final Scene scene;
    private final Group root;
    private final Player player;
    private final Pane displayPane;
    private final ScrollPane scrollPane;
    private AnimationTimer timer;
    private Element[][] elements;
    private int counter = 0;
    private boolean destroyed = false;

    /**
     * Create a new game screen.
     */
    public GameScreen() {
        this.root = new Group();
        this.scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setPrefViewportWidth(800);
        scrollPane.setPrefViewportHeight(1600);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // disable horizontal scroll
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // disable vertical scroll
        scrollPane.setStyle("-fx-background: #004873;");
        scrollPane.setVvalue(0);
        this.displayPane = new Pane();
        StackPane stackPane = new StackPane(scrollPane, displayPane);
        this.scene = new Scene(stackPane, 800, 800);
        createUnderground();
        player = new Player();
        root.getChildren().add(player);
        handleKeyPress();
        startGame();
    }

    /**
     * Create the underground elements randomly.
     */
    private void createUnderground() {
        Rectangle underground = new Rectangle(800, 1500);
        underground.setFill(javafx.scene.paint.Paint.valueOf("#8B4513"));
        underground.setY(103);
        root.getChildren().add(underground);
        elements = new Element[16][32]; // Corresponds to 16 rows and columns in a 800x800 screen

        Random random = new Random();
        Element element;
        for (int i = 0; i < 800; i += 50) {
            for (int j = 100; j < 1600; j += 50) {
                int gridX = i / 50;
                int gridY = j / 50;

                if (j == 100) {
                    element = new Dirt("assets/underground/top_01.png");
                } else if (i == 0 || i == 750 || j == 1500) {
                    element = new Obstacle("assets/underground/obstacle_01.png");
                } else {
                    int rand = random.nextInt(100);
                    if (rand > 95 && j >= 800) {
                        element = new Mineral("assets/underground/valuable_emerald.png", 5000, 60);
                    } else if (rand > 88 && j >= 400) {
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
                elements[gridX][gridY] = element;
                root.getChildren().add(element);
            }
        }
    }

    /**
     * Handle key press events and update the player's position.
     */
    private void handleKeyPress() {
        scene.setOnKeyPressed(event -> {
            int deltaX = 0;
            int deltaY = 0;
            int newGridX = player.getGridX();
            int newGridY = player.getGridY();

            switch (event.getCode()) {
                case UP:
                    deltaY = -50;
                    newGridY--;
                    counter = 0;
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
            if (element == null) {
                // Update player's grid position
                player.setGridX(newGridX);
                player.setGridY(newGridY);
                player.setX(player.getX() + deltaX);
                player.setY(player.getY() + deltaY);

                player.setFuel(player.getFuel() - 100);

                // Update the player's image based on the direction
                player.updatePlayerImage(event.getCode());
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
                player.setX(player.getX() + deltaX);
                player.setY(player.getY() + deltaY);

                player.setFuel(player.getFuel() - 100);

                // Remove the drilled element
                root.getChildren().remove(element);
                elements[newGridX][newGridY] = null;

                // Update the player's image based on the direction
                player.updatePlayerImage(event.getCode());
            }
        });
    }

    /**
     * Display the player's attributes.
     */
    private void displayAttributes() {
        displayPane.getChildren().clear();
        Text fuelText = new Text(10, 20, "Fuel: " + player.getFuel());
        Text haulText = new Text(10, 40, "Haul: " + player.getHaul());
        Text moneyText = new Text(10, 60, "Money: " + player.getMoney());
        fuelText.setFill(javafx.scene.paint.Paint.valueOf("#FFFFFF"));
        haulText.setFill(javafx.scene.paint.Paint.valueOf("#FFFFFF"));
        moneyText.setFill(javafx.scene.paint.Paint.valueOf("#FFFFFF"));
        fuelText.setFont(javafx.scene.text.Font.font(24));
        haulText.setFont(javafx.scene.text.Font.font(24));
        moneyText.setFont(javafx.scene.text.Font.font(24));
        displayPane.getChildren().addAll(fuelText, haulText, moneyText);
    }

    /**
     * Start the game. The game will run until the player runs out of fuel or hits lava.
     */
    private void startGame() {
        timer = new AnimationTimer() {
            int displayCounter = 0;

            @Override
            public void handle(long now) {
                if (player.getY() < 150) {
                    scrollPane.setVvalue(0);
                } else if (player.getY() > 150) {
                    scrollPane.setVvalue(player.getY() / 1600);
                }
                if (destroyed) {
                    timer.stop();
                    // display game over text with red background
                    displayPane.getChildren().clear();
                    Rectangle background = new Rectangle(0, 0, 800, 800);
                    background.setFill(Color.DARKRED);
                    Text gameOver = new Text(260, 400, "GAME OVER");
                    gameOver.setFill(Color.WHITE);
                    gameOver.setFont(javafx.scene.text.Font.font(50));
                    displayPane.getChildren().addAll(background, gameOver);
                    return;
                }
                if (player.getFuel() <= 0) {
                    timer.stop();
                    // display game over text with green background
                    displayPane.getChildren().clear();
                    Rectangle background = new Rectangle(0, 0, 800, 800);
                    background.setFill(Color.DARKGREEN);
                    Text gameOver = new Text(260, 400, "GAME OVER");
                    gameOver.setFill(Color.WHITE);
                    gameOver.setFont(javafx.scene.text.Font.font(50));
                    Text money = new Text(180, 450, "Collected Money: " + player.getMoney());
                    money.setFill(Color.WHITE);
                    money.setFont(javafx.scene.text.Font.font(40));
                    displayPane.getChildren().addAll(background, gameOver, money);
                    return;
                }
                displayCounter++;
                if (displayCounter > 10) {
                    player.setFuel(player.getFuel() - 1);
                    displayAttributes();
                    displayCounter = 0;
                }

                counter++;
                if (counter > 40 && player.getGridY() + 1 < elements[0].length && elements[player.getGridX()][player.getGridY() + 1] == null) {
                    player.move(0, 50);
                    player.setGridY(player.getGridY() + 1);
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
