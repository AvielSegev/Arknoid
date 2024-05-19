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
import java.util.Arrays;
import java.util.List;

import static Game.Management.GameLevel.HEIGHT;
import static Game.Management.GameLevel.WIDTH;

/**
 * The implementation of LevelInformation for Level Two.
 */
public class LevelTwo implements LevelInformation {
    private final List<Block> blocks;
    private final Counter score;
    private boolean finalLevel;

    // Constants for block dimensions, positions, and amounts
    static final int NUMBER_OF_BALLS = 10;
    static final int TOP_LEFT_BLOCK_X = 725;
    static final int TOP_LEFT_BLOCK_Y = 250;
    static final int AMOUNT_OF_BLOCKS = 15;
    static final double BLOCK_WIDTH = 50;
    static final int BLOCK_HEIGHT = 20;
    static final int PADDLE_WIDTH = 600;
    static final int PADDLE_SPEED = 1;

    /**
     * Constructs a new LevelTwo object with the given score and lives counters.
     *
     * @param score The score counter.
     */
    public LevelTwo(Counter score) {
        this.blocks = new ArrayList<>();

        // Create and add blocks
        Block[] blocks = new Block[AMOUNT_OF_BLOCKS];

        // Creating rows of blocks
        for (int i = 0; i < AMOUNT_OF_BLOCKS; i++) {
            blocks[i] = new Block(new Point(TOP_LEFT_BLOCK_X - BLOCK_WIDTH * i,
                    TOP_LEFT_BLOCK_Y), BLOCK_WIDTH, BLOCK_HEIGHT);
            if (i < 2) {
                blocks[i].setColor(new Color(51, 153, 255));
            } else if (i < 4) {
                blocks[i].setColor(new Color(196, 121, 195));
            } else if (i < 6) {
                blocks[i].setColor(Color.BLUE);
            } else if (i < 9) {
                blocks[i].setColor(Color.GREEN);
            } else if (i < 11) {
                blocks[i].setColor(Color.YELLOW);
            } else if (i < 13) {
                blocks[i].setColor(Color.ORANGE);
            } else {
                blocks[i].setColor(Color.RED);
            }
        }

        this.blocks.addAll(Arrays.asList(blocks).subList(0, AMOUNT_OF_BLOCKS));
        this.score = score;
        this.finalLevel = false;
    }

    @Override
    public int numberOfBalls() {
        return NUMBER_OF_BALLS;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsVelocityList = new ArrayList<>();

        // Set initial ball velocities
        for (int i = 0; i < NUMBER_OF_BALLS; i++) {
            int k = -45;
            ballsVelocityList.add(Velocity.fromAngleAndSpeed(k + i * 10, 8));
        }
        return ballsVelocityList;
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
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        Block backgroundBlock = new Block(new Point(0, 0), WIDTH, HEIGHT);
        backgroundBlock.setColor(Color.white);
        return backgroundBlock;
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

    @Override
    public Counter getCurrentScore() {
        return this.score;
    }
}
