// 318807104 Aviel Segev
package Game.Actions;

import Game.General.Counter;
import Game.Interfaces.HitListener;
import Game.Sprites.Collidable.Block;
import Game.Sprites.Shapes.Ball;
import Game.Management.GameLevel;

/**
 * The BlockRemover class is responsible for removing blocks from the game when they are hit.
 * It implements the HitListener interface to handle hit events.
 */
public class BlockRemover implements HitListener {
    private final GameLevel game;
    private final Counter remainingBlocks;
    static final int NO_MORE_BLOCK_SCORE = 100;

    /**
     * Constructs a BlockRemover object with the specified game and remaining blocks counter.
     *
     * @param game            The game object.
     * @param remainingBlocks The counter for remaining blocks in the game.
     */
    public BlockRemover(GameLevel game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Handles the hit event when a block is hit by a ball.
     * Decreases the remaining blocks counter, removes the hit listener from the block,
     * removes the block from the game, and increases the score if there are no more remaining blocks.
     *
     * @param beingHit The block that was hit.
     * @param hitter   The ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.remainingBlocks.decrease(1);
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(game);
        if (this.remainingBlocks.getValue() == 0) {
            game.getScore().increase(NO_MORE_BLOCK_SCORE);
        }
    }
}
