// 318807104 Aviel Segev
package Game.Sprites.Properties;

import Game.General.Counter;
import Game.Geometry.Basic.Point;
import Game.Interfaces.Sprite;

import Game.Management.GameLevel;
import biuoop.DrawSurface;


import java.awt.Color;

/**
 * The ScoreIndicator class represents a graphical indicator of the current score in the game.
 * It implements the Sprite interface to be drawable on the game's drawing surface.
 */
public class ScoreIndicator implements Sprite {
    private final Counter currentScore;
    private final Point topLeft = new Point(0, 0);

    // Constants for the dimensions and positioning of the score indicator
    static final int WIDTH = 800;
    static final int HEIGHT = 20;
    static final int TEXT_X = 330;
    static final int TEXT_Y = 16;
    static final int TEXT_FONT = 16;

    /**
     * Constructs a ScoreIndicator object with the specified score counter.
     *
     * @param score The counter to display as the current score.
     */
    public ScoreIndicator(Counter score) {
        this.currentScore = score;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.white);
        surface.fillRectangle((int) topLeft.getX(), (int) topLeft.getY(), WIDTH, HEIGHT);
        surface.setColor(Color.black);
        surface.drawText(TEXT_X, TEXT_Y, "Score: " + this.currentScore.getValue(), TEXT_FONT);
    }

    @Override
    public void timePassed() {
        // No time-based behavior for the score indicator
    }

    /**
     * Adds the score indicator to the specified game.
     * The indicator will be drawn and updated with the game's sprites.
     *
     * @param game The game to which the score indicator will be added.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}