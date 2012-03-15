package nebula.minigame.gravity;


import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player {
	Point position;
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
	public Player(String reference, int nbrImages, int x, int y) throws SlickException {
		this(new SpriteSheet(reference,
							Sprite.HAUTEUR.getValue(), 
							Sprite.LARGEUR.getValue(),
							Sprite.ESPACE.getValue()),
				nbrImages, x, y);
	}
	/** Constructeur complet */
	public Player(SpriteSheet ss, int nbrImages, int x, int y) {
		// Sprite Sheet Défaut
		this.sheet = ss;
		
		// Emplacement du personnage
		position = new Point(x,y);
		
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
	// Points du hero
	// --- Position
	public float getX() {
		return (float) (position.getX());
	}
	public void setX(float x) {
		position.setLocation(x, getY());
	}
	
	public float getY() {
		return (float) (position.getY());
	}
	public void setY(float y) {
		position.setLocation(getX(), y);
	}
	// --- Coins
	/*
	 * Changer les coins permet de modifier à quelle distance le personnage détecte les murs
	 */
	public Point[] getBordGauche() {
		Point p[] = {(Point) position.clone(),(Point) position.clone()};
		p[0].translate(2f,2f);
		p[1].translate(2f,13f);
		return p;
	}
	public Point[] getBordDroit() {
		Point p[] = {(Point) position.clone(),(Point) position.clone()};
		p[0].translate(13f,2f);
		p[1].translate(13f,13f);
		return p;
	}
	public Point[] getBordHaut() {
		Point p[] = {(Point) position.clone(),(Point) position.clone()};
		p[0].translate(2f,2f);
		p[1].translate(13f,2f);
		return p;
	}
	public Point[] getBordBas() {
		Point p[] = {(Point) position.clone(),(Point) position.clone()};
		p[0].translate(2f,13f);
		p[1].translate(13f,13f);
		return p;
	}
	
	// Images du hero
	public Image getImage() {
		return sheet;
	}
	public void setImage(SpriteSheet ss) {
		this.sheet = ss;
	}
	
	public int getSpriteHeight() {
		return sheet.getHeight();
	}
	public int getSpriteWidth() {
		return sheet.getWidth();
	}
	
	// Animation et déplacement
	public Animation getAnimation() {
		return this.animation;
	}
	public void setAnimation(Animation a) {
		this.animation = a;
	}
	// --- Statut d'immobilité
	public int getStill() {
		return isStill;
	}
	public void incStill() {
		isStill++;
	}
	public boolean isStill() {
		return animation.equals(animStill);
	}
	// --- Vitesse
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
	public void deplace() {
		
	}
	public void intiPosition() {
		setX(0);
		setY(0);
	}
	public void moveX(float dx) {
		position.translate(dx*vitesse, 0);
	}
	public void moveY(float dy) {
		position.translate(0, dy*vitesse);
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
