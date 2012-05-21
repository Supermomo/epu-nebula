package nebula.minigame.asteroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;
import nebula.core.helper.Utils;
import nebula.core.state.AbstractMinigameState;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
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

    private static final float objectsSpeed[]        = {0.04f,   0.05f,   0.06f  , 0.14f  };
    private static final float asteroidProbability[] = {0.0004f, 0.0008f, 0.0012f, 0.0025f};
    private static final int initialTime = 90 * 1000;

    private GameState gameState;
    private int time;
    private int invincibility;
    private int lifes;
    private int crystalTimer;
    private float xExplosion, yExplosion;
    private Rectangle limits;
    private Saucer saucer;
    private List<SpaceObject> spaceObjects;

    private static Image imgLife;
    private static Sound sndExplosion, sndInvincibility, sndCrystal;
    private static Animation animExplosion;
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

        // Initial properties
        lifes = 3;
        score = lifes * 200 * (difficulty.ordinal() + 1);
        time = initialTime;
        crystalTimer = 10 * 1000;
        invincibility = 0;

        // Initializations
        limits = new Rectangle(0, 0, gc.getWidth(), gc.getHeight());
        spaceObjects = new ArrayList<SpaceObject>();
        gameState = GameState.Active;
        saucer = new Saucer(limits);

        // Load images and sounds
        imgLife          = new Image(imgPath + "heart.png");
        sndExplosion     = new Sound(sndPath + "explosion.wav");
        sndInvincibility = new Sound(sndPath + "invincibility.wav");
        sndCrystal       = new Sound(sndPath + "crystal.wav");

        animExplosion = new Animation(
            new SpriteSheet(AsteroidGame.imgPath + "explosion.png", 160, 160),
            64
        );

        animExplosion.setAutoUpdate(true);
        animExplosion.setLooping(false);
        animExplosion.stopAt(12);
        animExplosion.stop();
        xExplosion = -animExplosion.getWidth();
        yExplosion = -animExplosion.getHeight();

        // Font
        font = NebulaFont.getFont(FontName.Batmfa, FontSize.Large);

        // Music
        initMusic(sndPath + "music.ogg", 0.3f, true);
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
                createObject(false);

            // Create crystal
            if (initialTime - time >= crystalTimer)
            {
                crystalTimer += 20 * 1000;
                createObject(true);
            }

            // For each object
            SpaceObject objectTouched = null;

            for (SpaceObject obj : spaceObjects)
            {
                // Collisions
                if (obj.getCollideZone().intersects(saucer.getCollideZone()))
                    objectTouched = obj;

                // Move
                obj.step(delta);
            }

            // Object touched
            if (objectTouched != null)
            {
                if (objectTouched.isCrystal())
                {
                    spaceObjects.remove(objectTouched);
                    score += 100 * (difficulty.ordinal() + 3);
                    sndCrystal.play();
                }
                else if (invincibility <= 0)
                {
                    spaceObjects.remove(objectTouched);

                    // Asteroid touched
                    lifes--;
                    score -= 200 * (difficulty.ordinal() + 1);
                    invincibility = 2 * 1000;
                    xExplosion = objectTouched.getCenterX() - animExplosion.getWidth()/2;
                    yExplosion = objectTouched.getCenterY() - animExplosion.getHeight()/2;
                    animExplosion.restart();
                    sndExplosion.play();
                }
            }

            // Decrease time
            time -= delta;

            // Decrease invincibility
            if (invincibility > 0)
            {
                invincibility -= delta;

                if (invincibility <= 0)
                    sndInvincibility.play();
            }
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
        for (SpaceObject obj : spaceObjects)
            obj.draw();

        // Render saucer
        saucer.draw(invincibility > 0);

        // Render explosion
        g.drawAnimation(animExplosion, xExplosion, yExplosion);

        // Render lifes
        final float lifeImageSize = 24.0f;
        for (int i = 0; i < lifes; i++)
            imgLife.draw(4.0f+i*(4.0f+lifeImageSize),
                           (gc.getHeight()-lifeImageSize-4.0f),
                           lifeImageSize, lifeImageSize);

        // Render time counter
        String timeStr = Utils.secondsToString(time/1000);

        font.drawString(
            gc.getWidth()/2 - font.getWidth(timeStr)/2,
            24.0f, timeStr, Color.white);

        // Render score
        //renderScore(gc, ScorePosition.BottomRight);
    }

    /**
     * Create a new object
     */
    private void createObject (boolean isCrystal) throws SlickException
    {
        spaceObjects.add(new SpaceObject(isCrystal, limits, getObjectsSpeed()));
    }

    /**
     * Returns the speed of objects
     * @return The speed of objects
     */
    private float getObjectsSpeed ()
    {
        if (Difficulty.Easy.equals(difficulty))
            return objectsSpeed[0];
        else if (Difficulty.Hard.equals(difficulty))
            return objectsSpeed[2];
        else if (Difficulty.Insane.equals(difficulty))
            return objectsSpeed[3];

        return objectsSpeed[1];
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
