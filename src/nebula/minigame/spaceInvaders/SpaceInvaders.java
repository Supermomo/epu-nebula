package nebula.minigame.spaceInvaders;

import java.util.Random;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.helper.Collision;
import nebula.core.helper.Utils;
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


public class SpaceInvaders extends AbstractMinigameState {

	int initialNbEnnemis = 8;
	int chanceTir;
	int nbTir;
	int multiple;
	int invincibility;
	Boolean droite = true;
	// Gestion du joueur avec position x,y et echelle
	Tank tank = null;
	// Image de l'arme
	Tir tir = null;
	Tir tirEnnemi[];
	// Image de l'ennemi
	Ennemi ennemi[][];
	// Image de la desctruction
	SpriteSheet explo = null;
	Animation explosion = null;
	float xExplo;
	float yExplo;
	// Le bonus
	SpriteSheet nyan = null;
	Animation nyanCat = null;
	float xnyan;
	float ynyan;
	int nbEnnemis;
	Random rand;
	int seuil;
	Image coeur = null;
	int scoreSpaceInvaders;
	boolean bonus;
	int time;
	Sound sonNyan;
	/* Game ID */
	@Override public int getID () { return NebulaState.SpaceInvaders.id; }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        // Call super method
        super.init(gc, game);
        switch (this.getDifficulty())
        {
			case Easy:
				this.initialNbEnnemis = 4;
				this.chanceTir = 75;
				this.nbTir = 1;
				scoreSpaceInvaders = 2000;
				break;

			case Hard:
				this.initialNbEnnemis = 12;
				this.chanceTir = 75;
				this.nbTir = 6;
				scoreSpaceInvaders = 4000;
				break;

			case Insane:
				this.initialNbEnnemis = 16;
				this.chanceTir = 50;
				this.nbTir = 10;
				scoreSpaceInvaders = 5000;
				break;

			default:
				this.initialNbEnnemis = 8;
				this.chanceTir = 75;
				this.nbTir = 3;
				scoreSpaceInvaders = 3000;
				break;
		}
        seuil = chanceTir;
        invincibility = 0;
        bonus = false;
        time = 0;
        xnyan = -100;
    	ynyan = -100;
    	sonNyan = new Sound("ressources/sons/spaceInvaders/nyan.ogg");


    	xExplo = -100;
    	yExplo = -100;
    	xnyan = -100;
    	ynyan = -100;

    	tank = new Tank(gc.getWidth(),gc.getHeight());
    	tank.setX(gc.getWidth()/2 - tank.getImage().getWidth()/2);
    	tank.setY(gc.getHeight() - 2*tank.getImage().getHeight());
    	tir = new Tir(0);
    	tirEnnemi = new Tir[nbTir];
    	for(int i = 0; i < nbTir; i++)
    	{
    		tirEnnemi[i] = new Tir(1);
    	}
    	explo = new SpriteSheet("ressources/images/spaceInvaders/explosion17.png",64,64,0);
    	explosion = new Animation(explo,25);
    	explosion.setAutoUpdate(true);
    	explosion.setLooping(false);
    	explosion.stopAt(26);
    	// ============= NYAN CAT ===============
    	nyan = new SpriteSheet("ressources/images/spaceInvaders/nyansprites.png",171,72,0);
    	nyanCat = new Animation(nyan,480);
    	nyanCat.setAutoUpdate(true);
    	nyanCat.setLooping(true);
    	nyanCat.stopAt(13);
    	multiple = initialNbEnnemis/4;
    	ennemi = new Ennemi[4][multiple];
        for(int i =0; i < 4; i++)
    	{
    		for(int j=0; j < multiple;j++)
    			ennemi[i][j] = new Ennemi(i*140,j*120);
    	}
    	rand = new Random();
    	coeur = new Image("ressources/images/spaceInvaders/coeur.png");
    	nbEnnemis=initialNbEnnemis;

