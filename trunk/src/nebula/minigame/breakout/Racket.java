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
    private Ball ballAttached;
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
        
        updateBall();
    }
    
    public void goLeft (float n)
    {
        x -= n;
        if (x < xmin) x = xmin;
        
        updateBall();
    }
    
    public void goUp (float n)
    {
        y -= n*(y-ymax)*0.04f;
        if (y < ymax) y = ymax;
        
        updateBall();
    }
    
    public void goActivePosition ()
    {
        y = ymax;
        updateBall();
    }
    
    public void attachBall (Ball ball)
    {
        ballAttached = ball;
        updateBall();
    }
    
    public void detachBall ()
    {
        ballAttached = null;
    }
    
    public boolean haveAttachedBall ()
    {
        return (ballAttached != null);
    }
    
    public void updateBall ()
    {
        if (ballAttached == null) return;

        ballAttached.setX(x+Racket.w/2-Ball.w/2);
        ballAttached.setY(y-Ball.h);
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
    
    public float getX ()
    {
        return x;
    }
    
    public float getY ()
    {
        return y;
    }
}
