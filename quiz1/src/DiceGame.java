import java.util.ArrayList;

/**
 * This class is designed to play a dice game with given input and output paths.
 */
public class DiceGame {
    /**
     * Initializes players with the given names.
     *
     * @param lines Lines of the input file.
     * @return Players initialized with the given names.
     */
    public static ArrayList<Player> initializePlayers(String[] lines) {
        String[] playerNames = lines[1].split(",");
        ArrayList<Player> players = new ArrayList<>();

        for (String name : playerNames) {
            players.add(new Player(name));
        }

        return players;
    }

    /**
     * Plays the dice game with the given input and output paths.
     *
     * @param inputPath  Path of the input file.
     * @param outputPath Path of the output file.
     */
    public static void play(String inputPath, String outputPath) {
        String[] lines = FileIO.readFile(inputPath, true, true);
        assert lines != null;
        ArrayList<Player> players = initializePlayers(lines);

        int lineIndex = 2;
        int playerIndex = 0;
        String output;
        while (players.size() > 1) {
            Player player = players.get(playerIndex);
            String[] parts = lines[lineIndex].split("-");
            int dice1 = Integer.parseInt(parts[0]);
            int dice2 = Integer.parseInt(parts[1]);

            String name = player.getName();
            int score = player.getScore();
            if (dice1 == 0) {
                output = name + " skipped the turn and " + name + "’s score is " + score + ".";
            } else if (dice1 == 1 && dice2 == 1) {
                output = name + " threw 1-1. Game over " + name + "!";
                playerIndex--;
                players.remove(player);
            } else if (dice1 == 1 || dice2 == 1) {
                output = name + " threw " + lines[lineIndex] + " and " + name + "’s score is " + score + ".";
            } else {
                score = player.setScore(score + dice1 + dice2);
                output = name + " threw " + lines[lineIndex] + " and " + name + "’s score is " + score + ".";
            }
            FileIO.writeToFile(outputPath, output, true, true);
            lineIndex++;
            playerIndex = (playerIndex + 1) % players.size();
        }

        Player winner = players.get(0);
        output = winner.getName() + " is the winner of the game with the score of " + winner.getScore() + ". Congratulations " + winner.getName() + "!";
        FileIO.writeToFile(outputPath, output, true, false);
    }

    public static void main(String[] args) {
        play(args[0], args[1]);
    }
}