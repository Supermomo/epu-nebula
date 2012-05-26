package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class HistoryAsteroid1State extends AbstractTransitionState
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.HistoryAsteroid1.id; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        this.setTransitionImage("ressources/images/histoire/asteroid1.png");
        this.setTransitionVoice("ressources/sons/histoire/asteroid1.ogg");
        this.setTransitionTime(12000.0f);
    }
}
