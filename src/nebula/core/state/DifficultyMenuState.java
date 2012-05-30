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
import nebula.core.config.NebulaConfig;
import nebula.core.state.AbstractMinigameState.Difficulty;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Difficulty choice menu
 */
public class DifficultyMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Difficulté");
        addMenuItem("Facile", sndPath + "easy.ogg", true);
        addMenuItem("Moyen", sndPath + "medium.ogg", true);
        addMenuItem("Difficile", sndPath + "hard.ogg", true);
        addMenuItem("Très difficile", sndPath + "insane.ogg", true);
        addMenuSpaces(1);
        addMenuItem("Retour", sndPath + "cancel.ogg", true);
        setSelectedIndex(1);
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        boolean wantBack = false;
        switch (index)
        {
            // Facile
            case 0:
                NebulaConfig.setAdventureDifficulty(Difficulty.Easy);
                break;
            // Moyen
            case 1:
                NebulaConfig.setAdventureDifficulty(Difficulty.Medium);
                break;
            // Difficile
            case 2:
                NebulaConfig.setAdventureDifficulty(Difficulty.Hard);
                break;
            // Très difficile
            case 3:
                NebulaConfig.setAdventureDifficulty(Difficulty.Insane);
                break;
            default:
                wantBack = true;
                break;
        }

        // Enter state
        if (wantBack)
        {
            if (NebulaConfig.getAdventureMinigame() != null)
                nebulaGame.enterState(NebulaState.LoadMenu.id);
            else
                nebulaGame.enterState(NebulaState.MainMenu.id);
        }
        else
        {
            NebulaConfig.setAdventureScore(0);
            NebulaConfig.setAdventureMinigame(null);
            nebulaGame.enterState(NebulaState.StartAdventure.id, TransitionType.Fade);
        }
    }


	@Override
	public int getID() { return NebulaState.DifficultyMenu.id; }
}
