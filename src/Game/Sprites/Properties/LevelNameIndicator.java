// 318807104 Aviel Segev
package Game.Sprites.Properties;

import Game.Interfaces.Sprite;

import Game.Management.GameLevel;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The LevelNameIndicator class is responsible for displaying the name of the current level on the game screen.
 * It implements the Sprite interface and can be added to the game as a sprite.
 */
public class LevelNameIndicator implements Sprite {
    private final String levelName;  // The name of the level

    static final int TEXT_X = 550;  // The x-coordinate for the text
    static final int TEXT_Y = 16;   // The y-coordinate for the text
    static final int TEXT_FONT = 16;  // The font size for the text

    /**
     * Constructs a LevelNameIndicator object with the specified level name.
     *
     * @param levelName The name of the level
     */
    public LevelNameIndicator(String levelName) {
        this.levelName = levelName;
    }

    /**
     * Draws the level name on the given DrawSurface.
     *
     * @param surface The DrawSurface on which to draw the level name
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.BLACK);
        surface.drawText(TEXT_X, TEXT_Y, "Level Name: " + this.levelName, TEXT_FONT);
    }

    /**
     * Performs the time-based behavior of the level name indicator (no behavior in this case).
     */
    @Override
    public void timePassed() {
        // No time-based behavior for the Level name indicator
    }

    /**
     * Adds the level name indicator to the specified game.
     *
     * @param game The GameLevel to which the level name indicator should be added
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}