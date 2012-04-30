package nebula.core.state;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.minigame.Minigame.Difficulty;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Options menu
 */
public class OptionsMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);
        
        // Difficulty
        String diff = "";
        
        if (Difficulty.Easy.equals(NebulaGame.difficulty))
            diff = "Facile";
        else if (Difficulty.Medium.equals(NebulaGame.difficulty))
            diff = "Moyen";
        else if (Difficulty.Hard.equals(NebulaGame.difficulty))
            diff = "Difficile";
        else if (Difficulty.Insane.equals(NebulaGame.difficulty))
            diff = "Très difficile";
        
        // Add menu items
        setMenuTitle("Options");
        addMenuItem("Difficulté : " + diff, true);
        addMenuItem("Retour", true);
    }
    
    
    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            case 0:
                if (Difficulty.Easy.equals(NebulaGame.difficulty))
                    NebulaGame.difficulty = Difficulty.Medium;
                else if (Difficulty.Medium.equals(NebulaGame.difficulty))
                    NebulaGame.difficulty = Difficulty.Hard;
                else if (Difficulty.Hard.equals(NebulaGame.difficulty))
                    NebulaGame.difficulty = Difficulty.Insane;
                else if (Difficulty.Insane.equals(NebulaGame.difficulty))
                    NebulaGame.difficulty = Difficulty.Easy;
                
                try { this.init(game.getContainer(), game); }
                catch (SlickException exc) { exc.printStackTrace(); }
                break;

            default:
                nebulaGame.enterState(NebulaState.MainMenu.id);
                break;
        }
    }

	@Override
	public int getID() { return NebulaState.OptionsMenu.id; }
}