    	// Music and help
    	initHelp("ressources/sons/spaceInvaders/help.ogg");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);
        time += delta;
    	Input input = gc.getInput();

    	// =================== Gestion des deplacements ==========================
    	if(input.isKeyDown(Input.KEY_RIGHT))
    	{
    		if(tank.getX() < gc.getWidth() - tank.getImage().getWidth())
    			tank.setX(tank.getX() + (0.4f * delta));
    	}

    	if(input.isKeyDown(Input.KEY_LEFT))
    	{
    		if(tank.getX() > 0)
    			tank.setX(tank.getX() - (0.4f * delta));
    	}
    	// =========================== BONUS ====================================
    	float alea = rand.nextInt(100);

    	if (alea == 42 && !bonus && time > 12000)
    	{
    		nyanCat.stop();
    		nyanCat.setCurrentFrame(0);
    		nyanCat.start();
    		xnyan = 0;
    		ynyan = 0;
    		bonus = true;
    	}

    	if(bonus)
    	{
    		xnyan += 0.35f * delta;
    	}

    	if(bonus && Collision.rectangle(xnyan,ynyan, nyanCat.getWidth(), nyanCat.getHeight(), tir.getX(), tir.getY(), tir.getImage().getWidth(), tir.getImage().getHeight()/3))
    	{
    		nyanCat.stop();
    		xnyan = -100;
    		ynyan = -100;
    		tank.addVie();
    		sonNyan.play();
    	}

    	// ========================  GESTION DES TIRS ============================
    	if(input.isKeyDown(Input.KEY_SPACE))
    	{
    		tank.tirer(tir);
    	}

    	if(tir.getY() > -100)
    		tir.setY(tir.getY() - 0.4f * delta);

    	for(int t=0; t < nbTir; t++)
    	{
    		if(tirEnnemi[t].getY() < gc.getHeight())
        		tirEnnemi[t].setY(tirEnnemi[t].getY() + 0.4f * delta);

        	for(int i=0; i < multiple; i++)
        	{
        		for(int j=0; j < 4; j++)
        		{
        			if(ennemi[j][i] != null)
    				{
    	    			float tire = rand.nextInt(100);
    	    			if(tire >= seuil)
    	    			{
        					ennemi[j][i].tirer(tirEnnemi[t],gc);
        					seuil = chanceTir;
        				}
    	    			else
    	    			{
    	    				seuil -= (initialNbEnnemis - nbEnnemis);
    	    			}
        			}
        		}
        	}
    	}


    	// ========================== ENNEMIS ==============================

    	float hip = 0.2f * delta * (((initialNbEnnemis - nbEnnemis)/12)+1);

    	for(int i=0; i < 4; i++)
    	{
    		for(int j=0; j < multiple; j++)
    		{
		    	if(droite)
		    	{
		    		if(ennemi[3-i][multiple-1-j] != null)
		    		{
			    		if(ennemi[3-i][multiple-1-j].getX() < gc.getWidth() - 120)
			    		{
			    			ennemi[3-i][multiple-1-j].setX(ennemi[3-i][multiple-1-j].getX() + hip);
			    		}
			    		else
			    		{
			    			droite = false;
			    			for(int lol=0; lol < 4; lol++)
			    	    	{
			    	    		for(int tg=0; tg < multiple; tg++)
			    	    		{
			    	    			if(ennemi[lol][tg] != null)
			    	    				ennemi[lol][tg].setY(ennemi[lol][tg].getY() + ennemi[lol][tg].getImage().getHeight());
			    	    		}
			    	    	}
			    		}
		    		}
		    	}
		    	else
		    	{
		    		if(ennemi[i][j] != null)
		    		{
			    		if(ennemi[i][j].getX() > 40)
			    		{
			    			ennemi[i][j].setX(ennemi[i][j].getX() - hip);
			    		}
			    		else
			    		{
			    			droite = true;
			    			for(int lol=0; lol < 4; lol++)
			    	    	{
			    	    		for(int tg=0; tg < multiple; tg++)
			    	    		{
			    	    			if(ennemi[lol][tg] != null)
			    	    				ennemi[lol][tg].setY(ennemi[lol][tg].getY() + ennemi[lol][tg].getImage().getHeight());
			    	    		}
			    	    	}
			    		}
		    		}
		    	}

	    		if(ennemi[i][j] != null && ennemi[i][j].touche(tir))
    	    	{
    	    		xExplo = ennemi[i][j].getX()+ ennemi[i][j].getImage().getWidth()/2;
    	    		yExplo = ennemi[i][j].getY()+ennemi[i][j].getImage().getHeight()/2;
    	    		if(explosion.isStopped())
    	    		{
    	    			explosion.restart();
    	    		}
    	    		nbEnnemis--;
    	    		ennemi[i][j].getSon().play();
    	    		ennemi[i][j] = null;
    	    		tir.setX(-100);
    	    		tir.setY(-100);
    	    	}

	    		if(ennemi[i][j] != null && ennemi[i][j].getY() + ennemi[i][j].getImage().getHeight() >= tank.getY())
	    		{
	    			tank.kill();
	    		}

    		}
    	}

    	for(int i = 0; i < nbTir; i++)
    	{
	    	if(tank.touche(tirEnnemi[i]) && invincibility <= 0)
	    	{
	    		tank.decrementeVie();
	    		invincibility = 2 * 1000;
	    		xExplo = tank.getX()+tank.getImage().getWidth()/2;
	    		yExplo = tank.getY()+tank.getImage().getHeight()/2;
	    		if(explosion.isStopped())
	    		{
	    			explosion.restart();
	    		}

	    		tank.getSon().play();
	    		tirEnnemi[i].setX(-100);
	    		tirEnnemi[i].setY(-100);
	    		//feu.update(ps, 20);
	    	}
    	}

    	if (invincibility > 0)
        {
            invincibility -= delta;
        }

    	if(nbEnnemis == 0)
    	{
    		//sVictoire.play();
    		//gc.pause();

    		if(scoreSpaceInvaders - 1000 > scoreSpaceInvaders + ((initialNbEnnemis - tank.getTirEffectue()) * (50 * (3.0f/tank.getVies()))) - (3 - tank.getVies())*250)
    			this.score = scoreSpaceInvaders - 1000;
    		else
    			this.score = scoreSpaceInvaders + ((initialNbEnnemis - tank.getTirEffectue()) * (int)(50 * (3.0f/tank.getVies())) - (3 - tank.getVies())*250);

    		this.score = Utils.checkScoreRange(this.score, difficulty);
    		gameVictory();
    	}

    	if(tank.dead())
    	{
    		gameDefeat();
    	}
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException
    {

        // Call super method
        super.render(gc, game, g);
        if(invincibility > 0)
        {
        	g.drawImage(tank.getImageInv(), tank.getX(), tank.getY());
        }
        else
        {
        	g.drawImage(tank.getImage(), tank.getX(), tank.getY());
        }
    	g.drawImage(tir.getImage(), tir.getX(), tir.getY());
    	for(int i =0; i < nbTir; i++)
    	{
    		g.drawImage(tirEnnemi[i].getImage(), tirEnnemi[i].getX(), tirEnnemi[i].getY());
    	}

    	for(int i =0; i < 4; i++)
    	{
    		for(int j=0; j < initialNbEnnemis/4;j++)
    			if(ennemi[i][j] != null)
    			{
    				g.drawImage(ennemi[i][j].getImage(), ennemi[i][j].getX(), ennemi[i][j].getY());
    			}
    	}
    	g.drawAnimation(explosion, xExplo-explosion.getWidth()/2, yExplo-explosion.getHeight()/2);
    	g.drawAnimation(nyanCat, xnyan, ynyan);
    	for(int i = 0; i < tank.getVies(); i++)
    	{
    		coeur.draw(10 + i * coeur.getWidth(), gc.getHeight() - coeur.getHeight());
    	}
    }
}