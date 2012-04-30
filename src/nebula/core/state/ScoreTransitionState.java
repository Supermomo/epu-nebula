package nebula.core.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;


/**
 * Score transition state
 */
public class ScoreTransitionState extends AbstractMenuState
{
    private int lastState;

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);
        
        lastState = NebulaState.MainMenu.id;
    }
    
    public void initScore (int score, boolean won, int lastState)
    {
        this.lastState = lastState;
        resetMenu();
        
        // Add menu items
        if (won) setMenuTitle("BRAVO !");
        else     setMenuTitle("PERDU !");
            
        addMenuItem("Score : " + score, false);
        addMenuItem("", false);
        addMenuItem("", false);
        
        if (NebulaGame.isScenario)
            addMenuItem("Continuer", true);
        
        addMenuItem("Quitter", true);
    }
    
    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            case 3:
                if (NebulaGame.isScenario)
                    nebulaGame.initAndEnterState(lastState+1, TransitionType.Fade);
                else
                    nebulaGame.enterState(NebulaState.RapidModeMenu.id);
                break;
            case 4:
                nebulaGame.enterState(NebulaState.MainMenu.id);
            default:
                break;
        }
    }

    @Override
    public int getID () { return NebulaState.ScoreTransition.id; }
}
