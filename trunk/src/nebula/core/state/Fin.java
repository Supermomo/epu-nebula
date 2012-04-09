package nebula.core.state;

import nebula.core.NebulaGame.StateID;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Fin extends Transition
{
    /* Transition ID */
    @Override public int getID () { return StateID.Fin.value; }
	
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException 
	{
	    // Call super method
        super.init(gc, game);
        
		this.setTransitionImage("ressources/images/miscellaneous/fin.png");
	}
}
