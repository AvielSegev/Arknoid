// 318807104 Aviel Segev
package Game.Management;

import Game.General.Counter;
import Game.Interfaces.Animation;
import Game.Interfaces.LevelInformation;
import Game.Level.LevelOne;
import Game.Level.LevelThree;
import Game.Level.LevelTwo;
import Game.Screen.EndGameScreen;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameFlow class manages the flow of the game and handles running levels.
 */
public class GameFlow {
    private final KeyboardSensor keyboardSensor;
    private final AnimationRunner animationRunner;
    private final Counter score;
    private final List<LevelInformation> levels;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    /**
     * Constructs a new GameFlow object with the given command line arguments.
     *
     * @param args The command line arguments specifying the levels to play.
     */
    public GameFlow(String[] args) {
        // create a GUI object
        GUI gui = new GUI("Arkanoid", WIDTH, HEIGHT);
        // create a AnimationRunner object for handling the animation
        this.animationRunner = new AnimationRunner(gui, 60);
        this.keyboardSensor = gui.getKeyboardSensor();
        this.score = new Counter();
        this.levels = new ArrayList<>();
        for (String arg : args) {
            if (arg.equals("1")) {
                this.levels.add(new LevelOne(this.score));
            }
            if (arg.equals("2")) {
                this.levels.add(new LevelTwo(this.score));
            }
            if (arg.equals("3")) {
                this.levels.add(new LevelThree(this.score));
            }
        }
        if (levels.isEmpty()) {
            defaultLevels();
        }
        this.levels.get(this.levels.size() - 1).setFinalLevel(true);
    }

    /**
     * Sets the default levels if no levels were specified in the command line arguments.
     */
    public void defaultLevels() {
        this.levels.add(new LevelOne(this.score));
        this.levels.add(new LevelTwo(this.score));
        this.levels.add(new LevelThree(this.score));
    }

    /**
     * Runs the list of levels.
     *
     * @param levels The levels to run.
     */
    public void runLevels(List<LevelInformation> levels) {
        GameLevel level = null;
        for (LevelInformation levelInformation : levels) {

            level = new GameLevel(levelInformation, this.keyboardSensor, this.animationRunner);

            level.initialize();

            do {
                level.run();
            } while (!level.shouldStop());

            if (level.getRemainedBalls().getValue() == 0) {
                break;
            }
        }
        if (level != null) {
            Animation endGameScreen = new EndGameScreen(this.animationRunner, this.keyboardSensor,
                    level.getRemainedBalls().getValue() != 0
                            && level.getLevelInformation().isFinalLevel(), this.score);
            this.animationRunner.run(endGameScreen);
        }
    }

    /**
     * Returns the list of levels.
     *
     * @return The list of levels.
     */
    public List<LevelInformation> getLevels() {
        return this.levels;
    }
}
