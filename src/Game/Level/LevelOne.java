// 318807104 Aviel Segev
package Game.Level;

import Game.General.Counter;
import Game.Geometry.Basic.Point;
import Game.Interfaces.LevelInformation;
import Game.Interfaces.Sprite;
import Game.Sprites.Collidable.Block;
import Game.Sprites.Properties.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static Game.Management.GameLevel.HEIGHT;
import static Game.Management.GameLevel.WIDTH;

/**
 * The implementation of LevelInformation for Level One.
 */
public class LevelOne implements LevelInformation {
    private final List<Block> blocks;
    private final Counter score;
    private boolean finalLevel;
    static final double SQUARE_SIZE = 30;
    static final int NUMBER_OF_BALLS = 1;
    static final int AMOUNT_OF_BLOCKS = 1;
    static final int PADDLE_WIDTH = 90;
    static final int PADDLE_SPEED = 10;

    /**
     * Constructs a new LevelOne object with the given score and lives counters.
     *
     * @param score The score counter.
     */
    public LevelOne(Counter score) {
        this.blocks = new ArrayList<>();

        // Create and add the block for Level One
        Block block = new Block(new Point((double) WIDTH / 2 - SQUARE_SIZE / 2, (double) HEIGHT / 5),
                SQUARE_SIZE, SQUARE_SIZE);
        block.setColor(Color.red);
        this.blocks.add(block);

        this.score = score;
        this.finalLevel = false;
    }

    @Override
    public int numberOfBalls() {
        return NUMBER_OF_BALLS;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsVeloctiyList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BALLS; i++) {
            ballsVeloctiyList.add(new Velocity(0, -5 - i));
        }
        return ballsVeloctiyList;
    }

    @Override
    public int paddleSpeed() {
        return PADDLE_SPEED;
    }

    @Override
    public int paddleWidth() {
        return PADDLE_WIDTH;
    }

    @Override
    public Counter getCurrentScore() {
        return this.score;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }
    @Override
    public Sprite getBackground() {
        return new Block(new Point(0, 0), WIDTH, HEIGHT);
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return AMOUNT_OF_BLOCKS;
    }

    @Override
    public void setFinalLevel(boolean finalLevel) {
        this.finalLevel = finalLevel;
    }

    @Override
    public boolean isFinalLevel() {
        return this.finalLevel;
    }
}