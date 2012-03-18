package nebula.minigame.breakout;

import java.util.ArrayList;
import java.util.List;

import nebula.core.helper.Collision;

import org.newdawn.slick.*;


/**
 * Breakout minigame
 * @author Thomas Di'Meco
 */
public class Breakout extends BasicGame
{
    static final String imgPath = "assets/images/breakout/";
    static final String sndPath = "assets/sound/breakout/";
    
    private float gameActiveCounter;
    private float whiteFadeAlpha;
    private float[] ballSpeed = new float[2];
    private Ball ball;
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
        bgImage = new Image(imgPath + "background.png");
        
        gameActiveCounter = 1.0f;
        whiteFadeAlpha = 0.0f;
        
        // Ball and racket
        ball = new Ball();
        racket = new Racket(0, gc.getWidth(),
            gc.getHeight()+Racket.h, gc.getHeight()-Racket.h - 30);
        
        racket.attachBall(ball);
        
        // Bricks
        BricksField field
            = new BricksField(0, 0, gc.getWidth(), gc.getHeight()/4, 3, 6);
        
        for (int i = 0; i < field.getRow(); i++)
            for (int j = 0; j < field.getColumn(); j++)
                bricks.add(new Brick(i, j, field));
    }

    @Override
    public void update (GameContainer gc, int delta) throws SlickException
    {
        // Active game
        if (gameActiveCounter <= 0.0f)
        {   
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
            
            if (input.isKeyDown(Input.KEY_SPACE) && racket.haveAttachedBall())
            {
                ballSpeed[0] = 0.0f;
                ballSpeed[1] = -0.8f;
                racket.detachBall();
            }
            
            // Speed ball
            ball.setX(ball.getX()+ballSpeed[0]*(float)delta*0.5f);
            ball.setY(ball.getY()+ballSpeed[1]*(float)delta*0.5f);
            //ballSpeed[0] *= 1.0001f*(float)delta*0.1f;
            //ballSpeed[1] *= 1.0001f*(float)delta*0.1f;
            
            // Collision with bricks
            for (Brick b : bricks)
            {
                if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    b.getX(), b.getY(), b.getWidth(), b.getHeight()))
                {
                    bricks.remove(b);
                    ballSpeed[1] = -ballSpeed[1];
                    break;
                }
            }
            
            // Collision with racket
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    racket.getX(), racket.getY(), Racket.w, Racket.h))
            {
                if (racket.getY()-ball.getY()-Ball.h+4 < 0)
                    invokeDefeat();
                else
                    ballSpeed[1] = -ballSpeed[1];
                
            }
            
            // Collision with bottom
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    0, gc.getHeight()-10, gc.getWidth(), 10))
                invokeDefeat();
            
            // Collision with top
            if (Collision.rectangle(
                    ball.getX(), ball.getY(), Ball.w, Ball.h,
                    0, 0, gc.getWidth(), 0))
                ballSpeed[1] = -ballSpeed[1];
        }
        // Inactive game
        else
        {   
            // Decrease white fade alpha
            whiteFadeAlpha -= 0.0025f * delta;
            if (whiteFadeAlpha <= 0.0f) whiteFadeAlpha = 0.0f;
            
            racket.goUp(Racket.vspeed * delta);
            
            // Decrease game active counter
            gameActiveCounter -= 0.0018f * delta;
            if (gameActiveCounter <= 0.0f)
            {
                gameActiveCounter = 0.0f;
                whiteFadeAlpha = 0.0f;
                racket.goActivePosition();
                ballSpeed[0] = 0.0f;
                ballSpeed[1] = 0.0f;
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
        
        // Render racket and ball
        racket.draw();
        ball.draw();
        
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
        racket.attachBall(ball);
    }

    public static void main (String[] args) throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new Breakout());
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(2000);
        app.start();
    }
}
