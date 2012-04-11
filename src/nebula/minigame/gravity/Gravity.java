package nebula.minigame.gravity;


import nebula.minigame.Minigame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Gravity extends Minigame {

	private enum EtatJeu {
		DEPLACEMENT_JOUEUR,
		DEPLACEMENT_MAP,
		VICTOIRE,
		DEFAITE;
	}

	private int stateID;
	private EtatJeu etatActuel;

	private final static String dossierData = "ressources/images/gravity/";
	private ModeleJeu modeleJeu;
	private ControleJeu controleJeu;
	private Image victoire, defaite, coeur;
	private int[] mapRender = {0,0,0,0};



	/////// DEBUG ///////
	private boolean out = false;

	public Gravity(int stateID) {
		this.stateID = stateID;
	}


	/**
	 * Initialisation des données pour le jeu
	 */
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {

		// Call super method
		super.init(gameContainer, stateBasedGame);
		try {
			modeleJeu = new ModeleJeu(new Player(dossierData+"heroSet.png",200,300), new BlockMap(dossierData+"2.tmx"), gameContainer.getHeight(),gameContainer.getWidth());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		controleJeu = new ControleJeu(modeleJeu);
		victoire = new Image(dossierData+"victoire.png");
		defaite = new Image(dossierData+"defaite.png");
		coeur = new Image(dossierData+"coeur.png");

		int[] tempRender = modeleJeu.renderMap();
		mapRender[0] = tempRender[0];
		mapRender[1] = tempRender[1];
		gameContainer.setVSync(true);

		etatActuel = EtatJeu.DEPLACEMENT_JOUEUR;

	} // *** Fin Init ***



	/**
	 * Méthode de rendu du jeu
	 */
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g)
			throws SlickException {


		// Call super method
		super.render(gameContainer, stateBasedGame, g);


		// Vérification

		switch(etatActuel) {
		case DEPLACEMENT_MAP:

			int newRender[] = modeleJeu.renderMap();
			boolean stop = false;

			if(!out) {
				System.out.println("*G*newR "+newRender[0]);
				System.out.println("*G*oldR"+mapRender[0]+"\n_____\n");
				out = true;
			}
			int vitesseDeplacement = 2;
			// Traitement des X
			if(newRender[0]*60 < mapRender[0]*60+mapRender[2]) {
				mapRender[2] -= vitesseDeplacement;
			} else if(newRender[0]*60 > mapRender[0]*60+mapRender[2]) {
				mapRender[2] += vitesseDeplacement;
			} else {
				stop = true;
				mapRender[0] += mapRender[2]/60;
				mapRender[2] = 0;
			}
			
			// Traitement des Y
			if(newRender[1]*60 < mapRender[1]*60+mapRender[3]) {
				mapRender[3] -= vitesseDeplacement;
				stop = false;
			} else if(newRender[1]*60 > mapRender[1]*60+mapRender[3]) {
				mapRender[3] += vitesseDeplacement;
				stop = false;
			} else {
				mapRender[1] += mapRender[3]/60;
				mapRender[3] = 0;
			}

			
			if(stop) {
				etatActuel = EtatJeu.DEPLACEMENT_JOUEUR;
			} else {
				System.out.println("*G*"+mapRender[2]+" "+mapRender[3]);
			}
			break;

		case VICTOIRE:
			victoire.draw(100, 250);
			break;

		case DEFAITE:
			defaite.draw(100,250);
			break;

		}


		//// Default
		// Render Map
		modeleJeu.getMap().getTiledMap().render(mapRender[0]*60+mapRender[2], mapRender[1]*60+mapRender[3]);

		// Render Hero
		g.drawAnimation(modeleJeu.getHero().getAnimation(), modeleJeu.getHero().getX()+mapRender[0]*60+mapRender[2], modeleJeu.getHero().getY()+mapRender[1]*60+mapRender[3]);

		// Render Vie
		for(int i = 0; i < modeleJeu.getHero().getNbrVies(); i++) {
			coeur.draw(10 + i * coeur.getWidth(), gameContainer.getHeight() - coeur.getHeight());
		}

	} // *** Fin Render ***



	/**
	 * Méthode de gestion des contrôles joueur
	 */
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
			throws SlickException {

		// Call super method
		super.update(gameContainer, stateBasedGame, delta);

		switch(etatActuel) {
		case DEPLACEMENT_JOUEUR:	
			controleJeu.inputJoueur(gameContainer.getInput(), delta);
			modeleJeu.getHero().incStill();
			if(deplacementMap()) etatActuel = EtatJeu.DEPLACEMENT_MAP;
			break;
		}
	}

	/**
	 * Vérifie si l'on doit ou non déplacer la map
	 * @return Vrai si on doit la déplacer
	 */
	private boolean deplacementMap() {
		int[] newRender = modeleJeu.renderMap();
		if(mapRender[0] != newRender[0]) return true;
		if(mapRender[1] != newRender[1]) return true;
		return false;
	}

	/**
	 * Retourne l'identifiant de l'état du jeu
	 */
	@Override
	public int getID() {
		return stateID;
	}


}
