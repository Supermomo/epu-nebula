package nebula.minigame.boss;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Missile 
{
	private Image image = null;
	private float x;
	private float y;
	private boolean tire;
	private int timerExplosion;
	private Sound son = null;
	private Sound sonExplo = null;
	
	public Missile(int t) throws SlickException
	{
		image = new Image("ressources/images/boss/nebula-rocket.png");
		son = new Sound("ressources/sons/boss/rocket.ogg");
		sonExplo = new Sound("ressources/sons/boss/miss_explosion.ogg");
		x = -1000;
		y = -1000;
		timerExplosion = t;
	}
	
	public void vise(Vaisseau v)
	{
		float angle = (float)Math.atan(((v.getX() + v.getImage().getWidth()/2) - this.getX() - this.getImage().getWidth()/2 )/((v.getY() + v.getImage().getHeight()/2) - this.getY() - this.getImage().getHeight()/2));
		if(v.getY() + v.getImage().getHeight()/2 >= this.getY() + this.getImage().getHeight()/2)
		{
			if(((v.getY() + v.getImage().getHeight()/2) - this.getY() - this.getImage().getHeight()/2) == 0)
			{
				if(v.getX() < this.getX())
				{
					angle = -90;
				}
				else
				{
					angle = 90;
				}
			}
			this.getImage().setRotation(180 - (float)Math.toDegrees(angle));
		}
		else
		{
			if(((v.getY() + v.getImage().getHeight()/2) - this.getY() - this.getImage().getHeight()/2) == 0)
			{
				if(v.getX() < this.getX())
				{
					angle = 90;
				}
				else
				{
					angle = -90;
				}
			}
			this.getImage().setRotation(360 - (float)Math.toDegrees(angle));
		}
	}
	
	public void explode(int t)
	{
		this.x = -1000;
		this.y = -1000;
		tire = false;
		timerExplosion = t;
		sonExplo.play();
	}

	public void minusExplosion(int delta)
	{
		this.timerExplosion -= delta;
	}
	
	public int getTimerExplosion()
	{
		return timerExplosion;
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
	
	public Sound getSon()
	{
		return this.son;
	}
	
}
