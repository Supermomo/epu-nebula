package nebula.core.state;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.minigame.Minigame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Custom abstract transition class
 */
public abstract class AbstractTransitionState extends BasicGameState
{
    protected NebulaGame nebulaGame;
    
    private float time = Float.NEGATIVE_INFINITY;
    private TransitionType transitionType = TransitionType.None;
    private Image image;
    private Sound voice;
    
    
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Backup game
        this.nebulaGame = (NebulaGame)game;
    }

    
    @Override
    public final void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        Input input = gc.getInput();
        
        // Next state key
        if (input.isKeyDown(Input.KEY_ENTER) ||
            input.isKeyDown(Input.KEY_ESCAPE))
            gotoNextState();
        
        // Transition time counter if needed
        if (time != Float.NEGATIVE_INFINITY)
        {
            if (time > 0) time -= delta;
            else gotoNextState();
        }
    }
    
    
    @Override
    public final void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Render image
        if (image != null)
            image.draw(0, 0, gc.getWidth(), gc.getHeight());
    }
    
    
    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);
        gc.getInput().clearKeyPressedRecord();
        
        if (voice != null) voice.play();
    }
    
    
    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException 
    {
        if (voice != null) voice.stop();
    }
    
    
    /**
     * Goto next state
     */
    protected void gotoNextState ()
    {
        GameState next = nebulaGame.getState(this.getID()+1);
        
        if (next == null)
            nebulaGame.enterState(NebulaState.MainMenu.id);
        else if (next instanceof Minigame)
            nebulaGame.initAndEnterState(this.getID()+1, transitionType);
        else
            nebulaGame.enterState(this.getID()+1, transitionType);
    }
    
    
    /**
     * Set the transition duration
     * @param time Time in milliseconds
     */
    protected void setTransitionTime (float time)
    {
        this.time = time;
    }
    
    
    /**
     * Set the transition image
     * @param path The image path
     */
    protected void setTransitionImage (String path)
    {
        try {
            image = new Image(path);
        }
        catch (SlickException exc) {
            exc.printStackTrace();
        }
    }
    
    
    /**
     * Set the transition voice
     * @param path The sound path
     */
    protected void setTransitionVoice (String path)
    {
        try {
            voice = new Sound(path);
        }
        catch (SlickException exc) {
            exc.printStackTrace();
        }        
    }
    
    
    /**
     * Set transition end type
     * @param transitionType The transition type
     */
    protected void setTransitionType (TransitionType transitionType)
    {
        this.transitionType = transitionType;
    }
}
