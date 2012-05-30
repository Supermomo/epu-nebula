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
package nebula.core;

import java.awt.Toolkit;

import nebula.core.config.NebulaConfig;
import nebula.core.state.LoadingState;
import nebula.core.state.ScoreTransitionState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;


/**
 * Nebula game main class
 */
public class NebulaGame extends StateBasedGame
{
    /**
     * Game states enum
     * All state Id must be defined here, and only here !
     */
    public static enum NebulaState
    {
        Loading                 (0),
        PlayerMenu              (1),
        PlayerSelectMenu        (2),
        PlayerName              (3),
        MainMenu                (4),
        RapidModeMenu           (5),
        ScoresMenu              (6),
        Credits                 (7),

        SetupAdventure          (50),
        LoadMenu                (51),
        DifficultyMenu          (52),

        StartAdventure          (100),
        HistoryInvaders1        (100),
        HistoryInvaders2        (101),
        HistoryInvaders3        (102),
        SpaceInvaders           (103),
        HistoryShepherd1        (104),
        HistoryShepherd2        (105),
        HistoryShepherd3        (106),
        SpaceShepherd           (107),
        HistoryAsteroidFly      (108),
        HistoryAsteroid1        (109),
        Asteroid                (110),
        HistoryGravity1         (111),
        HistoryGravity2         (112),
        Gravity                 (113),
        HistoryBreakout1        (114),
        HistoryBreakout2        (115),
        Breakout                (116),
        HistoryBossFly          (117),
        HistoryBoss1            (118),
        Boss                    (119),
        HistoryEnd1             (120),
        HistoryEnd2             (121),
        EndMenu                 (122),

        PauseMenu               (200),
        HelpMenu                (201),
        ScoreTransition         (202);

        public int id;
        private NebulaState (int id) { this.id = id; }
    }

    /**
     * Games enum
     */
    public static enum Minigame
    {
        SpaceInvaders ("Invasion"),
        SpaceShepherd ("Berger"),
        Asteroid      ("Astéroïdes"),
        Gravity       ("Gravité"),
        Breakout      ("Casse-Briques"),
        Boss          ("Boss");

        public String name;
        private Minigame (String name) { this.name = name; }
    }

    public static enum TransitionType
        {None, ShortFade, Fade, LongFade, HorizontalSplit, VerticalSplit};

    public static boolean isAdventureMode;


    /**
     * Constructeur du jeu
     * @throws SlickException
     */
    public NebulaGame () throws SlickException
    {
        super("Nebula");

        // Load default config
        NebulaConfig.loadDefaults();
    }

    /**
     * Quit the game
     */
    public void quitGame ()
    {
        this.getContainer().setForceExit(false);
        this.getContainer().exit();
    }

    /**
     * Load the configuration of the player
     * @param playerName Player name
     */
    public void loadPlayer (String playerName)
    {
        // Load config for the player
        NebulaConfig.loadData(playerName);
    }

    /**
     * Initialise states
     */
    @Override
    public void initStatesList (GameContainer gc) throws SlickException
    {
        // Hide mouse cursor
        gc.setMouseCursor(new Image("ressources/images/common/cursor.png"), 0, 0);

        // Loading state
        this.addState(new LoadingState());
        this.enterState(NebulaState.Loading.id);
    }

    /**
     * Goto state with the given transition
     */
    public void enterState (int state, TransitionType transition)
    {
        // Goto next state with the given transition
        if (TransitionType.HorizontalSplit.equals(transition))
            enterState(state, null, new HorizontalSplitTransition(Color.black));
        else if (TransitionType.VerticalSplit.equals(transition))
            enterState(state, null, new VerticalSplitTransition(Color.black));
        else if (TransitionType.ShortFade.equals(transition))
            enterState(state, new FadeOutTransition(Color.black, 200), new FadeInTransition(Color.black, 200));
        else if (TransitionType.Fade.equals(transition))
            enterState(state, new FadeOutTransition(Color.black, 1000), new FadeInTransition(Color.black, 1000));
        else if (TransitionType.LongFade.equals(transition))
            enterState(state, new FadeOutTransition(Color.black, 2000), new FadeInTransition(Color.black, 2000));
        else
            enterState(state, null, null);
    }

    /**
     * Goto state with the default transition
     */
    public void enterState (int state)
    {
        enterState(state, TransitionType.None);
    }

    /**
     * Init and goto state with the given transition
     */
    public void initAndEnterState (int state, TransitionType transition)
    {
        initState(state);
        enterState(state, transition);
    }

    /**
     * Init and goto state with the default transition
     */
    public void initAndEnterState (int state)
    {
        initAndEnterState(state, TransitionType.None);
    }

    /**
     * Init state
     */
    public void initState (int state)
    {
        try { getState(state).init(this.getContainer(), this); }
        catch (SlickException exc) { exc.printStackTrace(); }
    }

    /**
     * Show the score transition state
     */
    public void showScoreState (int score, boolean won, int lastState)
    {
        // Save user config
        NebulaConfig.saveData();

        // Set up score state
        ((ScoreTransitionState)getState(NebulaState.ScoreTransition.id))
            .initScore(score, won, lastState);

        enterState(NebulaState.ScoreTransition.id, TransitionType.Fade);
    }


    /**
     * Returns the minigame from the id
     * @param minigameId The minigame id
     * @return The minigame
     */
    public static Minigame minigameFromId (int minigameId)
    {
        Minigame minigame = null;

        if (NebulaState.SpaceInvaders.id == minigameId)
            minigame = Minigame.SpaceInvaders;
        else if (NebulaState.SpaceShepherd.id == minigameId)
            minigame = Minigame.SpaceShepherd;
        else if (NebulaState.Asteroid.id == minigameId)
            minigame = Minigame.Asteroid;
        else if (NebulaState.Gravity.id == minigameId)
            minigame = Minigame.Gravity;
        else if (NebulaState.Breakout.id == minigameId)
            minigame = Minigame.Breakout;
        else if (NebulaState.Boss.id == minigameId)
            minigame = Minigame.Boss;

        return minigame;
    }


    /**
     * Returns the minigame state id from the minigame
     * @param minigame The minigame
     * @return The minigame state id
     */
    public static int idFromMinigame (Minigame minigame)
    {
        switch (minigame)
        {
            case SpaceInvaders:
                return NebulaState.SpaceInvaders.id;
            case SpaceShepherd:
                return NebulaState.SpaceShepherd.id;
            case Asteroid:
                return NebulaState.Asteroid.id;
            case Gravity:
                return NebulaState.Gravity.id;
            case Breakout:
                return NebulaState.Breakout.id;
            case Boss:
                return NebulaState.Boss.id;
        }

        return -1;
    }


    /**
     * Create and start the Nebula slick game
     */
    public static void startNebulaGame ()
    {
        try {
            AppGameContainer app =
                new AppGameContainer(new NebulaGame());

            app.setDisplayMode(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                true);
            app.setTargetFrameRate(120);
            app.setVerbose(false);
            app.setShowFPS(false);
            app.start();
        }
        catch (Exception exc) { exc.printStackTrace(); }
    }

    /**
     * Start NebulaGame
     */
    public static void main (String[] args) throws SlickException
    {
        NebulaGame.startNebulaGame();
    }
}
