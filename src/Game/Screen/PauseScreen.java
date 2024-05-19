// 318807104 Aviel Segev
package Game.Screen;

import Game.Interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The PauseScreen class is responsible for displaying the pause screen during the game.
 * It prompts the player to press the space key to continue the game.
 */
public class PauseScreen extends KeyPressStoppableAnimation implements Animation {

    /**
     * Constructs a PauseScreen object with the specified keyboard sensor.
     *
     * @param k The KeyboardSensor object for detecting key presses
     */
    public PauseScreen(KeyboardSensor k) {
        super(k, KeyboardSensor.SPACE_KEY);
    }

    /**
     * Performs one frame of the pause screen animation.
     * Draws the pause message and checks if the space key is pressed to stop the animation.
     *
     * @param d The DrawSurface on which to draw the animation frame
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        super.doOneFrame(d);
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     * Checks if the pause screen animation should stop.
     *
     * @return true if the animation should stop, false otherwise
     */
    @Override
    public boolean shouldStop() {
        return super.shouldStop();
    }
}
