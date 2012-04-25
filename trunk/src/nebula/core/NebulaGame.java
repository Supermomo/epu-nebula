package nebula.core;

import java.awt.Toolkit;

import nebula.core.state.*;
import nebula.minigame.asteroid.AsteroidGame;
import nebula.minigame.breakout.BreakoutGame;
import nebula.minigame.gravity.Gravity;
import nebula.minigame.spaceInvaders.SpaceInvaders;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;


/**
 * Nebula game main class
 */
public class NebulaGame extends StateBasedGame
{
	public static boolean isScenario;
	
    /**
     * Game states enum
     * All state Id must be defined here, and only here !
     */
    public static enum NebulaState
    {
        MainMenu                (0),
        OptionMenu              (1),
        Score                   (2),
        StartAventure           (10),
        Intro1Jeu               (10),
        Intro2Jeu               (11),
        Intro3Jeu               (12),
        SpaceInvaders           (13),
        FinInvaders             (14),
        Bougibouga              (15),
        Jubba                   (16),
        Breakout                (17),
        Asteroid                (18),
        Gravity					(19),
        Fin                     (20);
        
        public int id;
        private NebulaState (int id) { this.id = id; }
    }
    	
	public static enum TransitionType
	    {Default, FadeOut, HorizontalSplit, VerticalSplit};
	
	/**
	 * Constructeur du jeu.
	 * Definit les différents états (menus / jeux) disponibles
	 * @throws SlickException 
	 */
    public NebulaGame () throws SlickException
    {
		super("Nebula");
		
		// ==== GAME STATES ===
		// Menus
		this.addState(new MainMenuState());
		this.addState(new OptionMenuState());
		this.addState(new StateScore());
		
		// Aventure
		this.addState(new Intro1Jeu());
		this.addState(new Intro2Jeu());
		this.addState(new Intro3Jeu());
		this.addState(new SpaceInvaders());
		this.addState(new FinInvaders());
		this.addState(new Bougibouga());
		this.addState(new Jubba());
		this.addState(new BreakoutGame());
		this.addState(new AsteroidGame());
		this.addState(new Gravity());
		this.addState(new Fin());
				
		// Starting state
		this.enterState(NebulaState.MainMenu.id);
	}

	/**
	 * Initialise states
	 */
	@Override
	public void initStatesList (GameContainer gc) throws SlickException {}

	/**
	 * Goto next state with the given transition
	 */
	public void next (int currentState, TransitionType transition)
	{
	    // Goto next state with the given transition
		if (TransitionType.HorizontalSplit.equals(transition))
			enterState(++currentState, null, new HorizontalSplitTransition(Color.black));
		else if (TransitionType.VerticalSplit.equals(transition))
			enterState(++currentState, null, new VerticalSplitTransition(Color.black));
		else
			enterState(++currentState, new FadeOutTransition(Color.black, 2000), new FadeInTransition(Color.black, 2000));
	}
	
	/**
     * Goto next state with the default transition
     */
	public void next (int currentState)
	{
	    next(currentState, TransitionType.Default);
	}
	
	public void showScore (int currentState, int score)
	{
		((StateScore)getState(NebulaState.Score.id)).setLastState(currentState);
		((StateScore)getState(NebulaState.Score.id)).setScore(score);
		enterState(NebulaState.Score.id);	
	}
	
	/**
	 * Start NebulaGame
	 */
	public static void main (String[] args) throws SlickException
	{
	    // FULLSCREEN //
	    final boolean FULLSCREEN = true;
	    
	    try {
    	    AppGameContainer app = new AppGameContainer(new NebulaGame());
    	    
    	    if (FULLSCREEN)
    	    {
                app.setDisplayMode(
                    Toolkit.getDefaultToolkit().getScreenSize().width,
                    Toolkit.getDefaultToolkit().getScreenSize().height,
                    true);
    	    }
    	    else app.setDisplayMode(800, 600, false);
    	    
            app.setTargetFrameRate(120);
            app.start();
	    }
	    catch (Exception exc) {
            exc.printStackTrace();
        }
	}
}
