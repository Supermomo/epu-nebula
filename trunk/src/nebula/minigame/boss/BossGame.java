package nebula.minigame.boss;

import java.util.ArrayList;

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
		ArrayList<Tir> tir;
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
		int kamoulox;
		float timeM;
		float timeL;
		float timeK;
		float timeV;
		float time;
		int unlockMove;
		int invincibility;
		float coeff;
		String mess;
		String kamou;
	
		private static Font font;
		
		/* Game ID */
		@Override public int getID () { return NebulaState.Boss.id; }

	    @Override
	    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

	        // Call super method
	        super.init(gc, game);
	        saucer = new Vaisseau(gc.getWidth(),gc.getHeight());
	        switch (this.getDifficulty())
	        {
				case Easy:
						timerTir = 10000;
						kamoulox = 5000;
						saucer.setVies(5);
						this.score = 2000;
						coeff = 9.7f;
					break;

				case Hard:
						timerTir = 6000;
						kamoulox = 10000;
						this.score = 4000;
						coeff = 12.7f;
					break;

				case Insane:
						timerTir = 3500;
						kamoulox = 10000;
						this.score = 5000;
						coeff = 13.7f;
					break;

				default:
						timerTir = 5000;
						kamoulox = 10000;
						saucer.setVies(4);
						this.score = 3000;
						coeff = 11.2f;
					break;
			}
	        
	        boss = new Boss();
	        boss.setX(gc.getWidth()/2 - boss.getImage().getWidth()/2);
	        boss.setY(0);
	        // TIRS BOSS
	        timeT = timerTir;
	        timeM = timerTir * 1.77f;
	        timeK = kamoulox;
	        timeL = 1000000000;
	        tir1 = new Tir(true);
	        tir2 = new Tir(true);
	        miss1 = new Missile(4000);
	        miss2 = new Missile(4000);
	        invincibility = 0;
	        mess = "";
	        timeV = 1000;
	        time = 0;
	        
	        // EXPLOSION
	        explo = new SpriteSheet("ressources/images/boss/skybusterExplosion.png",320,240,0);
	        explo4 = new SpriteSheet("ressources/images/boss/explosion17.png",64,64,0);
	        
			explosion = new Animation(explo,75);
	    	explosion.setAutoUpdate(true);
	    	explosion.setLooping(false);
	    	explosion.stopAt(20);
	    	xExplo = -1000;
			yExplo = -1000;
	    	
			explosion2 = new Animation(explo,75);
	    	explosion2.setAutoUpdate(true);
	    	explosion2.setLooping(false);
	    	explosion2.stopAt(20);
	    	xExplo2 = -1000;
			yExplo2 = -1000;
	    	
	    	explosion3 = new Animation(explo,75);
	    	explosion3.setAutoUpdate(true);
	    	explosion3.setLooping(false);
	    	explosion3.stopAt(20);
	    	xExplo3 = -1000;
			yExplo3 = -1000;
	    	
	    	explosion4 = new Animation(explo4,25);
	    	explosion4.setAutoUpdate(true);
	    	explosion4.setLooping(false);
	    	explosion4.stopAt(26);
	    	xExplo4 = -1000;
			yExplo4 = -1000;
	    	
	    	laser = new SpriteSheet("ressources/images/boss/nebula-laser-animation.png",142,2100,0);
	    	phatLaser = new Animation(laser, 200);
	    	phatLaser.setAutoUpdate(true);
	    	phatLaser.setLooping(false);
	    	phatLaser.stopAt(12);
	    	xLaser = -1000;
			yLaser = -1000;
	    	
	    	light = new SpriteSheet("ressources/images/boss/light_004.png", 300, 300, 0);
	    	phatExplosion = new Animation(light, 60);
	    	phatExplosion.setAutoUpdate(true);
	    	phatExplosion.setLooping(false);
	    	phatExplosion.stopAt(25);
	    	xPhat = -1000;
			yPhat= -1000;
	    	
	    	// TIR SAUCER
	    	tir = new ArrayList<Tir>();
	    	
	    	font = NebulaFont.getFont(FontName.Batmfa, FontSize.Medium);
	    	initMusic("ressources/sons/boss/music.ogg", 0.6f, true);
	    }

	    @Override
	    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException
	    {
	        // Call super method
	        super.update(gc, game, delta);
	        
	        timeT -= delta;
	        timeM -= delta;
	        timeL -= delta;
	        timeK -= delta;
	        timeV -= delta;
	        time += delta;
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
	    	if(input.isKeyDown(Input.KEY_SPACE) && timeV <= 0)
	    	{
	    		timeV = 1500;
	    		saucer.tirer(tir);
	    	}
	    	
	    	if(input.isKeyDown(Input.KEY_LCONTROL) && saucer.getMove() && timeK < 0)
	    	{
	    		timeK = kamoulox;
	    		xLaser = saucer.getX() + saucer.getImage().getWidth()/2 - phatLaser.getImage(0).getWidth()/2;
	    		yLaser = saucer.getY() - phatLaser.getImage(0).getHeight() + 100;
	    		unlockMove = 2000;
	    		if(phatLaser.isStopped())
	    		{
	    			phatLaser.restart();
	    		}
	    		
	    		if(saucer.getX() < boss.getX() + boss.getImage().getWidth() && saucer.getX() + saucer.getImage().getWidth() > boss.getX())
	    		{
	    			xPhat = boss.getX() + boss.getImage().getWidth()/2 - phatExplosion.getImage(0).getWidth()/2;
		    		yPhat = boss.getY() + boss.getImage().getHeight()/2 - phatExplosion.getImage(0).getHeight()/2;
		    		timeL = 2000;
	    		}
	    		saucer.setMove(false);
	    	}
	    	
	    	if(timeL <= 0)
	    	{
	    		if(phatExplosion.isStopped())
	    		{
	    			phatExplosion.restart();
	    		}
	    		boss.hit();
	    		boss.getDestroy();
	    		timeL = 1000000000;
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
	    	
	    	for(int i = 0; i < tir.size(); i++)
	    	{
	    		if(tir.get(i).getY() > -100)
	    			tir.get(i).setY(tir.get(i).getY() - 0.4f * delta);
		    	
		    	if(boss.touche(tir.get(i)))
		    	{
		    		xExplo3 = boss.getX() + boss.getImage().getWidth()/2 - explosion3.getImage(0).getWidth()/2;
		    		yExplo3 = boss.getY() + boss.getImage().getHeight()/2 - explosion3.getImage(0).getHeight()/2;
		    		explosion3.stop();
		    		explosion3.setCurrentFrame(0);
		    		explosion3.start();
		    		boss.loseLife();
		    		boss.getSon().play();
		    		boss.getDestroy();
		    		tir.remove(i);
		    	}
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
	    		if(1000 > (time/1000) * coeff - saucer.getVies() * 200)
	    		{
	    			this.score -= ((time/1000) * coeff) - saucer.getVies() * 200;
	    		}
	    		else
	    		{
	    			this.score -= 1000;
	    		}
	    		gameVictory();
	    	}
	    	
	    	if(saucer.dead())
	    	{
	    		gameDefeat();
	    	}
	    	
	    	mess = "Vie du BOSS : " + Integer.toString(boss.getVies());
	    	kamou = "KAMOULOX PRET";
	    }

	    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException
		{
	        // Call super method
	        super.render(gc, game, g);
	        g.drawAnimation(phatLaser, xLaser, yLaser);
	        boss.getImage().draw(boss.getX(), boss.getY());
	        tir1.getImage().draw(tir1.getX(),tir1.getY());
	        tir2.getImage().draw(tir2.getX(),tir2.getY());
	        for(int i = 0; i < tir.size(); i ++)
	        {
	        	tir.get(i).getImage().draw(tir.get(i).getX(), tir.get(i).getY());
	        }
	        miss1.getImage().draw(miss1.getX(), miss1.getY());
	        miss2.getImage().draw(miss2.getX(), miss2.getY());
	        g.drawAnimation(explosion3, xExplo3, yExplo3);
	        
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
	        
	        if(timeK < 0)
	        {
	        	font.drawString(
		            gc.getWidth() - font.getWidth(kamou)/2 - gc.getWidth()/2,
		            gc.getHeight() - font.getHeight(kamou) - 10, kamou, Color.red);
	        }
	        

	        font.drawString(
	            gc.getWidth() - font.getWidth(mess) - 10,
	            gc.getHeight() - font.getHeight(mess) - 10, mess, Color.white);
	    }
}
