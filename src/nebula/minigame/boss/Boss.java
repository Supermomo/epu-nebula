package nebula.minigame.boss;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Boss 
{
	private Image image = null;
	private Image imageInvincibility = null;
	private float x;
	private float y;
	private int vies;
	
	public Boss() throws SlickException
	{
		image = new Image("ressources/images/boss/saucer.png");
		x = 300 - this.getImage().getWidth()/2;
		y = 600 - 2 * this.getImage().getHeight() + 10;
		vies = 3;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImageInvincibility() {
		return imageInvincibility;
	}

	public void setImageInvincibility(Image imageInvincibility) {
		this.imageInvincibility = imageInvincibility;
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

	public int getVies() {
		return vies;
	}

	public void setVies(int vies) {
		this.vies = vies;
	}
	
}
