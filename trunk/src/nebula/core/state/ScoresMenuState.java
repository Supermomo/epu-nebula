package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Scores menu
 */
public class ScoresMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Scores");
        addMenuItem("Retour", true);
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        // TODO
        nebulaGame.enterState(NebulaState.MainMenu.id);
    }

    @Override
    public int getID() { return NebulaState.ScoresMenu.id; }
}
