package nebula.minigame.boss;

import nebula.core.helper.Collision;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Boss 
{
	private Image image = null;
	private float x;
	private float y;
	private int vies;
	private Sound son = null;
	
	public Boss() throws SlickException
	{
		image = new Image("ressources/images/boss/nebula-vaisseau-mere.png");
		son = new Sound("ressources/sons/boss/miss_explosion.ogg");
		vies = 1000;
	}
	
	public void tirer(Tir tir, boolean droite, Vaisseau v)
	{
		int hip = 180;
		if(droite)
		{
			
			tir.setX(180 + this.getX() - tir.getImage().getWidth()/2);
			tir.setY(200 + this.getY() - tir.getImage().getHeight()/2);
			float angle = (float)Math.atan(((v.getX() + v.getImage().getWidth()/2) - 180 - this.getX())/((v.getY() + v.getImage().getHeight()/2) - 200 - this.getY()));
			if(v.getY() >= this.getY() + 200)
			{
				tir.getImage().setRotation(hip - (float)Math.toDegrees(angle));
			}
			else
			{
				tir.getImage().setRotation(2 * hip - (float)Math.toDegrees(angle));
			}
			tir.getSon().play();
		}
		else
		{
			tir.setX(620 + this.getX() - tir.getImage().getWidth()/2);
			tir.setY(200 + this.getY() - tir.getImage().getHeight()/2);
			float angle = (float)Math.atan(((v.getX() + v.getImage().getWidth()/2) - 620 - this.getX())/((v.getY() + v.getImage().getHeight()/2) - 200 - this.getY()));
			if(v.getY() >= this.getY() + 200)
			{
				tir.getImage().setRotation(hip - (float)Math.toDegrees(angle));
			}
			else
			{
				tir.getImage().setRotation(2 * hip - (float)Math.toDegrees(angle));
			}
		}
		tir.setTire(true);
	}
	
	public void launch(Missile m, boolean droite, Vaisseau v)
	{
		if(droite)
		{
			
			m.setX(70 + this.getX() - m.getImage().getWidth()/2);
			m.setY(100 + this.getY() - m.getImage().getHeight()/2);
			m.vise(v);
		}
		else
		{
			m.setX(720 + this.getX() - m.getImage().getWidth()/2);
			m.setY(100 + this.getY() - m.getImage().getHeight()/2);
			m.vise(v);
		}
		
		m.setTire(true);
		m.getSon().play();
	}
	
	public boolean touche(Tir tir)
	{
		return Collision.rectangle(this.getX(),this.getY(), 3*this.getImage().getWidth()/4, 3*this.getImage().getHeight()/4, tir.getX(), tir.getY(), tir.getImage().getWidth(), tir.getImage().getHeight()/2);
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
	
	public Sound getSon()
	{
		return this.son;
	}
	
	public void loseLife()
	{
		this.vies -= 10;
	}
	
	public void hit()
	{
		this.vies -= 200;
	}
	
	public void getDestroy() throws SlickException
	{
		if(this.vies <= 800 && this.vies > 600)
		{
			this.image = new Image("ressources/images/boss/nebula-vaisseau-mere2.png");
		}
		else if(this.vies <= 600 && this.vies > 400)
		{
			this.image = new Image("ressources/images/boss/nebula-vaisseau-mere3.png");
		}
		else if(this.vies <= 400 && this.vies > 200)
		{
			this.image = new Image("ressources/images/boss/nebula-vaisseau-mere4.png");
		}
		else if(this.vies <= 200 && this.vies > 0)
		{
			this.image = new Image("ressources/images/boss/nebula-vaisseau-mere5.png");
		}
	}
	
	public boolean dead()
	{
		return vies <= 0;
	}
}
