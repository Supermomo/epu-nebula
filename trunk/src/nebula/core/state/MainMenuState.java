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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Main menu state
 */
public class MainMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Bienvenue, " + NebulaConfig.getPlayerName());
        addMenuItem("Mode Aventure", sndPath + "adventuremode.ogg", true);
        addMenuItem("Mode Rapide", sndPath + "rapidmode.ogg", true);
        addMenuItem("Scores", sndPath + "scores.ogg", true);
        addMenuItem("Crédits", sndPath + "credits.ogg", true);
        addMenuSpaces(1);
        addMenuItem("Changer de joueur", sndPath + "changeplayer.ogg", true);
        addMenuItem("Quitter", sndPath + "quit.ogg", true);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        // Index selected
        switch (index)
        {
            // Mode aventure
            case 0:
                NebulaGame.isAdventureMode = true;
                if (NebulaConfig.getAdventureMinigame() != null)
                    nebulaGame.initAndEnterState(NebulaState.LoadMenu.id);
                else
                    nebulaGame.initAndEnterState(NebulaState.DifficultyMenu.id);
                break;
            // Mode rapide
            case 1:
                NebulaGame.isAdventureMode = false;
                nebulaGame.initAndEnterState(NebulaState.RapidModeMenu.id);
                break;
            // Scores
            case 2:
                nebulaGame.initAndEnterState(NebulaState.ScoresMenu.id);
                break;
            // Crédits
            case 3:
                nebulaGame.initAndEnterState(NebulaState.Credits.id, TransitionType.Fade);
                break;
            // Changer de joueur
            case 4:
                nebulaGame.initAndEnterState(NebulaState.PlayerMenu.id);
                break;
            // Quitter
            case 5:
                nebulaGame.quitGame();
                break;
        }
    }

    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);

        // Save user config
        NebulaConfig.saveData();
    }

    @Override
    public int getID () { return NebulaState.MainMenu.id; }
}
