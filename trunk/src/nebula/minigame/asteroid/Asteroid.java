package nebula.minigame.asteroid;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;


/**
 * Racket class
 */
public class Asteroid
{
    static final float wbase = 140;
    static final float hbase = 130;
    static final float collideOffset = 12.0f;
    
    private float x;
    private float y;
    private float w;
    private float h;
    private Rectangle limits;
    private static Image image;

    public Asteroid (Rectangle limits) throws SlickException
    {
        this.limits = limits;
        this.w = wbase;
        this.h = hbase;
        randomize();

        // Set racket image first time
        if (image == null)
            image = new Image(AsteroidGame.imgPath + "asteroid.png");
    }
    
    private void randomize ()
    {
        x = 300;
        y = 300;
    }
    
    public Ellipse getCollideZone ()
    {
        return new Ellipse(
            x+w/2,
            y+h/2,
            w/2-Asteroid.collideOffset,
            w/2-Asteroid.collideOffset
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
    
    public float getWidth ()
    {
        return w;
    }
    
    public float getHeight ()
    {
        return h;
    }
}
