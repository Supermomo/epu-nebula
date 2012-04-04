package nebula.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class NebulaGame extends StateBasedGame {

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
		
		// Ajout des GameStates
		this.addState((new MainMenuState(State.MAIN_MENU.getValeur())));
		this.addState(new OptionMenuState(State.OPTION_MENU.getValeur()));
		this.addState(new nebula.minigame.gravity.Gravity(State.JEU_GRAVITY.getValeur()));
		
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
        this.getState(State.JEU_GRAVITY.getValeur()).init(gameContainer, this);
	}

	/**
	 * Méthode principale lancée au début du jeu
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new NebulaGame());
         app.setDisplayMode(800, 600, false);
         app.start();
    }
 

}
