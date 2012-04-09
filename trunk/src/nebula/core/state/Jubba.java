package nebula.core.state;

import nebula.core.NebulaGame.StateID;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Jubba extends Transition
{
    /* Transition ID */
    @Override public int getID () { return StateID.Jubba.value; }
	
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException 
	{
		// Call super method
        super.init(gc, game);
        
        this.setTransitionImage("ressources/images/histoire/nebula-jbba.png");
        this.setTransitionTime(5000.0f);
	}
}
