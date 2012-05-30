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
import nebula.core.NebulaGame.Minigame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Help menu
 */
public class HelpMenuState extends AbstractMenuState
{
    private int lastState = NebulaState.MainMenu.id;
    private Sound sndHelp;

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Minigame
        Minigame minigame = NebulaGame.minigameFromId(lastState);
        String name = (minigame != null ? minigame.name : "Aide");

        // Add menu items
        setMenuTitle("Comment jouer ?");
        addMenuItem("Aide du jeu \""+ name +"\"", null, false);
        addMenuSpaces(2);
        addMenuItem("Appuie sur F1 à tout moment pour", null, false);
        addMenuItem("écouter l'aide pendant le jeu", null, false);
        addMenuSpaces(3);
        addMenuItem("Continuer", null, true);
        addMenuItem("Réécouter", null, true);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            case 1:
                if (sndHelp != null)
                {
                    if (sndHelp.playing())
                        sndHelp.stop();

                    sndHelp.play();
                }
                break;

            default:
                nebulaGame.enterState(lastState, TransitionType.ShortFade);
                break;
        }
    }

    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);

        if (sndHelp != null)
            sndHelp.play();
    }

    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.leave(gc, game);

        if (sndHelp != null && sndHelp.playing())
            sndHelp.stop();
    }

    /**
     * Set the last state to return
     */
    public void setLastState (int lastState)
    {
        this.lastState = lastState;
    }

    /**
     * Set the help sound
     */
    public void setHelp (Sound sndHelp)
    {
        this.sndHelp = sndHelp;
    }

    @Override
    public int getID() { return NebulaState.HelpMenu.id; }
}
