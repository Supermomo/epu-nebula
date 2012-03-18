package nebula.minigame.breakout;

/**
 * Speed vector class
 */
public class SpeedVector
{
    private float x;
    private float y;
    private float speedCounter;
    
    public SpeedVector ()
    {
        reset();
    }
    
    public void reset ()
    {
        this.x = 0.0f;
        this.y = 0.0f;
        this.speedCounter = 0.0f;
    }
    
    public void invertX ()
    {
        x = -x;
    }
    
    public void invertY ()
    {
        y = -y;
    }
    
    public void setAngle (float angle)
    {
        float speed = getSpeed();
        x = (float)Math.cos(angle * (Math.PI / 180.0)) * speed;
        y = (float)Math.sin(angle * (Math.PI / 180.0)) * speed;
    }
    
    public float getAngle ()
    {
        return (float)(Math.atan2(y, x) * (180.0 / Math.PI));
    }
    
    public void increaseSpeed (float i)
    {
        speedCounter += i;
        while (speedCounter >= 100.0f)
        {
            x *= 1.01f;
            y *= 1.01f;
            speedCounter -= 100.0f;
        }
    }
    
    public float getSpeed ()
    {
        return (float)Math.sqrt(x*x + y*y);
    }
    
    public float getX ()
    {
        return x;
    }

    public void setX (float x)
    {
        this.x = x;
    }

    public float getY ()
    {
        return y;
    }

    public void setY (float y)
    {
        this.y = y;
    }
}
