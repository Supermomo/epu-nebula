package nebula.core.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;


/**
 * Main menu state
 */
public class MainMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Bienvenue, " + NebulaGame.playerName);
        addMenuItem("Mode Aventure", true);
        addMenuItem("Mode Rapide", true);
        addMenuItem("Scores", true);
        addMenuItem("Options", true);
        addMenuItem("Quitter", true);
    }
    
    @Override
    protected void indexSelectedEvent (int index)
    {        
        // Index selected
        switch (index)
        {
            // Mode aventure
            case 0:
                NebulaGame.isScenario = true;
                nebulaGame.enterState(NebulaState.StartAventure.id, TransitionType.Fade);
                break;
            // Mode rapide
            case 1:
                NebulaGame.isScenario = false;
                nebulaGame.enterState(NebulaState.RapidModeMenu.id);
                break;
            // Scores
            case 2:
                nebulaGame.enterState(NebulaState.ScoreMenu.id);
                break;
            // Options
            case 3:
                nebulaGame.enterState(NebulaState.OptionsMenu.id);
                break;
            // Quitter
            case 4:
            default:
                System.exit(0);
                break;
        }
    }

    @Override
    public int getID () { return NebulaState.MainMenu.id; }
}
