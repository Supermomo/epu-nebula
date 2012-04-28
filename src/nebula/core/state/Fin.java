package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Fin extends AbstractTransitionState
{
    /* Transition ID */
    @Override public int getID () { return NebulaState.Fin.id; }
	
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException 
	{
	    // Call super method
        super.init(gc, game);
        
        this.setTransitionType(TransitionType.Fade);
		this.setTransitionImage("ressources/images/miscellaneous/fin.png");
	}
}