package nebula.minigame.spaceInvaders;

import nebula.core.helper.Collision;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Ennemi
{
	private static Image image = null;
	private float x = 0;
	private float y = 0;
	private int pts = 0;
	private static Sound sonExp = null;
	private static Sound[] sonTir = null;


	public Ennemi(float x, float y) throws SlickException
	{
		if(image == null)
			image = new Image("ressources/images/spaceInvaders/ship.png");
		if(sonExp == null)
		{
			sonExp = new Sound("ressources/sons/spaceInvaders/explosion.ogg");

			sonTir = new Sound[4];
			for (int i = 0; i < sonTir.length; i++)
			    sonTir[i] = new Sound("ressources/sons/spaceInvaders/tirEnnemi.ogg");
		}

		this.x = x;
		this.y = y;
		this.pts = 100;
	}

	public boolean touche(Tir tir)
	{
		return Collision.rectangle(this.getX(),this.getY(), 3*this.getImage().getWidth()/4, 3*this.getImage().getHeight()/4, tir.getX(), tir.getY(), tir.getImage().getWidth(), tir.getImage().getHeight()/3);
	}

	public void tirer(Tir tir, GameContainer gc)
	{
		if(tir.getY() > gc.getHeight())
		{
			tir.setX(this.getX());
			tir.setY(this.getY() + tir.getImage().getHeight());

			// Play available sound
			for (Sound s : sonTir)
			{
			    if (!s.playing())
		        {
			        s.play();
			        break;
		        }
			}
		}
	}

	public Sound getSon()
	{
		return sonExp;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		Ennemi.image = image;
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
