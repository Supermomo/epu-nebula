package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * New/Load adventure menu
 */
public class LoadMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Mode aventure");
        addMenuItem("Reprendre la partie", true);
        addMenuItem("Nouvelle partie", true);
        addMenuSpaces(1);
        addMenuItem("Retour", true);
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            // Load game
            case 0:
                int nextGame = -1;
                switch (NebulaConfig.getAdventureMinigame())
                {
                    case SpaceInvaders:
                        nextGame = NebulaState.SpaceInvaders.id;
                        break;
                    case SpaceShepherd:
                        nextGame = NebulaState.SpaceShepherd.id;
                        break;
                    case Asteroid:
                        nextGame = NebulaState.Asteroid.id;
                        break;
                    case Gravity:
                        nextGame = NebulaState.Gravity.id;
                        break;
                    case Breakout:
                        nextGame = NebulaState.Breakout.id;
                        break;
                    default:
                        break;
                }
                if (nextGame > 0)
                    nebulaGame.enterState(nextGame + 1, TransitionType.Fade);
                else
                    nebulaGame.enterState(NebulaState.MainMenu.id);
                break;
            // New game
            case 1:
                nebulaGame.initAndEnterState(NebulaState.DifficultyMenu.id);
                break;
            default:
                nebulaGame.enterState(NebulaState.MainMenu.id);
                break;
        }
    }


	@Override
	public int getID() { return NebulaState.LoadMenu.id; }
}
