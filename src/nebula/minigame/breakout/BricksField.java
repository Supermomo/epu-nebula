package nebula.minigame.breakout;


/**
 * BricksField class
 */
public class BricksField
{
    private float x;
    private float y;
    private float w;
    private float h;
    private int r;
    private int c;
    
    public BricksField (float x, float y, float w, float h, int r, int c)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.r = r;
        this.c = c;
    }

    public float getX ()
    {
        return x;
    }

    public float getY ()
    {
        return y;
    }

    public float getWidth ()
    {
        return w;
    }

    public float getHeight ()
    {
        return h;
    }

    public int getRow ()
    {
        return r;
    }

    public int getColumn ()
    {
        return c;
    }
}
