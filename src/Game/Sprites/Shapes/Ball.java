// 318807104 Aviel Segev

package Game.Sprites.Shapes;

import Game.Geometry.Basic.Point;
import Game.Geometry.Basic.Line;
import Game.Geometry.Shapes.Rectangle;
import Game.Interfaces.Sprite;
import Game.Interfaces.Collidable;
import Game.Management.GameEnvironment;
import Game.Management.CollisionInfo;
import Game.Sprites.Properties.Velocity;
import Game.Management.GameLevel;

import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

/**
 * @author Aviel Segev <address>aviel281999@gmail.com</address>
 * @version 1.0
 * @since 03-04-2023
 * A Ball class representing a circle with a center point, radius, color, and velocity.
 */
public class Ball implements Sprite {
    private Point center; // the center point of the ball
    private int radius; // the radius of the ball
    private java.awt.Color color; // the color of the ball
    private Velocity velocity = new Velocity(5, 5); // the velocity of the ball
    private GameEnvironment environment = null;
    private Rectangle rectangleScreen = null;

    /**
     * Constructor for a Ball object.
     *
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = Math.abs(r);
        this.color = color;
    }

    /**
     * Accessor for the color of the ball.
     *
     * @return the color of the ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Accessor for the center point of the ball.
     *
     * @return the center point of the ball
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Sets the radius of the object.
     *
     * @param radius the new radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Sets the color of the object.
     *
     * @param color the new color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the game environment of this object.
     *
     * @param environment the game environment to set.
     */
    public void setEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Sets the rectangle screen of this object.
     * this rectangle represent the screen gui of the game inside borders.
     *
     * @param rectangleScreen the rectangle screen to set.
     */
    public void setRectangleScreen(Rectangle rectangleScreen) {
        this.rectangleScreen = rectangleScreen;
    }

