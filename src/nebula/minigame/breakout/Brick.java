package nebula.minigame.breakout;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Brick class
 */
public class Brick
{
    private float x;
    private float y;
    private float w;
    private float h;
    private Image image;
    
    public Brick (int r, int c, BricksField f) throws SlickException
    {
        // Set brick size
        if (f == null) return;
        
        w = f.getWidth()/f.getColumn();
        h = f.getHeight()/f.getRow();
        x = f.getX() + c*w;
        y = f.getY() + r*h;
        
        // Set brick image
        image = new Image(Breakout.rccPath + "brick-red.png");
    }
    
    public void draw ()
    {
        image.draw(x, y, w, h);
    }
}
