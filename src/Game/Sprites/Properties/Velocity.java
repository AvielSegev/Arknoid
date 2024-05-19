// 318807104 Aviel Segev

package Game.Sprites.Properties;

import Game.Geometry.Basic.Point;

/**
 * @author Aviel Segev <address>aviel281999@gmail.com</address>
 * @version 1.0
 * @since 03-04-2023
 * A class representing a velocity with a delta x and delta y.
 */
public class Velocity {
    private static final int GRAPH_ANGLE = 90; // axis system angle
    private double dx; // the delta x component of this velocity
    private double dy; // the delta y component of this velocity

    /**
     * Constructs a new Velocity object with the given dx and dy components.
     *
     * @param dx the delta x component of this velocity
     * @param dy the delta y component of this velocity
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Takes a point with position (x,y) and returns a new point
     * with position (x+dx, y+dy).
     *
     * @param p the point to apply this velocity to
     * @return a new point with the applied velocity
     */
    public Point applyToPoint(Point p) {
        if (p != null) {
            return new Point(p.getX() + dx, p.getY() + dy);
        } else {
            return null;
        }
    }

    /**
     * Returns the delta x component of this velocity.
     *
     * @return the delta x component of this velocity
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Returns the delta y component of this velocity.
     *
     * @return the delta y component of this velocity
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Sets the horizontal velocity component of the object.
     *
     * @param dx the new horizontal velocity component to set
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Sets the vertical velocity component of the object.
     *
     * @param dy the new vertical velocity component to set
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Calculates and returns the current speed of the ball.
     *
     * @return the current speed of the ball.
     */
    public double speed() {
        double dx = this.getDx();
        double dy = this.getDy();
        return Math.sqrt(Math.abs(Math.pow(dx, 2)) + Math.abs(Math.pow(dy, 2)));
    }

    /**
     * Constructs a new Velocity object with the given angle and speed.
     *
     * @param angle the angle in degrees
     * @param speed the speed
     * @return a new Velocity object with the given angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // Calculate the change in x and y components based on the angle and speed,
        // adjusting for the graphical coordinate system
        double dx = Math.cos(Math.toRadians(angle - GRAPH_ANGLE)) * speed;
        double dy = Math.sin(Math.toRadians(angle - GRAPH_ANGLE)) * speed;
        // Create and return a new Velocity object with the calculated x and y components
        return new Velocity(dx, dy);
    }

    /**
     * Returns a new velocity with the opposite direction to this velocity.
     *
     * @return the opposite velocity vector direction.
     */
    public Velocity oppositeVelocityVectorDirection() {
        return new Velocity(this.getDx() * -1, this.getDy() * -1);
    }
}