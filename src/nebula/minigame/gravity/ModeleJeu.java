package nebula.minigame.gravity;

public class ModeleJeu {

	private Player hero;
	private BlockMap map;
	private int gravite;

	private boolean fin;
	private boolean victoire;
	private boolean defaite;


	public ModeleJeu(Player hero, BlockMap map) {
		setHero(hero);
		setMap(map);
		gravite=1;

		fin = false;
		victoire = false;
		defaite = false;
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

	public int getGravite() {
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

	public void setDefaite(boolean b) {
		defaite = b;
	}
	public boolean getDefaite() {
		return defaite;
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
			hero.moveY(-delta);
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
			hero.moveY(+delta);
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
		// TODO Déplacer le personnage à l'origine de la map
		
		hero.domage();		
	}

}
