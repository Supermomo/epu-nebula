package nebula.core;

import java.awt.Toolkit;

import nebula.core.config.NebulaConfig;
import nebula.core.state.LoadingState;
import nebula.core.state.ScoreTransitionState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
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

        SetupAdventure          (50),
        DifficultyMenu          (51),

        StartAdventure          (100),
        HistoryInvaders1        (100),
        HistoryInvaders2        (101),
        HistoryInvaders3        (102),
        SpaceInvaders           (103),
        HistoryShepherdFly      (104),
        HistoryShepherd1        (105),
        HistoryShepherd2        (106),
        HistoryShepherd3        (107),
        SpaceShepherd           (108),
        HistoryAsteroidFly      (109),
        HistoryAsteroid1        (110),
        Asteroid                (111),
        HistoryGravity1         (112),
        HistoryGravity2         (113),
        Gravity                 (114),
        HistoryBreakout1        (115),
        Breakout                (116),
        HistoryEnd1             (117),
        EndMenu                 (118),

        PauseMenu               (200),
        ScoreTransition         (201);

        public int id;
        private NebulaState (int id) { this.id = id; }
    }

    public static enum TransitionType
        {None, Fade, HorizontalSplit, VerticalSplit};

    public static boolean isAdventureMode;


    /**
     * Constructeur du jeu
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
        // Hide mouse cursor
        gc.setMouseCursor(new Image("ressources/images/common/cursor.png"), 0, 0);

        // Loading state
        this.addState(new LoadingState());
        this.enterState(NebulaState.Loading.id);
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
        ((ScoreTransitionState)getState(NebulaState.ScoreTransition.id))
            .initScore(score, won, lastState);

        enterState(NebulaState.ScoreTransition.id, TransitionType.Fade);
    }


    /**
     * Create and start the Nebula slick game
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
            //app.setVerbose(false);
            //app.setShowFPS(false);
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
