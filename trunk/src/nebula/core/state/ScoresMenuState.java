package nebula.core.state;

import nebula.core.NebulaGame.Minigame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.config.NebulaConfig;

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

        // Scores
        addMenuItem("Aventure : " + NebulaConfig.getAdventureBestScore(), false);
        addMenuSpaces(1);

        for (Minigame mg : Minigame.values())
            addMenuItem(mg.name + " : " + NebulaConfig.getRapidmodeScore(mg), false);

        addMenuSpaces(2);
        addMenuItem("Retour", true);
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        nebulaGame.enterState(NebulaState.MainMenu.id);
    }

    @Override
    public int getID() { return NebulaState.ScoresMenu.id; }
}
