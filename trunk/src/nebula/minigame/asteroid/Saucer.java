package nebula.minigame.asteroid;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;


/**
 * Racket class
 */
public class Saucer
{
    static final float w = 128;
    static final float h = 93;
    static final float speed = 0.3f;
    static final float collideOffset = 16.0f;
    
    private float x;
    private float y;
    private Rectangle limits;
    private static Image image;

    public Saucer (Rectangle limits) throws SlickException
    {
        this.limits = limits;
        resetPosition();

        // Set racket image first time
        if (image == null)
            image = new Image(AsteroidGame.imgPath + "saucer.png");
    }
    
    public void goRight (float n)
    {
        x += n;
        
        float limit = limits.getX() + limits.getWidth();
        if (x + w > limit) x = limit - w;
    }
    
    public void goLeft (float n)
    {
        x -= n;
        
        float limit = limits.getX();
        if (x < limit) x = limit;
    }
    
    public void goUp (float n)
    {
        y -= n;
        
        float limit = limits.getY();
        if (y < limit) y = limit;
    }
    
    public void goDown (float n)
    {
        y += n;
        
        float limit = limits.getY() + limits.getHeight();
        if (y + h > limit) y = limit - h;
    }
    
    public void resetPosition ()
    {
        x = limits.getX() + limits.getWidth()/2 - w/2;
        y = limits.getY() + limits.getHeight()/2 - h/2;
    }
    
    public Ellipse getCollideZone ()
    {
        return new Ellipse(
            x+Saucer.w/2,
            y+Saucer.h/2,
            Saucer.w/2-Saucer.collideOffset,
            Saucer.h/2-Saucer.collideOffset
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
