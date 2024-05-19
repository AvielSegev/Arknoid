// 318807104 Aviel Segev

package Game.Geometry.Shapes;

import java.util.ArrayList;

import Game.Geometry.Basic.Point;
import Game.Geometry.Basic.Line;

import java.util.List;

/**
 * A class representing a rectangle defined by its upper-left and bottom-right corners.
 */
public class Rectangle {
    // The upper-left corner of the rectangle
    private Point upperLeft;

    // The upper-right corner of the rectangle
    private Point upperRight;

    // The bottom-left corner of the rectangle
    private Point bottomLeft;

    // The bottom-right corner of the rectangle
    private Point bottomRight;

    // The width of the rectangle
    private double width;

    // The height of the rectangle
    private double height;

    /**
     * Constructs a new rectangle with the given upper left corner point, width, and height.
     *
     * @param upperLeft the upper left corner point of the rectangle.
     * @param width     the width of the rectangle.
     * @param height    the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.upperRight = new Point(upperLeft.getX() + Math.abs(width), upperLeft.getY());
        this.bottomRight = new Point(upperLeft.getX() + Math.abs(width), upperLeft.getY() + Math.abs(height));
        this.bottomLeft = new Point(upperLeft.getX(), upperLeft.getY() + Math.abs(height));
        this.width = width;
        this.height = height;
    }

    /**
     * Calculates and returns a list of intersection points between this rectangle and a given line.
     *
     * @param line the line to check for intersections with this rectangle.
     * @return a list of intersection points between the given line and this rectangle.
     * The list will be empty if there are no intersections.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        // Create a list to store intersection points
        List<Point> intersectionPoints = new ArrayList<>();
        // Get the four lines of the rectangle
        Line[] rectLines = getRectangleLines();
        // Iterate through each line of the rectangle and check if it intersects with the given line
        for (Line rectline : rectLines) {
            if (line.isIntersecting(rectline)) {
                // Add the intersection point to the list of intersection points
                intersectionPoints.add(line.intersectionWith(rectline));
            }
        }
        // Return the list of intersection points
        return intersectionPoints;
    }


    /**
     * Returns the width of this rectangle.
     *
     * @return the width of this rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of this rectangle.
     *
     * @return the height of this rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the Point representing the upper left corner of this rectangle.
     *
     * @return the Point representing the upper left corner of this rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Returns the Point representing the upper right corner of this rectangle.
     *
     * @return the Point representing the upper right corner of this rectangle
     */
    public Point getUpperRight() {
        return this.upperRight;
    }

    /**
     * Returns the Point representing the bottom left corner of this rectangle.
     *
     * @return the Point representing the bottom left corner of this rectangle
     */
    public Point getBottomLeft() {
        return this.bottomLeft;
    }

    /**
     * Returns the Point representing the bottom right corner of this rectangle.
     *
     * @return the Point representing the bottom right corner of this rectangle
     */
    public Point getBottomRight() {
        return this.bottomRight;
    }

    /**
     * Sets the position of the rectangle to the specified upper left corner, width, and height.
     *
     * @param upperLeft the new upper left corner of the rectangle
     * @param width     the new width of the rectangle
     * @param height    the new height of the rectangle
     */
    public void setPosition(Point upperLeft, double width, double height) {
        // Set the upper-left point of the rectangle
        this.upperLeft = upperLeft;

        // Calculate and set the upper-right point of the rectangle
        this.upperRight = new Point(upperLeft.getX() + Math.abs(width), upperLeft.getY());

        // Calculate and set the bottom-right point of the rectangle
        this.bottomRight = new Point(upperLeft.getX() + Math.abs(width), upperLeft.getY() + Math.abs(height));

        // Calculate and set the bottom-left point of the rectangle
        this.bottomLeft = new Point(upperLeft.getX(), upperLeft.getY() + Math.abs(height));

        // Set the width and height of the rectangle
        this.width = width;
        this.height = height;
    }


    /**
     * Returns an array of four Line objects that represent the edges of the rectangle.
     * <p>
     * The first line, line[0], represents the top edge of the rectangle,
     * connecting the upper left corner to the upper right corner.
     * The second line, line[1], represents the right edge of the rectangle,
     * connecting the upper right corner to the bottom right corner.
     * The third line, line[2], represents the bottom edge of the rectangle,
     * connecting the bottom right corner to the bottom left corner.
     * The fourth line, line[3], represents the left edge of the rectangle,
     * connecting the bottom left corner to the upper left corner.
     * </p>
     *
     * @return an array of Line objects that represent the edges of the rectangle.
     */
    public Line[] getRectangleLines() {
        // Create four Line objects, each representing one of the edges of the rectangle.
        // The lines are ordered in clockwise direction, starting from the top edge.
        Line l1 = new Line(this.upperLeft, this.upperRight),
                l2 = new Line(this.upperRight, this.bottomRight),
                l3 = new Line(this.bottomLeft, this.bottomRight),
                l4 = new Line(this.bottomLeft, this.upperLeft);
        // Return an array of the four lines
        return new Line[]{l1, l2, l3, l4};
    }

    /**
     * Returns whether the specified point is on a horizontal edge of the rectangle.
     *
     * @param point the point to check
     * @return true if the point is on a horizontal edge, false otherwise
     */
    public boolean isOnHorizontalEdge(Point point) {
        // get the rectangle lines
        Line[] rectLines = this.getRectangleLines();
        // get the first and third lines
        Line l1 = rectLines[0], l2 = rectLines[2];
        // compare the y coordinate of the given point with the
        // y coordinate of the start point of the first and third lines
        // and return true if they are equal, using the floatsComparison method for floating-point comparison
        return Line.floatsComparison(l1.start().getY(), point.getY())
                || Line.floatsComparison(l2.start().getY(), point.getY());
    }


    /**
     * Returns whether the given point lies on a vertical edge of the rectangle.
     *
     * @param point the point to check
     * @return true if the point lies on a vertical edge, false otherwise
     */

    public boolean isOnVerticalEdge(Point point) {
        // Get the four lines of the rectangle
        Line[] rectLines = this.getRectangleLines();
        // Get the two vertical edges of the rectangle
        Line l1 = rectLines[1], l2 = rectLines[3];
        // Check if the x-coordinate of the point is equal to the x-coordinate of one of the vertical edges
        return Line.floatsComparison(l1.start().getX(), point.getX())
                || Line.floatsComparison(l2.start().getX(), point.getX());
    }
}
