package nebula.minigame.boss;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tir
{

	private Image image = null;
	private float x = -100;
	private float y = -100;
	private boolean tire;

	public Tir() throws SlickException
	{
			image = new Image("ressources/images/boss/ball.png");
			y = 2000;
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

}
