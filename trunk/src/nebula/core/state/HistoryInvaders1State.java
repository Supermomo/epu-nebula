package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class HistoryInvaders1State extends AbstractTransitionState
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.HistoryInvaders1.id; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        this.setTransitionType(TransitionType.HorizontalSplit);
        this.setTransitionImage("ressources/images/histoire/invaders1.png");
        this.setTransitionVoice("ressources/sons/histoire/invaders1.ogg");
        this.setTransitionTime(14000.0f);
    }
}
