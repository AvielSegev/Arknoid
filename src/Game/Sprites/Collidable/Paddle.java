// 318807104 Aviel Segev

package Game.Sprites.Collidable;

import Game.Sprites.Shapes.Ball;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import Game.Geometry.Basic.Point;
import Game.Geometry.Basic.Line;
import Game.Geometry.Shapes.Rectangle;
import Game.Interfaces.Sprite;
import Game.Interfaces.Collidable;
import Game.Management.GameEnvironment;
import Game.Sprites.Properties.Velocity;
import Game.Management.GameLevel;

import java.awt.Color;

/**
 * The Paddle class represents the paddle that the player controls
 * in the game. It implements both the Sprite and Collidable interfaces.
 * <p>
 * The paddle is movable by the player with the left and right arrow keys
 * and can collide with other collidables in the game.
 * </p>
 */
public class Paddle implements Sprite, Collidable {
    private final biuoop.KeyboardSensor keyboard;
    private final Rectangle paddle;
    private GameEnvironment environment = null;
    private Color color = Color.black;
    private int velocityDx;

    /**
     * Constructs a new Paddle object.
     *
     * @param paddle the rectangle that represents the paddle
     * @param gui    the GUI object that is used to get the keyboard sensor
     */
    public Paddle(Rectangle paddle, GUI gui) {
        this.keyboard = gui.getKeyboardSensor();
        this.paddle = paddle;
        this.velocityDx = 10; // default velocity
    }

    /**
     * Sets the game environment that the paddle can collide with.
     *
     * @param environment the game environment
     */
    public void setEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Sets the color of the paddle.
     *
     * @param color the new color of the paddle
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the velocity of the object in the x-axis.
     *
     * @param velocity The velocity to set
     */
    public void setVelocity(int velocity) {
        this.velocityDx = velocity;
    }

    /**
     * Moves the paddle to the left.
     * If the move is illegal (i.e. the paddle will collide with another
     * collidable object in the game), the move is not executed.
     */
    public void moveLeft() {
        // Get the lines that make up the paddle's rectangle
        Line[] paddleLines = this.paddle.getRectangleLines();
        // Check if the move is legal by iterating over all the collidables in the game
        boolean moveIsLegal = true;
        for (Collidable c : this.environment.getCollidableList()) {
            if (c == this) {
                continue;
            }
            // Create a line representing the right edge of the collidable
            Line rightLine = new Line(c.getCollisionRectangle().getUpperRight(),
                    c.getCollisionRectangle().getBottomRight());

            // Check if the paddle intersects with the collidable
            for (Line l : paddleLines) {
                if (l.isIntersecting(rightLine)) {
                    moveIsLegal = false;
                }
            }
        }
        // If the move is legal, move the paddle to the left
        if (moveIsLegal) {
            Point upperLeftPrev = this.paddle.getUpperLeft();
            this.paddle.setPosition(new Point(upperLeftPrev.getX() - Math.abs(this.velocityDx),
                            upperLeftPrev.getY()),
                    this.paddle.getWidth(), this.paddle.getHeight());
        }
    }

    /**
     * Moves the paddle to the right.
     * If the move is illegal (i.e. the paddle will collide with another
     * collidable object in the game), the move is not executed.
     */
    public void moveRight() {
        // Get the lines that make up the paddle's rectangle
        Line[] paddleLines = this.paddle.getRectangleLines();
        // Check if the move is legal by iterating over all the collidables in the game
        boolean moveIsLegal = true;
        for (Collidable c : this.environment.getCollidableList()) {
            if (c == this) {
                continue;
            }
            // Create a line representing the left edge of the collidable
            Line leftLine = new Line(c.getCollisionRectangle().getUpperLeft(),
                    c.getCollisionRectangle().getBottomLeft());
            // Check if the paddle intersects with the collidable
            for (Line l : paddleLines) {
                if (l.isIntersecting(leftLine)) {
                    moveIsLegal = false;
                }
            }
        }
        // If the move is legal, move the paddle to the right
        if (moveIsLegal) {
            Point upperLeftPrev = this.paddle.getUpperLeft();
            this.paddle.setPosition(new Point(upperLeftPrev.getX() + Math.abs(this.velocityDx),
                            upperLeftPrev.getY()),
                    this.paddle.getWidth(), this.paddle.getHeight());
        }
    }

