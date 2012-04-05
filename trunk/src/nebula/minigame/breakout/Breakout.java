package nebula.minigame.breakout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nebula.core.NebulaGame;
import nebula.core.helper.Collision;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Breakout minigame
 * @author Thomas Di'Meco
 */
public class Breakout extends BasicGameState
{
    static final String imgPath = "ressources/images/breakout/";
    static final String sndPath = "ressources/sons/breakout/";
    
    private final float initialSpeed = 0.25f;
    private int lifes = 3;
    
    private float gameCounter;
    private GameState gameState;
    private boolean useMouse;
    private SpeedVector ballSpeed = new SpeedVector();
    private Ball ball;
    private Racket racket;
    private BricksField bricksField;
    private List<Brick> bricks = new ArrayList<Brick>();
    private Image imgBackground, imgLife;
    private Sound sndBounce, sndLaunch, sndBreak, sndLose;
    
    private static Random random = new Random();
    private enum GameState {Inactive, Active, Ingame}
    
    
    /* Game ID */
    @Override public int getID () { return 1; }

    @Override
    public void init (GameContainer gc, StateBasedGame game) throws SlickException
    {
        // Load images and sounds
        imgBackground   = new Image(imgPath + "background.png");
        imgLife = new Image(imgPath + "ball.png");
        sndBounce = new Sound(sndPath + "bounce.ogg");
        sndLaunch = new Sound(sndPath + "launch.ogg");
        sndBreak  = new Sound(sndPath + "break.ogg");
        sndLose   = new Sound(sndPath + "lose.ogg");
        
        // Game state and counters
        gameCounter = 1.0f;
        gameState = GameState.Inactive;
        useMouse = false;
        
        // Ball and racket
        ball = new Ball();
        racket = new Racket(0, gc.getWidth(),
            gc.getHeight()+Racket.h+Ball.h, gc.getHeight()-Racket.h - 30);
        
        racket.attachBall(ball, getRandomRPos());
        
        // Bricks
        bricksField
            = new BricksField(0, 0, gc.getWidth(), gc.getHeight()/4, 3, 6);
        
        for (int i = 0; i < bricksField.getRow(); i++)
            for (int j = 0; j < bricksField.getColumn(); j++)
                bricks.add(new Brick(i, j, bricksField));
    }

    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        Input input = gc.getInput();
        
        // Ingame state
        if (GameState.Ingame.equals(gameState))
        {
            // Move ball
            ball.setX(ball.getX()+ballSpeed.getX()*(float)delta);
            ball.setY(ball.getY()+ballSpeed.getY()*(float)delta);
            ballSpeed.increaseSpeed(delta * 0.04f);
            
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
                sndBounce.play();
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
                sndBounce.play();
            }
            
            
            // Collision with bricks
            if (ball.getY() <= bricksField.getY() + bricksField.getHeight())
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
                    
                    sndBreak.play();
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
                    sndLaunch.play();
                }
            }
            
            // Victory condition
            if (bricks.isEmpty())
                ((NebulaGame)game).next(this.getID());
        }
        // Active state
        else if (GameState.Active.equals(gameState))
        {
            // Launch ball event
            if (racket.haveAttachedBall() &&
                ((!useMouse && input.isKeyDown(Input.KEY_SPACE)) ||
                 (useMouse && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))))
            {
                ballSpeed.setX(0.0f);
                ballSpeed.setY(initialSpeed);
                racket.detachBall();
                gameState = GameState.Ingame;
                sndLaunch.play();
            }
        }
        // Active and ingame states
        if (GameState.Active.equals(gameState) ||
            GameState.Ingame.equals(gameState))
        {
            // Racket events
            if (input.isKeyDown(Input.KEY_RIGHT))
                racket.goRight(Racket.hspeed * delta);
            
            if (input.isKeyDown(Input.KEY_LEFT))
                racket.goLeft(Racket.hspeed * delta);
            
            if (useMouse) racket.setX(input.getAbsoluteMouseX()-Racket.w/2);
        }
        // Inactive state
        if (GameState.Inactive.equals(gameState))
        {
            racket.goUp(Racket.vspeed * delta);
            
            // Decrease game counter
            gameCounter -= 0.0018f * delta;
            if (gameCounter <= 0.0f)
            {
                gameCounter = 0.0f;
                gameState = GameState.Active;
                racket.goActivePosition();
                ballSpeed.reset();
            }
        }
        
        // All states
        if (input.isKeyPressed(Input.KEY_M))
            useMouse = !useMouse;
    }

    @Override
    public void render (GameContainer gc,  StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Render background
        for (int x = 0; x < gc.getWidth(); x += imgBackground.getWidth())
            for (int y = 0; y < gc.getHeight(); y += imgBackground.getHeight())
                imgBackground.draw(x, y);
        
        // Render lifes
        final float lifeImageSize = 20.0f;
        for (int i = 0; i < lifes; i++)
            imgLife.draw(4.0f+i*(4.0f+lifeImageSize),
                           (gc.getHeight()-lifeImageSize-4.0f),
                           lifeImageSize, lifeImageSize);
        
        // Render bricks
        for (Brick b : bricks) b.draw();
        
        // Render racket and ball
        racket.draw();
        ball.draw();
    }
    
    private void invokeDefeat ()
    {
        gameState = GameState.Inactive;
        gameCounter = 1.0f;
        lifes--;
        racket.resetPosition();
        racket.attachBall(ball, getRandomRPos());
        sndLose.play();
    }
    
    private float getRandomRPos ()
    {
        return random.nextFloat() * 0.6f - 0.3f;
    }
}
