package nebula.minigame.breakout;


import nebula.core.helper.Collision;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * Ball class
 */
public class Ball
{
    public static final float w = 48;
    public static final float h = 48;
    
    private float x;
    private float y;
    private static Image image;

    public Ball () throws SlickException
    {
        // Set ball image first time
        if (image == null)
            image = new Image(Breakout.imgPath + "ball.png");
    }
    
    public boolean collideBrick (Brick b)
    {
        return Collision.rectangle(
            x, y, w, h,
            b.getX(), b.getY(), b.getWidth(), b.getHeight()
        );
    }
    
    public boolean collideRacket (Racket r)
    {
        return Collision.rectangle(
            x, y, w, h,
            r.getX(), r.getY(), Racket.w, Racket.h
        );
    }

    public void draw ()
    {
        image.draw(x, y, w, h);
    }
    
    public void setX (float x)
    {
        this.x = x;
    }
    
    public float getX ()
    {
        return x;
    }
    
    public void setY (float y)
    {
        this.y = y;
    }
    
    public float getY ()
    {
        return y;
    }
}
