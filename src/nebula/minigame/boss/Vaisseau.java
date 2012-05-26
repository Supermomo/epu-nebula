package nebula.minigame.boss;

import java.util.ArrayList;

import nebula.core.helper.Collision;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Vaisseau
{
	private Image image = null;
	private Image imageInvincibility = null;
	private Image coeur = null;
	private Sound son;
	private float x;
	private float y;
	private int vies;
	private boolean canMove;

	public Vaisseau(int width, int height) throws SlickException
	{
		image = new Image("ressources/images/boss/saucer.png");
		imageInvincibility = new Image("ressources/images/boss/saucer-inv.png");
        imageInvincibility.setAlpha(0.95f);
        coeur = new Image("ressources/images/boss/coeur.png");
        son = new Sound("ressources/sons/boss/explosion.ogg");
		x = width/2 - this.getImage().getWidth()/2;
		y = height - 2 * this.getImage().getHeight();
		vies = 3;
		canMove = true;
	}

	public void tirer(ArrayList<Tir> tir) throws SlickException
	{
		Tir t = new Tir(false);
		t.setX(this.getX() + this.getImage().getWidth()/2 - t.getImage().getWidth()/2);
		t.setY(this.getY() - t.getImage().getHeight());
		t.getSon().play();
		tir.add(t);
	}

	public boolean touche(Tir tir)
	{
		return Collision.rectangle(this.getX() + this.getImage().getWidth()/4,this.getY() + this.getImage().getHeight()/4, this.getImage().getWidth()/2, this.getImage().getHeight()/2, tir.getX() + tir.getImage().getWidth()/4, tir.getY() + tir.getImage().getHeight()/4,tir.getImage().getWidth()/2, tir.getImage().getHeight()/2);
	}
	
	public boolean hit(Missile m)
	{
		return Collision.rectangle(this.getX() + this.getImage().getWidth()/4,this.getY() + this.getImage().getHeight()/4, this.getImage().getWidth()/2, this.getImage().getHeight()/2, m.getX() + m.getImage().getWidth()/4, m.getY() + m.getImage().getHeight()/4,m.getImage().getWidth()/2, m.getImage().getHeight()/2);
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

	public Image getImageInv() {
		return imageInvincibility;
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
	
	public boolean getMove()
	{
		return this.canMove;
	}
	
	public void setMove(boolean b)
	{
		this.canMove = b;
	}
	
	public Sound getSon()
	{
		return son;
	}

	public Image getCoeur() {
		return coeur;
	}

	public void setCoeur(Image coeur) {
		this.coeur = coeur;
	}

	public void setVies(int v)
	{
		this.vies = v;
	}
	
}
