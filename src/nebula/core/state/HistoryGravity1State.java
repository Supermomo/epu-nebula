package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class HistoryGravity1State extends AbstractTransitionState
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.HistoryGravity1.id; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        this.setTransitionImage("ressources/images/histoire/gravity1.png");
        this.setTransitionVoice("ressources/sons/histoire/gravity1.ogg");
        this.setTransitionTime(10000.0f);
    }
}
