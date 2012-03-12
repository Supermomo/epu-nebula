package nebula.minigame.spaceInvaders;
import java.util.Random;

import org.newdawn.slick.*;


public class SpaceInvaders extends BasicGame{
	
	Image land = null;
	Boolean droite = true;
	// Gestion du joueur avec position x,y et echelle
	Tank tank = null;
	// Image de l'arme
	Tir tir = null;
	Tir tirEnnemi = null;
	// Image de l'ennemi
	Ennemi ennemi[][] = new Ennemi[4][2];
	// Image de la desctruction
	SpriteSheet explo = null;
	Animation explosion = null;
	float xExplo = -100;
	float yExplo = -100;
	int nbEnnemis;
	Image victoire = null;
	Random rand;
	int seuil = 99999;
	Image coeur = null;
	int score = 0;
	Image defaite = null;
	
    public SpaceInvaders(int nbEnnemis)
    {
        super("Space Invaders");
        this.nbEnnemis = nbEnnemis;
    }
 
    @Override
    public void init(GameContainer gc) 
			throws SlickException {
    	gc.setMinimumLogicUpdateInterval(20);
    	land = new Image("assets/spaceInvaders/fond.png");
    	tank = new Tank();
    	tir = new Tir(0);
    	tirEnnemi = new Tir(1);
    	victoire = new Image("assets/spaceInvaders/victoire.png");
    	explo = new SpriteSheet("assets/spaceInvaders/explosion17.png",64,64,0);
    	explosion = new Animation();
    	explosion.setAutoUpdate(true);
    	for(int i =0; i < 5; i++)
    	{
    		for(int j=0; j < 5; j++)
    			explosion.addFrame(explo.getSprite(i,j),75);
    	}
    	explosion.setLooping(false);
    	explosion.stopAt(24);
    	int multiple = nbEnnemis/4;
        for(int i =0; i < 4; i++)
    	{
    		for(int j=0; j < multiple;j++)
    			ennemi[i][j] = new Ennemi(i*100,j*84);
    	}
    	rand = new Random();
    	coeur = new Image("assets/spaceInvaders/coeur.png");
    	defaite = new Image("assets/spaceInvaders/defaite.png");
    }
 
    @Override
    public void update(GameContainer gc, int delta) 
			throws SlickException     
    {
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
    	
    	// ========================  GESTION DES TIRS ============================
    	if(input.isKeyDown(Input.KEY_SPACE))
    	{
    		if(tir.getY() < 0)
    		{
    			tir.setX(tank.getX());
    			tir.setY(tank.getY() - tir.getImage().getHeight());
    		}
    	}
    	
    	if(tir.getY() > -100)
    		tir.setY(tir.getY() - 0.4f * delta);
    	
    	if(tirEnnemi.getY() < 700 )
    		tirEnnemi.setY(tirEnnemi.getY() + 0.4f * delta);
    	
    	for(int i=0; i < 4; i++)
    	{
    		for(int j=0; j < 2; j++)
    		{
    			if(ennemi[i][j] != null)
				{
	    			float tire = rand.nextInt(100000);
	    			if(tire >= seuil) 
	    			{
    					tirEnnemi.setX(ennemi[i][j].getX());
        				tirEnnemi.setY(ennemi[i][j].getY() + tirEnnemi.getImage().getHeight());
        				seuil = 99999;
    				}
	    			else
	    			{
	    				seuil-= 1 * (8 - nbEnnemis);
	    			}
    			}
    		}
    	}
    	
    	// ======================== ENNEMIS ==============================
    	for(int i=0; i < 4; i++)
    	{
    		for(int j=0; j < 2; j++)
    		{
		    	if(droite)
		    	{
		    		if(ennemi[3-i][1-j] != null)
		    		{
			    		if(ennemi[3-i][1-j].getX() < gc.getWidth() - 120)
			    		{
			    			ennemi[3-i][1-j].setX(ennemi[3-i][1-j].getX() + 0.2f * delta);
			    		}
			    		else
			    		{
			    			droite = false;
			    			for(int lol=0; lol < 4; lol++)
			    	    	{
			    	    		for(int tg=0; tg < 2; tg++)
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
			    			ennemi[i][j].setX(ennemi[i][j].getX() - 0.2f * delta);
			    		}
			    		else
			    		{
			    			droite = true;
			    			for(int lol=0; lol < 4; lol++)
			    	    	{
			    	    		for(int tg=0; tg < 2; tg++)
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
    	    		xExplo = ennemi[i][j].getX();
    	    		yExplo = ennemi[i][j].getY();
    	    		if(explosion.isStopped())
    	    		{
    	    			explosion.restart();
    	    		}
    	    		nbEnnemis--;
    	    		score += ennemi[i][j].getPts();
    	    		ennemi[i][j] = null;
    	    		tir.setX(-100);
    	    		tir.setY(-100);
    	    	}
    		}
    	}
    	
    	if(tank.touche(tirEnnemi))
    	{
    		tank.decrementeVie();
    		xExplo = tank.getX();
    		yExplo = tank.getY();
    		if(explosion.isStopped())
    		{
    			explosion.restart();
    		}
    		tirEnnemi.setX(-100);
    		tirEnnemi.setY(-100);
    	}
    	
    	if(nbEnnemis == 0)
    	{
    		gc.pause();
    	}
    	
    	if(tank.vies == 0)
    	{
    		gc.pause();
    	}
    }
 
    public void render(GameContainer gc, Graphics g) 
			throws SlickException 
    {
    	land.draw(0, 0);
    	g.drawImage(tank.getImage(), tank.getX(), tank.getY());
    	g.drawImage(tir.getImage(), tir.getX(), tir.getY());
    	g.drawImage(tirEnnemi.getImage(), tirEnnemi.getX(), tirEnnemi.getY());
    	for(int i =0; i < 4; i++)
    	{
    		for(int j=0; j < 2;j++)
    			if(ennemi[i][j] != null)
    			{
    				g.drawImage(ennemi[i][j].getImage(), ennemi[i][j].getX(), ennemi[i][j].getY());
    			}
    	}
    	if(nbEnnemis == 0)
    		victoire.draw(100,250);
    	if(tank.vies == 0)
    		defaite.draw(100,250);
    	g.drawAnimation(explosion, xExplo, yExplo);
    	for(int i = 0; i < tank.vies; i++)
    	{
    		coeur.draw(10 + i * coeur.getWidth(), gc.getHeight() - coeur.getHeight());
    	}
    }
 
    public static void main(String[] args) 
			throws SlickException
    {
         AppGameContainer app = 
			new AppGameContainer(new SpaceInvaders(8));
 
         app.setDisplayMode(800, 600, false);
         app.setTargetFrameRate(600);
         app.start();
         
    }
}