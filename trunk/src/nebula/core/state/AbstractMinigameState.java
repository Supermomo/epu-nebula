package nebula.core.state;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.config.NebulaConfig;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Basic minigame state
 */
public abstract class AbstractMinigameState extends AbstractState
{
    /* Score display offset */
    private static float SCORE_OFFSET = 8.0f;

    /* Score positions */
    public static enum ScorePosition
        {TopLeft, TopCenter, TopRight, BottomLeft, BottomCenter, BottomRight}

    /* Minigame difficulties */
    public static enum Difficulty {Easy, Medium, Hard, Insane}

    protected Difficulty difficulty;
    protected int score;

    private Font font;

    // Music
    private Music music;
    private float musicVolume;
    private boolean musicLoop;
    private int musicState;


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Reset minigame score
        score = 0;

        // Set the minigame difficulty
        if (NebulaGame.isAdventureMode)
            difficulty = NebulaConfig.getAdventureDifficulty();
        else
            difficulty = NebulaConfig.getRapidmodeDifficulty();

        // Load font
        font = NebulaFont.getFont(FontName.Batmfa, FontSize.Small);

        // Reset musics
        music = null;
        musicState = 0;
    }

    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);

        Input input = gc.getInput();

        // Escape key
        if (input.isKeyPressed(Input.KEY_ESCAPE))
            this.escapeMinigame();

        // Debug victory key
        if (input.isKeyPressed(Input.KEY_W) &&
            input.isKeyDown(Input.KEY_LSHIFT))
            this.gameVictory();
    }

    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);
    }

    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);

        // Play music
        if (music != null)
        {
            if (musicState == 0)
            {
                if (musicLoop) music.loop(1.0f, musicVolume);
                else           music.play(1.0f, musicVolume);

                musicState = 1;
            }
            else if (musicState == 1)
                music.resume();
        }
    }

    @Override
    public void leave (GameContainer container, StateBasedGame game)
        throws SlickException
    {
        super.leave(container, game);

        // Pause music
        if (music != null)
        {
            if (music.playing())
                music.pause();
            else
                musicState = 2;
        }
    }

    /**
     * Render the score
     */
    public void renderScore (GameContainer gc, ScorePosition scorePosition)
        throws SlickException
    {
        // Render score
        String scoreText = Integer.toString(score);
        float scoreWidth = font.getWidth(scoreText);
        float scoreHeight = font.getHeight(scoreText);
        float x = 0.0f, y = 0.0f;

        if (ScorePosition.TopLeft.equals(scorePosition))
        {
            x = SCORE_OFFSET;
            y = SCORE_OFFSET;
        }
        else if (ScorePosition.TopCenter.equals(scorePosition))
        {
            x = gc.getWidth()/2 - scoreWidth/2;
            y = SCORE_OFFSET;
        }
        else if (ScorePosition.TopRight.equals(scorePosition))
        {
            x = gc.getWidth() - scoreWidth - SCORE_OFFSET;
            y = SCORE_OFFSET;
        }
        else if (ScorePosition.BottomLeft.equals(scorePosition))
        {
            x = SCORE_OFFSET;
            y = gc.getHeight() - scoreHeight - SCORE_OFFSET;
        }
        else if (ScorePosition.BottomCenter.equals(scorePosition))
        {
            x = gc.getWidth()/2 - scoreWidth/2;
            y = gc.getHeight() - scoreHeight - SCORE_OFFSET;
        }
        else if (ScorePosition.BottomRight.equals(scorePosition))
        {
            x = gc.getWidth() - scoreWidth - SCORE_OFFSET;
            y = gc.getHeight() - scoreHeight - SCORE_OFFSET;
        }

        font.drawString(x, y, scoreText, Color.yellow);
    }

    /**
     * Set the background music of the minigame
     */
    protected void initMusic (String path, float volume, boolean loop)
    {
        try
        {
            music = new Music(path);
            music.stop();
            musicVolume = volume;
            musicLoop = loop;
        }
        catch (SlickException exc) { exc.printStackTrace(); }
    }

    /**
     * Escape command
     */
    protected void escapeMinigame ()
    {
        ((PauseMenuState)nebulaGame.getState(NebulaState.PauseMenu.id))
            .setLastState(this.getID());

        nebulaGame.initAndEnterState(NebulaState.PauseMenu.id);
    }

    /**
     * Invoke the game victory
     */
    protected void gameVictory ()
    {
        nebulaGame.showScoreState(score, true, getID());
    }

    /**
     * Invoke the game defeat
     */
    protected void gameDefeat ()
    {
        score = 0;
        nebulaGame.showScoreState(score, false, getID());
    }

    /**
     * Set the minigame difficulty
     * @param difficulty The difficulty
     */
    public void setDifficulty (Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }

    /**
     * Get the minigame difficulty
     * @return The difficulty
     */
    public Difficulty getDifficulty ()
    {
        return difficulty;
    }

    /**
     * Get the minigame score
     * @return The score
     */
    public int getScore ()
    {
        return score;
    }
}
