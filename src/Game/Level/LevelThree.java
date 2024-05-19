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
 * The implementation of LevelInformation for Level Three.
 */
public class LevelThree implements LevelInformation {
    private final List<Block> blocks;
    private final Counter score;
    private boolean finalLevel;
    // Constants for block dimensions, positions, and amounts
    static final int BLOCK_WIDTH = 40;
    static final int BLOCK_HEIGHT = 20;
    static final int TOP_LEFT_BLOCK_X = 735;
    static final int TOP_LEFT_BLOCK_Y = 150;
    static final int AMOUNT_OF_BLOCKS = 57;
    static final int AMOUNT_FIRST_ROW_BLOCK = 12;
    static final int AMOUNT_SECOND_ROW_BLOCK = 23;
    static final int AMOUNT_THIRD_ROW_BLOCK = 33;
    static final int AMOUNT_FOURTH_ROW_BLOCK = 42;
    static final int AMOUNT_FIFTH_ROW_BLOCK = 50;
    static final int AMOUNT_SIXTH_ROW_BLOCK = 57;
    static final int NUMBER_OF_BALLS = 2;
    static final int PADDLE_WIDTH = 90;
    static final int PADDLE_SPEED = 10;

    /**
     * Constructs a new LevelThree object with the given score and lives counters.
     *
     * @param score The score counter.
     */
    public LevelThree(Counter score) {
        this.blocks = new ArrayList<>();
        // Create and add blocks
        double x = TOP_LEFT_BLOCK_X, y = TOP_LEFT_BLOCK_Y;
        int k = 0;
        Block[] blocks = new Block[AMOUNT_OF_BLOCKS];
        // First row of blocks
        for (int i = 0; i < AMOUNT_FIRST_ROW_BLOCK; i++) {
            blocks[i] = new Block(new Point(x - BLOCK_WIDTH * i, y), BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks[i].setColor(Color.gray);
        }
        // Second row of blocks
        for (int i = AMOUNT_FIRST_ROW_BLOCK; i < AMOUNT_SECOND_ROW_BLOCK; i++) {
            blocks[i] = new Block(new Point(x - BLOCK_WIDTH * k, y + BLOCK_HEIGHT), BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks[i].setColor(Color.red);
            k++;
        }
        // Third row of blocks
        k = 0;
        for (int i = AMOUNT_SECOND_ROW_BLOCK; i < AMOUNT_THIRD_ROW_BLOCK; i++) {
            blocks[i] = new Block(new Point(x - BLOCK_WIDTH * k, y + BLOCK_HEIGHT * 2), BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks[i].setColor(Color.yellow);
            k++;
        }
        // Fourth row of blocks
        k = 0;
        for (int i = AMOUNT_THIRD_ROW_BLOCK; i < AMOUNT_FOURTH_ROW_BLOCK; i++) {
            blocks[i] = new Block(new Point(x - BLOCK_WIDTH * k, y + BLOCK_HEIGHT * 3), BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks[i].setColor(Color.cyan);
            k++;
        }
        // Fifth row of blocks
        k = 0;
        for (int i = AMOUNT_FOURTH_ROW_BLOCK; i < AMOUNT_FIFTH_ROW_BLOCK; i++) {
            blocks[i] = new Block(new Point(x - BLOCK_WIDTH * k, y + BLOCK_HEIGHT * 4), BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks[i].setColor(Color.pink);
            k++;
        }
        // Sixth row of blocks
        k = 0;
        for (int i = AMOUNT_FIFTH_ROW_BLOCK; i < AMOUNT_SIXTH_ROW_BLOCK; i++) {
            blocks[i] = new Block(new Point(x - BLOCK_WIDTH * k, y + BLOCK_HEIGHT * 5), BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks[i].setColor(Color.green);
            k++;
        }
        this.blocks.addAll(Arrays.asList(blocks).subList(0, AMOUNT_SIXTH_ROW_BLOCK));
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
            ballsVeloctiyList.add(new Velocity(Math.pow(-1, i + 1) * 5, -5));
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
    public String levelName() {
        return "Green 3";
    }

    @Override
    public Sprite getBackground() {
        Color darkGreen = new Color(0, 102, 0);
        Block backgroundBlock = new Block(new Point(0, 0), WIDTH, HEIGHT);
        backgroundBlock.setColor(darkGreen);
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