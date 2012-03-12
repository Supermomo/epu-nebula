package nebula.minigame.spaceInvaders;

import org.newdawn.slick.*;

public class Tir 
{

	private Image image = null;
	private float x = -100;
	private float y = -100;
	
	public Tir(int i) throws SlickException
	{
		if(i==0)
			image = new Image("assets/spaceInvaders/tir.png");
		else
			image = new Image("assets/spaceInvaders/tirEnnemi.png");
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
