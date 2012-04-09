package nebula.core.intro;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class IntroJeu extends Transition
{
	/* Transition ID */
	@Override public int getID () { return 3; }
	
	@Override
	public void init (GameContainer gc, StateBasedGame game)
	    throws SlickException
	    
	{
	    // Call super method
	    super.init(gc, game);
	    
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
