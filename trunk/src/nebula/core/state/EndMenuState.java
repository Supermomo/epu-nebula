package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
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
        setMenuTitle("Félicitation !");
        addMenuItem("Bravo, tu as fini l'aventure !", null, false);
        addMenuSpaces(1);
        addMenuItem("Score : " + NebulaConfig.getAdventureScore(), null, false);
        addMenuSpaces(2);
        addMenuItem("Retour", sndPath + "cancel.ogg", true);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        nebulaGame.initAndEnterState(NebulaState.Credits.id, TransitionType.Fade);
    }

    @Override
    public int getID () { return NebulaState.EndMenu.id; }
}
