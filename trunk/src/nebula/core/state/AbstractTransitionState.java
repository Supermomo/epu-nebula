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

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Custom abstract transition class
 */
public abstract class AbstractTransitionState extends AbstractState
{
    private float time = Float.NEGATIVE_INFINITY;
    private Image image;
    private Sound voice;


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Remove default background
        setUseDefaultBackground(false);
    }


    @Override
    public final void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);

        Input input = gc.getInput();

        // Next state key
        if (input.isKeyDown(Input.KEY_ENTER) ||
            input.isKeyDown(Input.KEY_ESCAPE) ||
            input.isKeyDown(Input.KEY_SPACE))
            gotoNextState();

        // Transition time counter if needed
        if (time != Float.NEGATIVE_INFINITY)
        {
            if (time > 0) time -= delta;
            else gotoNextState();
        }
    }


    @Override
    public final void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);

        // Render image
        if (image != null)
            image.draw(0, 0, gc.getWidth(), gc.getHeight());
    }


    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.enter(gc, game);

        if (voice != null) voice.play();
    }


    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.leave(gc, game);
        if (voice != null) voice.stop();
    }


    /**
     * Goto next state
     */
    protected void gotoNextState ()
    {
        GameState next = nebulaGame.getState(this.getID()+1);

        if (next == null)
            nebulaGame.enterState(NebulaState.MainMenu.id);
        else if (next instanceof AbstractMenuState)
            nebulaGame.initAndEnterState(this.getID()+1, TransitionType.ShortFade);
        else if (next instanceof AbstractMinigameState)
        {
            // Show help
            nebulaGame.initState(this.getID()+1);

            AbstractMinigameState mg =
                (AbstractMinigameState) nebulaGame.getState(this.getID()+1);

            mg.helpMinigame();
        }
        else
            nebulaGame.enterState(this.getID()+1, TransitionType.HorizontalSplit);
    }


    /**
     * Set the transition duration
     * @param time Time in milliseconds
     */
    protected void setTransitionTime (float time)
    {
        this.time = time;
    }


    /**
     * Set the transition image
     * @param path The image path
     */
    protected void setTransitionImage (String path)
    {
        try {
            image = new Image(path);
        }
        catch (SlickException exc) {
            exc.printStackTrace();
        }
    }


    /**
     * Set the transition voice
     * @param path The sound path
     */
    protected void setTransitionVoice (String path)
    {
        try {
            voice = new Sound(path);
        }
        catch (SlickException exc) {
            exc.printStackTrace();
        }
    }
}
