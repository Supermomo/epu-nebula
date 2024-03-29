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

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.minigame.asteroid.AsteroidGame;
import nebula.minigame.boss.BossGame;
import nebula.minigame.breakout.BreakoutGame;
import nebula.minigame.gravity.Gravity;
import nebula.minigame.spaceInvaders.SpaceInvaders;
import nebula.minigame.spaceShepherd.SpaceShepherd;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Loading state class
 */
public class LoadingState extends AbstractState
{
    // Images path
    public static String imgPath = "ressources/images/common/";

    // Loading bar
    private static final float LBAR_WIDTH  = 600.0f;
    private static final float LBAR_HEIGHT = 24.0f;

    private int loadStep;
    private Image imgLoading;
    private LoadState loadState;
    private List<BasicGameState> states = new ArrayList<BasicGameState>();

    // Loading state
    private enum LoadState {Start, Loading, Finish}


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        loadStep = 0;
        loadState = LoadState.Start;

        // Load images
        imgLoading = new Image(imgPath + "loading.png");
    }


    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);

        // Start state
        if (LoadState.Start.equals(loadState))
        {
            this.addStates();
            loadState = LoadState.Loading;
        }
        // Loading state
        else if (LoadState.Loading.equals(loadState))
        {
            if (loadStep >= states.size())
                loadState = LoadState.Finish;
            else
            {
                // Init current state
                BasicGameState state = states.get(loadStep);
                nebulaGame.addState(state);
                state.init(nebulaGame.getContainer(), nebulaGame);

                loadStep++;
            }
        }
        // Finish state
        else if (LoadState.Finish.equals(loadState))
            nebulaGame.enterState(NebulaState.PlayerMenu.id, TransitionType.ShortFade);
    }

    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);

        // Render loading image
        imgLoading.drawCentered(gc.getWidth()/2, gc.getHeight()/2);

        // Render loading bar
        if (LoadState.Loading.equals(loadState) ||
            LoadState.Finish.equals(loadState))
        {
            g.setColor(Color.yellow);
            g.setLineWidth(3.0f);
            g.drawRect(
                gc.getWidth()/2 - LBAR_WIDTH/2,
                gc.getHeight()/2 - LBAR_HEIGHT/2,
                LBAR_WIDTH,
                LBAR_HEIGHT
            );
            g.fillRect(
                gc.getWidth()/2 - LBAR_WIDTH/2,
                gc.getHeight()/2 - LBAR_HEIGHT/2,
                ((float)loadStep/(float)states.size()) * LBAR_WIDTH,
                LBAR_HEIGHT
            );
        }
    }


    /**
     * Add states to the loading list
     */
    private void addStates ()
    {
        states.add(new PlayerMenuState());
        states.add(new PlayerSelectMenuState());
        states.add(new PlayerNameState());
        states.add(new MainMenuState());
        states.add(new RapidModeMenuState());
        states.add(new ScoresMenuState());
        states.add(new CreditsState());
        states.add(new DifficultyMenuState());
        states.add(new LoadMenuState());
        states.add(new ScoreTransitionState());
        states.add(new EndMenuState());
        states.add(new PauseMenuState());
        states.add(new HelpMenuState());

        // Adventure
        states.add(new HistoryInvaders1State());
        states.add(new HistoryInvaders2State());
        states.add(new HistoryInvaders3State());
        states.add(new SpaceInvaders());
        states.add(new HistoryShepherd1State());
        states.add(new HistoryShepherd2State());
        states.add(new HistoryShepherd3State());
        states.add(new SpaceShepherd());
        states.add(new HistoryAsteroidFlyState());
        states.add(new HistoryAsteroid1State());
        states.add(new AsteroidGame());
        states.add(new HistoryGravity1State());
        states.add(new HistoryGravity2State());
        states.add(new Gravity());
        states.add(new HistoryBreakout1State());
        states.add(new HistoryBreakout2State());
        states.add(new BreakoutGame());
        states.add(new HistoryBossFlyState());
        states.add(new HistoryBoss1State());
        states.add(new BossGame());
        states.add(new HistoryEnd1State());
        states.add(new HistoryEnd2State());
    }

    @Override public int getID () { return NebulaState.Loading.id; }
}

