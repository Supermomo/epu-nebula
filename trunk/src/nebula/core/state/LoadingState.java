package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Loading state class
 */
public class LoadingState extends AbstractNebulaState
{
    // Images path
    public static String imgPath = "ressources/images/common/";
    
    // Loading state
    private enum LoadState {Start, Load, Finish}
    
    private Image imgLoading;
    private LoadState loadState;
    

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);
        
        loadState = LoadState.Start;
        setUseDefaultBackground(false);
        
        // Load images
        imgLoading = new Image(imgPath + "loading.png");
    }
    
    
    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);
        
        if (LoadState.Start.equals(loadState))
            loadState = LoadState.Load;
        else if (LoadState.Load.equals(loadState))
        {
            nebulaGame.loadGame();
            loadState = LoadState.Finish;
        }
    }

    
    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);
        
        // Render loading image
        imgLoading.drawCentered(gc.getWidth()/2, gc.getHeight()/2);
    }

    @Override public int getID () { return NebulaState.Loading.id; }
}
