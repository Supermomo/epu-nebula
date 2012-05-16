package nebula.core.state;

import java.util.ArrayList;
import java.util.List;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.minigame.asteroid.AsteroidGame;
import nebula.minigame.breakout.BreakoutGame;
import nebula.minigame.gravity.Gravity;
import nebula.minigame.spaceInvaders.SpaceInvaders;
import nebula.minigame.spaceShepherd.SpaceShepherd;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Loading state class
 */
public class LoadingState extends AbstractState
{
    // Images path
    public static String imgPath = "ressources/images/common/";

    // Loading bar
    private static final float LBAR_WIDTH  = 600.0f;
    private static final float LBAR_HEIGHT = 24.0f;

    private List<BasicGameState> states = new ArrayList<BasicGameState>();
    private int loadStep;
    private Image imgLoading;
    private LoadState loadState;

    // Loading state
    private enum LoadState {Start, Load, Finish}


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        loadStep = 0;
        loadState = LoadState.Start;

        // Load images
        imgLoading = new Image(imgPath + "loading.png");
    }


    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);

        if (LoadState.Start.equals(loadState))
        {
            this.addStates();
            loadState = LoadState.Load;
        }
        else if (LoadState.Load.equals(loadState))
        {
            if (loadStep >= states.size())
                loadState = LoadState.Finish;
            else
            {
                // Init current state
                BasicGameState state = states.get(loadStep);
                nebulaGame.addState(state);

                try { state.init(nebulaGame.getContainer(), nebulaGame); }
                catch (SlickException exc) { exc.printStackTrace(); }

                loadStep++;
            }
        }
        else
            nebulaGame.enterState(NebulaState.MainMenu.id, TransitionType.Fade);
    }

    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);

        // Render loading image
        imgLoading.drawCentered(gc.getWidth()/2, gc.getHeight()/2 - imgLoading.getHeight()/2 - 12.0f);

        // Render loading bar
        if (LoadState.Load.equals(loadState) ||
            LoadState.Finish.equals(loadState))
        {
            g.setColor(Color.yellow);
            g.setLineWidth(3.0f);
            g.drawRect(
                gc.getWidth()/2 - LBAR_WIDTH/2,
                gc.getHeight()/2 - LBAR_HEIGHT/2 + 12.0f,
                LBAR_WIDTH,
                LBAR_HEIGHT
            );
            g.fillRect(
                gc.getWidth()/2 - LBAR_WIDTH/2,
                gc.getHeight()/2 - LBAR_HEIGHT/2 + 12.0f,
                ((float)loadStep/(float)states.size()) * LBAR_WIDTH,
                LBAR_HEIGHT
            );
        }
    }


    /**
     * Add states to the loading list
     */
    private void addStates ()
    {
        // Menus
        states.add(new MainMenuState());
        states.add(new RapidModeMenuState());
        states.add(new OptionsMenuState());
        states.add(new ScoresMenuState());
        states.add(new PauseMenuState());
        states.add(new DifficultyMenuState());
        states.add(new ScoreTransitionState());
        states.add(new EndMenuState());

        // Aventure
        states.add(new Intro1JeuState());
        states.add(new Intro2JeuState());
        states.add(new Intro3JeuState());
        states.add(new SpaceInvaders());
        states.add(new FinInvadersState());
        states.add(new BougibougaState());
        states.add(new JubbaState());
        states.add(new BreakoutGame());
        states.add(new AsteroidGame());
        states.add(new Gravity());
        states.add(new SpaceShepherd());
    }

    @Override public int getID () { return NebulaState.Loading.id; }
}

