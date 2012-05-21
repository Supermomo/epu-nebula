package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;
import nebula.core.state.AbstractMinigameState.Difficulty;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Difficulty choice menu
 */
public class DifficultyMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Difficulté");
        addMenuItem("Facile", true);
        addMenuItem("Moyen", true);
        addMenuItem("Difficile", true);
        addMenuItem("Très difficile", true);
        addMenuSpaces(1);
        addMenuItem("Retour", true);
        setSelectedIndex(1);
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        boolean wantBack = false;
        switch (index)
        {
            // Facile
            case 0:
                NebulaConfig.setAdventureDifficulty(Difficulty.Easy);
                break;
            // Moyen
            case 1:
                NebulaConfig.setAdventureDifficulty(Difficulty.Medium);
                break;
            // Difficile
            case 2:
                NebulaConfig.setAdventureDifficulty(Difficulty.Hard);
                break;
            // Très difficile
            case 3:
                NebulaConfig.setAdventureDifficulty(Difficulty.Insane);
                break;
            default:
                wantBack = true;
                break;
        }

        // Enter state
        if (wantBack)
        {
            if (NebulaConfig.getAdventureMinigame() != null)
                nebulaGame.enterState(NebulaState.LoadMenu.id);
            else
                nebulaGame.enterState(NebulaState.MainMenu.id);
        }
        else
        {
            NebulaConfig.setAdventureScore(0);
            NebulaConfig.setAdventureMinigame(null);
            nebulaGame.enterState(NebulaState.StartAdventure.id, TransitionType.Fade);
        }
    }


	@Override
	public int getID() { return NebulaState.DifficultyMenu.id; }
}
