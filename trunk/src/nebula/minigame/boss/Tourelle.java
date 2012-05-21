package nebula.minigame.boss;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tourelle 
{
	private static Image image = null;
	private float x;
	private float y;
	
	public Tourelle() throws SlickException
	{
		image = new Image("ressources/images/boss/ship.png");
		this.x = x; 
		this.y = y;
	}
	
	public void rotate(float delta, boolean droite)
	{	 
		if(!droite)
		{
        	this.getImage().rotate(-0.2f * delta);
		}
        else
        {
        	this.getImage().rotate(0.2f * delta);
        }
	}

	public static Image getImage() 
	{
		return image;
	}

	public static void setImage(Image image) 
	{
		Tourelle.image = image;
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
	
	
}
