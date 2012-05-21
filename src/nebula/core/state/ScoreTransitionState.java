package nebula.core.state;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Score transition state
 */
public class ScoreTransitionState extends AbstractMenuState
{
    private int lastState;
    private boolean won;

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        lastState = NebulaState.MainMenu.id;

        // Music
        initMusic("ressources/sons/common/fanfare.ogg", 0.3f, true);
    }

    public void initScore (int score, boolean won, int lastState)
    {
        // Init
        try { this.init(nebulaGame.getContainer(), nebulaGame); }
        catch (SlickException exc) { exc.printStackTrace(); }

        this.lastState = lastState;
        this.won = won;
        resetMenu();

        // Add menu items
        if (won) setMenuTitle("BRAVO !");
        else     setMenuTitle("PERDU !");

        if (won)
        {
            addMenuItem("Score : " + score, false);
            addMenuSpaces(2);
        }

        if (NebulaGame.isAdventureMode && won)
            addMenuItem("Continuer", true);
        else
            addMenuItem("Recommencer", true);

        addMenuItem("Quitter", true);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            case 0:
                if (NebulaGame.isAdventureMode)
                {
                    if (won)
                        nebulaGame.initAndEnterState(lastState+1, TransitionType.Fade);
                    else
                        nebulaGame.initAndEnterState(lastState, TransitionType.Fade);
                }
                else
                    nebulaGame.initAndEnterState(lastState, TransitionType.Fade);
                break;
            case 1:
                if (NebulaGame.isAdventureMode)
                    nebulaGame.enterState(NebulaState.MainMenu.id);
                else
                    nebulaGame.enterState(NebulaState.RapidModeMenu.id);
            default:
                break;
        }
    }

    @Override
    public int getID () { return NebulaState.ScoreTransition.id; }
}
