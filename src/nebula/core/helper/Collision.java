package nebula.core.helper;

/**
 * Collision helper
 */
public class Collision
{
    /**
     * Check a collision between two rectangles
     */
    public static boolean rectangle (
        int x1, int y1, int w1, int h1,
        int x2, int y2, int w2, int h2
    )
    {
        return (!(y1+h1 < y2 || y1 > y2+h2 || x1+w1 < x2 || x1 > x2+w2));
    }
    
    /**
     * Check a collsion between a point and a rectangle
     */
    public static boolean point (
        int x1, int y1,
        int x2, int y2, int w2, int h2
    )
    {
        return (!(y1 < y2 || y1 > y2+h2 || x1 < x2 || x1 > x2+w2));
    }
}
