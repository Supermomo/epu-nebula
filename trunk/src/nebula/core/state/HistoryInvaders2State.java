package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class HistoryInvaders2State extends AbstractTransitionState
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.HistoryInvaders2.id; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        this.setTransitionImage("ressources/images/histoire/invaders2.png");
        this.setTransitionVoice("ressources/sons/histoire/invaders2.ogg");
    	this.setTransitionTime(9000.0f);
    }
}
