package nebula.minigame.spaceInvaders;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tank 
{
	Image image = null;
	float x;
	float y;
	int vies;

	public Tank() throws SlickException
	{
		image = new Image("assets/spaceInvaders/Char.png");
		x = 300 - this.getImage().getWidth()/2;
		y = 600 - 2 * this.getImage().getHeight() + 10;
		vies = 3;
	}
	
	public boolean touche(Tir tir)
	{
		if(tir.getX() + tir.getImage().getWidth()/2 > this.getX() && 
				tir.getX() + tir.getImage().getWidth()/2 < this.getX() + this.getImage().getWidth()
    			&& tir.getY() <= this.getY() + this.getImage().getHeight() 
    			&& tir.getY() > this.getY())
			return true;
		else
			return false;
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
	
}
