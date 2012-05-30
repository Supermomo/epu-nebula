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

import java.util.ArrayList;
import java.util.List;

import nebula.core.NebulaGame.Minigame;
import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;
import nebula.core.state.AbstractMinigameState.Difficulty;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Rapid mode menu state
 */
public class RapidModeMenuState extends AbstractMenuState
{
    private static List<Sound> sndDifficulties;


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Difficulty
        String diffStr = "";
        Difficulty diff = NebulaConfig.getRapidmodeDifficulty();

        if (Difficulty.Easy.equals(diff))
            diffStr = "Facile";
        else if (Difficulty.Medium.equals(diff))
            diffStr = "Moyen";
        else if (Difficulty.Hard.equals(diff))
            diffStr = "Difficile";
        else if (Difficulty.Insane.equals(diff))
            diffStr = "Très difficile";

        // Add menu items
        setMenuTitle("Mode Rapide");
        addMenuItem("Difficulté : " + diffStr, sndPath + "difficulty.ogg", true);
        addMenuSpaces(2);

        // Minigames
        for (Minigame mg : Minigame.values())
        {
            String voiceFile = null;

            switch (mg)
            {
                case SpaceInvaders:
                    voiceFile = "spaceInvaders.ogg";
                    break;
                case SpaceShepherd:
                    voiceFile = "spaceShepherd.ogg";
                    break;
                case Asteroid:
                    voiceFile = "asteroid.ogg";
                    break;
                case Gravity:
                    voiceFile = "gravity.ogg";
                    break;
                case Breakout:
                    voiceFile = "breakout.ogg";
                    break;
                case Boss:
                    voiceFile = "boss.ogg";
                    break;
            }

            if (voiceFile != null)
                voiceFile = sndPath + voiceFile;

            addMenuItem(mg.name, voiceFile, true);
        }

        addMenuSpaces(1);
        addMenuItem("Retour", sndPath + "cancel.ogg", true);

        // Sounds
        if (sndDifficulties == null)
        {
            sndDifficulties = new ArrayList<Sound>();
            sndDifficulties.add(new Sound(sndPath + "easy.ogg"));
            sndDifficulties.add(new Sound(sndPath + "medium.ogg"));
            sndDifficulties.add(new Sound(sndPath + "hard.ogg"));
            sndDifficulties.add(new Sound(sndPath + "insane.ogg"));
        }
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        int nextGame = -2;

        // Index selected
        switch (index)
        {
            // Difficulty
            case 0:
                Difficulty diff = NebulaConfig.getRapidmodeDifficulty();

                if (Difficulty.Easy.equals(diff))
                    NebulaConfig.setRapidmodeDifficulty(Difficulty.Medium);
                else if (Difficulty.Medium.equals(diff))
                    NebulaConfig.setRapidmodeDifficulty(Difficulty.Hard);
                else if (Difficulty.Hard.equals(diff))
                    NebulaConfig.setRapidmodeDifficulty(Difficulty.Insane);
                else if (Difficulty.Insane.equals(diff))
                    NebulaConfig.setRapidmodeDifficulty(Difficulty.Easy);

                // Sound
                stopSounds();
                sndDifficulties.get(NebulaConfig.getRapidmodeDifficulty().ordinal()).play();

                refreshMenu();
                break;
            // Space Invaders
            case 1:
                nextGame = NebulaState.SpaceInvaders.id;
                break;
            // Space Shepherd
            case 2:
                nextGame = NebulaState.SpaceShepherd.id;
                break;
            // Asteroid
            case 3:
                nextGame = NebulaState.Asteroid.id;
                break;
            // Gravity
            case 4:
                nextGame = NebulaState.Gravity.id;
                break;
            // Breakout
            case 5:
                nextGame = NebulaState.Breakout.id;
                break;
            // Boss
            case 6:
                nextGame = NebulaState.Boss.id;
                break;
            // Escape
            default:
                nextGame = -1;
                break;
        }

        // Change state if requested
        if (nextGame > 0)
            nebulaGame.initAndEnterState(nextGame, TransitionType.Fade);
        else if (nextGame == -1)
            nebulaGame.enterState(NebulaState.MainMenu.id);
    }


    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.leave(gc, game);

        stopSounds();

        // Save user config
        NebulaConfig.saveData();
    }


    private void stopSounds()
    {
        for (Sound s : sndDifficulties)
            if (s.playing())
                s.stop();
    }


    @Override
    public int getID () { return NebulaState.RapidModeMenu.id; }
}
