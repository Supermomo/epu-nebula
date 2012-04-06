package nebula.minigame.gravity;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class ModeleJeu {
	
	

	private final static String dossierData = "data/";
	
	
	private Player hero;
	private BlockMap map;
	private float gravite;
	
	private boolean fin;
	private boolean victoire;
	
	// Ensemble des sons
	private Sound sonJump;
	private Sound sonDomage;
	private Sound sonVictoire;
	private Sound sonDefaite;
	

	/** Constructeur par défaut
	 * Initialise les instances du héro et de la map.
	 * Initialise les sons.
	 * @param hero - Instance du hero créé dans le jeu de base
	 * @param map - Instance de map créé dans le jeu de base
	 */
	public ModeleJeu(Player hero, BlockMap map) {
		setHero(hero);
		setMap(map);
		hero.setPosition((Point)map.getDepart().clone());
		gravite=0.0981f;

		fin = false;
		victoire = false;
		
		// Chargement des sons
		try {
			sonJump = new Sound(dossierData+"sound/jump.wav");
			sonDomage = new Sound(dossierData+"sound/hurt.wav");
			sonVictoire = new Sound(dossierData+"sound/defaite.wav");
			sonDefaite = new Sound(dossierData+"sound/victoire.wav");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Player getHero() {
		return hero;
	}
	public void setHero(Player hero) {
		this.hero = hero;
	}

	public BlockMap getMap() {
		return map;
	}
	public void setMap(BlockMap map) {
		this.map = map;
	}

	public float getGravite() {
		return gravite;
	}

	public void setFin(boolean b) {
		fin = b;
	}
	public boolean getFin() {
		return fin;
	}

	public void setVictoire(boolean b) {
		victoire = b;
	}
	public boolean getVictoire() {
		return victoire;
	}

	public boolean getDefaite() {
		if(hero.getNbrVies()<=0) {
			System.out.println("*MJ* Get Defaite");
			return true;
		}
		else return false;
	}

	
	
	
	
	//---
	// Méthode de déplacement du héro
	/////////////////////////////////
	public void deplacementGauche(int delta) {
		BlockType i = map.collisionType(hero.getBordGauche());
		if(i==null) i = BlockType.VIDE;
		switch(map.collisionType(hero.getBordGauche())) {
		case BLOCK:
			// On ne bouge pas
			break;
		case DEATH:
			mort();
			break;
		case END:
			victoire();
		default:
			// Déplacement du personnage
			hero.moveX(-delta);
			hero.setAnimGauche();
		}
	}
	public void deplacementDroite(int delta) {
		Point[] p = hero.getBordDroit();
		switch(map.collisionType(p)) {
		case BLOCK:
			// On ne bouge pas
			break;
		case DEATH:
			mort();
			break;
		case END:
			victoire();
		default:
			// Déplacement du personnage
			hero.moveX(+delta);
			hero.setAnimDroite();
		}
	}
	public void deplacementHaut(int delta) {
		switch(map.collisionType(hero.getBordHaut())) {
		case BLOCK:
			hero.peutInverserGravite(true);
			break;
		case DEATH:
			mort();
			break;
		case END:
			victoire();
		default:
			// Déplacement du personnage
			hero.moveY(delta, gravite);
			hero.peutInverserGravite(false);
		}

	}
	public void deplacementBas(int delta) {
		switch(map.collisionType(hero.getBordBas())) {
		case BLOCK:
			hero.peutInverserGravite(true);
			break;
		case DEATH:
			mort();
			break;
		case END:
			victoire();
		default:
			// Déplacement du personnage
			hero.moveY(delta, gravite);
			hero.peutInverserGravite(false);
		}
	}

	public void courrir() {
		hero.courrir();
	}

	
	
	
	
	// ---
	// Méthodes de gestion de la gravité
	///////////////////////////////////

	public void inverserGravite() {
		if(hero.getPIG()) {
			gravite *= -1;
			hero.setAnimRetourner(gravite);
			hero.peutInverserGravite(false);
			sonJump.play();
		}
	}

	public void appliquerGravite(int delta) {
		if(gravite>0) // On va vers le bas de l'écran (y++)
			deplacementBas(delta);
		else deplacementHaut(delta);
	}


	
	

	// ---
	// Méthodes de gestion de la victoire/défaite
	/////////////////////////////////////////////

	private void victoire() {
		// TODO Modifier le controleur (désactivé le controleur de jeu)
		
	}

	private void mort() {
		//TODO Rajouter une animation spéciale
		hero.setPosition((Point)map.getDepart().clone());
		if(hero.getNbrVies()>0) {
			hero.domage();
			sonDomage.play();
		}
		else {
			setFin(true);
		}
	}
	
	
	
	
	
	
	//---
	// Méthode de rendu de la map lors du déplacement
	/////////////////////////////////////////////////

	/** Rendu de la map
	 * Dessine la partie de la map occupée par le personnage
	 */
	public int[] renderMap() {
		double heroY = hero.getPosition().getY()/map.getTiledMap().getTileHeight();
		double heroX = hero.getPosition().getX()/map.getTiledMap().getTileWidth();		

		int renderX = 0;
		int renderY = 0;
		
		while(heroX>renderX+5) renderX += 5;
		while(heroY>renderY+10) renderY += 10;
	
		//System.out.println(renderX+" "+renderY);
		// Render( ScreenX, ScreenY, , , NbrTilesX, NbrTilesY)
		int i[] = {-renderX,-renderY};
		return i;
	}

}
