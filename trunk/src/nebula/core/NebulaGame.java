package nebula.core;

import nebula.minigame.spaceInvaders.IntroSpaceInvaders;
import nebula.minigame.spaceInvaders.SpaceInvaders;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

public class NebulaGame extends StateBasedGame {

	private MinigameController controleur;
	
	enum State {
		MAIN_MENU(0),
		OPTION_MENU(1),
		JEU_GRAVITY(10);
		
		private int valeur;
		
		private State(int n) {
			valeur = n;
		}
		
		public int getValeur() { return valeur; }
	}
	
	/**
	 * Constructeur du jeu.
	 * Definit les différents états (menus / jeux) disponnibles
	 */
	public NebulaGame() {
		super("Nebula");
		
		controleur = new MinigameController();
		
		// Ajout des GameStates
		this.addState((new MainMenuState(State.MAIN_MENU.getValeur())));
		this.addState(new OptionMenuState(State.OPTION_MENU.getValeur()));
		this.addState(new IntroSpaceInvaders());
		this.addState(new SpaceInvaders(8));
		this.addState(new nebula.minigame.gravity.Gravity(5));
		
		
		// Selection de l'état de départ
		this.enterState(State.MAIN_MENU.getValeur());
	}

	/**
	 * 
	 */
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(State.MAIN_MENU.getValeur()).init(gameContainer, this);
        this.getState(State.OPTION_MENU.getValeur()).init(gameContainer, this);
        this.getState(5).init(gameContainer, this);
	}

	/**
	 * Méthode principale lancée au début du jeu
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new NebulaGame());
         app.setDisplayMode(1280, 768, false);
         app.start();
    }
 
	public MinigameController getControleur()
	{
		return this.controleur;
	}
	
	public void next(int state)
	{
		enterState(++state, null, new FadeInTransition(Color.black,3000));
	}

}
