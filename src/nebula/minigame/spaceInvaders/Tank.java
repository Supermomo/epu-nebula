package nebula.minigame.spaceInvaders;

import nebula.core.helper.Collision;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tank 
{
	private Image image = null;
	private float x;
	private float y;
	private int vies;

	public Tank() throws SlickException
	{
		image = new Image("assets/images/spaceInvaders/char.png");
		x = 300 - this.getImage().getWidth()/2;
		y = 600 - 2 * this.getImage().getHeight() + 10;
		vies = 3;
	}
	
	public void tirer(Tir tir)
	{
		if(tir.getY() < 0)
		{
			tir.setX(this.getX());
			tir.setY(this.getY() - tir.getImage().getHeight());
			tir.getSon().play();
		}
	}
	
	public boolean touche(Tir tir)
	{
		return Collision.rectangle(this.getX(),this.getY(), 3*this.getImage().getWidth()/4, 3*this.getImage().getHeight()/4, tir.getX(), tir.getY(), tir.getImage().getWidth(), tir.getImage().getHeight()/3);
	}
	
	public boolean dead()
	{
		return vies == 0;
	}
	
	public void kill()
	{
		this.vies = 0;
	}
	
	public void decrementeVie()
	{
		this.vies--;
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
	
	public int getVies()
	{
		return vies;
	}
	
}