    /**
     * Draws the ball on the given DrawSurface.
     * // draw the ball on the given DrawSurface
     *
     * @param surface the DrawSurface to draw the ball on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the new velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param dx the change in x-coordinate of the ball's velocity
     * @param dy the change in y-coordinate of the ball's velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Accessor for the velocity of the ball.
     *
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Moves the ball by one step according to its current velocity and the boundaries of the playing area.
     * If the ball hits a boundary, its velocity is reflected accordingly.
     */
    public void moveOneStep() {
        // If velocity is not defined, do not move the ball
        if (this.velocity == null) {
            return;
        }

        // If the ball is out of screen borders, bounce it back in
        if (this.rectangleScreen != null) {
            boolean outOfBorders = false;
            if (this.center.getX() < this.rectangleScreen.getUpperLeft().getX()
                    || Line.floatsComparison(this.center.getX(), this.rectangleScreen.getUpperLeft().getX())) {
                this.velocity.setDx(Math.abs(this.velocity.getDx()));
                outOfBorders = true;
            }
            if (this.center.getX() > this.rectangleScreen.getUpperRight().getX()
                    || Line.floatsComparison(this.center.getX(), this.rectangleScreen.getUpperRight().getX())) {
                this.velocity.setDx(Math.abs(this.velocity.getDx()) * -1);
                outOfBorders = true;
            }
            if (this.center.getY() < this.rectangleScreen.getUpperLeft().getY()
                    || Line.floatsComparison(this.center.getY(), this.rectangleScreen.getUpperLeft().getY())) {
                this.velocity.setDy(Math.abs(this.velocity.getDy()));
                outOfBorders = true;
            }
            if (outOfBorders) {
                this.center = this.getVelocity().applyToPoint(this.center);
                return;
            }
        }

        // If the ball is stuck, change its direction to avoid getting stuck
        if (this.ballIsStuck(this.environment.getCollidableList())) {
            Velocity opposite = this.getVelocity().oppositeVelocityVectorDirection();
            Point temp = this.center;
            this.center = new Point(this.center.getX() + opposite.getDx(),
                    this.center.getY() + opposite.getDy());
            if (!this.ballIsStuck(this.environment.getCollidableList())) {
                this.setVelocity(this.velocity.oppositeVelocityVectorDirection());
                return;
            } else {
                this.center = temp;
            }
            this.center = this.getVelocity().applyToPoint(this.center);
            return;
        }

        // Get the trajectory of the ball and find the closest collision with a collidable object
        Line trajectory = this.ballTrajectory();
        CollisionInfo currentCollision = this.environment.getClosestCollision(trajectory);

        // Check if there is a collision with another object
        if (currentCollision != null) {
            // Get the point of collision and the collidable object
            Point collisionPoint = currentCollision.collisionPoint();
            Collidable collisionObject = currentCollision.collisionObject();
            // Determine the range of the ball's velocity
            double range = this.velocity.speed();
            // Check if the ball collides with the collidable object within range
            if (this.getCenter().distance(collisionPoint) < range
                    || Line.floatsComparison(this.getCenter().distance(collisionPoint), range)) {
                // Get all collisions that may occur during the ball's movement
                List<CollisionInfo> allCollusion = this.environment.getCollisions(trajectory);
                // Check if there are multiple collisions during the ball's movement
                boolean multipleCollision = false;
                CollisionInfo c = null;
                for (CollisionInfo collusion : allCollusion) {
                    // If the current collision is detected, skip it
                    if (collusion.collisionObject() == currentCollision.collisionObject()) {
                        continue;
                    }
                    // Check if the collision point is within the range of currentCollision Point
                    if ((collusion.collisionPoint().equals(currentCollision.collisionPoint()))) {
                        multipleCollision = true;
                        c = collusion;
                    }
                }
                // If there is only one collision, change the ball's velocity according to the collision object
                if (!multipleCollision) {
                    this.setVelocity(collisionObject.hit(this, collisionPoint, this.getVelocity()));
                    // If there are multiple collisions, change the ball's velocity to the opposite direction
                } else {
                    this.velocity = this.multipleCollisionBehaviorAtSamePoint(c, currentCollision);
                }
            }
        }
        // Update the ball's center position based on its current velocity
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * Calculates the new velocity of a ball when it collides with two collidables at the same point.
     *
     * @param info1 information about the first collision.
     * @param info2 information about the second collision.
     * @return the new velocity of the ball after the collision.
     */

    public Velocity multipleCollisionBehaviorAtSamePoint(CollisionInfo info1, CollisionInfo info2) {
        double dx = this.velocity.getDx(), dy = this.velocity.getDy();
        // Calling hit method for run notify hit in the blocks
        Velocity v = new Velocity(0, 0);
        v = info1.collisionObject().hit(this, info1.collisionPoint(), v);
        v = info2.collisionObject().hit(this, info2.collisionPoint(), v);
        Rectangle rect1 = info1.collisionObject().getCollisionRectangle(),
                rect2 = info2.collisionObject().getCollisionRectangle();
        Line[] l1 = rect1.getRectangleLines();
        Line[] l2 = rect2.getRectangleLines();

        // Check if the ball hit the collidables on their horizontal edges
        if (Line.floatsComparison(l1[0].start().getY(), l2[0].start().getY())
                && Line.floatsComparison(l1[2].start().getY(), l2[2].start().getY())) {
            return new Velocity(dx, -1 * dy); // Invert the y component of the velocity
        }

        // Check if the ball hit the collidables on their vertical edges
        if (Line.floatsComparison(l1[1].start().getX(), l2[1].start().getX())
                && Line.floatsComparison(l1[3].start().getX(), l2[3].start().getX())) {
            return new Velocity(-1 * dx, dy); // Invert the x component of the velocity
        }

        return this.velocity.oppositeVelocityVectorDirection(); // Invert both components of the velocity
    }

    /**
     * Returns the line representing the current trajectory of the ball. Calculates the trajectory
     * by iterating the ball's position until it exits the game screen or hits a boundary, using the
     * current velocity. If the velocity is zero, returns null.
     *
     * @return the line representing the current trajectory of the ball, or null if the velocity is zero
     */
    public Line ballTrajectory() {
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Define upper left and bottom right points of screen
        Point upperLeft = new Point(0, 0),
                bottomRight = new Point(screenSize.getWidth(), screenSize.getHeight());
        // Get velocity components and current position
        double dx = this.velocity.getDx(), dy = this.velocity.getDy(),
                x = this.center.getX(), y = this.center.getY();
        // If there is no movement, return null
        if (dx == 0 && dy == 0) {
            return null;
        }
        // Calculate the trajectory of the ball until it leaves the screen
        while ((x > upperLeft.getX() || Line.floatsComparison(x, upperLeft.getX()))
                && (x < bottomRight.getX() || Line.floatsComparison(x, bottomRight.getX()))
                && (y > upperLeft.getY() || Line.floatsComparison(y, upperLeft.getY()))
                && (y < bottomRight.getY() || Line.floatsComparison(y, bottomRight.getY()))) {
            x += dx;
            y += dy;
        }
        // Return the line representing the trajectory of the ball
        return new Line(this.center, new Point(x, y));
    }


    /**
     * Adds the ball to the given game, by adding it as a sprite and setting the game environment.
     *
     * @param g the game to add the ball to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        this.setEnvironment(g.getEnvironment());
    }

    /**
     * Removes the ball from the game by removing it from the game's sprites.
     *
     * @param g The game from which the ball will be removed.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }


    /**
     * Determines if the ball is currently stuck inside a collidable object, by checking if its center
     * is inside the bounds of any collidable objects in the given list.
     *
     * @param collidableList a list of collidable objects to check against
     * @return true if the ball is stuck inside a collidable object, false otherwise
     */
    public boolean ballIsStuck(List<Collidable> collidableList) {
        boolean ballStuck = false;
        for (Collidable c : collidableList) {
            if (this.center.pointInsideRectangle(c)) {
                ballStuck = true;
                break;
            }
        }
        return ballStuck;
    }
}