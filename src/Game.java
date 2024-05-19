// 318807104 Aviel Segev
import Game.Management.GameFlow;

/**

 The Ass3Game class is responsible for running the game by initializing and running a Game object.
 */
public class Game {
    /**
     * The main method creates a Game object, initializes it and runs it.
     * @param args an array of command-line arguments for the main method (not used)
     */
    public static void main(String[] args) {
        GameFlow game = new GameFlow(args);
        game.runLevels(game.getLevels());
    }
}