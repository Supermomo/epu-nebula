package nebula.minigame.breakout;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * Brick class
 */
class Brick
{
    private static final int MAX_RESISTANCE = 2;

    private float x;
    private float y;
    private float w;
    private float h;
    private int resistance = 1;
    private static Image[] images = {null, null};


    public Brick (int r, int c, BricksField f) throws SlickException
    {
        // Set brick size
        if (f == null) return;

        w = f.getWidth()/(float)f.getColumn();
        h = f.getHeight()/(float)f.getRow();
        x = f.getX() + (float)c*w;
        y = f.getY() + (float)r*h;

        // Set brick images first time
        if (images[0] == null)
        {
            images[0] = new Image(BreakoutGame.imgPath + "brick1.png");
            images[1] = new Image(BreakoutGame.imgPath + "brick2.png");
        }
    }

    public void touch ()
    {
        if (resistance > 0) resistance--;
    }

    public boolean isBroken ()
    {
        return (resistance <= 0);
    }

    public void draw ()
    {
        final int img = (resistance <= 0 ? 0 : resistance-1);
        images[img].draw(x, y, w, h);
    }

    public void setResistance (int resistance)
    {
        if (1 <= resistance && resistance <= MAX_RESISTANCE)
            this.resistance = resistance;
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
}
