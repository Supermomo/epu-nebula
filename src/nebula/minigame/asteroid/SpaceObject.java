package nebula.minigame.asteroid;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;


/**
 * Space object abstract class
 */
class SpaceObject
{
    static final float asteroidW = 140;
    static final float asteroidH = 130;
    static final float crystalW  = 96;
    static final float crystalH  = 96;
    static final float asteroidCollideOffset = 16.0f;
    static final float crystalCollideOffset  = 12.0f;
    static final float asteroidRayon = (float)Math.sqrt(asteroidW*asteroidW/4 + asteroidH*asteroidH/4);
    static final float crystalRayon  = (float)Math.sqrt(crystalW*crystalW/4 + crystalH*crystalH/4);

    private boolean isCrystal;

    private float x;
    private float y;
    private float w;
    private float h;
    private float collideOffset;
    private float rayon;
    private float rotation;
    private float rotationSpeed;
    private float speed;
    private float[] direction = {0.0f, 0.0f};
    private double creationDistance;
    private Rectangle limits;
    private Image image;


    public SpaceObject (boolean isCrystal, Rectangle limits, float speed)
        throws SlickException
    {
        this.isCrystal = isCrystal;
        this.limits = limits;
        this.speed = speed;

        if (isCrystal)
        {
            w = crystalW;
            h = crystalH;
            collideOffset = crystalCollideOffset;
            rayon = crystalRayon;
            image = new Image(AsteroidGame.imgPath + "crystal.png");
        }
        else
        {
            w = asteroidW;
            h = asteroidH;
            collideOffset = asteroidCollideOffset;
            rayon = asteroidRayon;
            image = new Image(AsteroidGame.imgPath + "asteroid.png");
        }

        rotation = 0.0f;
        rotationSpeed = (AsteroidGame.random.nextFloat()-0.5f) * 0.1f;
        creationDistance = Math.sqrt(
            limits.getWidth()*limits.getWidth()/4 +
            limits.getHeight()*limits.getHeight()/4
        ) + rayon;

        // Randomize
        randomize();
    }

    private void randomize ()
    {
        // Randomize position
        double position = AsteroidGame.random.nextFloat() * 2.0d * Math.PI;

        x = limits.getCenterX() - rayon + (float)(Math.cos(position) * creationDistance);
        y = limits.getCenterY() - rayon + (float)(Math.sin(position) * creationDistance);

        // Randomize direction
        double randOffset = isCrystal ?
            ((AsteroidGame.random.nextFloat()-0.5f) * 2.0d * Math.PI / 6.0d) :
            ((AsteroidGame.random.nextFloat()-0.5f) * 2.0d * Math.PI / 4.0d);
        direction[0] = -(float)Math.cos(position + randOffset);
        direction[1] = -(float)Math.sin(position + randOffset);
    }

    public Ellipse getCollideZone ()
    {
        // Return circle collide zone
        return new Ellipse(
            x+w/2,
            y+h/2,
            w/2-collideOffset,
            w/2-collideOffset
        );
    }

    public void step (int delta)
    {
        // Move
        x += direction[0] * delta * speed;
        y += direction[1] * delta * speed;

        // Rotate
        image.rotate(rotation + rotationSpeed * delta);
    }

    public void draw ()
    {
        image.draw(x, y, w, h);
    }

    public boolean isCrystal ()
    {
        return isCrystal;
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
