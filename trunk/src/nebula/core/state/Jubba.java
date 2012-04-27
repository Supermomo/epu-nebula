package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Jubba extends AbstractTransitionState
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.Jubba.id; }
	
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException 
	{
		// Call super method
        super.init(gc, game);
        
        this.setTransitionType(TransitionType.Fade);
        this.setTransitionImage("ressources/images/histoire/nebula-jbba.png");
        this.setTransitionTime(5000.0f);
	}
}
