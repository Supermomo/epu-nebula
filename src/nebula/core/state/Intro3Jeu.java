package nebula.core.state;

import nebula.core.NebulaGame.StateID;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Intro3Jeu extends Transition
{
    /* Transition ID */
    @Override public int getID () { return StateID.Intro3Jeu.value; }
    
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException 
	{
	    // Call super method
        super.init(gc, game);
        
        this.setTransitionImage("ressources/images/histoire/nebula fuite.png");
        this.setTransitionVoice("ressources/sons/histoire/intro3.ogg");
        this.setTransitionTime(12000.0f);
	}
}
