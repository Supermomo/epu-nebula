package nebula.minigame.boss;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.state.AbstractMinigameState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.state.StateBasedGame;

public class BossGame extends AbstractMinigameState
{		
		Vaisseau saucer = null; 
		Boss boss = null;
		Tir tir1;
		Tir tir2;
		Missile miss1;
		
		int timerTir;
		int timeT;
		int timeM;
	
		/* Game ID */
		@Override public int getID () { return NebulaState.Boss.id; }

	    @Override
	    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

	        // Call super method
	        super.init(gc, game);
	        switch (this.getDifficulty())
	        {
				case Easy:
						timerTir = 40000;
					break;

				case Hard:
						timerTir = 20000;
					break;

				case Insane:
						timerTir = 10000;
					break;

				default:
						timerTir = 30000;
					break;
			}
	        saucer = new Vaisseau();
	        boss = new Boss();
	        boss.setX(gc.getWidth()/2 - boss.getImage().getWidth()/2);
	        boss.setY(0);
	        // TIRS BOSS
	        timeT = timerTir;
	        timeM = timerTir * 2;
	        tir1 = new Tir();
	        tir2 = new Tir();
	        miss1 = new Missile();

	    }

	    @Override
	    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException
	    {
	        // Call super method
	        super.update(gc, game, delta);
	        
	        timeT -= delta;
	        timeM -= delta;
	        float hip = delta * 0.25f;
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
    	
	    	if(timeT <= 0)
	    	{
	    		tir1.getImage().setRotation(0);
	    		tir2.getImage().setRotation(0);
	    		timeT = timerTir;
	    		boss.tirer(tir1, true, saucer);
	    		boss.tirer(tir2, false, saucer);
	    	}
	    	
	    	if(timeM <= 0)
	    	{
	    		miss1.getImage().setRotation(0);
	    		timeM = timerTir;
	    		boss.launch(miss1, true, saucer);
	    	}
	        
	    	if(tir1.getTire())
	    	{
	    		tir1.setX(tir1.getX() + hip * (float)Math.sin(Math.toRadians(tir1.getImage().getRotation())));
	    		tir1.setY(tir1.getY() - hip * (float)Math.cos(Math.toRadians(tir1.getImage().getRotation())));
	    	}
	    	
	    	if(tir2.getTire())
	    	{
	    		tir2.setX(tir2.getX() + hip * (float)Math.sin(Math.toRadians(tir2.getImage().getRotation())));
	    		tir2.setY(tir2.getY() - hip * (float)Math.cos(Math.toRadians(tir2.getImage().getRotation())));
	    	}
	    	
	    	if(miss1.getTire())
	    	{
	    		miss1.vise(saucer);
	    		miss1.setX(miss1.getX() + hip * (float)Math.sin(Math.toRadians(miss1.getImage().getRotation())));
	    		miss1.setY(miss1.getY() - hip * (float)Math.cos(Math.toRadians(miss1.getImage().getRotation())));
	    	}
	    }

	    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException
		{
	        // Call super method
	        super.render(gc, game, g);
	        saucer.getImage().draw(saucer.getX(), saucer.getY());
	        boss.getImage().draw(boss.getX(), boss.getY());
	        tir1.getImage().draw(tir1.getX(),tir1.getY());
	        tir2.getImage().draw(tir2.getX(),tir2.getY());
	        miss1.getImage().draw(miss1.getX(), miss1.getY());
	    }
}
