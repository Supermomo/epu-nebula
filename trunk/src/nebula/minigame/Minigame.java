package nebula.minigame;

import nebula.core.NebulaGame;

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
    /* Minigame difficulty */
    public static enum Difficulty {Easy, Medium, Hard}
    
    protected NebulaGame nebulaGame;
    protected Difficulty difficulty;
    
    
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
        
        // Debug victory key
        if (input.isKeyPressed(Input.KEY_W) &&
            input.isKeyDown(Input.KEY_LSHIFT))
            this.gotoNextState();
    }
    
    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException 
    {
    	this.init(container, game);
    }
    
    /**
     * Set the minigame difficulty
     * @param difficulty The difficulty
     */
    public void setDifficulty (Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }
    
    /**
     * Escape command
     */
    protected void escapeMinigame ()
    {
        nebulaGame.enterState(0);
    }
    
    /**
     * Go to next state
     */
    protected void gotoNextState ()
    {
        nebulaGame.next(this.getID());
    }
}
