// 318807104 Aviel Segev
package Game.Interfaces;

import Game.Geometry.Basic.Point;
import Game.Geometry.Shapes.Rectangle;
import Game.Sprites.Properties.Velocity;
import Game.Sprites.Shapes.Ball;

/**

 The Collidable interface represents an object that can collide with other objects.

 It provides methods for retrieving the collision shape of the object and for handling collisions.
 */
public interface Collidable {

    /**

     Returns the collision shape of the object.
     @return the collision rectangle of the object.
     */
    Rectangle getCollisionRectangle();
    /**

     Notifies the object that it was hit at a specified collision point and with a given velocity.
     @param collisionPoint the point where the collision occurred.
     @param currentVelocity the velocity of the colliding object before the hit.
     @param hitter the ball object hitter.
     @return the new velocity of the colliding object after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}