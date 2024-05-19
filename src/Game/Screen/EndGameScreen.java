// 318807104 Aviel Segev
package Game.Screen;

import Game.General.Counter;
import Game.Interfaces.Animation;
import Game.Management.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * The EndGameScreen class is responsible for displaying the end game screen
 * indicating whether the player has won or lost the game, along with the final score.
 */
public class EndGameScreen extends KeyPressStoppableAnimation implements Animation {
    private final GUI gui;  // The GUI object for displaying the end game screen
    private final boolean win;  // Indicates whether the player has won the game
    private final Counter score;  // The final score of the game
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    /**
     * Constructs an EndGameScreen object with the specified parameters.
     *
     * @param animationRunner The AnimationRunner object for accessing the GUI
     * @param k               The KeyboardSensor for detecting key presses
     * @param win             Indicates whether the player has won the game
     * @param score           The final score of the game
     */
    public EndGameScreen(AnimationRunner animationRunner, KeyboardSensor k, boolean win, Counter score) {
        super(k, KeyboardSensor.SPACE_KEY);
        this.gui = animationRunner.getGui();
        this.win = win;
        this.score = score;
    }

    /**
     * Performs one frame of the end game screen animation.
     * Draws the appropriate end game message based on the win status,
     * detects if the space key is pressed to stop the animation, and closes the GUI if necessary.
     *
     * @param d The DrawSurface on which to draw the animation frame
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        super.doOneFrame(d);
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, WIDTH, HEIGHT);
        d.setColor(Color.RED);

        // Display the appropriate end game message
        if (!this.win) {
            d.drawText(50, 300, "Game Over. Your score is " + this.score.getValue(), 50);
        } else {
            d.drawText(60, 300, "You Win! Your score is " + this.score.getValue(), 50);
        }

        // Check if the animation should stop and close the GUI if necessary
        if (shouldStop()) {
            this.gui.close();
        }
    }
}