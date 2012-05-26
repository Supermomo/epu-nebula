package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class HistoryBossFlyState extends AbstractTransitionState
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.HistoryBossFly.id; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        this.setTransitionImage("ressources/images/histoire/fly.png");
        this.setTransitionVoice("ressources/sons/histoire/fly2.ogg");
        this.setTransitionTime(8000.0f);
    }
}
