package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Intro1Jeu extends AbstractTransitionState
{
	/* Transition ID */
	@Override public int getID () { return NebulaState.Intro1Jeu.id; }
	
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException
	{
	    // Call super method
	    super.init(gc, game);
	    
	    this.setTransitionType(TransitionType.HorizontalSplit);
	    this.setTransitionImage("ressources/images/histoire/nebula_intro.jpg");
	    this.setTransitionVoice("ressources/sons/histoire/intro1.ogg");
	    this.setTransitionTime(14000.0f);
	}
}
