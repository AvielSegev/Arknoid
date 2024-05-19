// 318807104 Aviel Segev
package Game.Management;

import Game.Interfaces.Animation;
import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.DrawSurface;

/**
 * The AnimationRunner class is responsible for running animations.
 * It controls the flow of the animation and manages the GUI.
 */
public class AnimationRunner {
    private final GUI gui;
    private final int framesPerSecond;
    private final Sleeper sleeper;

    /**
     * Constructs a new AnimationRunner with the given GUI and frames per second.
     *
     * @param gui             The GUI to display the animation.
     * @param framesPerSecond The desired frames per second of the animation.
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new Sleeper();
    }

    /**
     * Runs the given animation.
     *
     * @param animation The animation to run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d);

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Returns the GUI of the AnimationRunner.
     *
     * @return The GUI.
     */
    public GUI getGui() {
        return this.gui;
    }
}
