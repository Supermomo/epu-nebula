package nebula.minigame.gravity;


import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player {
	
	private Point position;
	private SpriteSheet sheet;

	private int isStill;
	private boolean isReversed;
	private float vitesse;
	private boolean enCourse;
	private boolean peutIG;
	
	private int nbrVies;
	
	private Animation animation;
	private AnimGravite animGravite;

	
	//---
	// Constructeurs
	////////////////
	public Player() throws SlickException {
		this("data/heroSet.png");
	}
	public Player(String reference) throws SlickException {
		this(reference,0,0);
	}
	public Player(String reference, int x, int y) throws SlickException {
		this(new SpriteSheet(reference,
							Sprite.HAUTEUR.getValue(), 
							Sprite.LARGEUR.getValue(),
							Sprite.ESPACE.getValue()),
				x, y);
	}
	/** Constructeur complet */
	public Player(SpriteSheet ss, int x, int y) {
		// Sprite Sheet Défaut
		this.sheet = ss;
		
		// Emplacement du personnage
		position = new Point(x,y);
		
		// -- Animation par défaut
		animGravite = new AnimGravite(ss);
		animation = animGravite.still(false);
		isStill = -1;
		this.nbrVies = 5;
		this.vitesse = 0.15f;
		this.enCourse = false;
		this.peutIG = false;
		this.isReversed = false;
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
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point p) {
		this.position = p;
	}
	/*
	 * Changer les coins permet de modifier à quelle distance le personnage détecte les murs
	 */
	public Point[] getBordGauche() {
		Point p[] = {(Point) position.clone(),(Point) position.clone()};
		p[0].translate(2f, 2f);
		p[1].translate(2f, Sprite.HAUTEUR.getValue()-2f);
		return p;
	}
	public Point[] getBordDroit() {
		Point p[] = {(Point) position.clone(),(Point) position.clone()};
		p[0].translate(Sprite.LARGEUR.getValue()-2f, 2f);
		p[1].translate(Sprite.LARGEUR.getValue()-2f, Sprite.HAUTEUR.getValue()-2f);
		return p;
	}
	public Point[] getBordHaut() {
		Point p[] = {(Point) position.clone(),(Point) position.clone()};
		p[0].translate(10f, -1f);
		p[1].translate(Sprite.LARGEUR.getValue()-10f, -1f);
		return p;
	}
	public Point[] getBordBas() {
		Point p[] = {(Point) position.clone(),(Point) position.clone()};
		p[0].translate(10f, Sprite.HAUTEUR.getValue()+1f);
		p[1].translate(Sprite.LARGEUR.getValue()-10f, Sprite.HAUTEUR.getValue()+1f);
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
		if(isStill>5) setAnimStill();
	}
	public boolean isStill() {
		return animation.equals(animGravite.still(isReversed));
	}
	// --- Vitesse
	public float getVitesse() {
		return vitesse;
	}
	public void setVitesse(float v) {
		this.vitesse = v;
	}

	// Changement de gravité
	public boolean getPIG() {
		return peutIG;
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
	public void moveX(float dx) {
		position.translate(dx*vitesse, 0);
	}
	public void moveY(float dy, float gravite) {
		position.translate(0, dy*gravite);
	}
	
	/**
	 * Active ou désactive la course en fonction du boolean enCourse
	 * Modifie la vitesse de déplacement du héro
	 */
	public void courrir() {
		if(enCourse) {
			vitesse =  0.15f;
			enCourse = false;
		} else {
			vitesse = 0.5f;
			enCourse = true;
		}
		System.out.println("*Player Vitesse : "+vitesse);
	}
	
	//---
	// Gestion de l'animation
	/////////////////////////

	public void setAnimGauche() {
		isStill = 0;
		animation = animGravite.gauche(isReversed);
	}
	public void setAnimDroite() {
		isStill = 0;
		animation = animGravite.droite(isReversed);
	}
	public void setAnimStill() {
		isStill = -1;
		animation = animGravite.still(isReversed);
	}
	
	public void setAnimRetourner(float g) {
		if(g>0) {
			isReversed = false;
			animation = animGravite.retourner(animation);
		} else {
			isReversed = true;
			animation = animGravite.retourner(animation);
		}
	}
	
	//---
	// Gestion de la gravité au niveau du héro
	//////////////////////////////////////////
	public void peutInverserGravite(boolean b) {
		peutIG = b;
	}

	//---
	// Gestion de la vie du héro
	////////////////////////////
	
	public int getNbrVies() {
		return nbrVies;
	}
	public void setNbrVies(int n) {
		nbrVies = n;
	}
	/**
	 * Méthode utilisée pour enlever une vie au joueur
	 */
	public void domage() {
		setNbrVies(getNbrVies()-1);
	}
	
	
	
}
