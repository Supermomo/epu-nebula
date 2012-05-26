package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Help menu
 */
public class HelpMenuState extends AbstractMenuState
{
    private int lastState = NebulaState.MainMenu.id;
    private Sound sndHelp;

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Aide");
        addMenuItem("Retour", null, true);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        nebulaGame.enterState(lastState);
    }

    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);

        if (sndHelp != null)
            sndHelp.play();
    }

    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.leave(gc, game);

        if (sndHelp != null && sndHelp.playing())
            sndHelp.stop();
    }

    /**
     * Set the last state to return
     */
    public void setLastState (int lastState)
    {
        this.lastState = lastState;
    }

    /**
     * Set the help sound
     */
    public void setHelp (Sound sndHelp)
    {
        this.sndHelp = sndHelp;
    }

    @Override
    public int getID() { return NebulaState.HelpMenu.id; }
}
