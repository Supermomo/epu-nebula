package nebula.core.state;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


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
        setMenuTitle("Bienvenue, " + NebulaConfig.getPlayerName());
        addMenuItem("Mode Aventure", true);
        addMenuItem("Mode Rapide", true);
        addMenuItem("Scores", true);
        addMenuItem("Crédits", true);
        addMenuSpaces(1);
        addMenuItem("Quitter", true);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        // Index selected
        switch (index)
        {
            // Mode aventure
            case 0:
                NebulaGame.isAdventureMode = true;
                if (NebulaConfig.getAdventureMinigame() != null)
                    nebulaGame.initAndEnterState(NebulaState.LoadMenu.id);
                else
                    nebulaGame.initAndEnterState(NebulaState.DifficultyMenu.id);
                break;
            // Mode rapide
            case 1:
                NebulaGame.isAdventureMode = false;
                nebulaGame.initAndEnterState(NebulaState.RapidModeMenu.id);
                break;
            // Scores
            case 2:
                nebulaGame.initAndEnterState(NebulaState.ScoresMenu.id);
                break;
            // Crédits
            case 3:
                nebulaGame.initAndEnterState(NebulaState.Credits.id, TransitionType.Fade);
                break;
            // Quitter
            case 4:
                nebulaGame.getContainer().exit();
                break;
            default:
                break;
        }
    }

    @Override
    public int getID () { return NebulaState.MainMenu.id; }
}
