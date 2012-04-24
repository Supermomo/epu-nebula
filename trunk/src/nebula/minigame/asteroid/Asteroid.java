package nebula.minigame.asteroid;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;


/**
 * Asteroid class
 */
public class Asteroid
{
    static final float w = 140;
    static final float h = 130;
    static final float collideOffset = 16.0f;
    static final float rayon = (float)Math.sqrt(w*w/4 + h*h/4);
    
    private float x;
    private float y;
    private float rotation;
    private float rotationSpeed;
    private float speed;
    private float[] direction = {0.0f, 0.0f};
    private double creationDistance;
    private Rectangle limits;
    private Image image;

    
    public Asteroid (Rectangle limits, float speed) throws SlickException
    {
        this.limits = limits;
        this.speed = speed;
        
        rotation = 0.0f;
        rotationSpeed = (AsteroidGame.random.nextFloat()-0.5f) * 0.1f;
        creationDistance = Math.sqrt(
            limits.getWidth()*limits.getWidth()/4 +
            limits.getHeight()*limits.getHeight()/4
        ) + rayon;
        
        // Randomize
        randomize();

        // Set image
        image = new Image(AsteroidGame.imgPath + "asteroid.png");
    }
    
    private void randomize ()
    {
        // Randomize position
        double position = AsteroidGame.random.nextFloat() * 2.0d * Math.PI;
        
        x = limits.getCenterX() - rayon + (float)(Math.cos(position) * creationDistance);
        y = limits.getCenterY() - rayon + (float)(Math.sin(position) * creationDistance);
        
        // Randomize direction
        double randOffset =
            (AsteroidGame.random.nextFloat()-0.5f) * 2.0d * Math.PI / 3.0d;
        direction[0] = -(float)Math.cos(position + randOffset);
        direction[1] = -(float)Math.sin(position + randOffset);
    }
    
    public Ellipse getCollideZone ()
    {
        // Return circle collide zone
        return new Ellipse(
            x+w/2,
            y+h/2,
            w/2-Asteroid.collideOffset,
            w/2-Asteroid.collideOffset
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
