package nebula.core.helper;

import org.newdawn.slick.geom.Rectangle;

/**
 * Collision helper
 */
public class Collision
{
    /**
     * Check a collision between two rectangles
     */
    public static boolean rectangle (
        float x1, float y1, float w1, float h1,
        float x2, float y2, float w2, float h2
    )
    {
        return (!(y1+h1 < y2 || y1 > y2+h2 || x1+w1 < x2 || x1 > x2+w2));
    }
    
    /**
     * Check a collision between two rectangles
     */
    public static boolean rectangle (Rectangle r1, Rectangle r2)
    {
        return Collision.rectangle(
            r1.getX(), r1.getY(), r1.getWidth(), r1.getHeight(),
            r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight()
        );
    }
    
    /**
     * Check a collsion between a point and a rectangle
     */
    public static boolean point (
        float x1, float y1,
        float x2, float y2, float w2, float h2
    )
    {
        return (!(y1 < y2 || y1 > y2+h2 || x1 < x2 || x1 > x2+w2));
    }
}
