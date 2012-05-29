package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Load player menu
 */
public class PlayerMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Choix du joueur");
        addMenuItem("Ancien joueur", null, true);
        addMenuItem("Nouveau joueur", null, true);
        addMenuSpaces(1);
        addMenuItem("Quitter", sndPath + "quit.ogg", true);
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            // Ancien joueur
            case 0:
                nebulaGame.initAndEnterState(NebulaState.PlayerSelectMenu.id);
                break;
            // Nouveau joueur
            case 1:
                nebulaGame.initAndEnterState(NebulaState.PlayerName.id);
                break;
            // Quitter
            case 2:
                nebulaGame.quitGame();
                break;
        }
    }


	@Override
	public int getID() { return NebulaState.PlayerMenu.id; }
}
