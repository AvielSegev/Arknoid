// 318807104 Aviel Segev
package Game.Interfaces;

import Game.Sprites.Collidable.Block;
import Game.Sprites.Shapes.Ball;

/**
 * The HitListener interface represents a listener for hit events in the game.
 * Implementing classes can receive notifications when an object is hit by a ball.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit by a ball.
     *
     * @param beingHit The block that was hit.
     * @param hitter   The ball that hit the block.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
