// 318807104 Aviel Segev
package Game.Screen;

import Game.Interfaces.Animation;

import Game.Management.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * The CountdownAnimation class displays a given gameScreen for a specified duration,
 * while showing a countdown from a specified number back to 1. Each number in the countdown
 * is displayed on the screen for a specific amount of time before being replaced with the next number.
 */
public class CountdownAnimation implements Animation {
    private final double numOfSeconds;  // The total duration of the countdown animation in seconds
    private final int countFrom;  // The number to count down from
    private int count = 0;  // The current count
    private final SpriteCollection gameScreen;  // The game screen to display during the countdown

    /**
     * Constructs a CountdownAnimation object with the specified parameters.
     *
     * @param numOfSeconds The total duration of the countdown animation in seconds
     * @param countFrom    The number to count down from
     * @param gameScreen   The game screen to display during the countdown
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.numOfSeconds = numOfSeconds;
        this.gameScreen = gameScreen;
    }

    /**
     * Performs one frame of the countdown animation.
     * Draws the current countdown number on the screen, sleeps for a specific duration,
     * and increments the count.
     *
     * @param d The DrawSurface on which to draw the animation frame
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw the countdown number on the screen
        d.drawText(385, 100, String.valueOf(this.countFrom - this.count), 50);

        Sleeper sleeper = new Sleeper();
        // Sleep for the duration of each countdown number
        sleeper.sleepFor((long) ((numOfSeconds / countFrom) * 1000));

        // Increment the count
        this.count++;

        // Draw the game screen
        this.gameScreen.drawAllOn(d);
    }

    /**
     * Checks if the countdown animation should stop.
     * Stops the animation when the count reaches the countFrom value.
     *
     * @return true if the animation should stop, false otherwise
     */
    @Override
    public boolean shouldStop() {
        return this.countFrom == this.count;
    }
}
