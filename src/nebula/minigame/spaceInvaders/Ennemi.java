package nebula.minigame.spaceInvaders;

import nebula.core.helper.Collision;

import org.newdawn.slick.*;

public class Ennemi 
{
	private static Image image = null;
	private float x = 0;
	private float y = 0;
	private int pts = 0;
	private static Sound son = null;
	
	public Ennemi(float x, float y) throws SlickException
	{
		if(image == null)
			image = new Image("ressources/images/spaceInvaders/ship.png");
		if(son == null)
			son = new Sound("ressources/sons/spaceInvaders/explosion.ogg");
		
		this.x = x;
		this.y = y;
		this.pts = 100;
	}
	
	public boolean touche(Tir tir)
	{
		return Collision.rectangle(this.getX(),this.getY(), 3*this.getImage().getWidth()/4, 3*this.getImage().getHeight()/4, tir.getX(), tir.getY(), tir.getImage().getWidth(), tir.getImage().getHeight()/3);
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

	public int getPts() {
		return pts;
	}

	
}
