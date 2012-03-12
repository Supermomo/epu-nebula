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
        
        BricksField field
            = new BricksField(0, 0, gc.getWidth(), gc.getHeight()/4, 3, 6);
        
        for (int i = 0; i < field.getRow(); i++)
            for (int j = 0; j < field.getColumn(); j++)
                bricks.add(new Brick(i, j, field));
    }

    @Override
    public void update (GameContainer gc, int delta) throws SlickException
    {
        
    }

    @Override
    public void render (GameContainer gc, Graphics g) throws SlickException
    {
        // Render background
        for (int x = 0; x < gc.getWidth(); x += bgImage.getWidth())
            for (int y = 0; y < gc.getHeight(); y += bgImage.getHeight())
                bgImage.draw(x, y);
        
        // Render bricks
        for (Brick b : bricks) b.draw();
    }

    public static void main (String[] args) throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new Breakout());
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(200);
        app.start();
    }
}
