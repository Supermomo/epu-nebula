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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Load player menu
 */
public class PlayerMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Choix du joueur");
        addMenuItem("Ancien joueur", sndPath + "oldplayer.ogg", true);
        addMenuItem("Nouveau joueur", sndPath + "newplayer.ogg", true);
        addMenuSpaces(1);
        addMenuItem("Quitter", sndPath + "quit.ogg", true);
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            // Ancien joueur
            case 0:
                PlayerSelectMenuState state = (PlayerSelectMenuState) nebulaGame.getState(NebulaState.PlayerSelectMenu.id);
                state.resetOffset();
                nebulaGame.initAndEnterState(NebulaState.PlayerSelectMenu.id);
                break;
            // Nouveau joueur
            case 1:
                nebulaGame.initAndEnterState(NebulaState.PlayerName.id);
                break;
            // Quitter
            case 2:
                nebulaGame.quitGame();
                break;
        }
    }


	@Override
	public int getID() { return NebulaState.PlayerMenu.id; }
}
