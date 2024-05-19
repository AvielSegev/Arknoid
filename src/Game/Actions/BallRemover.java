// 318807104 Aviel Segev
package Game.Actions;

import Game.General.Counter;
import Game.Interfaces.HitListener;
import Game.Management.GameLevel;
import Game.Sprites.Collidable.Block;
import Game.Sprites.Shapes.Ball;

/**
 * The BallRemover class implements the HitListener interface and is responsible for removing balls from the game
 * <p>
 * when they hit a block. It also updates the remaining balls counter.
 * </p>
 */
public class BallRemover implements HitListener {
    private final GameLevel game;
    private final Counter remainingBalls;

    /**
     * Constructs a BallRemover object with the specified game and remainingBalls counter.
     *
     * @param game           the game object from which balls will be removed
     * @param remainingBalls the counter representing the remaining balls in the game
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Handles the hit event when a ball collides with a block.
     * Removes the ball from the game and decreases the remaining balls counter by 1.
     *
     * @param beingHit the block being hit by the ball
     * @param hitter   the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}