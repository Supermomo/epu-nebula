package nebula.minigame.gravity;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.helper.Utils;
import nebula.core.state.AbstractMinigameState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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

	private Queue<String> listeNiveaux;

	private Image coeur;
	private int[] mapRender;
	private int timer;


	/* Game ID */
	@Override public int getID () { return NebulaState.Gravity.id; }

	/**
	 * Initialisation des données pour le jeu
	 */
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		// Call super method
		super.init(gameContainer, stateBasedGame);

		score = 0;
		mapRender= new int[]{0,0,0,0};
		// Chargement des niveaux pour le jeu
		// --- Choix de la difficulté
		String prefixeChemin;
		switch(difficulty) {
		case Medium:
			prefixeChemin = "medium/";
			break;
		case Hard:
			prefixeChemin = "hard/";
			break;
		case Insane:
			prefixeChemin = "insane/";
			break;
		case Easy:default:
			prefixeChemin = "easy/";
			break;
		}
		// --- Création de la liste des niveaux
		Queue<String> listeNiveaux = new LinkedList<String>();
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0;i<3;) {
			// On veut boucler tant qu'il n'a pas ses trois map différentes
			int choix = (int) (Math.random() * 8 + 1); // Choix des maps entre 1 et 8
			if(!list.contains(choix)) {
				// On l'ajoute à la liste temporaire
				list.add(choix);
				// On l'ajoute à la liste des niveaux
				listeNiveaux.add(prefixeChemin+choix);
				i++; // L'incrémentation du compteur !!
			}
		}

		controleJeu = new ControleJeu();
		coeur = new Image(dossierData+"coeur.png");

		// Music and help
        initMusic("ressources/sons/gravity/music.ogg", 0.3f, true);
        initHelp("ressources/sons/gravity/help.ogg");

		// Envoie de l'initialisation partielle suivant le niveau
		chargement_carte(gameContainer, stateBasedGame, listeNiveaux);
	} // *** Fin Init ***


	public void chargement_carte(GameContainer gameContainer, StateBasedGame stateBasedGame, Queue<String> listeNiveaux)
			throws SlickException {


		this.listeNiveaux = listeNiveaux;

		try {
			modeleJeu = new ModeleJeu(new Player(dossierData+"heroSet.png",200,300), new BlockMap(dossierData+this.listeNiveaux.poll()+".tmx"), gameContainer.getHeight(),gameContainer.getWidth());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		controleJeu.setModele(modeleJeu);


		int[] tempRender = modeleJeu.renderMap();
		mapRender[0] = tempRender[0];
		mapRender[1] = tempRender[1];

		etatActuel = EtatJeu.DEPLACEMENT_JOUEUR;
		timer = 0; // Reset Timer pour la nouvelle map

	}



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
		modeleJeu.getMap().getTiledMap().render(mapRender[0]*modeleJeu.getMap().getTiledMap().getTileWidth()+mapRender[2], mapRender[1]*modeleJeu.getMap().getTiledMap().getTileHeight()+mapRender[3]);

		// Render Hero
		g.drawAnimation(modeleJeu.getHero().getAnimation(), modeleJeu.getHero().getX()+mapRender[0]*modeleJeu.getMap().getTiledMap().getTileWidth()+mapRender[2], modeleJeu.getHero().getY()+mapRender[1]*modeleJeu.getMap().getTiledMap().getTileWidth()+mapRender[3]);

		// Render Vie
		for(int i = 0; i < modeleJeu.getHero().getNbrVies(); i++) {
			coeur.draw(10 + i * coeur.getWidth(),gameContainer.getHeight() - coeur.getHeight());
		}


		// Affichage de l'image en fonction de l'état
		switch(etatActuel) {
		case DEPLACEMENT_JOUEUR:
			// On vérifie si on a atteint un des états final
			if(modeleJeu.getDefaite()) etatActuel = EtatJeu.DEFAITE;
			if(modeleJeu.getVictoire()) etatActuel = EtatJeu.VICTOIRE;
			break;
		}



	} // *** Fin Render ***



	/**
	 * Méthode de gestion des contrôles joueur
	 * Traitement de la fin du jeu
	 * Calcul du déplacement de la map
	 */
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
			throws SlickException {


		float vitesseDeplacement = 0.4f * delta; // Vitesse de scroll de la map

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
			modeleJeu.arreterSon();
			// Mise à jour du score
			score += modeleJeu.getHero().getNbrVies()*500 + (1000 - timer/100);
			// Appel à la fonction héritée
			if(this.listeNiveaux.size() > 0) {
				// Appel de la carte suivante
				etatActuel = EtatJeu.CHANGEMENT_CARTE;
			} else { // FIN
				// Application du taux score/difficulté
				switch(difficulty) {
				case Easy:
					score *= 0.58;
					break;
				case Medium:
					score *= 0.85;
					break;
				case Hard:
					score *= 1.18;
					break;
				case Insane:
					score *= 1.53;
					break;
				}
				
				score = Utils.checkScoreRange(score/3, difficulty);
				gameVictory(); // Fin du jeu
			}
			break;

		case CHANGEMENT_CARTE:
			chargement_carte(gameContainer, stateBasedGame, this.listeNiveaux);
			break;

		case DEPLACEMENT_MAP:

			int newRender[] = modeleJeu.renderMap();
			boolean stop = false;


			// Traitement des X
			if(newRender[0]*modeleJeu.getMap().getTiledMap().getTileWidth() < mapRender[0]*modeleJeu.getMap().getTiledMap().getTileWidth()+mapRender[2]) {
				mapRender[2] -= vitesseDeplacement;
			} else if(newRender[0]*modeleJeu.getMap().getTiledMap().getTileWidth() > mapRender[0]*modeleJeu.getMap().getTiledMap().getTileWidth()+mapRender[2]) {
				mapRender[2] += vitesseDeplacement;
			} else {
				stop = true;
				mapRender[0] += mapRender[2]/modeleJeu.getMap().getTiledMap().getTileWidth();
				mapRender[2] = 0;
			}

			// Traitement des Y
			if(newRender[1]*modeleJeu.getMap().getTiledMap().getTileWidth() < mapRender[1]*modeleJeu.getMap().getTiledMap().getTileWidth()+mapRender[3]) {
				mapRender[3] -= vitesseDeplacement;
				stop = false;
			} else if(newRender[1]*modeleJeu.getMap().getTiledMap().getTileWidth() > mapRender[1]*modeleJeu.getMap().getTiledMap().getTileWidth()+mapRender[3]) {
				mapRender[3] += vitesseDeplacement;
				stop = false;
			} else {
				mapRender[1] += mapRender[3]/modeleJeu.getMap().getTiledMap().getTileWidth();
				mapRender[3] = 0;
			}


			if(stop) {
				etatActuel = EtatJeu.DEPLACEMENT_JOUEUR;
			}
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
