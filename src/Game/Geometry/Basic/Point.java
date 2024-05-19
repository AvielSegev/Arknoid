// 318807104 Aviel Segev

package Game.Geometry.Basic;

import Game.Geometry.Shapes.Rectangle;
import Game.Interfaces.Collidable;

/**
 * @author Aviel Segev <address>aviel281999@gmail.com</address>
 * @version 1.0
 * @since 03-04-2023
 * Represents a 2-dimensional point with x and y coordinates.
 */

public class Point {
    static final double EPSILON = .00001; // the approximation value for compare two doubles
    private final double x; // x coordinate of the point
    private final double y; // y coordinate of the point

    /**
     * Constructs a Point with the specified x and y coordinates.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the distance between this point and another point.
     *
     * @param other the other point to calculate the distance to
     * @return the distance between this point and the other point
     */
    public double distance(Point other) {
        double distance = 0;
        // If the two Points are equal, return a distance of 0
        if (equals(other)) {
            return distance;
        } else {
            // Calculate the distance between the x and y coordinates using the Pythagorean theorem
            double xDistance = Math.abs(other.x - this.x);
            double yDistance = Math.abs(other.y - this.y);
            distance = ((xDistance * xDistance) + (yDistance * yDistance));
            return Math.sqrt(distance);
        }
    }

    /**
     * Determines whether this point is equal to another point.
     *
     * @param other the other point to compare to
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return (Math.abs(other.x - this.x) < EPSILON) && (Math.abs(other.y - this.y) < EPSILON);
    }

    /**
     * Returns the x coordinate of this point.
     *
     * @return the x coordinate of this point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate of this point.
     *
     * @return the y coordinate of this point
     */
    public double getY() {
        return this.y;
    }


    /**
     * Finds the closest point in a given array to this point.
     *
     * @param arr the array of points to search
     * @return the closest point in the array to this point,
     * or null if the array is empty or contains only null elements
     */
    public Point closest(Point[] arr) {
        int size = arr.length;
        double[] distances = new double[size];
        for (int i = 0; i < size; i++) {
            if (arr[i] != null) {
                distances[i] = this.distance(arr[i]);
            } else {
                distances[i] = Double.MAX_VALUE;
            }
        }
        double minDistance = distances[0];
        int j = 0;
        for (int i = 1; i < size; i++) {
            if (distances[i] < minDistance) {
                minDistance = distances[i];
                j = i;
            }
        }
        if (minDistance != Double.MAX_VALUE) {
            return arr[j];
        } else {
            return null;
        }
    }


    /**
     * Checks if a given point is inside the collision rectangle of a given Collidable object.
     *
     * @param c the Collidable object to check against
     * @return true if the point is inside the collision rectangle of the Collidable object, false otherwise
     */
    public boolean pointInsideRectangle(Collidable c) {
        double x = this.getX(), y = this.getY();  // get the coordinates of the point
        Rectangle rect = c.getCollisionRectangle();  // get the rectangle of the collidable object
        Point upperLeft = rect.getUpperLeft();  // get the upper left point of the rectangle
        Point bottomRight = rect.getBottomRight();  // get the bottom right point of the rectangle
        // check if the point is inside the rectangle
        return x > upperLeft.getX() && x < bottomRight.getX()
                && y > upperLeft.getY() && y < bottomRight.getY();
    }

}