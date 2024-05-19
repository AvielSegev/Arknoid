// 318807104 Aviel Segev

package Game.Management;

import Game.Geometry.Basic.Point;
import Game.Geometry.Basic.Line;
import Game.Interfaces.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameEnvironment class represents the environment in which the game takes place.
 * <p>
 * It holds a list of collidable objects and provides methods to add and remove collidables from the list.
 * </p>
 * It also provides methods to check for collisions between a given line trajectory and
 * the collidable objects in the list.
 */
public class GameEnvironment {
    // The list of collidable objects in the environment.
    private final java.util.List<Collidable> collidableList;

    /**
     * Constructor for GameEnvironment class.
     * Initializes an empty list of collidable objects.
     */
    public GameEnvironment() {
        this.collidableList = new ArrayList<>();
    }

    /**
     * Add a given collidable object to the list of collidables in the environment.
     *
     * @param c the collidable object to be added to the environment
     */
    public void addCollidable(Collidable c) {
        collidableList.add(c);
    }

    /**
     * Remove a given collidable object from the list of collidables in the environment.
     *
     * @param c the collidable object to be removed from the environment
     */
    public void removeCollidable(Collidable c) {
        collidableList.remove(c);
    }

    /**
     * Get the list of collidable objects in the environment.
     *
     * @return the list of collidable objects in the environment
     */
    public List<Collidable> getCollidableList() {
        return this.collidableList;
    }

    /**
     * Check for the closest collision between a given line trajectory
     * and the collidable objects in the environment.
     * If no collision occurs, return null.
     *
     * @param trajectory the line trajectory to check for collisions with
     * @return the CollisionInfo object containing information about the closest collision,
     * or null if no collision occurs
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // If the trajectory is null, return null
        if (trajectory == null) {
            return null;
        }

        java.util.List<Collidable> collidableList = new ArrayList<>(this.collidableList);

        // Declare variables for collision detection
        Point collisionPoint, trajectoryStart = trajectory.start();

        // If there are no collidables in the list, return null
        if (collidableList.isEmpty()) {
            return null;
        } else {
            int size = collidableList.size();
            int i = 0, j = 0;
            Point[] intersections = new Point[size];

            // Iterate through the collidables to get their intersection points with the trajectory
            for (Collidable obj : collidableList) {
                intersections[i] = trajectory.closestIntersectionToStartOfLine(obj.getCollisionRectangle());
                i++;
            }

            // Find the closest intersection point to the start of the trajectory
            collisionPoint = trajectoryStart.closest(intersections);

            // If there is no collision point, return null
            if (collisionPoint == null) {
                return null;
            }

            // Find the collidable object associated with the closest intersection point
            while (!collisionPoint.equals(intersections[j])) {
                j++;
            }

            // Create a new CollisionInfo object with the collision point, collidable object, and collision direction
            return new CollisionInfo(collisionPoint, collidableList.get(j),
                    collidableList.get(j).getCollisionRectangle().isOnVerticalEdge(collisionPoint),
                    collidableList.get(j).getCollisionRectangle().isOnHorizontalEdge(collisionPoint));
        }
    }


    /**
     * Check for all collisions between a given line trajectory and the collidable objects in the environment.
     *
     * @param trajectory the line trajectory to check for collisions with
     * @return a list of CollisionInfo objects containing information about all collisions,
     * or an empty list if no collisions occur
     */
    public List<CollisionInfo> getCollisions(Line trajectory) {
        List<CollisionInfo> collisionInfos = new ArrayList<>();
        // Iterate over all collidables in the list
        for (Collidable obj : this.collidableList) {
            // Find the closest intersection point between the trajectory and the collidable's collision rectangle
            Point intersection = trajectory.closestIntersectionToStartOfLine(obj.getCollisionRectangle());
            // If an intersection exists, add a new CollisionInfo object to the list
            if (intersection != null) {
                collisionInfos.add(new CollisionInfo(intersection, obj,
                        // Determine whether the intersection point is on
                        // a vertical or horizontal edge of the collidable
                        obj.getCollisionRectangle().isOnVerticalEdge(intersection),
                        obj.getCollisionRectangle().isOnHorizontalEdge(intersection)));
            }
        }
        // Return the list of CollisionInfo objects
        return collisionInfos;
    }

}
