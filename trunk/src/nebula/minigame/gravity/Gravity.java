package nebula.minigame.gravity;


import nebula.core.NebulaGame.NebulaState;
import nebula.core.state.AbstractMinigameState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Gravity extends AbstractMinigameState {

	private enum EtatJeu {
		DEPLACEMENT_JOUEUR,
		DEPLACEMENT_MAP,
		VICTOIRE,
		DEFAITE,
		CHANGEMENT_CARTE;
	}

	private EtatJeu etatActuel;

	private final static String dossierData = "ressources/images/gravity/";
	private ModeleJeu modeleJeu;
	private ControleJeu controleJeu;
	private Image coeur;
	private int[] mapRender = {0,0,0,0};
	private int numeroMap;
	private int timer;



	/////// DEBUG ///////
	private boolean out = false;

	/* Game ID */
    @Override public int getID () { return NebulaState.Gravity.id; }

	/**
	 * Initialisation des données pour le jeu
	 */
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {

		numeroMap = 1;
		
		// Call super method
		super.init(gameContainer, stateBasedGame);
		try {
			modeleJeu = new ModeleJeu(new Player(dossierData+"heroSet.png",200,300), new BlockMap(dossierData+numeroMap+".tmx"), gameContainer.getHeight(),gameContainer.getWidth());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		controleJeu = new ControleJeu(modeleJeu);
		coeur = new Image(dossierData+"coeur.png");

		int[] tempRender = modeleJeu.renderMap();
		mapRender[0] = tempRender[0];
		mapRender[1] = tempRender[1];

		etatActuel = EtatJeu.DEPLACEMENT_JOUEUR;
		timer = 0;
		score = 0;

	} // *** Fin Init ***



	/**
	 * Méthode de rendu du jeu
	 */
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g)
			throws SlickException {


		// Call super method
		super.render(gameContainer, stateBasedGame, g);
		
		//// Default
		// Render Map
		modeleJeu.getMap().getTiledMap().render(mapRender[0]*60+mapRender[2], mapRender[1]*60+mapRender[3]);

		// Render Hero
		g.drawAnimation(modeleJeu.getHero().getAnimation(), modeleJeu.getHero().getX()+mapRender[0]*60+mapRender[2], modeleJeu.getHero().getY()+mapRender[1]*60+mapRender[3]);

		// Render Vie
		for(int i = 0; i < modeleJeu.getHero().getNbrVies(); i++) {
			coeur.draw(10 + i * coeur.getWidth(),gameContainer.getHeight() - coeur.getHeight());
		}
		

		// Affichage de l'image en fonction de l'état
		switch(etatActuel) {
		case DEPLACEMENT_MAP:

			int newRender[] = modeleJeu.renderMap();
			boolean stop = false;

			float vitesseDeplacement = 5f;
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
			}
			break;
			
		case DEPLACEMENT_JOUEUR:
			// On vérifie si on a atteint un des états final
			if(modeleJeu.getDefaite()) etatActuel = EtatJeu.DEFAITE;
			if(modeleJeu.getVictoire()) etatActuel = EtatJeu.VICTOIRE;
			break;

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
			timer += delta; // On augmente le temps
			break;
		case DEFAITE:
			modeleJeu.arreterSon();
			score = 100;
			gameDefeat();
			break;
		case VICTOIRE:
			//if(gameContainer.getInput().isKeyDown(Input.KEY_ENTER)) {
			modeleJeu.arreterSon();
			// Mise à jour du score
			score += modeleJeu.getHero().getNbrVies()*500 + (1000 - timer/100);
			// Appel à la fonction héritée
			
			if(numeroMap < 3)
				etatActuel = EtatJeu.CHANGEMENT_CARTE;
			else
				gameVictory();
			break;
		case CHANGEMENT_CARTE:
			super.init(gameContainer, stateBasedGame);
			try {
				modeleJeu = new ModeleJeu(new Player(dossierData+"heroSet.png",200,300), new BlockMap(dossierData+(++numeroMap)+".tmx"), gameContainer.getHeight(),gameContainer.getWidth());
			} catch (SlickException e) {
				e.printStackTrace();
			}
			controleJeu = new ControleJeu(modeleJeu);
			coeur = new Image(dossierData+"coeur.png");

			int[] tempRender = modeleJeu.renderMap();
			mapRender[0] = tempRender[0];
			mapRender[1] = tempRender[1];

			etatActuel = EtatJeu.DEPLACEMENT_JOUEUR;
			timer = 0;
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
}
