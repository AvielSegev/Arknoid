// 318807104 Aviel Segev
package Game.Management;

import Game.Geometry.Basic.Point;
import Game.Interfaces.Collidable;


/**
 * A class representing information about a collision between a ball and a collidable object.
 */
public class CollisionInfo {
    // The point at which the collision occurs.
    private final Point collisionPoint;
    // The collidable object involved in the collision.
    private final Collidable obj;
    // A boolean indicating if the collision is vertical.
    private final boolean verticalCollusion;
    // A boolean indicating if the collision is horizontal.
    private final boolean horizontalCollusion;

    /**
     * Constructor for the CollisionInfo class.
     *
     * @param collisionPoint      the point at which the collision occurred.
     * @param obj                 the collidable object involved in the collision.
     * @param verticalCollision   a boolean indicating if the collision is vertical.
     * @param horizontalCollision a boolean indicating if the collision is horizontal.
     */
    public CollisionInfo(Point collisionPoint, Collidable obj,
                         boolean verticalCollision, boolean horizontalCollision) {
        this.collisionPoint = collisionPoint;
        this.obj = obj;
        this.horizontalCollusion = verticalCollision;
        this.verticalCollusion = horizontalCollision;
    }

    /**
     * Returns a boolean indicating if the collision is vertical.
     *
     * @return a boolean indicating if the collision is vertical.
     */
    public boolean isVerticalCollusion() {
        return verticalCollusion;
    }

    /**
     * Returns a boolean indicating if the collision is horizontal.
     *
     * @return a boolean indicating if the collision is horizontal.
     */
    public boolean isHorizontalCollusion() {
        return horizontalCollusion;
    }

    /**
     * Returns the point at which the collision occurred.
     *
     * @return the point at which the collision occurred.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.obj;
    }

}