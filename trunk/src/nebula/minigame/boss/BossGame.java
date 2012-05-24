package nebula.minigame.boss;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.state.AbstractMinigameState;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class BossGame extends AbstractMinigameState
{		
		Vaisseau saucer = null; 
		Boss boss = null;
		Tir tir1;
		Tir tir2;
		Tir tir;
		Missile miss1;
		Missile miss2;
		
		private SpriteSheet explo = null;
		
		private Animation explosion = null;
		private float xExplo = -1000;
		private float yExplo = -1000;
		
		private Animation explosion2 = null;
		private float xExplo2 = -1000;
		private float yExplo2 = -1000;
		
		private SpriteSheet laser = null;
		private Animation phatLaser = null;
		private float xLaser = -2200;
		private float yLaser = -100;
		
		int timerTir;
		int timeT;
		float timeM;
		int unlockMove;
	
		/* Game ID */
		@Override public int getID () { return NebulaState.Boss.id; }

	    @Override
	    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

	        // Call super method
	        super.init(gc, game);
	        switch (this.getDifficulty())
	        {
				case Easy:
						timerTir = 20000;
					break;

				case Hard:
						timerTir = 10000;
					break;

				case Insane:
						timerTir = 5000;
					break;

				default:
						timerTir = 15000;
					break;
			}
	        saucer = new Vaisseau();
	        boss = new Boss();
	        boss.setX(gc.getWidth()/2 - boss.getImage().getWidth()/2);
	        boss.setY(0);
	        // TIRS BOSS
	        timeT = timerTir;
	        timeM = timerTir * 1.77f;
	        tir1 = new Tir(true);
	        tir2 = new Tir(true);
	        miss1 = new Missile(timerTir);
	        miss2 = new Missile(timerTir);
	        
	        // EXPLOSION
	        explo = new SpriteSheet("ressources/images/boss/skybusterExplosion.png",320,240,0);
			explosion = new Animation(explo,75);
	    	explosion.setAutoUpdate(true);
	    	explosion.setLooping(false);
	    	explosion.stopAt(20);
	    	
			explosion2 = new Animation(explo,75);
	    	explosion2.setAutoUpdate(true);
	    	explosion2.setLooping(false);
	    	explosion2.stopAt(20);
	    	
	    	laser = new SpriteSheet("ressources/images/boss/nebula-laser-animation.png",142,2100,0);
	    	phatLaser = new Animation(laser, 200);
	    	phatLaser.setAutoUpdate(true);
	    	phatLaser.setLooping(false);
	    	phatLaser.stopAt(12);
	    	
	    	// TIR SAUCER
	    	tir = new Tir(false);
	    }

	    @Override
	    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException
	    {
	        // Call super method
	        super.update(gc, game, delta);
	        
	        timeT -= delta;
	        timeM -= delta;
	        if(!saucer.getMove())
	        	unlockMove -= delta;
	        if(unlockMove < 0)
	        	saucer.setMove(true);
	        
	        float hip = delta * 0.25f;
	    	Input input = gc.getInput();

	    	// =================== Gestion des deplacements ==========================
	    	if(input.isKeyDown(Input.KEY_RIGHT) && saucer.getMove())
	    	{
	    		if(saucer.getX() < gc.getWidth() - saucer.getImage().getWidth())
	    			saucer.setX(saucer.getX() + (0.4f * delta));
	    	}

	    	if(input.isKeyDown(Input.KEY_LEFT) && saucer.getMove())
	    	{
	    		if(saucer.getX() > 0)
	    			saucer.setX(saucer.getX() - (0.4f * delta));
	    	}

	    	if(input.isKeyDown(Input.KEY_DOWN) && saucer.getMove())
	    	{
	    		if(saucer.getY() + saucer.getImage().getHeight() < gc.getHeight())
	    			saucer.setY(saucer.getY() + (0.4f * delta));
	    	}

	    	if(input.isKeyDown(Input.KEY_UP) && saucer.getMove())
	    	{
	    		if(saucer.getY() > 0)
	    			saucer.setY(saucer.getY() - (0.4f * delta));
	    	}

	    	// ========================  GESTION DES TIRS ============================
	    	if(input.isKeyDown(Input.KEY_SPACE))
	    	{
	    		saucer.tirer(tir);
	    	}
	    	
	    	if(input.isKeyDown(Input.KEY_LCONTROL))
	    	{
	    		xLaser = saucer.getX() + saucer.getImage().getWidth()/2 - phatLaser.getImage(0).getWidth()/2;
	    		yLaser = saucer.getY() - phatLaser.getImage(0).getHeight();
	    		saucer.setMove(false);
	    		unlockMove = 2000;
	    		if(phatLaser.isStopped())
	    		{
	    			phatLaser.restart();
	    		}
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
	    		miss1.getImage().setRotation(0);
	    		timeM = timerTir * 2;
	    		boss.launch(miss1, true, saucer);
	    		boss.launch(miss2, false, saucer);
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
	    		miss1.minusExplosion(delta);
	    		if(miss1.getTimerExplosion() <= 0)
	    		{
	    			xExplo = miss1.getX() + miss1.getImage().getWidth()/2 - explosion.getImage(0).getWidth()/2;
	    			yExplo = miss1.getY() + miss1.getImage().getHeight()/2 - explosion.getImage(0).getHeight()/2;
	    			if(explosion.isStopped())
    	    		{
    	    			explosion.restart();
    	    		}
	    			miss1.explode(timerTir);
	    		}
	    		miss1.vise(saucer);
	    		miss1.setX(miss1.getX() + hip * (float)Math.sin(Math.toRadians(miss1.getImage().getRotation())));
	    		miss1.setY(miss1.getY() - hip * (float)Math.cos(Math.toRadians(miss1.getImage().getRotation())));
	    	}
	    	
	    	if(miss2.getTire())
	    	{
	    		miss2.minusExplosion(delta);
	    		if(miss2.getTimerExplosion() <= 0)
	    		{
	    			
	    			xExplo2 = miss2.getX() + miss2.getImage().getWidth()/2 - explosion2.getImage(0).getWidth()/2;
	    			yExplo2 = miss2.getY() + miss2.getImage().getHeight()/2 - explosion2.getImage(0).getHeight()/2;
	    			if(explosion2.isStopped())
    	    		{
    	    			explosion2.restart();
    	    		}
	    			miss2.explode(timerTir);
	    		}
	    		miss2.vise(saucer);
	    		miss2.setX(miss2.getX() + hip * (float)Math.sin(Math.toRadians(miss2.getImage().getRotation())));
	    		miss2.setY(miss2.getY() - hip * (float)Math.cos(Math.toRadians(miss2.getImage().getRotation())));
	    	}
	    	
	    	if(tir.getY() > -100)
	    		tir.setY(tir.getY() - 0.4f * delta);
	    	
	    	if(boss.touche(tir))
	    	{
	    		xExplo = boss.getX() + boss.getImage().getWidth()/2 - explosion.getImage(0).getWidth()/2;
	    		yExplo = boss.getY() + boss.getImage().getHeight()/2 - explosion.getImage(0).getHeight()/2;
	    		if(explosion.isStopped())
	    		{
	    			explosion.restart();
	    		}
	    		boss.loseLife();
	    		boss.getSon().play();
	    		
	    		tir.setX(-100);
	    		tir.setY(-100);
	    	}
	    	
	    }

	    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException
		{
	        // Call super method
	        super.render(gc, game, g);
	        boss.getImage().draw(boss.getX(), boss.getY());
	        tir1.getImage().draw(tir1.getX(),tir1.getY());
	        tir2.getImage().draw(tir2.getX(),tir2.getY());
	        tir.getImage().draw(tir.getX(), tir.getY());
	        miss1.getImage().draw(miss1.getX(), miss1.getY());
	        miss2.getImage().draw(miss2.getX(), miss2.getY());
	        g.drawAnimation(explosion, xExplo, yExplo);
	        g.drawAnimation(explosion2, xExplo2, yExplo2);
	        g.drawAnimation(phatLaser, xLaser, yLaser);
	        
	        saucer.getImage().draw(saucer.getX(), saucer.getY());
	    }
}
