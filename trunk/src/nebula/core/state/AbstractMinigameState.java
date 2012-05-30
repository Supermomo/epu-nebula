/**
 * Nebula - Copyright (C) 2012
 * Gwenn Aubert, Thomas Di'Meco, Matthieu Maugard, Gaspard Perrot
 *
 * This file is part of project 'Nebula'
 *
 * 'Nebula' is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * 'Nebula' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Nebula'. If not, see <http://www.gnu.org/licenses/>.
 */
package nebula.core.state;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;
import nebula.core.helper.Utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
    private Sound sndHelp;
    private static Sound sndVictory;
    private static Sound sndDefeat;


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

        // Load sounds
        if (sndVictory == null || sndDefeat == null)
        {
            sndVictory = new Sound("ressources/sons/common/victory.ogg");
            sndDefeat  = new Sound("ressources/sons/common/defeat.ogg");
        }
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

        // Help key
        if (input.isKeyPressed(Input.KEY_F1))
            this.helpMinigame();

        // Debug victory key
        if (input.isKeyDown(Input.KEY_W) &&
            input.isKeyDown(Input.KEY_I) &&
            input.isKeyDown(Input.KEY_N) &&
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
     * Escape command
     */
    protected void escapeMinigame ()
    {
        PauseMenuState pauseState = (PauseMenuState)nebulaGame.getState(NebulaState.PauseMenu.id);
        pauseState.setLastState(this.getID());

        nebulaGame.initAndEnterState(NebulaState.PauseMenu.id, TransitionType.ShortFade);
    }

    /**
     * Help command
     */
    public void helpMinigame ()
    {
        if (sndHelp == null) return;

        HelpMenuState helpState = (HelpMenuState)nebulaGame.getState(NebulaState.HelpMenu.id);
        helpState.setLastState(this.getID());
        helpState.setHelp(sndHelp);

        nebulaGame.initAndEnterState(NebulaState.HelpMenu.id, TransitionType.ShortFade);
    }

    /**
     * Invoke the game victory
     */
    protected void gameVictory ()
    {
        // Check score
        score = Utils.checkScoreRange(score, difficulty);

        sndVictory.play();
        nebulaGame.showScoreState(score, true, getID());
    }

    /**
     * Invoke the game defeat
     */
    protected void gameDefeat ()
    {
        score = 0;
        sndDefeat.play();
        nebulaGame.showScoreState(score, false, getID());
    }

    /**
     * Set the minigame help sound
     * @return The sound
     */
    public void initHelp (String helpFile)
    {
        if (helpFile != null)
            try { sndHelp = new Sound(helpFile); }
            catch (SlickException exc) { exc.printStackTrace(); }
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
