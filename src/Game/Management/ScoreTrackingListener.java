// 318807104 Aviel Segev
package Game.Management;

import Game.General.Counter;
import Game.Interfaces.HitListener;
import Game.Sprites.Collidable.Block;
import Game.Sprites.Shapes.Ball;

/**
 * The ScoreTrackingListener class is responsible for tracking and updating the score when blocks are hit.
 * It implements the HitListener interface to handle hit events.
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;
    static final int BLOCK_HIT_SCORE = 5;

    /**
     * Constructs a ScoreTrackingListener object with the specified score counter.
     *
     * @param scoreCounter The counter to track the score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Handles the hit event when a block is hit by a ball.
     * Increases the current score by the block hit score and removes the hit listener from the block.
     *
     * @param beingHit The block that was hit.
     * @param hitter   The ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(BLOCK_HIT_SCORE);
        beingHit.removeHitListener(this);
    }
}
