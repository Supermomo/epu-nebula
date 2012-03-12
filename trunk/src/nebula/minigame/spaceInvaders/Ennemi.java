package nebula.minigame.spaceInvaders;

import nebula.core.helper.Collision;

import org.newdawn.slick.*;

public class Ennemi 
{
	private Image image = null;
	private float x = 0;
	private float y = 0;
	
	public Ennemi(float x, float y) throws SlickException
	{
		image = new Image("assets/spaceInvaders/ship.png");
		this.x = x;
		this.y = y;
	}
	
	public boolean touche(Tir tir)
	{
		return Collision.rectangle(this.getX(),this.getY(), this.getImage().getWidth(), this.getImage().getHeight(), tir.getX(), tir.getY(), tir.getImage().getWidth(), tir.getImage().getHeight()/3);
		/*if(tir.getX() + tir.getImage().getWidth()/2 > this.getX()
				&& tir.getX() + tir.getImage().getWidth()/2 < this.getX() + this.getImage().getWidth()
    			&& tir.getY() <= this.getY() + this.getImage().getHeight() 
    			&& tir.getY() >= this.getY())
			return true;
		else
			return false;*/
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
