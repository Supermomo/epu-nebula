package nebula.core.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;


/**
 * Rapid mode menu state
 */
public class RapidModeMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);
        
        // Add menu items
        setMenuTitle("Mode Rapide");
        addMenuItem("Astéroïdes", true);
        addMenuItem("Casse-briques", true);
        addMenuItem("Invasion", true);
        addMenuItem("Gravité", true);
        addMenuItem("Retour", true);
    }
    
    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        int nextGame = -1;
        
        // Index selected
        switch (index)
        {
            // Asteroids
            case 0:
                nextGame = NebulaState.Asteroid.id;
                break;
            // Breakout
            case 1:
                nextGame = NebulaState.Breakout.id;
                break;
            // Space Invaders
            case 2:
                nextGame = NebulaState.SpaceInvaders.id;
                break;
            // Gravity
            case 3:
                nextGame = NebulaState.Gravity.id;
                break;
            // Escape
            case 4:
            default:
                break;
        }
        
        // Change state if requested
        if (nextGame != -1)
            nebulaGame.initAndEnterState(nextGame, TransitionType.Fade);
        else
            nebulaGame.enterState(NebulaState.MainMenu.id);
    }

    @Override
    public int getID () { return NebulaState.RapidModeMenu.id; }
}
