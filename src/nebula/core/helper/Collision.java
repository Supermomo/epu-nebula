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
        float x1, float y1, int w1, int h1,
        float x2, float y2, int w2, int h2
    )
    {
        return (!(y1+h1 < y2 || y1 > y2+h2 || x1+w1 < x2 || x1 > x2+w2));
    }
    
    /**
     * Check a collsion between a point and a rectangle
     */
    public static boolean point (
        float x1, float y1,
        float x2, float y2, int w2, int h2
    )
    {
        return (!(y1 < y2 || y1 > y2+h2 || x1 < x2 || x1 > x2+w2));
    }
}
