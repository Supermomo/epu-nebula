package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class HistoryShepherd1State extends AbstractTransitionState
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.HistoryShepherd1.id; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        this.setTransitionType(TransitionType.HorizontalSplit);
        this.setTransitionImage("ressources/images/histoire/shepherd1.png");
        this.setTransitionVoice("ressources/sons/histoire/shepherd1.ogg");
        this.setTransitionTime(7000.0f);
    }
}
