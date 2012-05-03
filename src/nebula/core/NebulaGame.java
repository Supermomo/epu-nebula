package nebula.core;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import nebula.core.config.NebulaConfig;
import nebula.core.state.*;
import nebula.minigame.asteroid.AsteroidGame;
import nebula.minigame.breakout.BreakoutGame;
import nebula.minigame.gravity.Gravity;
import nebula.minigame.spaceInvaders.SpaceInvaders;
import nebula.minigame.spaceShepherd.SpaceShepherd;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
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
    /**
     * Game states enum
     * All state Id must be defined here, and only here !
     */
    public static enum NebulaState
    {
        Loading                 (0),
        MainMenu                (1),
        RapidModeMenu           (2),
        OptionsMenu             (3),
        ScoresMenu              (4),
        PauseMenu               (5),
        ScoreTransition         (9),
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
        SpaceShepherd           (20),
        EndMenu                 (21);

        public int id;
        private NebulaState (int id) { this.id = id; }
    }

    public static enum TransitionType
        {None, Fade, HorizontalSplit, VerticalSplit};
    
    public static boolean isScenario;

    
    /**
     * Constructeur du jeu.
     * Definit les différents états (menus / jeux) disponibles
     * @throws SlickException 
     */
    public NebulaGame (String playerName) throws SlickException
    {
        super("Nebula");
        
        // Load config for the player
        NebulaConfig.loadData(playerName);
    }

    /**
     * Initialise states
     */
    @Override
    public void initStatesList (GameContainer gc) throws SlickException
    {
        // Loading state
        this.addState(new LoadingState());
        this.enterState(NebulaState.Loading.id);
    }
    
    /**
     * Load all states
     */
    public void loadGame ()
    {
        List<BasicGameState> states = new ArrayList<BasicGameState>();
        
        // Menus
        states.add(new MainMenuState());
        states.add(new RapidModeMenuState());
        states.add(new OptionsMenuState());
        states.add(new ScoresMenuState());
        states.add(new PauseMenuState());
        states.add(new ScoreTransitionState());
        states.add(new EndMenuState());

        // Aventure
        states.add(new Intro1Jeu());
        states.add(new Intro2Jeu());
        states.add(new Intro3Jeu());
        states.add(new SpaceInvaders());
        states.add(new FinInvaders());
        states.add(new Bougibouga());
        states.add(new Jubba());
        states.add(new BreakoutGame());
        states.add(new AsteroidGame());
        states.add(new Gravity());
        states.add(new SpaceShepherd());
        
        // Init all states
        for (BasicGameState state : states)
        {
            this.addState(state);
            
            try { state.init(this.getContainer(), this); }
            catch (SlickException exc) { exc.printStackTrace(); }
        }

        // Enter main state
        this.enterState(NebulaState.MainMenu.id, TransitionType.Fade);
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
    public void initAndEnterState (int state, TransitionType transition)
    {
        try { getState(state).init(this.getContainer(), this); }
        catch (SlickException exc) { exc.printStackTrace(); }

        enterState(state, transition);
    }

    /**
     * Init and goto next state with the default transition
     */
    public void initAndEnterState (int state)
    {
        initAndEnterState(state, TransitionType.None);
    }

    /**
     * Show the score transition state
     */
    public void showScoreState (int score, boolean won, int lastState)
    {
        // Save user config
        NebulaConfig.saveData();
        
        // Set up score state
        ((ScoreTransitionState)getState(NebulaState.ScoreTransition.id)).
        initScore(score, won, lastState);

        enterState(NebulaState.ScoreTransition.id, TransitionType.Fade);
    }


    /**
     * Create and start the Nebula sick game
     */
    public static void startNebulaGame ()
    {
        try {
            AppGameContainer app =
                new AppGameContainer(new NebulaGame("Joueur"));

            app.setDisplayMode(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                true);
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
}
