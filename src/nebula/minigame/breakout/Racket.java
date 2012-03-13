package nebula.minigame.breakout;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * Racket class
 */
public class Racket
{
    public static final float w = 280;
    public static final float h = 35;
    public static final float hspeed = 0.8f;
    public static final float vspeed = 0.2f;
    
    private float x;
    private float y;
    private float xmin;
    private float xmax;
    private float ymin;
    private float ymax;
    private static Image image;

    public Racket (float xmin, float xmax,
                   float ymin, float ymax) throws SlickException
    {
        this.xmin = xmin;
        this.xmax = xmax-w;
        this.ymin = ymin;
        this.ymax = ymax;
        
        resetPosition();

        // Set racket image first time
        if (image == null)
            image = new Image(Breakout.rccPath + "racket.png");
    }
    
    public void goRight (float n)
    {
        x += n;
        if (x > xmax) x = xmax;
    }
    
    public void goLeft (float n)
    {
        x -= n;
        if (x < xmin) x = xmin;
    }
    
    public void goUp (float n)
    {
        y -= n;
        if (y < ymax) y = ymax;
    }
    
    public void resetPosition ()
    {
        x = (xmax-xmin)/2;
        y = ymin;
    }

    public void draw ()
    {
        image.draw(x, y, w, h);
    }
}
