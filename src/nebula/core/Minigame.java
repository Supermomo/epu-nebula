package nebula.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Basic minigame state
 */
public abstract class Minigame extends BasicGameState
{
    protected NebulaGame nebulaGame;
    
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Backup game
        this.nebulaGame = (NebulaGame)game;
    }

    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        Input input = gc.getInput();
        
        // Escape key
        if (input.isKeyPressed(Input.KEY_ESCAPE))
            this.escapeMinigame();
    }
    
    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
    }
    
    /**
     * Escape command
     */
    public void escapeMinigame ()
    {
        nebulaGame.enterState(0);
    }
    
    /**
     * Go to next state
     */
    public void gotoNextState ()
    {
        nebulaGame.next(this.getID());
    }
}
