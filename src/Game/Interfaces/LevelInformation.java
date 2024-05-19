// 318807104 Aviel Segev
package Game.Interfaces;

import Game.General.Counter;
import Game.Sprites.Collidable.Block;
import Game.Sprites.Properties.Velocity;

import java.util.List;

/**
 * The LevelInformation interface represents the information of a game level.
 */
public interface LevelInformation {
    /**
     * Get the number of balls in the level.
     *
     * @return The number of balls.
     */
    int numberOfBalls();

    /**
     * Get the initial velocities of the balls.
     * Note that initialBallVelocities().size() should be equal to numberOfBalls().
     *
     * @return A list of initial velocities.
     */
    List<Velocity> initialBallVelocities();

    /**
     * Get the speed of the paddle.
     *
     * @return The speed of the paddle.
     */
    int paddleSpeed();

    /**
     * Get the width of the paddle.
     *
     * @return The width of the paddle.
     */
    int paddleWidth();

    /**
     * Get the counter for the current score.
     *
     * @return The counter for the current score.
     */
    Counter getCurrentScore();

    /**
     * Get the name of the level.
     *
     * @return The name of the level.
     */
    String levelName();

    /**
     * Get the background sprite of the level.
     *
     * @return The background sprite.
     */
    Sprite getBackground();

    /**
     * Get the blocks that make up this level.
     *
     * @return The list of blocks.
     */
    List<Block> blocks();

    /**
     * Get the number of blocks that should be removed to clear the level.
     *
     * @return The number of blocks to remove.
     */
    int numberOfBlocksToRemove();

    /**
     * Set whether this level is the final level.
     *
     * @param finalLevel true if it's the final level, false otherwise.
     */
    void setFinalLevel(boolean finalLevel);

    /**
     * Check if this level is the final level.
     *
     * @return true if it's the final level, false otherwise.
     */
    boolean isFinalLevel();
}