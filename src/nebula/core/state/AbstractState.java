package nebula.core.state;

import nebula.core.NebulaGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Abstract main state class
 */
public abstract class AbstractState extends BasicGameState
{
    // Images path
    public static String imgPath = "ressources/images/common/";

    private static Image imgBackground;

    protected NebulaGame nebulaGame;
    private boolean defaultBackground = true;


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Nebula game
        nebulaGame = (NebulaGame)game;

        // Load background
        imgBackground = new Image(imgPath + "default-background.png");
    }


    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
    }


    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Render background
        if (defaultBackground)
            for (int x = 0; x < gc.getWidth(); x += imgBackground.getWidth())
                for (int y = 0; y < gc.getHeight(); y += imgBackground.getHeight())
                    imgBackground.draw(x, y);
    }


    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);
        gc.getInput().clearKeyPressedRecord();
    }

    /**
     * Set use default background in the state
     * @param defaultBackground true to use default background
     */
    public void setUseDefaultBackground (boolean defaultBackground)
    {
        this.defaultBackground = defaultBackground;
    }
}
