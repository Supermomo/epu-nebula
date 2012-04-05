package nebula.minigame.gravity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Gravity extends BasicGameState {
	
	private int stateID;
	
	private final static String dossierData = "ressources/images/gravity/";
	private ModeleJeu modeleJeu;
	private ControleJeu controleJeu;
	private Image victoire, defaite, coeur;
		
	public Gravity(int stateID) {
		this.stateID = stateID;
	}
	

	/**
	 * Initialisation des données pour le jeu
	 */
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		try {
			modeleJeu = new ModeleJeu(new Player(dossierData+"heroSet.png",200,300), new BlockMap(dossierData+"2.tmx"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		controleJeu = new ControleJeu(modeleJeu);
		victoire = new Image(dossierData+"victoire.png");
		defaite = new Image(dossierData+"defaite.png");
		coeur = new Image(dossierData+"coeur.png");
		gameContainer.setVSync(true);
	}

	/**
	 * Méthode de rendu du jeu
	 */
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g)
			throws SlickException {
		modeleJeu.getMap().getTiledMap().render(0, 0);
		g.drawAnimation(modeleJeu.getHero().getAnimation(), modeleJeu.getHero().getX(), modeleJeu.getHero().getY());
    	
		if(modeleJeu.getFin()) {
			if(modeleJeu.getVictoire())
				victoire.draw(100, 250);
			else if(modeleJeu.getDefaite()) {
				defaite.draw(100,250);
			}
		}

    	for(int i = 0; i < modeleJeu.getHero().getNbrVies(); i++) {
			coeur.draw(10 + i * coeur.getWidth(), gameContainer.getHeight() - coeur.getHeight());
    	}
	}

	/**
	 * Méthode de gestion des contrôles joueur
	 */
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
			throws SlickException {
		controleJeu.inputJoueur(gameContainer.getInput(), delta);
		modeleJeu.getHero().incStill();

	}

	/**
	 * Retourne l'identifiant de l'état du jeu
	 */
	@Override
	public int getID() {
		return stateID;
	}

}
