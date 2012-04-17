package nebula.minigame.asteroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nebula.core.NebulaGame.StateID;
import nebula.minigame.Minigame;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Asteroid minigame
 * @author Thomas Di'Meco
 */
public class AsteroidGame extends Minigame
{
    static final String imgPath = "ressources/images/asteroid/";
    static final String sndPath = "ressources/sons/asteroid/";
    
    private GameState gameState;
    private int lifes;
    private Rectangle limits;
    private Saucer saucer;
    private List<Asteroid> asteroids;
    
    private static Image imgBackground, imgLife;
    
    private static Random random = new Random();
    private static enum GameState {Active, Destroying}
    
    
    /* Game ID */
    @Override public int getID () { return StateID.Asteroid.value; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);
        
        // Load images and sounds
        imgBackground   = new Image(imgPath + "background.png");
        imgLife         = new Image(imgPath + "heart.png");
        
        // Initial life count
        lifes = 3;
        
        // Initialisations
        limits = new Rectangle(0, 0, gc.getWidth(), gc.getHeight());
        asteroids = new ArrayList<Asteroid>();
        gameState = GameState.Active;
        saucer = new Saucer(limits);
        
        asteroids.add(new Asteroid(limits));
    }

    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);
        
        Input input = gc.getInput();
        
        // ==== Active state ====
        if (GameState.Active.equals(gameState))
        {
            // Saucer events
            if (input.isKeyDown(Input.KEY_RIGHT))
                saucer.goRight(Saucer.speed * delta);
            
            if (input.isKeyDown(Input.KEY_LEFT))
                saucer.goLeft(Saucer.speed * delta);
            
            if (input.isKeyDown(Input.KEY_UP))
                saucer.goUp(Saucer.speed * delta);
            
            if (input.isKeyDown(Input.KEY_DOWN))
                saucer.goDown(Saucer.speed * delta);
            
            // Collisions
            for (Asteroid a : asteroids)
                if (a.getCollideZone().intersects(saucer.getCollideZone()))
                    invokeDefeat();
        }
        // ==== Inactive state ====
        else if (GameState.Destroying.equals(gameState))
        {
            
        }
        
        // ==== All states ====
        // Victory & Defeat
    }

    @Override
    public void render (GameContainer gc,  StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);
        
        // Render background
        for (int x = 0; x < gc.getWidth(); x += imgBackground.getWidth())
            for (int y = 0; y < gc.getHeight(); y += imgBackground.getHeight())
                imgBackground.draw(x, y);
        
        // Asteroids
        for (Asteroid a : asteroids) a.draw();
        
        // Saucer
        saucer.draw();
        
        // Render lifes
        final float lifeImageSize = 24.0f;
        for (int i = 0; i < lifes; i++)
            imgLife.draw(4.0f+i*(4.0f+lifeImageSize),
                           (gc.getHeight()-lifeImageSize-4.0f),
                           lifeImageSize, lifeImageSize);
    }
    
    /**
     * Reduce life and start a new try
     */
    private void invokeDefeat ()
    {
        lifes--;
        gameState = GameState.Destroying;
        saucer.resetPosition();
    }
}
