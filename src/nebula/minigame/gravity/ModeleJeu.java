package nebula.minigame.gravity;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class ModeleJeu {



	private final static String dossierSon = "ressources/sons/gravity/";


	private Player hero;
	private BlockMap map;
	private float gravite;

	private boolean fin;
	private boolean victoire;

	// Ensemble des sons
	private Sound sonEnCours;
	private Sound sonJump;
	private Sound sonDomage;
	
	//--- Calcul de l'affichage de la carte
	// Nombre de tiles affichées à l'écran
	private int shownTileX;
	private int shownTileY;
	// Tile origine affichée
	private int mapRenderX;
	private int mapRenderY;


	/** Constructeur par défaut
	 * Initialise les instances du héro et de la map.
	 * Initialise les sons.
	 * @param hero - Instance du hero créé dans le jeu de base
	 * @param map - Instance de map créé dans le jeu de base
	 */
	public ModeleJeu(Player hero, BlockMap map, int shownPixelsY, int shownPixelsX) {
		gravite=0.2f;
		
		setHero(hero);
		setMap(map);
		hero.setPosition((Point)map.getDepart().clone());
		hero.setSavedPosition((Point)map.getDepart().clone());

		hero.setSavedGravity(gravite);
		fin = false;
		victoire = false;

		//--- Chargement des sons
		try {
			sonJump = new Sound(dossierSon+"jump.wav");
			sonDomage = new Sound(dossierSon+"hurt.wav");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		//--- Calcul pour la mise en place de la map au départ
		int tileHeight = map.getTiledMap().getTileHeight();
		int tileWidth = map.getTiledMap().getTileWidth();

		// Nombre de Tiles affichées
		this.shownTileY = shownPixelsY/tileHeight;
		this.shownTileX = shownPixelsX/tileWidth;

		int heroY = (int) hero.getPosition().getY()/tileHeight;
		int heroX = (int) hero.getPosition().getX()/tileWidth;
		
		mapRenderX = (int) (heroX / shownTileX * shownTileX);
		mapRenderY = (int) (heroY / shownTileY * shownTileY);
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
			return true;
		}
		return false;
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
			//Point p = hero.getBordGauche()[0];
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
		setFin(true);
		setVictoire(true);
	}

	private void mort() {
		if(hero.getNbrVies()>1) {
			// Remise en place du personnage à la position sauvegardée
			gravite = hero.resetSaved();
			hero.domage();
			sonDomage.play();
			sonEnCours = sonDomage;
		}
		else if(hero.getNbrVies()==1) {
			hero.domage();
			sonDomage.play();
			sonEnCours = sonDomage;
			setFin(true);
			setVictoire(false);
		}
	}

	public void arreterSon() {
		if(sonEnCours != null && sonEnCours.playing()) sonEnCours.stop();
	}





	//---
	// Méthode de rendu de la map lors du déplacement
	/////////////////////////////////////////////////

	/** Rendu de la map
	 * Dessine la partie de la map occupée par le personnage
	 */
	public int[] renderMap() {
		

		// Nombre de Tiles par rapport au bord auquel il faut faire la transition
		float delta = 2f;
		int tileHeight = map.getTiledMap().getTileHeight();
		int tileWidth = map.getTiledMap().getTileWidth();

		// Tile sur laquelle le hero est
		int heroTileY = (int) (hero.getPosition().getY()/tileHeight);
		int heroTileX = (int) (hero.getPosition().getX()/tileWidth);

		// Nombre de tiles total sur la carte
		int nbrTilesY = map.getTiledMap().getHeight();
		int nbrTilesX = map.getTiledMap().getWidth();

		/*//-- Mode Personnage Toujours Centré
		mapRenderX = heroTileX - shownTileX/2;
		mapRenderY = heroTileY - shownTileY/2;
		
		if(mapRenderX<0) mapRenderX = 0;
		else if(mapRenderX>nbrTilesX-shownTileX) mapRenderX = nbrTilesX-shownTileX;
		if(mapRenderY<0) mapRenderY = 0;
		else if(mapRenderY>nbrTilesY-shownTileY) mapRenderY = nbrTilesY-shownTileY;
		*/
		
		

		//-- Pour avoir le fond fixé.
		// Gestion des X
		if(heroTileX<mapRenderX+delta) {
			mapRenderX -= shownTileX/2;
			// Pour ne pas afficher d'emplacement noir
			if(mapRenderX<0) mapRenderX = 0;
		} else if(heroTileX>mapRenderX+shownTileX-delta) {
			mapRenderX += shownTileX/2;
			// Pour ne pas afficher d'emplacement noir
			if(mapRenderX>nbrTilesX-shownTileX) mapRenderX = nbrTilesX-shownTileX;
		}
		
		// Gestion des Y
		if(heroTileY<mapRenderY+delta) {
			mapRenderY -= shownTileY*2/3;
			// Pour ne pas afficher d'emplacement noir
			if(mapRenderY<0) mapRenderY = 0;
		} else if(heroTileY>mapRenderY+shownTileY-delta) {
			mapRenderY += shownTileY*2/3;
			// Pour ne pas afficher d'emplacement noir
			if(mapRenderY>nbrTilesY-shownTileY) mapRenderY = nbrTilesY-shownTileY;
		}
		
		
		// Inversion pour l'affichage
		int i[] = {-mapRenderX, -mapRenderY};
		return i;
	}

}
