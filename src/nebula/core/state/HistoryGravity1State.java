package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

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

        this.setTransitionType(TransitionType.HorizontalSplit);
        this.setTransitionImage("ressources/images/histoire/gravity1.png");
        this.setTransitionTime(3000.0f);
    }
}