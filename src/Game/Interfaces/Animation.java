// 318807104 Aviel Segev
package Game.Interfaces;

import biuoop.DrawSurface;

/**
 * The Animation interface represents an animation that can be displayed on a DrawSurface.
 */
public interface Animation {
    /**
     * Perform one frame of the animation.
     *
     * @param d The DrawSurface to draw on.
     */
    void doOneFrame(DrawSurface d);

    /**
     * Check if the animation should stop.
     *
     * @return true if the animation should stop, false otherwise.
     */
    boolean shouldStop();
}
