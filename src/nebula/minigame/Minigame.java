package nebula.minigame;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.state.StateScore;

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
    /* Minigame difficulties */
    public static enum Difficulty {Easy, Medium, Hard}
    
    protected NebulaGame nebulaGame;
    protected Difficulty difficulty;
    protected int score;
    
    
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Backup game
        this.nebulaGame = (NebulaGame)game;
        
        // Reset minigame score
        score = 0;
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
            this.gameVictory();
    }
    
    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
    }
    
    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException 
    {
    }
    
    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException 
    {
        this.init(gc, game);
    }
    
    /**
     * Escape command
     */
    protected void escapeMinigame ()
    {
        nebulaGame.enterState(NebulaState.MainMenu.id);
    }
    
    /**
     * Go to next state
     */
    protected void gotoNextState ()
    {
        nebulaGame.next(this.getID());
    }
    
    /**
     * Invoke the game victory
     */
    protected void gameVictory ()
    {
        ((StateScore)nebulaGame.getState(NebulaState.Score.id)).setMessage("Bravo ! ");
        nebulaGame.showScore(getID(), score);
    }
    
    /**
     * Invoke the game defeat
     */
    protected void gameDefeat ()
    {
        ((StateScore)nebulaGame.getState(NebulaState.Score.id)).setMessage("Perdu ! ");
        nebulaGame.showScore(getID(), score);
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
     * Get the minigame difficulty
     * @return The difficulty
     */
    public Difficulty getDifficulty ()
    {
        return difficulty;
    }
    
    /**
     * Get the minigame score
     * @return The score
     */
    public int getScore ()
    {
        return score;
    }
}
