// 318807104 Aviel Segev
package Game.Screen;

import Game.Interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;


/**
 * The KeyPressStoppableAnimation class is responsible for creating animations that stop upon a key press.
 * It implements the Animation interface and provides a mechanism to stop the animation when a specific key is pressed.
 */
public class KeyPressStoppableAnimation implements Animation {
    private final KeyboardSensor keyboard;  // The KeyboardSensor used for detecting key presses
    private final String key;               // The key that stops the animation
    private Boolean isAlreadyPressed;       // Indicates if the key is already pressed
    private Boolean stop;                   // Indicates if the animation should stop

    /**
     * Constructs a KeyPressStoppableAnimation object with the specified parameters.
     *
     * @param sensor The KeyboardSensor used for detecting key presses
     * @param key    The key that stops the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key) {
        this.keyboard = sensor;
        this.key = key;
        this.isAlreadyPressed = true;
        this.stop = false;
    }

    /**
     * Performs one frame of the animation.
     *
     * @param d The DrawSurface on which to perform the animation
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        if (!this.isAlreadyPressed) {
            if (this.keyboard.isPressed(key)) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * Checks if the animation should stop.
     *
     * @return true if the animation should stop, false otherwise
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}