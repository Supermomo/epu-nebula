package nebula.minigame.gravity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player {
	private float x;
	private float y;
	private SpriteSheet sheet;

	private int isStill;
	private float vitesse;
	
/*	public enum Anim {
		
	}
*/	
	private Animation animation;
	private Animation animGauche;
	private Animation animDroite;
	private Animation animStill;
	//---
	// Constructeurs
	////////////////
	public Player() throws SlickException {
		this("",0);
	}
	public Player(String reference,int nbrImages) throws SlickException {
		this(reference,nbrImages,0,0);
	}
	public Player(String reference, int nbrImages, float x, float y) throws SlickException {
		this(new SpriteSheet(reference,
							Sprite.HAUTEUR.getValue(), 
							Sprite.LARGEUR.getValue(),
							Sprite.ESPACE.getValue()),
				nbrImages, x, y);
	}
	/** Constructeur complet */
	public Player(SpriteSheet ss, int nbrImages, float x, float y) {
		// Sprite Sheet Défaut
		this.sheet = ss;
		
		// Emplacement du personnage
		this.x = x;
		this.y = y;
		
		// Création des animations
		// -- Animation Still
		animStill = new Animation();
		animStill.addFrame(sheet.getSprite(0,0), 150);
		animStill.addFrame(sheet.getSprite(5,0), 150);
		
		// -- Animation Gauche
		animGauche = new Animation();
		animGauche.addFrame(sheet.getSprite(1,0), 150);
		animGauche.addFrame(sheet.getSprite(2,0), 150);
		
		// -- Animation Droite
		animDroite = new Animation();
		animDroite.addFrame(sheet.getSprite(3,0), 150);
		animDroite.addFrame(sheet.getSprite(4,0), 150);
		
		// -- Animation par défaut
		animation = animStill;
		isStill = -1;
		this.vitesse = 0.10f;
	}
	
	
	//---
	// Accesseurs
	/////////////
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
	public Image getImage() {
		return sheet;
	}
	public void setImage(SpriteSheet ss) {
		this.sheet = ss;
	}
	public Animation getAnimation() {
		return this.animation;
	}
	public void setAnimation(Animation a) {
		this.animation = a;
	}
	public int getStill() {
		// A l'arret quand c'est égal à -1
		return isStill;
	}
	public void incStill() {
		isStill++;
	}
	public boolean isStill() {
		return animation.equals(animStill);
	}
	public float getVitesse() {
		return vitesse;
	}
	public void setVitesse(float v) {
		this.vitesse = v;
	}
	//---
	// toString
	///////////
	public String toString() {
		return "";
	}
	
	//---
	// Gestion du déplacement
	/////////////////////////
	public void intiPosition() {
		setX(0);
		setY(0);
	}
	public void moveX(float deltaX) {
		setX(x+deltaX);
	}
	public void moveY(float deltaY) {
		setY(y+deltaY);
	}
	
	//---
	// Gestion de l'animation
	/////////////////////////

	public void setAnimGauche() {
		isStill = 0;
		animation = animGauche;
	}
	public void setAnimDroite() {
		isStill = 0;
		animation = animDroite;
	}
	public void setAnimStill() {
		isStill = -1;
		animation = animStill;
	}
	
	
	
	
	
	
}
