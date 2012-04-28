package nebula.core;

import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	public static String playerName = "Joueur";
	
    /**
     * Game states enum
     * All state Id must be defined here, and only here !
     */
    public static enum NebulaState
    {
        MainMenu                (0),
        RapidModeMenu           (1),
        OptionsMenu             (2),
        ScoresMenu              (3),
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
        Fin                     (20),
        ScoreTransition         (100);
        
        public int id;
        private NebulaState (int id) { this.id = id; }
    }
    	
	public static enum TransitionType
	    {None, Fade, HorizontalSplit, VerticalSplit};
	
	/**
	 * Constructeur du jeu.
	 * Definit les différents états (menus / jeux) disponibles
	 * @throws SlickException 
	 */
    public NebulaGame () throws SlickException
    {
		super("Nebula");
	}

	/**
	 * Initialise states
	 */
	@Override
	public void initStatesList (GameContainer gc) throws SlickException
	{
	    // Menus
        this.addState(new MainMenuState());
        this.addState(new RapidModeMenuState());
        this.addState(new OptionsMenuState());
        this.addState(new ScoresMenuState());
        this.addState(new ScoreTransitionState());
        
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
        
        this.enterState(NebulaState.MainMenu.id);
	}

	/**
	 * Goto next state with the given transition
	 */
	public void enterState (int state, TransitionType transition)
	{
	    // Goto next state with the given transition
		if (TransitionType.HorizontalSplit.equals(transition))
			enterState(state, null, new HorizontalSplitTransition(Color.black));
		else if (TransitionType.VerticalSplit.equals(transition))
			enterState(state, null, new VerticalSplitTransition(Color.black));
		else if (TransitionType.Fade.equals(transition))
		    enterState(state, new FadeOutTransition(Color.black, 1000), new FadeInTransition(Color.black, 1000));
		else
			enterState(state, null, null);
	}
	
	/**
     * Goto next state with the default transition
     */
    public void enterState (int state)
    {
        enterState(state, TransitionType.None);
    }
	
	/**
     * Init and goto next state with the given transition
     */
    public void enterAndInitState (int state, TransitionType transition)
    {
        try { getState(state).init(this.getContainer(), this); }
        catch (SlickException exc) { exc.printStackTrace(); }
        
        enterState(state, transition);
    }
    
    /**
     * Init and goto next state with the default transition
     */
    public void enterAndInitState (int state)
    {
        enterAndInitState(state, TransitionType.None);
    }
    
	/**
	 * Show the score transition state
	 */
	public void showScoreState (int score, boolean won, int lastState)
	{
		((ScoreTransitionState)getState(NebulaState.ScoreTransition.id)).
		    initScore(score, won, lastState);
		
		enterState(NebulaState.ScoreTransition.id, TransitionType.Fade);	
	}
	
	
	/**
	 * Create and start the Nebula sick game
	 */
	public static void startNebulaGame ()
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
	
	/**
	 * Start NebulaGame
	 */
	public static void main (String[] args) throws SlickException
	{
	    NebulaGame.startNebulaGame();
	}
	
	/**
	 * Print step message
	 */
	public static void printStep (String text)
	{
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
        System.out.println("[" + sdf.format(new Date()) + "] " + text);
	}
}
