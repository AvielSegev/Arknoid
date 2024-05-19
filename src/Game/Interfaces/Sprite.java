// 318807104 Aviel Segev

package Game.Interfaces;

import biuoop.DrawSurface;

/**
 * The Sprite interface represents a game object that can be drawn on the screen and
 * updated over time.
 */
public interface Sprite {
    /**
     * Draws the sprite on the given DrawSurface.
     *
     * @param d the DrawSurface to draw the sprite on
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that a unit of time has passed.
     * This method should be called once per game loop cycle.
     */
    void timePassed();
}
