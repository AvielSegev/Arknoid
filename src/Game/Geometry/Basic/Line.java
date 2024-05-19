// 318807104 Aviel Segev

package Game.Geometry.Basic;

import java.util.List;

import Game.Geometry.Shapes.Rectangle;

/**
 * @author Aviel Segev <address>aviel281999@gmail.com</address>
 * @version 1.0
 * @since 03-04-2023
 * The Line class represents a geometric line in 2D space.
 */
public class Line {
    private final Point start; // one edge of line, x coordinates smaller than end Point
    private final Point end; // Second edge of line, x coordinates Bigger than end Point
    private final double slope; // The slope of the line
    private final double b; // The y-intercept of the line
    private boolean vertical = false; // True if the line is vertical, false otherwise
    private boolean horizontal = false; // True if the line is horizontal, false otherwise
    private boolean pointSwapped = false; // True if start and end point swap in compare to constructor provided values

    /**
     * Creates a line using two given points.
     * If the start point has a greater x-coordinate than the end point, they will be swapped.
     * Calculates the slope and the intercept of the line equation,
     * and determines if the line is horizontal or vertical.
     *
     * @param start the starting point of the line
     * @param end   the end point of the line
     */
    public Line(Point start, Point end) {
        if (!(start.getX() < end.getX())) {
            // If the starting X coordinate is greater than or equal to the ending X coordinate,
            // swap the start and end points to ensure that the line is drawn left-to-right
            this.start = end;
            this.end = start;
            this.pointSwapped = true;
        } else {
            // Otherwise, use the given start and end points
            this.start = start;
            this.end = end;
        }

        // Calculate the slope and y-intercept of the line
        if ((start.getX() - end.getX() != 0)) {
            // If the line is not vertical, calculate its slope and y-intercept
            this.slope = (start.getY() - end.getY()) / (start.getX() - end.getX());
        } else {
            // If the line is vertical, set its slope to positive infinity
            this.slope = Double.POSITIVE_INFINITY;
        }
        this.b = start.getY() - start.getX() * slope;

        // Check if the line is vertical or horizontal
        if (floatsComparison(this.start.getX(), this.end.getX())) {
            this.vertical = true;
        }
        if (floatsComparison(this.start.getY(), this.end.getY())) {
            this.horizontal = true;
        }
    }

    /**
     * Creates a line using four given coordinates.
     * If the start point has a greater x-coordinate than the end point, they will be swapped.
     * Calculates the slope and the intercept of the line equation,
     * and determines if the line is horizontal or vertical.
     *
     * @param x1 the x-coordinate of the starting point of the line
     * @param y1 the y-coordinate of the starting point of the line
     * @param x2 the x-coordinate of the end point of the line
     * @param y2 the y-coordinate of the end point of the line
     */
    public Line(double x1, double y1, double x2, double y2) {
        // Create two Point objects from the given x and y coordinates
        Point start = new Point(x1, y1);
        Point end = new Point(x2, y2);
        if (!(start.getX() < end.getX())) {
            // Ensure that the start point has a smaller x-coordinate than the end point
            // by swapping them if necessary
            this.start = end;
            this.end = start;
            this.pointSwapped = true;
        } else {
            this.start = start;
            this.end = end;
        }
        // Calculate the slope and y-intercept of the line
        if ((start.getX() - end.getX() != 0)) {
            // Avoid division by zero and calculate the slope of a non-vertical line
            this.slope = (y1 - y2) / (x1 - x2);
        } else {
            // For a vertical line, set the slope to positive infinity
            this.slope = Double.POSITIVE_INFINITY;
        }
        // Calculate the y-intercept of the line using the slope and one point on the line
        this.b = y1 - x1 * slope;
        // Determine if the line is vertical or horizontal based on the start and end points
        if (floatsComparison(this.start.getX(), this.end.getX())) {
            this.vertical = true;
        }
        if (floatsComparison(this.start.getY(), this.end.getY())) {
            this.horizontal = true;
        }
    }

    /**
     * Returns the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        // Calculate the middle x and y coordinates
        double middleX = ((this.start.getX() + this.end.getX()) / 2);
        double middleY = ((this.start.getY() + this.end.getY()) / 2);
        // Create a new point object with the middle coordinates and return it
        return new Point(middleX, middleY);
    }

    /**
     * Returns the start point of the line based on order entered to constructor.
     *
     * @return the start point of the line
     */
    public Point start() {
        if (!pointSwapped) {
            return this.start;
        } else {
            return this.end;
        }
    }

    /**
     * Returns the end point of the line, based on order entered to constructor.
     *
     * @return The end point of the line.
     */
    public Point end() {
        if (!pointSwapped) {
            return this.end;
        } else {
            return this.start;
        }
    }

