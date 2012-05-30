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
import nebula.core.config.NebulaConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Score transition state
 */
public class ScoreTransitionState extends AbstractMenuState
{
    private int lastState;
    private boolean won;

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        lastState = NebulaState.MainMenu.id;
    }

    public void initScore (int score, boolean won, int lastState)
    {
        // Init
        try { this.init(nebulaGame.getContainer(), nebulaGame); }
        catch (SlickException exc) { exc.printStackTrace(); }

        // Music
        if (won)
            initMusic("ressources/sons/common/victory-fanfare.ogg", 0.6f, false);
        else
            initMusic("ressources/sons/common/defeat-fanfare.ogg", 0.6f, false);

        this.lastState = lastState;
        this.won = won;
        resetMenu();

        // Save game
        saveGame(score, won, lastState);

        // Add menu items
        if (won) setMenuTitle("BRAVO !");
        else     setMenuTitle("PERDU !");

        if (won)
        {
            addMenuItem("Score : " + score, null, false);
            addMenuSpaces(2);
        }

        if (NebulaGame.isAdventureMode && won)
            addMenuItem("Continuer", sndPath + "continue.ogg", true);
        else
            addMenuItem("Recommencer", sndPath + "retry.ogg", true);

        addMenuItem("Quitter", sndPath + "quit.ogg", true);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        switch (index)
        {
            case 0:
                if (NebulaGame.isAdventureMode)
                {
                    if (won)
                        nebulaGame.initAndEnterState(lastState+1, TransitionType.Fade);
                    else
                        nebulaGame.initAndEnterState(lastState, TransitionType.Fade);
                }
                else
                    nebulaGame.initAndEnterState(lastState, TransitionType.Fade);
                break;
            case 1:
                if (NebulaGame.isAdventureMode)
                    nebulaGame.enterState(NebulaState.MainMenu.id);
                else
                    nebulaGame.enterState(NebulaState.RapidModeMenu.id);
            default:
                break;
        }
    }

    private void saveGame (int score, boolean won, int lastState)
    {
        // Return if lose
        if (!won) return;

        // Save current minigame
        Minigame minigame = NebulaGame.minigameFromId(lastState);

        if (NebulaGame.isAdventureMode)
        {
            // Save score
            NebulaConfig.setAdventureScore(
                NebulaConfig.getAdventureScore() + score);

            // Last minigame
            if (Minigame.Boss.equals(minigame))
            {
                // Adventure finished
                NebulaConfig.setAdventureMinigame(null);

                if (NebulaConfig.getAdventureScore() > NebulaConfig.getAdventureBestScore())
                    NebulaConfig.setAdventureBestScore(NebulaConfig.getAdventureScore());

            }
            else
                NebulaConfig.setAdventureMinigame(minigame);
        }

        // Save best minigame score
        if (score > NebulaConfig.getRapidmodeScore(minigame))
                NebulaConfig.setRapidmodeScore(minigame, score);

        NebulaConfig.saveData();
    }

    @Override
    public int getID () { return NebulaState.ScoreTransition.id; }
}
