package nebula.minigame.boss;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class Missile 
{
	private static Image image = null;
	private float x;
	private float y;
	private boolean tire;
	private int timerExplosion;
	
	
	public Missile() throws SlickException
	{
		image = new Image("ressources/images/boss/ship.png");
		x = -100;
		y = -100;
		timerExplosion = 3000;
	}
	
	public void vise(Vaisseau v)
	{
		float angle = (float)Math.atan(((v.getX() + v.getImage().getWidth()/2) - this.getX() - this.getImage().getWidth()/2 )/((v.getY() + v.getImage().getHeight()/2) - this.getY() - this.getImage().getHeight()/2));
		if(v.getY() > this.getY() + 10)
		{
			this.getImage().setRotation(180 - (float)Math.toDegrees(angle));
		}
		else
		{
			this.getImage().setRotation(360 - (float)Math.toDegrees(angle));
		}
	}

	public void minusExplosion(int delta)
	{
		this.timerExplosion -= delta;
	}
	
	public Image getImage() 
	{
		return image;
	}

	public float getX() 
	{
		return x;
	}

	public void setX(float x) 
	{
		this.x = x;
	}

	public float getY() 
	{
		return y;
	}

	public void setY(float y) 
	{
		this.y = y;
	}
	
	public void setTire(boolean a)
	{
		this.tire = a;
	}

	public boolean getTire()
	{
		return this.tire;
	}
	
}
