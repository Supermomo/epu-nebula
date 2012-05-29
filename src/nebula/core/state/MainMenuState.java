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
        addMenuItem("Mode Aventure", sndPath + "adventuremode.ogg", true);
        addMenuItem("Mode Rapide", sndPath + "rapidmode.ogg", true);
        addMenuItem("Scores", sndPath + "scores.ogg", true);
        addMenuItem("Crédits", sndPath + "credits.ogg", true);
        addMenuSpaces(1);
        addMenuItem("Changer de joueur", sndPath + "changeplayer.ogg", true);
        addMenuItem("Quitter", sndPath + "quit.ogg", true);
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
            // Changer de joueur
            case 4:
                nebulaGame.initAndEnterState(NebulaState.PlayerMenu.id);
                break;
            // Quitter
            case 5:
                nebulaGame.quitGame();
                break;
        }
    }

    @Override
    public int getID () { return NebulaState.MainMenu.id; }
}
