package nebula.core.state;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Custom base transition class
 */
public abstract class AbstractTransitionState extends BasicGameState
{
    protected NebulaGame nebulaGame;
    
    private String text;
    private float time = Float.NEGATIVE_INFINITY;
    private float timeBeforeText = 100.0f;
    private TransitionType transitionType = TransitionType.None;
    private Image image, face, frame;
    private Sound voice;
    private boolean voicePlayed = false;
    private Rectangle frameRect = new Rectangle(0.0f, 0.0f, 0.0f, 0.0f);
    private float screenw, screenh;
    private Font font;
    
    private static final float FRAME_MARGIN = 24.0f;
    private static final float FRAME_HEIGHT = 300.0f;
    private static final float FACE_WIDTH = 250.0f;
    
    
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Backup game
        this.nebulaGame = (NebulaGame)game;
        
        // Screen size
        screenw = gc.getWidth();
        screenh = gc.getHeight();
        
        // Font
        font = NebulaFont.getFont(FontName.Batmfa, FontSize.Medium);
    }

    
    @Override
    public final void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        Input input = gc.getInput();
        
        // Next state key
        if(input.isKeyDown(Input.KEY_ENTER))
        {
            if (voice != null) voice.stop();
            gotoNextState();
        }
        
        // Escape key
        if(input.isKeyDown(Input.KEY_ESCAPE))
        {
            if (voice != null) voice.stop();
            nebulaGame.enterState(0);
        }
        
        // Text time counter
        if (timeBeforeText > 0) timeBeforeText -= delta;
        else timeBeforeText = 0.0f;
        
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
        
        // Render text and frame
        if (text != null && frame != null && timeBeforeText == 0.0f)
        {
            frame.draw(
                frameRect.getX(), frameRect.getY(),
                frameRect.getWidth(), frameRect.getHeight());
            
            font.drawString(
                frameRect.getX() + 64.0f, frameRect.getY() + 32.0f,
                text.toString());
            
            // Face
            if (face != null)
            {
                face.draw(
                    FRAME_MARGIN,
                    screenh - FRAME_MARGIN - FRAME_HEIGHT,
                    FACE_WIDTH,
                    FRAME_HEIGHT
                );
            }
        }
        
        // Sound
        if (!voicePlayed && voice != null)
        {
            voicePlayed = true;
            voice.play();
        }
    }
    
    
    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException 
    {
        this.init(gc, game);
    }
    
    
    /**
     * Goto next state
     */
    protected void gotoNextState ()
    {
        nebulaGame.enterState(this.getID()+1, transitionType);
    }
    
    
    /**
     * Create frame rectangle
     */
    private void createFrameRect ()
    {
        if (face == null)
        {
            frameRect.setBounds(
                FRAME_MARGIN,
                screenh - FRAME_MARGIN - FRAME_HEIGHT,
                screenw - 2*FRAME_MARGIN,
                FRAME_HEIGHT
            );
        }
        else
        {
            frameRect.setBounds(
                FACE_WIDTH + 2*FRAME_MARGIN,
                screenh - FRAME_MARGIN - FRAME_HEIGHT,
                screenw - 3*FRAME_MARGIN - FACE_WIDTH,
                FRAME_HEIGHT
            );
        }
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
     * Set the transition time before text
     * @param time Time in milliseconds
     */
    protected void setTransitionTimeBeforeText (float time)
    {
        this.timeBeforeText = time;
    }
    
    
    /**
     * Set the transition text
     * @param text Transition text
     */
    protected void setTransitionText (String text)
    {
        this.text = text;
        
        try {
            frame = new Image("ressources/images/miscellaneous/cadre.png");
        }
        catch (SlickException exc) {
            exc.printStackTrace();
        }
        
        createFrameRect();
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
     * Set the transition face character
     * @param path The image path
     */
    protected void setTransitionFace (String path)
    {
        try {
            face = new Image(path);
        }
        catch (SlickException exc) {
            exc.printStackTrace();
        }
        
        createFrameRect();
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
