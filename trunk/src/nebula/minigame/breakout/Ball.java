package nebula.minigame.breakout;


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * Ball class
 */
public class Ball
{
    static final float w = 48;
    static final float h = 48;
    
    private float x;
    private float y;
    private float xprev;
    private float yprev;
    private static Image image;

    public Ball () throws SlickException
    {
        // Set ball image first time
        if (image == null)
            image = new Image(Breakout.imgPath + "ball.png");
    }

    public void draw ()
    {
        image.draw(x, y, w, h);
    }
    
    public void goPrevPosition ()
    {
        x = xprev;
        y = yprev;
    }
    
    public void setX (float x)
    {
        xprev = this.x;
        this.x = x;
    }
    
    public float getX ()
    {
        return x;
    }
    
    public void setY (float y)
    {
        yprev = this.y;
        this.y = y;
    }
    
    public float getY ()
    {
        return y;
    }
}
