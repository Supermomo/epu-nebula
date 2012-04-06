package nebula.minigame.gravity;


import nebula.core.NebulaGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
		int taille[] = modeleJeu.renderMap();
		modeleJeu.getMap().getTiledMap().render(taille[0]*60, taille[1]*60);
		g.drawAnimation(modeleJeu.getHero().getAnimation(), modeleJeu.getHero().getX()+taille[0]*60, modeleJeu.getHero().getY()+taille[1]*60);
    	
		if(modeleJeu.getFin()) {
			if(modeleJeu.getVictoire()) {
				victoire.draw(100, 250);
				NebulaGame ng=(NebulaGame)stateBasedGame;
				ng.next(getID());
			} else if(modeleJeu.getDefaite()) {
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
		
		if(gameContainer.getInput().isKeyDown(Input.KEY_ESCAPE))
		{
			((NebulaGame)stateBasedGame).enterState(0);
		}
		
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
