package nebula.minigame.breakout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nebula.core.NebulaGame.StateID;
import nebula.core.helper.Collision;
import nebula.minigame.Minigame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Breakout minigame
 * @author Thomas Di'Meco
 */
public class BreakoutGame extends Minigame
{
    static final String imgPath = "ressources/images/breakout/";
    static final String sndPath = "ressources/sons/breakout/";
    
    private static final float[] initialSpeed     = {0.25f, 0.35f, 0.45f};
    private static final float[] brickProbability = {0.0f,  0.2f,  0.4f };
    private static final int[]   brickRowCount    = {3,     3,     4    };
    
    private int lifes;
    private float gameCounter;
    private GameState gameState;
    private boolean useMouse;
    private Ball ball;
    private Racket racket;
    private SpeedVector ballSpeed = new SpeedVector();
    private List<Brick> bricks = new ArrayList<Brick>();
    private BricksField bricksField;
    
    private static Image imgBackground, imgLife;
    private static Sound sndBounce, sndLaunch, sndBreak, sndLose;
    
    private static Random random = new Random();
    private static enum GameState {Inactive, Active, Ingame}
    
    
    /* Game ID */
    @Override public int getID () { return StateID.Breakout.value; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Load images and sounds
        imgBackground   = new Image(imgPath + "background.png");
        imgLife         = new Image(imgPath + "ball.png");
        sndBounce       = new Sound(sndPath + "bounce.wav");
        sndLaunch       = new Sound(sndPath + "launch.wav");
        sndBreak        = new Sound(sndPath + "break.wav");
        sndLose         = new Sound(sndPath + "lose.wav");
        
        // Initial life count
        lifes = 3;
        
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
        bricksField = new BricksField(
            0, 0, gc.getWidth(), gc.getHeight()*getBrickRowCount()/12,
            getBrickRowCount(), 6);
        
        bricks.clear();
        
        for (int i = 0; i < bricksField.getRow(); i++)
        {
            for (int j = 0; j < bricksField.getColumn(); j++)
            {
                Brick brick = new Brick(i, j, bricksField);
                
                if (random.nextFloat() < getBrickProbability())
                    brick.setResistance(2);
                
                bricks.add(brick);
            }
        }
    }

    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);
        
        Input input = gc.getInput();
        
        // ==== Ingame state ====
        if (GameState.Ingame.equals(gameState))
        {
            // Move ball
            ball.setX(ball.getX()+ballSpeed.getX()*(float)delta);
            ball.setY(ball.getY()+ballSpeed.getY()*(float)delta);
            ballSpeed.increaseSpeed(delta * 0.05f);
            
            // Collision with bottom
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    0, gc.getHeight()+Ball.h/2, gc.getWidth(), 400))
                invokeDefeat();
            
            // Collision with top
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    0, -400, gc.getWidth(), 400))
            {
                ball.goPrevPosition();
                ballSpeed.invertY();
                sndBounce.play();
            }
            
            // Collision with right or left
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    gc.getWidth(), -400, 400, gc.getHeight()+800)
                ||
                Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    -400, -400, 400, gc.getHeight()+800))
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
                        b.touch();
                        if (b.isBroken()) brickToRemove.add(b);
                            
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
                    racket.getX(), racket.getY(), Racket.w, Racket.h+400))
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
        }
        // ==== Active state ====
        else if (GameState.Active.equals(gameState))
        {
            // Launch ball event
            if (racket.haveAttachedBall() &&
                ((!useMouse && input.isKeyDown(Input.KEY_SPACE)) ||
                 (useMouse && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))))
            {
                ballSpeed.setX(0.0f);
                ballSpeed.setY(getInitialSpeed());
                racket.detachBall();
                gameState = GameState.Ingame;
                sndLaunch.play();
            }
        }
        // ==== Active and ingame states ====
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
        // ==== Inactive state ====
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
        
        // ==== All states ====
        // Mouse support
        if (input.isKeyPressed(Input.KEY_M) &&
            input.isKeyDown(Input.KEY_LSHIFT))
            useMouse = !useMouse;
        
        // Victory condition
        if (bricks.isEmpty())
            gameVictory();
        // Defeat condition
        else if (lifes < 0)
            gameDefeat();
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
    
    /**
     * Reduce life and start a new game
     */
    private void invokeDefeat ()
    {
        lifes--;
        gameCounter = 1.0f;
        gameState = GameState.Inactive;
        racket.resetPosition();
        racket.attachBall(ball, getRandomRPos());
        sndLose.play();
    }
    
    /**
     * Returns a random ball position relative to the racket
     * @return The random position
     */
    private float getRandomRPos ()
    {
        return random.nextFloat() * 0.6f - 0.3f;
    }
    
    /**
     * Returns the initial ball speed depending on the difficulty
     * @return The ball speed
     */
    private float getInitialSpeed ()
    {
        if (Difficulty.Easy.equals(difficulty))
            return initialSpeed[0];
        else if (Difficulty.Hard.equals(difficulty))
            return initialSpeed[2];
        
        return initialSpeed[1];
    }
    
    /**
     * Returns the brick resistance probability depending on the difficulty
     * @return The resistance probability
     */
    private float getBrickProbability ()
    {
        if (Difficulty.Easy.equals(difficulty))
            return brickProbability[0];
        else if (Difficulty.Hard.equals(difficulty))
            return brickProbability[2];
        
        return brickProbability[1];
    }
    
    /**
     * Returns the line count of the brick field
     * @return The line count
     */
    private int getBrickRowCount ()
    {
        if (Difficulty.Easy.equals(difficulty))
            return brickRowCount[0];
        else if (Difficulty.Hard.equals(difficulty))
            return brickRowCount[2];
        
        return brickRowCount[1];
    }
}