    // Sprite
    @Override
    public void timePassed() {
        // Move paddle right or left according to the user's input
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        } else if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
    }

    @Override
    public void drawOn(DrawSurface surface) {
        Rectangle rect = this.paddle;
        Line[] rectLines = rect.getRectangleLines();
        surface.setColor(this.color);
        int x = (int) rect.getUpperLeft().getX(), y = (int) rect.getUpperLeft().getY(),
                dx = (int) Math.abs(rect.getWidth()), dy = (int) Math.abs(rect.getHeight());
        // Draw the paddle on the screen
        surface.fillRectangle(x, y, dx, dy);
        // Draw the paddle's borders
        for (Line l : rectLines) {
            surface.setColor(Color.black);
            surface.drawLine((int) l.start().getX(), (int) l.start().getY(),
                    (int) l.end().getX(), (int) l.end().getY());
        }
    }

    // Collidable
    @Override
    public Rectangle getCollisionRectangle() {
        // Return the paddle as the collision rectangle
        return this.paddle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Determine the region of the paddle that the collision occurred with
        int region = this.region(collisionPoint);
        // Get relevant paddle and collision points
        double topPaddleY = this.paddle.getUpperLeft().getY(),
                bottomPaddleY = this.paddle.getBottomLeft().getY(),
                collisionY = collisionPoint.getY(),
                speed = currentVelocity.speed();
        // Handle collision with paddle horizontally
        if (collisionY < topPaddleY && collisionY > bottomPaddleY) {
            return new Velocity(currentVelocity.getDx() * -1, currentVelocity.getDy() * -1);
        } else if (Line.floatsComparison(collisionY, topPaddleY)) { // Handle collision with paddle vertically
            switch (region) {
                case 1:
                    return Velocity.fromAngleAndSpeed(300, speed);
                case 2:
                    return Velocity.fromAngleAndSpeed(330, speed);
                case 3:
                    return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
                case 4:
                    return Velocity.fromAngleAndSpeed(30, speed);
                case 5:
                    return Velocity.fromAngleAndSpeed(60, speed);
                default:
                    // Handle the default case if needed
                    break;
            }
        }
        // Handle collision with paddle corner
        return new Velocity(currentVelocity.getDx() * -1, currentVelocity.getDy() * -1);
    }

    /**
     * Determines which region of the paddle the ball hit based on the point of collision.
     * The paddle is divided into 5 equally-sized regions, each region returning a different integer value:
     * 1 for the leftmost region, 2 for the second from left, 3 for the middle, 4 for the second from right,
     * and 5 for the rightmost region. If the ball hits the paddle outside of these regions, 0 is returned.
     *
     * @param p the point of collision between the ball and the paddle
     * @return an integer value representing the region of the paddle that was hit by the ball
     */
    public int region(Point p) {
        // Calculate the top line of the paddle
        Line top = new Line(this.paddle.getUpperLeft(), this.paddle.getUpperRight());
        // Calculate the width of each region
        double regionWidth = Math.abs(top.start().getX() - top.end().getX()) / 5;

        // Check which region the collision point is in and return its number
        if (p.getX() <= top.start().getX() + regionWidth
                && p.getX() >= top.start().getX()) {
            return 1;
        }
        if (p.getX() <= top.start().getX() + 2 * regionWidth
                && p.getX() > top.start().getX() + regionWidth) {
            return 2;
        }
        if (p.getX() <= top.start().getX() + regionWidth * 3
                && p.getX() > top.start().getX() + 2 * regionWidth) {
            return 3;
        }
        if (p.getX() <= top.start().getX() + regionWidth * 4
                && p.getX() > top.start().getX() + 3 * regionWidth) {
            return 4;
        }
        if (p.getX() <= top.start().getX() + regionWidth * 5
                && p.getX() > top.start().getX() + 4 * regionWidth) {
            return 5;
        }
        // If the collision point is not on the paddle, return 0
        return 0;
    }

    /**
     * Adds this paddle to the specified game by adding it as a collidable and a sprite,
     * and setting the game's environment as the paddle's environment.
     *
     * @param g the game to which this paddle is being added
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
        this.setEnvironment(g.getEnvironment());
    }
}