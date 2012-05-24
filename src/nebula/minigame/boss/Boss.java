package nebula.minigame.boss;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Boss 
{
	private Image image = null;
	private Image imageInvincibility = null;
	private float x;
	private float y;
	private int vies;
	
	public Boss() throws SlickException
	{
		image = new Image("ressources/images/boss/nebula-vaisseau-mere.png");
		vies = 3;
	}
	
	public void tirer(Tir tir, boolean droite, Vaisseau v)
	{
		int hip = 180;
		if(droite)
		{

			tir.setX(180 + this.getX());
			tir.setY(200 + this.getY()-tir.getImage().getHeight()/2);
			float angle = (float)Math.atan(((v.getX() + v.getImage().getWidth()/2) - 180 - this.getX())/((v.getY() + v.getImage().getHeight()/2) - 200 - this.getY()));
			tir.getImage().setRotation(hip - (float)Math.toDegrees(angle));
		}
		else
		{

			tir.setX(620 + this.getX());
			tir.setY(200 + this.getY() - tir.getImage().getHeight()/2);
			float angle = (float)Math.atan(((v.getX() + v.getImage().getWidth()/2) - 620 - this.getX())/((v.getY() + v.getImage().getHeight()/2) - 200 - this.getY()));
			tir.getImage().setRotation(hip - (float)Math.toDegrees(angle));
		}
		tir.setTire(true);
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

	public int getVies() {
		return vies;
	}

	public void setVies(int vies) {
		this.vies = vies;
	}
	
}
