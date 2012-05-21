package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.config.NebulaConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * End menu state
 */
public class EndMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Fin");
        addMenuItem("Bravo, tu as fini l'aventure !", false);
        addMenuSpaces(1);
        addMenuItem("Score : " + NebulaConfig.getAdventureScore(), false);
        addMenuSpaces(2);
        addMenuItem("Quitter", true);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        nebulaGame.enterState(NebulaState.MainMenu.id);
    }

    @Override
    public int getID () { return NebulaState.EndMenu.id; }
}
