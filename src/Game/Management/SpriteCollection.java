// 318807104 Aviel Segev

package Game.Management;

import biuoop.DrawSurface;

import Game.Interfaces.Sprite;

import java.util.ArrayList;

/**
 * The SpriteCollection class represents a collection of Sprites.
 * <p>
 * The collection is implemented as a list, and allows adding and removing sprites,
 * </p>
 * as well as calling timePassed() and drawOn() methods on all sprites.
 */
public class SpriteCollection {
    private final java.util.List<Sprite> spriteList;

    /**
     * Constructs a new SpriteCollection object.
     * The collection is implemented as an ArrayList.
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList<>();
    }

    /**
     * Adds the given sprite to the collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * Removes the given sprite from the collection, if it exists in the collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.spriteList.remove(s);
    }

    /**
     * Calls the timePassed() method on all sprites in the collection.
     */
    public void notifyAllTimePassed() {
        java.util.List<Sprite> spriteList = new ArrayList<>(this.spriteList);
        for (Sprite s : spriteList) {
            s.timePassed();
        }
    }

    /**
     * Calls the drawOn(d) method on all sprites in the collection.
     *
     * @param d the DrawSurface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        java.util.List<Sprite> spriteList = new ArrayList<>(this.spriteList);
        for (Sprite s : spriteList) {
            s.drawOn(d);
        }
    }
}