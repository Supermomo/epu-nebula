package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Intro3JeuState extends AbstractTransitionState
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.Intro3Jeu.id; }

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        this.setTransitionType(TransitionType.Fade);
        this.setTransitionImage("ressources/images/histoire/nebula fuite.png");
        this.setTransitionVoice("ressources/sons/histoire/intro3.ogg");
        this.setTransitionTime(12000.0f);
    }
}
