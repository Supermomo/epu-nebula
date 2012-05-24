package nebula.minigame.boss;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;
import nebula.core.state.AbstractMinigameState;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
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
		private SpriteSheet explo4 = null;
		
		private Animation explosion = null;
		private float xExplo = -1000;
		private float yExplo = -1000;
		
		private Animation explosion2 = null;
		private float xExplo2 = -1000;
		private float yExplo2 = -1000;
		
		private Animation explosion3 = null;
		private float xExplo3 = -1000;
		private float yExplo3 = -1000;
		
		private Animation explosion4 = null;
		private float xExplo4 = -1000;
		private float yExplo4 = -1000;
		
		private SpriteSheet laser = null;
		private Animation phatLaser = null;
		private float xLaser = -2200;
		private float yLaser = -100;
		
		private SpriteSheet light = null;
		private Animation phatExplosion = null;
		private float xPhat = -2000;
		private float yPhat = -2000;
		
		int timerTir;
		int timeT;
		float timeM;
		int unlockMove;
		int invincibility;
		String mess;
	
		private static Font font;
		
		/* Game ID */
		@Override public int getID () { return NebulaState.Boss.id; }

	    @Override
	    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

	        // Call super method
	        super.init(gc, game);
	        switch (this.getDifficulty())
	        {
				case Easy:
						timerTir = 10000;
					break;

				case Hard:
						timerTir = 6000;
					break;

				case Insane:
						timerTir = 3500;
					break;

				default:
						timerTir = 5000;
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
	        invincibility = 0;
	        mess = "KIKOO";
	        
	        // EXPLOSION
	        explo = new SpriteSheet("ressources/images/boss/skybusterExplosion.png",320,240,0);
	        explo4 = new SpriteSheet("ressources/images/boss/explosion17.png",64,64,0);
	        
			explosion = new Animation(explo,75);
	    	explosion.setAutoUpdate(true);
	    	explosion.setLooping(false);
	    	explosion.stopAt(20);
	    	
			explosion2 = new Animation(explo,75);
	    	explosion2.setAutoUpdate(true);
	    	explosion2.setLooping(false);
	    	explosion2.stopAt(20);
	    	
	    	explosion3 = new Animation(explo,75);
	    	explosion3.setAutoUpdate(true);
	    	explosion3.setLooping(false);
	    	explosion3.stopAt(20);
	    	
	    	explosion4 = new Animation(explo4,25);
	    	explosion4.setAutoUpdate(true);
	    	explosion4.setLooping(false);
	    	explosion4.stopAt(26);
	    	
	    	laser = new SpriteSheet("ressources/images/boss/nebula-laser-animation.png",142,2100,0);
	    	phatLaser = new Animation(laser, 200);
	    	phatLaser.setAutoUpdate(true);
	    	phatLaser.setLooping(false);
	    	phatLaser.stopAt(12);
	    	
	    	light = new SpriteSheet("ressources/images/boss/light_004.png", 500, 500, 0);
	    	phatExplosion = new Animation(light, 60);
	    	phatExplosion.setAutoUpdate(true);
	    	phatExplosion.setLooping(false);
	    	phatExplosion.stopAt(25);
	    	
	    	// TIR SAUCER
	    	tir = new Tir(false);
	    	
	    	font = NebulaFont.getFont(FontName.Batmfa, FontSize.Medium);
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
	    	
	    	if(input.isKeyDown(Input.KEY_LCONTROL) && saucer.getMove())
	    	{
	    		xLaser = saucer.getX() + saucer.getImage().getWidth()/2 - phatLaser.getImage(0).getWidth()/2;
	    		yLaser = saucer.getY() - phatLaser.getImage(0).getHeight();
	    		unlockMove = 2000;
	    		if(phatLaser.isStopped())
	    		{
	    			phatLaser.restart();
	    		}
	    		
	    		if(saucer.getX() < boss.getX() + boss.getImage().getWidth() && saucer.getX() + saucer.getImage().getWidth() > boss.getX())
	    		{
	    			boss.hit();
	    			xPhat = boss.getX() + boss.getImage().getWidth()/2 - phatExplosion.getImage(0).getWidth()/2;
		    		yPhat = boss.getY() + boss.getImage().getHeight()/2 - phatExplosion.getImage(0).getHeight()/2;
		    		if(phatExplosion.isStopped())
		    		{
		    			phatExplosion.restart();
		    		}
		    		boss.getDestroy();
	    		}
	    		saucer.setMove(false);
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
	    			explosion.stop();
		    		explosion.setCurrentFrame(0);
		    		explosion.start();
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
	    			explosion2.stop();
		    		explosion2.setCurrentFrame(0);
		    		explosion2.start();
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
	    		xExplo3 = boss.getX() + boss.getImage().getWidth()/2 - explosion3.getImage(0).getWidth()/2;
	    		yExplo3 = boss.getY() + boss.getImage().getHeight()/2 - explosion3.getImage(0).getHeight()/2;
	    		explosion3.stop();
	    		explosion3.setCurrentFrame(0);
	    		explosion3.start();
	    		boss.loseLife();
	    		boss.getSon().play();
	    		boss.getDestroy();
	    		tir.setX(-100);
	    		tir.setY(-100);
	    	}
	    	
	    	if(saucer.touche(tir1) && invincibility <= 0)
	    	{
	    		saucer.decrementeVie();
	    		invincibility = 2000;
	    		xExplo4 = saucer.getX();
	    		yExplo4 = saucer.getY();
	    		explosion4.stop();
	    		explosion4.setCurrentFrame(0);
	    		explosion4.start();
	    		saucer.getSon().play();
	    		tir1.setX(-100);
	    		tir1.setY(-100);
	    		tir1.setTire(false);
	    	}
	    	
	    	if(saucer.touche(tir2) && invincibility <= 0)
	    	{
	    		saucer.decrementeVie();
	    		invincibility = 2000;
	    		xExplo4 = saucer.getX();
	    		yExplo4 = saucer.getY();
	    		explosion4.stop();
	    		explosion4.setCurrentFrame(0);
	    		explosion4.start();
	    		saucer.getSon().play();
	    		tir2.setX(-100);
	    		tir2.setY(-100);
	    		tir2.setTire(false);
	    	}
	    	
	    	if(saucer.hit(miss1) && invincibility <= 0)
	    	{
	    		saucer.decrementeVie();
	    		invincibility = 2000;
	    		xExplo = saucer.getX() + saucer.getImage().getWidth()/2 - explosion.getImage(0).getWidth()/2;
	    		yExplo = saucer.getY() + saucer.getImage().getWidth()/2 - explosion.getImage(0).getHeight()/2;
	    		explosion.stop();
	    		explosion.setCurrentFrame(0);
	    		explosion.start();
	    		miss1.getSonExplo().play();
	    		miss1.setX(-100);
	    		miss1.setY(-100);
	    		miss1.setTire(false);
	    		miss1.explode(timerTir);
	    	}
	    	
	    	if(saucer.hit(miss2) && invincibility <= 0)
	    	{
	    		saucer.decrementeVie();
	    		invincibility = 2000;
	    		xExplo2 = saucer.getX() + saucer.getImage().getWidth()/2 - explosion.getImage(0).getWidth()/2;
	    		yExplo2 = saucer.getY() + saucer.getImage().getWidth()/2 - explosion.getImage(0).getHeight()/2;
	    		explosion2.stop();
	    		explosion2.setCurrentFrame(0);
	    		explosion2.start();
	    		miss2.getSonExplo().play();
	    		miss2.setX(-100);
	    		miss2.setY(-100);
	    		miss2.setTire(false);
	    		miss2.explode(timerTir);
	    	}
	    	
	    	if(invincibility > 0)
	    	{
	    		invincibility -= delta;
	    	}
	    	
	    	if(boss.dead())
	    	{
	    		gameVictory();
	    	}
	    	
	    	if(saucer.dead())
	    	{
	    		gameDefeat();
	    	}
	    	
	    	mess = "Vie du BOSS : " + Integer.toString(boss.getVies());
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
	        g.drawAnimation(explosion3, xExplo3, yExplo3);
	        g.drawAnimation(phatLaser, xLaser, yLaser);
	        g.drawAnimation(phatExplosion, xPhat, yPhat);
	        
	        if(invincibility > 0)
	        {
	        	saucer.getImageInv().draw(saucer.getX(), saucer.getY());
	        }
	        else
	        {
	        	saucer.getImage().draw(saucer.getX(), saucer.getY());
	        }
	        g.drawAnimation(explosion, xExplo, yExplo);
	        g.drawAnimation(explosion2, xExplo2, yExplo2);
	        g.drawAnimation(explosion4, xExplo4, yExplo4);
	        
	        for(int i = 0; i < saucer.getVies(); i++)
	    	{
	    		saucer.getCoeur().draw(10 + i * saucer.getCoeur().getWidth(), gc.getHeight() - saucer.getCoeur().getHeight());
	    	}
	        
	        

	        font.drawString(
	            gc.getWidth() - font.getWidth(mess) - 10,
	            gc.getHeight() - font.getHeight(mess) - 10, mess, Color.white);
	    }
}
