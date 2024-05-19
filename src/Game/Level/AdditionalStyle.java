// 318807104 Aviel Segev
package Game.Level;

import Game.Geometry.Basic.Point;
import Game.Geometry.Shapes.Rectangle;
import Game.Interfaces.LevelInformation;
import Game.Interfaces.Sprite;
import Game.Sprites.Collidable.Block;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The AdditionalStyle class represents additional styling for specific levels.
 */
public class AdditionalStyle implements Sprite {
    private final String level;
    private final LevelInformation levelInformation;
    private final DrawSurface d;

    /**
     * Constructs a new AdditionalStyle object.
     *
     * @param d                The DrawSurface to draw on.
     * @param levelInformation The level information.
     */
    public AdditionalStyle(DrawSurface d, LevelInformation levelInformation) {
        this.level = levelInformation.levelName();
        this.d = d;
        this.levelInformation = levelInformation;
    }

    /**
     * Add additional style based on the level name.
     */
    public void addAdditionalStyle() {
        switch (this.level) {
            case "Direct Hit":
                additionalStyleLevel1();
                break;
            case "Wide Easy":
                additionalStyleLevel2();
                break;
            case "Green 3":
                additionalStyleLevel3();
                break;
            default:
                break;
        }
    }

    /**
     * Add additional style for "Direct Hit" level.
     */
    public void additionalStyleLevel1() {
        Block block = this.levelInformation.blocks().get(0);
        Rectangle rectangle = block.getCollisionRectangle();
        // Calculate the center point of the square
        double squareCenterX = rectangle.getUpperLeft().getX()
                + Math.abs(rectangle.getUpperLeft().getX() - rectangle.getUpperRight().getX()) / 2;
        double squareCenterY = rectangle.getUpperLeft().getY()
                + Math.abs(rectangle.getBottomLeft().getY() - rectangle.getUpperRight().getY()) / 2;

        // Draw circles around the square
        d.setColor(Color.blue);
        d.drawCircle((int) squareCenterX, (int) squareCenterY, 35);
        d.drawCircle((int) squareCenterX, (int) squareCenterY, 65);
        d.drawCircle((int) squareCenterX, (int) squareCenterY, 90);

        // Draw lines extending from the square
        d.drawLine((int) rectangle.getUpperRight().getX() + 5, (int) squareCenterY,
                (int) rectangle.getUpperRight().getX() + 100, (int) squareCenterY);
        d.drawLine((int) rectangle.getUpperLeft().getX() - 5, (int) squareCenterY,
                (int) rectangle.getUpperLeft().getX() - 100, (int) squareCenterY);
        d.drawLine((int) squareCenterX, (int) rectangle.getBottomLeft().getY() + 5,
                (int) squareCenterX, (int) rectangle.getBottomLeft().getY() + 100);
        d.drawLine((int) squareCenterX, (int) rectangle.getUpperLeft().getY() - 5,
                (int) squareCenterX, (int) rectangle.getUpperLeft().getY() - 75);
    }

    /**
     * Add additional style for "Wide Easy" level.
     */
    public void additionalStyleLevel2() {
        Color lightYellow = new Color(245, 245, 132);
        Color darkYellow = new Color(238, 238, 79);
        int xCenter = 120, yCenter = 150, xLine = 25, yLine = 250;

        // Draw lines from a central point
        d.setColor(lightYellow);
        while (xLine < 730) {
            d.drawLine(xCenter, yCenter, xLine, yLine);
            if (xLine <= 650) {
                xLine += 8;
            } else {
                xLine += 10;
            }
        }

        // Draw circles with different colors
        d.setColor(lightYellow);
        d.fillCircle(xCenter, yCenter, 60);
        d.setColor(darkYellow);
        d.fillCircle(xCenter, yCenter, 50);
        d.setColor(Color.yellow);
        d.fillCircle(xCenter, yCenter, 40);
    }

    /**
     * Add additional style for "Green 3" level.
     */
    public void additionalStyleLevel3() {
        Rectangle pillar = new Rectangle(new Point(60, 400), 100, 200);
        Rectangle pillar1 = new Rectangle(new Point(97, 350), 26, 50);
        Rectangle pillar2 = new Rectangle(new Point(106, 200), 8, 150);

        // Draw the main pillar
        d.setColor(Color.black);
        d.fillRectangle((int) pillar.getUpperLeft().getX(), (int) pillar.getUpperLeft().getY(),
                (int) pillar.getWidth(), (int) pillar.getHeight());

        // Draw additional rectangles
        Rectangle[] whiteBlocks = new Rectangle[25];
        d.setColor(Color.darkGray);
        d.fillRectangle((int) pillar1.getUpperLeft().getX(), (int) pillar1.getUpperLeft().getY(),
                (int) pillar1.getWidth(), (int) pillar1.getHeight());
        d.fillRectangle((int) pillar2.getUpperLeft().getX(), (int) pillar2.getUpperLeft().getY(),
                (int) pillar2.getWidth(), (int) pillar2.getHeight());

        int k = 0, x = 68, y = 405;

        // Draw a grid of white rectangles
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                whiteBlocks[k] = new Rectangle(new Point(x, y), 12, 40);
                x += 18;
                k++;
            }
            x = 68;
            y += 45;
        }

        // Draw the white rectangles
        d.setColor(Color.white);
        for (Rectangle block : whiteBlocks) {
            d.fillRectangle((int) block.getUpperLeft().getX(), (int) block.getUpperLeft().getY(),
                    (int) block.getWidth(), (int) block.getHeight());
        }

        // Draw circles with different colors
        d.setColor(new Color(255, 153, 0));
        d.fillCircle(110, 200, 12);
        d.setColor(Color.red);
        d.fillCircle(110, 200, 8);
        d.setColor(Color.white);
        d.fillCircle(110, 200, 4);
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.addAdditionalStyle();
    }

    @Override
    public void timePassed() {

    }
}