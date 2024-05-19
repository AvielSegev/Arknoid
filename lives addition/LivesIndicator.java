// 318807104 Aviel Segev

package Game.Sprites.Properties;

import Game.General.Counter;
import Game.Interfaces.Sprite;
import Game.Management.GameLevel;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The LivesIndicator class is responsible for displaying the number of lives remaining on the game screen.
 * It implements the Sprite interface and can be added to the game as a sprite.
 */
public class LivesIndicator implements Sprite {
    private final int lives;  // The number of lives remaining

    static final int TEXT_X = 150;  // The x-coordinate for the text
    static final int TEXT_Y = 16;   // The y-coordinate for the text
    static final int TEXT_FONT = 16;  // The font size for the text

    /**
     * Constructs a LivesIndicator object with the specified lives counter.
     *
     * @param lives The Counter object representing the number of lives
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives.getValue();
    }

    /**
     * Draws the number of lives on the given DrawSurface.
     *
     * @param surface The DrawSurface on which to draw the lives indicator
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.BLACK);
        surface.drawText(TEXT_X, TEXT_Y, "Lives: " + String.valueOf(this.lives), TEXT_FONT);
    }

    /**
     * Performs the time-based behavior of the lives indicator (no behavior in this case).
     */
    @Override
    public void timePassed() {
        // No time-based behavior for the lives indicator
    }

    /**
     * Adds the lives indicator to the specified game.
     *
     * @param game The GameLevel to which the lives indicator should be added
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}