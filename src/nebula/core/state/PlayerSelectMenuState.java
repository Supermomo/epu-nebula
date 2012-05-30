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

import java.util.List;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Load player menu
 */
public class PlayerSelectMenuState extends AbstractMenuState
{
    private int offset = 0;
    private List<String> players;
    private static Sound sndSelect;

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Sounds
        if (sndSelect == null)
            sndSelect = new Sound(sndPath + "selectplayer.ogg");

        // Get players
        players = NebulaConfig.getPlayers();

        // Add menu items
        setMenuTitle("Choix du joueur");

        if (players.size() <= 7)
        {
            offset = 0;

            for (String p : players)
                addMenuItem(p, null, true);

            // No players
            if (players.isEmpty())
                addMenuItem("Aucun joueur", null, false);

            addMenuSpaces(1);
            addMenuItem("Retour", sndPath + "cancel.ogg", true);
        }
        else
        {
            for (int i = 0; i < 7; i++)
            {
                int j = offset - 3 + i;

                if (j < 0 || j >= players.size())
                    addMenuItem(" ", null, true);
                else
                    addMenuItem(players.get(j), null, true);
            }

            setSelectedIndex(3);
        }
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        if (index == -1 || index == players.size())
            nebulaGame.enterState(NebulaState.PlayerMenu.id);
        else if (0 <= index && index < players.size())
        {
            // Load game
            int playerIndex = (players.size() > 7 ? offset : index);
            nebulaGame.loadPlayer(players.get(playerIndex));
            nebulaGame.initAndEnterState(NebulaState.MainMenu.id, TransitionType.ShortFade);
        }
    }


    @Override
    protected void indexMovedEvent (int index, StateBasedGame game)
    {
        if (players.size() <= 7) return;

        if (index > 3)
            offset++;
        else
            offset--;

        if (offset < 0) offset = players.size()-1;
        if (offset >= players.size()) offset = 0;

        refreshMenu();
    }


    public void resetOffset ()
    {
        offset = 0;
    }


    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);
        sndSelect.play();
    }


    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.leave(gc, game);
        sndSelect.stop();
    }


	@Override
	public int getID() { return NebulaState.PlayerSelectMenu.id; }
}
