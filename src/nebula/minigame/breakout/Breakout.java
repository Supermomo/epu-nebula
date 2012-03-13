package nebula.minigame.breakout;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.*;


/**
 * Breakout minigame
 * @author Thomas Di'Meco
 */
public class Breakout extends BasicGame
{
    static final String rccPath = "assets/breakout/";
    
    private float gameActiveCounter;
    private float whiteFadeAlpha;
    private Racket racket;
    private List<Brick> bricks = new ArrayList<Brick>();
    private Image bgImage;
    

    /**
     * Constructor
     */
    public Breakout ()
    {
        super("Breakout");
    }

    @Override
    public void init (GameContainer gc) throws SlickException
    {
        // Load images
        bgImage = new Image(rccPath + "background.png");
        
        gameActiveCounter = 1.0f;
        whiteFadeAlpha = 0.0f;
        
        racket = new Racket(0, gc.getWidth(),
            gc.getHeight()+Racket.h, gc.getHeight()-Racket.h - 30);
        
        BricksField field
            = new BricksField(0, 0, gc.getWidth(), gc.getHeight()/4, 3, 6);
        
        for (int i = 0; i < field.getRow(); i++)
            for (int j = 0; j < field.getColumn(); j++)
                bricks.add(new Brick(i, j, field));
    }

    @Override
    public void update (GameContainer gc, int delta) throws SlickException
    {
        if (gameActiveCounter <= 0.0f)
        {
            whiteFadeAlpha = 0.0f;
            
            // Input events
            Input input = gc.getInput();
    
            if (input.isKeyDown(Input.KEY_RIGHT))
            {
                racket.goRight(Racket.hspeed * delta);
            }
            
            if (input.isKeyDown(Input.KEY_LEFT))
            {
                racket.goLeft(Racket.hspeed * delta);
            }
            
            if (input.isKeyDown(Input.KEY_SPACE))
            {
                invokeDefeat();
            }
        }
        else
        {
            // Decrease game active counter
            gameActiveCounter -= 0.0018f * delta;
            if (gameActiveCounter <= 0.0f) gameActiveCounter = 0.0f;
            
            // Decrease white fade alpha
            whiteFadeAlpha -= 0.0025f * delta;
            if (whiteFadeAlpha <= 0.0f) whiteFadeAlpha = 0.0f;
            
            racket.goUp(Racket.vspeed * delta);
        }
    }

    @Override
    public void render (GameContainer gc, Graphics g) throws SlickException
    {
        // Render background
        for (int x = 0; x < gc.getWidth(); x += bgImage.getWidth())
            for (int y = 0; y < gc.getHeight(); y += bgImage.getHeight())
                bgImage.draw(x, y);
        
        // Render racket
        racket.draw();
        
        // Render bricks
        for (Brick b : bricks) b.draw();
        
        // Render white fade
        if (whiteFadeAlpha > 0.0f)
        {
            g.setColor(new Color(1.0f, 1.0f, 1.0f, whiteFadeAlpha));
            g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        }
    }
    
    private void invokeDefeat ()
    {
        gameActiveCounter = 1.0f;
        whiteFadeAlpha = 1.0f;
        racket.resetPosition();
    }

    public static void main (String[] args) throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new Breakout());
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(200);
        app.start();
    }
}
