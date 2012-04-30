package nebula.minigame.asteroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;
import nebula.core.state.AbstractMinigameState;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Asteroid minigame
 * @author Thomas Di'Meco
 */
public class AsteroidGame extends AbstractMinigameState
{
    static final String imgPath = "ressources/images/asteroid/";
    static final String sndPath = "ressources/sons/asteroid/";
    
    private static final float asteroidSpeed[]       = {0.04f,   0.05f,   0.06f  , 0.14f  };
    private static final float asteroidProbability[] = {0.0004f, 0.0008f, 0.0012f, 0.0025f};
    
    private GameState gameState;
    private int time;
    private int lifes;
    private Rectangle limits;
    private Saucer saucer;
    private List<Asteroid> asteroids;
    
    private static Image imgLife;
    private static Font font;
    private static enum GameState {Active}
    
    static Random random = new Random();
    
    
    /* Game ID */
    @Override public int getID () { return NebulaState.Asteroid.id; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Load images and sounds
        imgLife = new Image(imgPath + "heart.png");
        
        // Initial life count and time
        lifes = 3;
        time = 90 * 1000;
        
        // Initializations
        limits = new Rectangle(0, 0, gc.getWidth(), gc.getHeight());
        asteroids = new ArrayList<Asteroid>();
        gameState = GameState.Active;
        saucer = new Saucer(limits);
        
        // Font
        font = NebulaFont.getFont(FontName.Batmfa, FontSize.Large);
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
            
            // Create asteroid
            if (random.nextFloat() < getAsteroidProbability() * delta)
                createAsteroid();
            
            // For each asteroid
            Asteroid asteroidTouched = null;
            
            for (Asteroid a : asteroids)
            {   
                // Collisions
                if (a.getCollideZone().intersects(saucer.getCollideZone()))
                {
                    lifes--;
                    asteroidTouched = a;
                }
                
                // Move
                a.step(delta);
            }
            
            // Destrooy touched asteroid
            if (asteroidTouched != null)
                asteroids.remove(asteroidTouched);
            
            // Decrease time
            time -= delta;
        }
        
        // ==== All states ====
        // Victory
        if (time < 750)
            gameVictory();
        else if (lifes < 0)
            gameDefeat();
    }

    @Override
    public void render (GameContainer gc,  StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);
        
        // Render asteroids
        for (Asteroid a : asteroids)
            a.draw();
        
        // Render saucer
        saucer.draw();
        
        // Render lifes
        final float lifeImageSize = 24.0f;
        for (int i = 0; i < lifes; i++)
            imgLife.draw(4.0f+i*(4.0f+lifeImageSize),
                           (gc.getHeight()-lifeImageSize-4.0f),
                           lifeImageSize, lifeImageSize);
        
        // Render time counter
        String timeStr =
            Integer.toString(time / (60 * 1000)) +
            " : " +
            (time % (60 * 1000) < 10 * 1000 ? "0" : "") +
            Integer.toString((time % (60 * 1000))/1000);
        
        font.drawString(
            gc.getWidth()/2 - font.getWidth(timeStr)/2,
            24.0f, timeStr, Color.white);
        
        // Render score
        renderScore(gc, ScorePosition.BottomRight);
    }
    
    /**
     * Create a new asteroid
     */
    private void createAsteroid () throws SlickException
    {
        asteroids.add(new Asteroid(limits, getAsteroidSpeed()));
    }
    
    /**
     * Returns the speed of astereroids
     * @return The speed of astereroids
     */
    private float getAsteroidSpeed ()
    {
        if (Difficulty.Easy.equals(difficulty))
            return asteroidSpeed[0];
        else if (Difficulty.Hard.equals(difficulty))
            return asteroidSpeed[2];
        else if (Difficulty.Insane.equals(difficulty))
            return asteroidSpeed[3];
        
        return asteroidSpeed[1];
    }
    
    /**
     * Returns the probability of astereroids
     * @return The probability of astereroids
     */
    private float getAsteroidProbability ()
    {
        if (Difficulty.Easy.equals(difficulty))
            return asteroidProbability[0];
        else if (Difficulty.Hard.equals(difficulty))
            return asteroidProbability[2];
        else if (Difficulty.Insane.equals(difficulty))
            return asteroidProbability[3];
        
        return asteroidProbability[1];
    }
}
