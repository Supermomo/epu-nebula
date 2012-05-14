package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;
import nebula.core.state.AbstractMinigameState.Difficulty;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Rapid mode menu state
 */
public class RapidModeMenuState extends AbstractMenuState
{
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
        addMenuItem("Difficulté : " + diffStr, true);
        addMenuSpaces(2);

        // Minigames
        addMenuItem("Astéroïdes", true);
        addMenuItem("Casse-briques", true);
        addMenuItem("Invasion", true);
        addMenuItem("Gravité", true);
        addMenuItem("Berger", true);
        addMenuSpaces(1);
        addMenuItem("Retour", true);
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

                refreshMenu();
                break;
            // Asteroids
            case 1:
                nextGame = NebulaState.Asteroid.id;
                break;
            // Breakout
            case 2:
                nextGame = NebulaState.Breakout.id;
                break;
            // Space Invaders
            case 3:
                nextGame = NebulaState.SpaceInvaders.id;
                break;
            // Gravity
            case 4:
                nextGame = NebulaState.Gravity.id;
                break;
            // Gravity
            case 5:
                nextGame = NebulaState.SpaceShepherd.id;
                break;
            // Escape
            case 6:
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

        // Save user config
        NebulaConfig.saveData();
    }


    @Override
    public int getID () { return NebulaState.RapidModeMenu.id; }
}
