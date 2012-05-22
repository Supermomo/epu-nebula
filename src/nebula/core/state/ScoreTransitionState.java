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

        // Music
        initMusic("ressources/sons/common/fanfare.ogg", 0.3f, true);
    }

    public void initScore (int score, boolean won, int lastState)
    {
        // Init
        try { this.init(nebulaGame.getContainer(), nebulaGame); }
        catch (SlickException exc) { exc.printStackTrace(); }

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
            addMenuItem("Score : " + score, false);
            addMenuSpaces(2);
        }

        if (NebulaGame.isAdventureMode && won)
            addMenuItem("Continuer", true);
        else
            addMenuItem("Recommencer", true);

        addMenuItem("Quitter", true);
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
        Minigame minigame = null;
        if (NebulaState.SpaceInvaders.id == lastState)
            minigame = Minigame.SpaceInvaders;
        else if (NebulaState.SpaceShepherd.id == lastState)
            minigame = Minigame.SpaceShepherd;
        else if (NebulaState.Asteroid.id == lastState)
            minigame = Minigame.Asteroid;
        else if (NebulaState.Gravity.id == lastState)
            minigame = Minigame.Gravity;
        else if (NebulaState.Breakout.id == lastState)
            minigame = Minigame.Breakout;
        else if (NebulaState.Boss.id == lastState)
            minigame = Minigame.Boss;

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
        else
        {
            if (score > NebulaConfig.getRapidmodeScore(minigame))
                NebulaConfig.setRapidmodeScore(minigame, score);
        }

        NebulaConfig.saveData();
    }

    @Override
    public int getID () { return NebulaState.ScoreTransition.id; }
}
