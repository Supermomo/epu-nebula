package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class FinInvaders extends Transition
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.FinInvaders.id; }
	
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException 
	{
	    // Call super method
        super.init(gc, game);
        
        this.setTransitionType(TransitionType.HorizontalSplit);
        this.setTransitionImage("ressources/images/histoire/nebula-vol_espace.png");
        this.setTransitionTime(5000.0f);
	}
}
