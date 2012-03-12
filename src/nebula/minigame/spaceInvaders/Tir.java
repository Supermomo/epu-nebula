package nebula.minigame.spaceInvaders;

import org.newdawn.slick.*;

public class Tir 
{

	private Image image = null;
	private float x = 0;
	private float y = 0;
	
	public Tir() throws SlickException
	{
		image = new Image("assets/spaceInvaders/tir.png");
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
