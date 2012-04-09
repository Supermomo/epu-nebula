package nebula.core.state;

import nebula.core.NebulaGame.StateID;
import nebula.core.NebulaGame.TransitionType;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Intro1Jeu extends Transition
{
	/* Transition ID */
	@Override public int getID () { return StateID.Intro1Jeu.value; }
	
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException
	    
	{
	    // Call super method
	    super.init(gc, game);
	    
	    this.setTransitionType(TransitionType.HorizontalSplit);
	    this.setTransitionImage("ressources/images/histoire/nebula_intro.jpg");
	    this.setTransitionText(
	        "Bidibop est un alien. Et aujourd'hui il a 10 ans.\n" +
	        "C'est a dire qu'aujourd'hui il peut conduire son\n" +
	        "propre vaisseau spatial !\n" +
	        "Et ca tombe bien car Bidibop construit depuis\n" +
	        "2 ans une superbe soucoupe rouge avec son papa."
	    );
	}
}
