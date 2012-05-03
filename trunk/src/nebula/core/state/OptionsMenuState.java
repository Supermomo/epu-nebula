package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.config.NebulaConfig;
import nebula.core.state.AbstractMinigameState.Difficulty;

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
        String diffStr = "";
        Difficulty diff = NebulaConfig.getDifficulty();
        
        if (Difficulty.Easy.equals(diff))
            diffStr = "Facile";
        else if (Difficulty.Medium.equals(diff))
            diffStr = "Moyen";
        else if (Difficulty.Hard.equals(diff))
            diffStr = "Difficile";
        else if (Difficulty.Insane.equals(diff))
            diffStr = "Très difficile";
        
        // Add menu items
        setMenuTitle("Options");
        addMenuItem("Difficulté : " + diffStr, true);
        addMenuSpaces(1);
        addMenuItem("Retour", true);
    }
    
    
    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            case 0:
                Difficulty diff = NebulaConfig.getDifficulty();
                
                if (Difficulty.Easy.equals(diff))
                    NebulaConfig.setDifficulty(Difficulty.Medium);
                else if (Difficulty.Medium.equals(diff))
                    NebulaConfig.setDifficulty(Difficulty.Hard);
                else if (Difficulty.Hard.equals(diff))
                    NebulaConfig.setDifficulty(Difficulty.Insane);
                else if (Difficulty.Insane.equals(diff))
                    NebulaConfig.setDifficulty(Difficulty.Easy);
                
                try { this.init(game.getContainer(), game); }
                catch (SlickException exc) { exc.printStackTrace(); }
                break;

            default:
                nebulaGame.enterState(NebulaState.MainMenu.id);
                break;
        }
    }
    
    
    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.leave(gc, game);
        
        // Save user config
        NebulaConfig.saveData();
    }
    

	@Override
	public int getID() { return NebulaState.OptionsMenu.id; }
}
