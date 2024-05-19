// 318807104 Aviel Segev

package Game.Sprites.Collidable;

import Game.Geometry.Basic.Point;
import Game.Geometry.Basic.Line;
import Game.Geometry.Shapes.Rectangle;
import Game.Interfaces.HitListener;
import Game.Interfaces.HitNotifier;
import Game.Interfaces.Sprite;
import Game.Interfaces.Collidable;
import Game.Sprites.Properties.Velocity;
import Game.Management.GameLevel;

import Game.Sprites.Shapes.Ball;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The Block class represents a block in a Breakout game.
 * A Block is a rectangle with a given position and dimensions,
 * and can be collided with by a ball. The Block can also be drawn on a given surface.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private final List<HitListener> hitListeners;
    private final Rectangle blockRectangle;
    private Color color = Color.black;

    /**
     * Constructor for a Block.
     *
     * @param upperLeft The upper left corner of the block.
     * @param width     The width of the block.
     * @param height    The height of the block.
     */
    public Block(Point upperLeft, double width, double height) {
        this.blockRectangle = new Rectangle(upperLeft, width, height);
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Get the collision rectangle of the block.
     *
     * @return The collision rectangle of the block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.blockRectangle;
    }

    /**
     * Calculate the new velocity of an object after hitting the block.
     *
     * @param collisionPoint  The point where the object collided with the block.
     * @param currentVelocity The current velocity of the object.
     * @return The new velocity of the object.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        Rectangle rect = this.getCollisionRectangle();
        Line[] rectLines = rect.getRectangleLines(); // l0, l2 is horizontal

        // If the collision point is on the same Y coordinate as the start of the top or bottom rectangle lines,
        // reverse Y velocity
        if (Line.floatsComparison(rectLines[0].start().getY(), collisionPoint.getY())
                || Line.floatsComparison(rectLines[2].start().getY(), collisionPoint.getY())) {
            dy *= -1;
        }

        // If the collision point is on the same X coordinate as the start of the left or right rectangle lines,
        // reverse X velocity
        if (Line.floatsComparison(rectLines[1].start().getX(), collisionPoint.getX())
                || Line.floatsComparison(rectLines[3].start().getX(), collisionPoint.getX())) {
            dx *= -1;
        }

        // Return a new velocity with the updated dx and dy
        return new Velocity(dx, dy);
    }


    /**
     * Draw the block on a given surface.
     *
     * @param surface The surface on which to draw the block.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        // Set the color of the ball and fill the collision rectangle with the color
        surface.setColor(this.color);
        Rectangle rect = this.getCollisionRectangle();
        int x = (int) rect.getUpperLeft().getX(), y = (int) rect.getUpperLeft().getY(),
                dx = (int) Math.abs(rect.getWidth()), dy = (int) Math.abs(rect.getHeight());
        surface.fillRectangle(x, y, dx, dy);

        // Set the color to black and draw the outline of the collision rectangle
        surface.setColor(Color.black);
        Line[] rectLines = rect.getRectangleLines();
        for (Line l : rectLines) {
            surface.drawLine((int) l.start().getX(), (int) l.start().getY(),
                    (int) l.end().getX(), (int) l.end().getY());
        }
    }


    /**
     * Perform an action for each passing time unit.
     */
    @Override
    public void timePassed() {

    }

    /**
     * Set the color of the block.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Add the block to a given game, as both a collidable and a sprite.
     *
     * @param game The game to add the block to.
     */
    public void addToGame(GameLevel game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    /**
     * Removes the block object from the game by removing it from the game's collidables and sprites.
     *
     * @param game The game from which the collidable object will be removed.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }


    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}