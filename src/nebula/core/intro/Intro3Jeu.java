package nebula.core.intro;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Intro3Jeu extends Transition
{
    /* Transition ID */
    @Override public int getID () { return 5; }
    
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException 
	{
	    // Call super method
        super.init(gc, game);
        
        this.setTransitionImage("ressources/images/histoire/nebula fuite.png");
        this.setTransitionTime(3000.0f);
	}
}
