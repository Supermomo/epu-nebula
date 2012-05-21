package nebula.minigame.boss;

import java.util.Random;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.state.AbstractMinigameState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class BossGame extends AbstractMinigameState 
{
		
		Vaisseau saucer = null; 
	
	
		/* Game ID */
		@Override public int getID () { return NebulaState.SpaceInvaders.id; }
	 
	    @Override
	    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
	        
	        // Call super method
	        super.init(gc, game);
	        switch (this.getDifficulty()) 
	        {
				case Easy:
						
					break;

				case Hard:
					
					break;
					
				case Insane:
					
					break;
					
				default:
					
					break;
			}
	        saucer = new Vaisseau();
	        
	    }
	 
	    @Override
	    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException     
	    {
	        // Call super method
	        super.update(gc, game, delta);
	        
	    	Input input = gc.getInput();
	    	
	    	// =================== Gestion des deplacements ==========================  	
	    	if(input.isKeyDown(Input.KEY_RIGHT))
	    	{
	    		if(saucer.getX() < gc.getWidth() - saucer.getImage().getWidth())
	    			saucer.setX(saucer.getX() + (0.4f * delta));
	    	}
	    	
	    	if(input.isKeyDown(Input.KEY_LEFT))
	    	{
	    		if(saucer.getX() > 0)
	    			saucer.setX(saucer.getX() - (0.4f * delta));
	    	}
	    	
	    	if(input.isKeyDown(Input.KEY_DOWN))
	    	{
	    		if(saucer.getY() + saucer.getImage().getHeight() < gc.getHeight())
	    			saucer.setY(saucer.getY() + (0.4f * delta));
	    	}
	    	
	    	if(input.isKeyDown(Input.KEY_UP))
	    	{
	    		if(saucer.getY() > 0)
	    			saucer.setY(saucer.getY() - (0.4f * delta));
	    	}
	    	
	    	// ========================  GESTION DES TIRS ============================
	    	if(input.isKeyDown(Input.KEY_SPACE))
	    	{
	    		
	    	}
	    }
	 
	    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException 
		{ 
	        // Call super method
	        super.render(gc, game, g);
	       
	    }
}