    /**
     * Returns true if the given y value lies on the line, false otherwise.
     *
     * @param y The y coordinate to check.
     * @return True if the y value lies on the line, false otherwise.
     */
    public boolean yIsOnLine(double y) {
        // Check if y is between the start and end y-coordinates of the line
        return ((y > this.start.getY() || Line.floatsComparison(y, this.start.getY()))
                && (y < this.end.getY() || Line.floatsComparison(y, this.end.getY())))
                || ((y < this.start.getY() || Line.floatsComparison(y, this.start.getY()))
                && (y > this.end.getY() || Line.floatsComparison(y, this.end.getY())));
    }

    /**
     * Returns true if this line intersects with the given line, false otherwise.
     *
     * @param other The line to check for intersection with.
     * @return True if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        // Check if at least one line is vertical
        if (this.vertical || other.vertical) {
            // Check if the vertical lines overlap on the same X coordinate
            if (floatsComparison(this.start.getX(), other.start.getX())
                    && this.vertical && other.vertical) {
                // If one of the lines starts before the other ends, there is intersection
                if (this.start.getY() < other.start.getY()) {
                    return this.end.getY() > other.start.getY()
                            || Line.floatsComparison(other.start.getY(), this.end.getY());
                } else if (this.start.getY() > other.start.getY()) {
                    return other.end.getY() > this.start.getY()
                            || Line.floatsComparison(other.end.getY(), this.start.getY());
                } else {
                    return true;
                }
            }
            // If only one of the lines is vertical, delegate the intersection check to the other line
            if (this.vertical) {
                return other.verticalIntersection(this);
            } else {
                return this.verticalIntersection(other);
            }
        } else { // The lines are not vertical
            // If the lines are parallel, there is no intersection unless they overlap
            if (floatsComparison(this.slope, other.slope)) {
                if (!floatsComparison(this.b, other.b)) {
                    return false;
                }
                if (this.start.getX() < other.start.getX()) {
                    return this.end.getX() > other.start.getX()
                            || Line.floatsComparison(this.end.getX(), other.start.getX());
                } else if (this.start.getX() > other.start.getX()) {
                    return other.end.getX() > this.start.getX()
                            || Line.floatsComparison(other.end.getX(), this.start.getX());
                } else {
                    return true;
                }
            } else { // The lines are not parallel and intersect at a point
                Point p = calcIntersection(other); // Calculate the intersection point
                double pX = p.getX(); // Store the X coordinate of the intersection point
                // Check if the intersection point is inside the bounds of both lines
                return (pX > other.start.getX() || Line.floatsComparison(pX, other.start.getX()))
                        && (pX > this.start.getX() || Line.floatsComparison(pX, this.start.getX()))
                        && (pX < other.end.getX() || Line.floatsComparison(pX, other.end.getX()))
                        && (pX < this.end.getX() || Line.floatsComparison(pX, this.end.getX()));
            }
        }
    }

    /**
     * This function returns the intersection point of two lines, or null if the lines do not intersect.
     * If the two lines are vertical, the function checks if they intersect in a point or have a common segment.
     * If only one line is vertical, the function returns the intersection point.
     * If the two lines are non-vertical and have the same slope, the function returns null.
     * Otherwise, the function calculates the intersection point using the formula y = mx + b and the slope-intercept
     * form of a line.
     *
     * @param other The line to check intersection with.
     * @return The intersection point if the lines intersect, or null if they do not.
     */
    public Point intersectionWith(Line other) {
        // check if the lines are intersecting
        if (!isIntersecting(other)) {
            return null;
        } else {
            // check if one or both lines are vertical
            if (this.vertical || other.vertical) {
                // if both lines are vertical
                if (this.vertical && other.vertical) {
                    // check if the lines share a common point
                    if (floatsComparison(this.end.getY(), other.start.getY())) {
                        double x = this.end.getX(), y = this.end.getY();
                        return new Point(x, y);
                    } else if (floatsComparison(this.start.getY(), other.end.getY())) {
                        double x = this.start.getX(), y = this.start.getY();
                        return new Point(x, y);
                    } else {
                        return null;
                    }
                } else {
                    // if only one line is vertical
                    if (this.vertical) {
                        return new Point(this.start.getX(), other.verticalIntersectionPoint(this.start.getX()));
                    } else {
                        return new Point(other.start.getX(), this.verticalIntersectionPoint(other.start.getX()));
                    }
                }
            } else {
                // if neither line is vertical, and they have different slopes, find the intersection point
                if (this.slope != other.slope) {
                    return calcIntersection(other);
                }
                // if neither line is vertical, and they have the same slope, check if they share a common point
                if (floatsComparison(this.end.getX(), other.start.getX())) {
                    double x = this.end.getX(), y = this.slope * x + this.b;
                    return new Point(x, y);
                } else if (floatsComparison(this.start.getX(), other.end.getX())) {
                    double x = this.start.getX(), y = this.slope * x + this.b;
                    return new Point(x, y);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Calculates the intersection point between two non-vertical lines.
     *
     * @param other The other line to intersect with.
     * @return The intersection point between the two lines.
     */
    private Point calcIntersection(Line other) {
        double x = (this.b - other.b) / (other.slope - this.slope); // Calculate x value of intersection point
        // y = mx + b
        double y = (this.slope * x) + this.b; // Calculate y value of intersection point using line's equation
        return new Point(x, y); // Return intersection point
    }

    /**
     * Checks if this Line object is equal to another Line object by comparing the X and Y coordinates of
     * their start and end points within a certain threshold.
     *
     * @param other the other Line object to compare to
     * @return true if the two Line objects are equal within a certain threshold, false otherwise
     */
    public boolean equals(Line other) {
        // Extracting the X and Y coordinates of the start and end points of the other Line object.
        double thisStartX = this.start.getX(), thisEndX = this.end.getX(),
                thisStartY = this.start.getY(), thisEndY = this.end.getY();
        double otherStartX = other.start.getX(), otherEndX = other.end.getX(),
                otherStartY = other.start.getY(), otherEndY = other.end.getY();
        // Checking if the X and Y coordinates of the start and end points of both Line objects are equal
        // within a certain threshold using the thresholdBasedFloatsComparison method.
        return floatsComparison(thisStartX, otherStartX)
                && floatsComparison(thisStartY, otherStartY)
                && floatsComparison(thisEndX, otherEndX)
                && floatsComparison(thisEndY, otherEndY);
    }

    /**
     * Compares two floating-point coordinates within a certain threshold to account for rounding errors.
     *
     * @param coordinate1 the first coordinate to compare
     * @param coordinate2 the second coordinate to compare
     * @return true if the absolute difference between the two coordinates is less than a certain threshold,
     * false otherwise
     */
    public static boolean floatsComparison(double coordinate1, double coordinate2) {
        // Defining the threshold as 0.00001.
        final double epsilon = .00001;
        // Checking if the absolute difference between the two coordinates is less than the threshold.
        return Math.abs(coordinate1 - coordinate2) < epsilon;
    }

    /**
     * Checks if this Line object intersects with the other Line object vertically.
     *
     * @param other the Line object to check for vertical intersection with this Line object.
     * @return true if this Line object intersects with the other Line object vertically, false otherwise.
     */
    private boolean verticalIntersection(Line other) {
        // Extracting the X coordinate of the start point of the other Line object.
        double x = other.start.getX();
        // Calculating the Y coordinate of the intersection point using the equation of this Line object.
        double y = (this.slope * x) + this.b;
        // Checking if this Line object is horizontal.
        if (this.horizontal) {
            // If this Line object is horizontal,
            // the intersection point lies on the Y coordinate of the start point of this Line object.
            // Checking if the Y coordinate of the intersection point is on the other Line object
            // and if the X coordinate of the intersection point
            // lies within the range of X coordinates of this Line object.
            return other.yIsOnLine(y)
                    && (((x > this.start.getX() || Line.floatsComparison(x, this.start.getX()))
                    && (x < this.end.getX() || Line.floatsComparison(x, this.end.getX())))
                    || ((x < this.start.getX() || Line.floatsComparison(x, this.start.getX()))
                    && (x > this.end.getX()) || Line.floatsComparison(x, this.end.getX())));
        } else {
            // If this Line object is not horizontal, we need to check if the Y coordinate of the intersection point
            // lies within the range of Y coordinates of both Line objects.
            return this.yIsOnLine(y) && other.yIsOnLine(y);
        }
    }

    /**
     * Calculates the Y coordinate of the intersection point between this Line object and a vertical Line
     * passing through the specified X coordinate.
     *
     * @param x the X coordinate of the intersection point
     * @return the Y coordinate of the intersection point
     */
    private double verticalIntersectionPoint(double x) {
        // Calculating the Y coordinate of the intersection point using the equation of this Line object.
        return this.slope * x + this.b;
    }


    /**
     * Calculates the closest intersection point between this line and a given rectangle.
     * If this line does not intersect with the rectangle, returns null.
     *
     * @param rect the rectangle to check for intersection with
     * @return the closest intersection point to the start of the line, or null if there is no intersection
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        Point start = this.start;
        if (this.pointSwapped) {
            start = this.end;
        }
        if (intersectionPoints.isEmpty()) { // No intersection points were found
            return null;
        } else { // At least one intersection point was found
            int size = intersectionPoints.size(), i = 0;
            double[] distance = new double[size];
            // Calculate the distance between each intersection point and the start point of the line
            for (Point p : intersectionPoints) {
                if (p != null) {
                    distance[i] = p.distance(start);
                    i++;
                }
            }
            // Find the intersection point that is closest to the start point of the line
            double minDistance = distance[0];
            int j = 0;
            for (int k = 0; k < i; k++) {
                if (minDistance > distance[k]) {
                    minDistance = distance[k];
                    j = k;
                }
            }
            return intersectionPoints.get(j);
        }
    }
}