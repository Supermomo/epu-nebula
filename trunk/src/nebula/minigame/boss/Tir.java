package nebula.minigame.boss;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Tir
{

	private Image image = null;
	private Sound son = null;
	private float x = -100;
	private float y = -100;
	private boolean tire;

	public Tir(boolean b) throws SlickException
	{
		if(b)
		{
			image = new Image("ressources/images/boss/ball.png");
			son = new Sound("ressources/sons/boss/tirEnnemi.ogg");
			y = -100;
		}
		else
		{
			image = new Image("ressources/images/boss/tir.png");
			son = new Sound("ressources/sons/boss/tir.ogg");
			y = -100;
		}
			
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
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
