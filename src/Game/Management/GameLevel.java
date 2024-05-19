// 318807104 Aviel Segev
package Game.Management;

import Game.Interfaces.LevelInformation;
import Game.Level.AdditionalStyle;
import Game.Screen.CountdownAnimation;
import Game.Screen.PauseScreen;

import Game.Actions.BallRemover;
import Game.Actions.BlockRemover;
import Game.General.Counter;
import Game.Interfaces.Animation;
import Game.Sprites.Properties.LevelNameIndicator;
import Game.Sprites.Properties.ScoreIndicator;
import Game.Sprites.Properties.Velocity;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import Game.Geometry.Basic.Point;
import Game.Geometry.Shapes.Rectangle;
import Game.Interfaces.Sprite;
import Game.Interfaces.Collidable;
import Game.Sprites.Shapes.Ball;
import Game.Sprites.Collidable.Block;
import Game.Sprites.Collidable.Paddle;

import java.awt.Color;
import java.util.List;

/**
 * The Game class represents a game environment.
 * It contains a sprite collection, a game environment, and a border size.
 * It is responsible for adding collidables and sprites to the game, as well as returning the game environment.
 */
public class GameLevel implements Animation {
    // These are objects that the game will use to keep track of sprites and the game environment
    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    // Counter values in the game
    private Counter remainedBlocks;
    private Counter remainedBalls;
    private Counter score;

    // Animation part of the game
    private final AnimationRunner runner;
    private boolean running;
    // Keyboard sensor
    private final KeyboardSensor keyboard;

    // Level information
    private final LevelInformation levelInformation;

    // These are constants for the width and height of the game window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    // This is a constant for the size of the border around the game window
    public static final int BORDER_SIZE = 25;
    // These are constants for the size and starting positions of two balls in the game
    static final int RADIUS = 5;

    // These are constants for the width and height of the paddle in the game
    static final double PADDLE_HEIGHT = 20;

    /**
     * Constructs a new GameLevel object with the given level information, keyboard sensor, and animation runner.
     *
     * @param levelInformation The level information for the level.
     * @param keyboard         The keyboard sensor for capturing input.
     * @param animationRunner  The animation runner for running the level animation.
     */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor keyboard, AnimationRunner animationRunner) {
        this.levelInformation = levelInformation;
        this.keyboard = keyboard;
        this.runner = animationRunner;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to be added.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the game environment.
     *
     * @param s the sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Returns the game environment of the game.
     *
     * @return the game environment of the game.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Returns the counter for the number of balls remained in the game.
     *
     * @return The counter for the number of balls remained.
     */
    public Counter getRemainedBalls() {
        return this.remainedBalls;
    }

    /**
     * Returns the level information for the current game level.
     *
     * @return The level information for the current game level.
     */
    public LevelInformation getLevelInformation() {
        return this.levelInformation;
    }

    /**
     * Initializes the game: creates balls, blocks, and borders and adds them to the game.
     */
    public void initialize() {
        int numberOfBalls = this.levelInformation.numberOfBalls();
        Ball[] balls = new Ball[numberOfBalls];
        List<Velocity> ballsVelocitiesList = this.levelInformation.initialBallVelocities();
        for (int i = 0; i < numberOfBalls; i++) {
            balls[i] = new Ball(
                    new Point((double) (WIDTH) / 2,
                            HEIGHT - BORDER_SIZE - PADDLE_HEIGHT - RADIUS),
                    RADIUS, Color.white);
            balls[i].addToGame(this);
            balls[i].setVelocity(ballsVelocitiesList.get(i));
            balls[i].setRectangleScreen(new Rectangle(new Point(BORDER_SIZE, BORDER_SIZE),
                    WIDTH - 2 * BORDER_SIZE, HEIGHT - 2 * BORDER_SIZE));
        }

        // Create and add borders
        Block top = new Block(new Point(0, BORDER_SIZE - 5), WIDTH, BORDER_SIZE);
        Block right = new Block(new Point(WIDTH - BORDER_SIZE, 0), BORDER_SIZE, HEIGHT);
        Block left = new Block(new Point(0, 0), BORDER_SIZE, HEIGHT);
        Block bottom = new Block(new Point(0, HEIGHT), WIDTH, BORDER_SIZE);
        top.setColor(Color.darkGray);
        bottom.setColor(Color.darkGray);
        right.setColor(Color.darkGray);
        left.setColor(Color.darkGray);
        top.addToGame(this);
        right.addToGame(this);
        left.addToGame(this);
        bottom.addToGame(this);

        // initial number of remainedBlocks counter
        this.remainedBlocks = new Counter();
        this.remainedBlocks.increase(this.levelInformation.numberOfBlocksToRemove());
        // Create a block remover
        BlockRemover blockRemover = new BlockRemover(this, remainedBlocks);
        // initial number of remainedBalls counter
        this.remainedBalls = new Counter();
        this.remainedBalls.increase(this.levelInformation.numberOfBalls());
        // Create a balls remover
        BallRemover ballRemover = new BallRemover(this, remainedBalls);
        // Add ballRemover to bottom block notifier
        bottom.addHitListener(ballRemover);
        // Initial Score listener
        this.score = this.levelInformation.getCurrentScore();
        ScoreTrackingListener scoreTracking = new ScoreTrackingListener(this.score);
        // Add all blocks needed to blockRemover as listener
        List<Block> removableBlocks = this.levelInformation.blocks();
        for (Block block : removableBlocks) {
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTracking);
            block.addToGame(this);
        }
        // Initial ScoreIndicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);

        // Initial Level name

        LevelNameIndicator levelNameIndicator = new LevelNameIndicator(this.levelInformation.levelName());
        levelNameIndicator.addToGame(this);

        // initialize the paddle
        initializePaddle(this.runner.getGui());
    }

    /**
     * Runs the game level.
     */
    public void run() {
        // Countdown before the turn starts.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));

        // Set running to true to start the game animation.
        this.running = true;

        // Use the animation runner to run the current game level.
        this.runner.run(this);
    }


    /**
     * Returns the score counter associated with the game.
     *
     * @return The score counter.
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * Removes a collidable object from the game environment.
     *
     * @param c The collidable object to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes a sprite object from the game.
     *
     * @param s The sprite object to be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }


    /**
     * Initializes the paddle object in the game.
     *
     * @param gui The graphical user interface of the game.
     */
    public void initializePaddle(GUI gui) {
        int paddleWidth = this.levelInformation.paddleWidth();
        Paddle paddle =
                new Paddle(new Rectangle(new Point((double) (WIDTH - paddleWidth) / 2,
                        HEIGHT - BORDER_SIZE - PADDLE_HEIGHT), paddleWidth,
                        PADDLE_HEIGHT), gui);
        paddle.addToGame(this);
        Color paddleColor = new Color(255, 200, 2);
        paddle.setColor(paddleColor);
        paddle.setVelocity(this.levelInformation.paddleSpeed());
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        Sprite background = this.levelInformation.getBackground();
        background.drawOn(d);
        this.additionalStyle(d);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        if (this.remainedBlocks.getValue() == 0 || this.remainedBalls.getValue() == 0) {
            this.running = false;
        }
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("פ")) {
            Animation pauseScreen = new PauseScreen(this.keyboard);
            this.runner.run(pauseScreen);
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Applies additional style to the game level.
     *
     * @param d The DrawSurface on which to apply the additional style.
     */
    public void additionalStyle(DrawSurface d) {
        AdditionalStyle additionalStyle = new AdditionalStyle(d, this.levelInformation);
        additionalStyle.addAdditionalStyle();
    }
}