package nebula.minigame.spaceInvaders;

import org.newdawn.slick.*;

public class Tir 
{

	private Image image = null;
	private Sound son = null;
	private float x = -100;
	private float y = -100;
	
	public Tir(int i) throws SlickException
	{
		if(i==0)
		{
			image = new Image("ressources/images/spaceInvaders/tir.png");
			son = new Sound("ressources/sons/spaceInvaders/tir.ogg");
		}
		else
		{
			image = new Image("ressources/images/spaceInvaders/tirEnnemi.png");
			son = new Sound("ressources/sons/spaceInvaders/tirEnnemi.ogg");
			y = 2000;
		}
	}
	
	public Sound getSon()
	{
		return son;
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
	
	
}
