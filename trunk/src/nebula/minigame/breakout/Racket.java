package nebula.minigame.breakout;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * Racket class
 */
public class Racket
{
    static final float w = 280;
    static final float h = 35;
    static final float hspeed = 0.8f;
    static final float vspeed = 0.2f;
    
    private float x;
    private float y;
    private float xmin;
    private float xmax;
    private float ymin;
    private float ymax;
    private float ballRPos;
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
            image = new Image(Breakout.imgPath + "racket.png");
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
    
    public void attachBall (Ball ball, float rpos)
    {
        ballRPos = rpos;
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

        ballAttached.setX(
            (x+Racket.w/2-Ball.w/2) + ballRPos * (Racket.w/2 + Ball.w/2));
        ballAttached.setY(y-Ball.h-1);
    }
    
    public void resetPosition ()
    {
        x = (xmax-xmin)/2;
        y = ymin;
        updateBall();
    }

    public void draw ()
    {
        image.draw(x, y, w, h);
    }
    
    public void setX (float x)
    {
        this.x = x;
        updateBall();
    }
    
    public float getX ()
    {
        return x;
    }
    
    public void setY (float y)
    {
        this.y = y;
        updateBall();
    }
    
    public float getY ()
    {
        return y;
    }
}
