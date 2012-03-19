package nebula.minigame.breakout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nebula.core.helper.Collision;
import nebula.core.helper.Console;

import org.newdawn.slick.*;


/**
 * Breakout minigame
 * @author Thomas Di'Meco
 */
public class Breakout extends BasicGame
{
    static final String imgPath = "assets/images/breakout/";
    static final String sndPath = "assets/sound/breakout/";
    
    private final float initialSpeed = 0.3f;
    
    private float gameCounter;
    private float whiteFadeAlpha;
    private GameState gameState;
    private SpeedVector ballSpeed = new SpeedVector();
    private Ball ball;
    private Racket racket;
    private BricksField bricksField;
    private List<Brick> bricks = new ArrayList<Brick>();
    private Image bgImage;
    
    private static Random random = new Random();
    
    private enum GameState {Inactive, Active, Ingame}
    

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
        bgImage = new Image(imgPath + "background.png");
        
        // Game state and counters
        gameCounter = 1.0f;
        whiteFadeAlpha = 0.0f;
        gameState = GameState.Inactive;
        
        // Ball and racket
        ball = new Ball();
        racket = new Racket(0, gc.getWidth(),
            gc.getHeight()+Racket.h, gc.getHeight()-Racket.h - 30);
        
        racket.attachBall(ball, getRandomRPos());
        
        // Bricks
        bricksField
            = new BricksField(0, 0, gc.getWidth(), gc.getHeight()/4, 3, 6);
        
        for (int i = 0; i < bricksField.getRow(); i++)
            for (int j = 0; j < bricksField.getColumn(); j++)
                bricks.add(new Brick(i, j, bricksField));
    }

    @Override
    public void update (GameContainer gc, int delta) throws SlickException
    {
        Input input = gc.getInput();
        
        // Ingame state
        if (GameState.Ingame.equals(gameState))
        {
            // Move ball
            ball.setX(ball.getX()+ballSpeed.getX()*(float)delta);
            ball.setY(ball.getY()+ballSpeed.getY()*(float)delta);
            ballSpeed.increaseSpeed(delta * 0.08f);

            // Collision with bottom
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    0, gc.getHeight()+Ball.h/2, gc.getWidth(), 200))
                invokeDefeat();
            
            // Collision with top
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    0, -200, gc.getWidth(), 200))
            {
                ball.goPrevPosition();
                ballSpeed.invertY();
            }
            
            // Collision with right or left
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    gc.getWidth(), 0, 200, gc.getHeight())
                ||
                Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    -200, 0, 200, gc.getHeight()))
            {
                ball.goPrevPosition();
                ballSpeed.invertX();
            }
            
            
            // Collision with bricks
            if (ball.getY() <= bricksField.getY()+bricksField.getHeight())
            {
                Brick bcol = null;
                List<Brick> brickToRemove = new ArrayList<Brick>();
                
                for (Brick b : bricks)
                {
                    if (Collision.rectangle(
                        ball.getX(), ball.getY(), Ball.w, Ball.h,
                        b.getX(), b.getY(), b.getWidth(), b.getHeight()))
                    {
                        if (bcol == null) bcol = b;
                        brickToRemove.add(b);
                    }
                }
                
                bricks.removeAll(brickToRemove);
                
                if (bcol != null)
                {
                    ball.goPrevPosition();
                    
                    if (Collision.right(ball.getX(), bcol.getX(), bcol.getWidth()) ||
                        Collision.left (ball.getX(), Ball.w, bcol.getX()))
                        ballSpeed.invertX();
                    else
                        ballSpeed.invertY();
                }
            }
            
            // Collision with racket
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    racket.getX(), racket.getY(), Racket.w, Racket.h))
            {
                ball.goPrevPosition();
                
                if (racket.getY()-ball.getY()-Ball.h < 0)
                    invokeDefeat();
                else
                {
                    float rpos = 
                        (ball.getX()-(racket.getX()+Racket.w/2-Ball.w/2)) /
                        (Racket.w/2 + Ball.w/2);
                    
                    ballSpeed.setAngle(-90.0f + 60.0f*rpos);
                }
            }
        }
        // Active state
        else if (GameState.Active.equals(gameState))
        {
            // Launch ball event
            if (input.isKeyDown(Input.KEY_SPACE) && racket.haveAttachedBall())
            {
                ballSpeed.setX(0.0f);
                ballSpeed.setY(initialSpeed);
                racket.detachBall();
                gameState = GameState.Ingame;
            }
        }
        // Active and ingame states
        if (GameState.Active.equals(gameState) ||
            GameState.Ingame.equals(gameState))
        {
            // Racket events
            if (input.isKeyDown(Input.KEY_RIGHT))
            {
                racket.goRight(Racket.hspeed * delta);
            }
            
            if (input.isKeyDown(Input.KEY_LEFT))
            {
                racket.goLeft(Racket.hspeed * delta);
            }
        }
        // Inactive state
        if (GameState.Inactive.equals(gameState))
        {
            // Decrease white fade alpha
            whiteFadeAlpha -= 0.0025f * delta;
            if (whiteFadeAlpha <= 0.0f) whiteFadeAlpha = 0.0f;
            
            racket.goUp(Racket.vspeed * delta);
            
            // Decrease game counter
            gameCounter -= 0.0018f * delta;
            if (gameCounter <= 0.0f)
            {
                gameCounter = 0.0f;
                whiteFadeAlpha = 0.0f;
                gameState = GameState.Active;
                racket.goActivePosition();
                ballSpeed.reset();
            }
        }
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
        
        // Render racket and ball
        racket.draw();
        ball.draw();
        
        // Render white fade
        if (whiteFadeAlpha > 0.0f)
        {
            g.setColor(new Color(1.0f, 1.0f, 1.0f, whiteFadeAlpha));
            g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        }
    }
    
    private void invokeDefeat ()
    {
        gameState = GameState.Inactive;
        gameCounter = 1.0f;
        whiteFadeAlpha = 1.0f;
        racket.resetPosition();
        racket.attachBall(ball, getRandomRPos());
        Console.log("You failed");
    }
    
    private float getRandomRPos ()
    {
        return random.nextFloat() * 0.6f - 0.3f;
    }

    public static void main (String[] args) throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new Breakout());
        app.setDisplayMode(800, 600, true);
        app.setTargetFrameRate(2000);
        app.start();
    }
}
