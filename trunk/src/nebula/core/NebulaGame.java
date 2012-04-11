package nebula.core;

import java.awt.Toolkit;

import nebula.core.state.*;
import nebula.minigame.breakout.Breakout;
import nebula.minigame.gravity.Gravity;
import nebula.minigame.spaceInvaders.SpaceInvaders;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;


public class NebulaGame extends StateBasedGame
{
	public static boolean isScenario;
	
    /**
     * Game states enum
     * All state Id must be defined here, and only here !
     */
    public static enum StateID
    {
        MainMenu                (0),
        OptionMenu              (1),
        StartAventure           (10),
        Intro1Jeu               (10),
        Intro2Jeu               (11),
        Intro3Jeu               (12),
        SpaceInvaders           (13),
        FinInvaders             (14),
        Bougibouga              (15),
        Jubba                   (16),
        Breakout                (17),
        Fin                     (18),
        Gravity					(100),
        Score				(-1);
        
        public int value;
        private StateID (int value) { this.value = value; }
    }
    
	public final static String fontPath = "ressources/font/batmfa.ttf";
	private UnicodeFont uFont;
	
	public static enum TransitionType
	    {Default, FadeOut, HorizontalSplit, VerticalSplit};
	
	/**
	 * Constructeur du jeu.
	 * Definit les différents états (menus / jeux) disponnibles
	 * @throws SlickException 
	 */
    public NebulaGame() throws SlickException
    {
		super("Nebula");
		
		// ==== GAME STATES ===
		// Menus / Options
		this.addState(new MainMenuState());
		this.addState(new OptionMenuState());
		
		// Main timeline
		this.addState(new Intro1Jeu());
		this.addState(new Intro2Jeu());
		this.addState(new Intro3Jeu());
		this.addState(new SpaceInvaders(8));
		this.addState(new FinInvaders());
		this.addState(new Bougibouga());
		this.addState(new Jubba());
		this.addState(new Breakout());
		this.addState(new Fin());
		this.addState(new Gravity(StateID.Gravity.value));
		
		//Score
		this.addState(new StateScore());
		
		// Fonts
		uFont = new UnicodeFont(fontPath, (int)((Toolkit.getDefaultToolkit().getScreenSize().width/1920.0f) * 44.0f), false, false);
		uFont.addAsciiGlyphs();
		uFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
		
		// Starting state
		this.enterState(StateID.MainMenu.value);
	}

	/**
	 * 
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException
	{
        this.getState(StateID.MainMenu.value).init(gc, this);
	}

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
			enterState(++currentState, new FadeOutTransition(Color.black, 3000), new FadeInTransition(Color.black,3000));
	}
	
	/**
     * Goto next state with the default transition
     */
	public void next (int currentState)
	{
	    next(currentState, TransitionType.Default);
	}
	
	public void showScore(int currentState, int Score, boolean isScenar){
		((StateScore)getState(StateID.Score.value)).setLastState(currentState);
		((StateScore)getState(StateID.Score.value)).setScore(Score);
		enterState(StateID.Score.value);	
	}

	/**
	 * Get the main font
	 */
	public UnicodeFont getFont () { return uFont; }
	
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
