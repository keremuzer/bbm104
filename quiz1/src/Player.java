/**
 * Represents a player in the game.
 * Each player has a name and a score.
 */
public class Player {
    private final String name;
    private int score;

    /**
     * Constructs a player with the given name.
     *
     * @param name Name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the player to the given value.
     *
     * @param newScore New score of the player.
     * @return New score of the player.
     */
    public int setScore(int newScore) {
        score = newScore;
        return score;
    }
}
