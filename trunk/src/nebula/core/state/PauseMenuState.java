package nebula.core.state;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Pause menu
 */
public class PauseMenuState extends AbstractMenuState
{
    private int lastState = NebulaState.MainMenu.id;

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Pause");
        addMenuItem("Retour au jeu", sndPath + "resume.ogg", true);
        addMenuItem("Recommencer", sndPath + "retry.ogg", true);
        addMenuItem("Quitter", sndPath + "quit.ogg", true);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            case 1:
                nebulaGame.initAndEnterState(lastState, TransitionType.Fade);
                break;
            case 2:
                if (NebulaGame.isAdventureMode)
                    nebulaGame.enterState(NebulaState.MainMenu.id);
                else
                    nebulaGame.enterState(NebulaState.RapidModeMenu.id);
                break;

            default:
                nebulaGame.enterState(lastState);
                break;
        }
    }

    /**
     * Set the last state to return
     */
    public void setLastState (int lastState)
    {
        this.lastState = lastState;
    }

    @Override
    public int getID() { return NebulaState.PauseMenu.id; }
}
