package nebula.minigame.spaceInvaders;
import org.newdawn.slick.*;


public class spaceInvaders extends BasicGame{
	
	Image land = null;
	Boolean droite = true;
	// Gestion du joueur avec position x,y et echelle
	Image tank = null;
	float x = 400;
	float y = 480;
	float scale = 1.0f;
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
	
    public spaceInvaders(int nbEnnemis)
    {
        super("Space Invaders");
        this.nbEnnemis = nbEnnemis;
    }
 
    @Override
    public void init(GameContainer gc) 
			throws SlickException {
    	gc.setMinimumLogicUpdateInterval(20);
    	land = new Image("assets/spaceInvaders/fond.png");
    	tank = new Image("assets/spaceInvaders/Char.png");
    	tir = new Tir();
    	explo = new SpriteSheet("assets/spaceInvaders/explosion17.png",64,64,0);
    	explosion = new Animation();
    	explosion.setAutoUpdate(true);
    	for(int i =0; i < 5; i++)
    	{
    		for(int j=0; j < 5; j++)
    			explosion.addFrame(explo.getSprite(i,j),75);
    	}
    	explosion.setLooping(false);
    	int multiple = nbEnnemis/4;
        for(int i =0; i < 4; i++)
    	{
    		for(int j=0; j < multiple;j++)
    			ennemi[i][j] = new Ennemi(i*100,j*84);
    	}
    	
    }
 
    @Override
    public void update(GameContainer gc, int delta) 
			throws SlickException     
    {
    	Input input = gc.getInput();
    	
    	// =================== Gestion des deplacements ==========================
    	
    	if(input.isKeyDown(Input.KEY_RIGHT))
    	{
    		if(x < gc.getWidth() - tank.getWidth())
    			x+=0.4f * delta;
    	}
    	
    	if(input.isKeyDown(Input.KEY_LEFT))
    	{
    		if(x > 0)
    			x-=0.4f * delta;
    	}
    	
    	// ========================  GESTION DES TIRS ============================
    	if(input.isKeyDown(Input.KEY_SPACE))
    	{
    		if(tir.getY() < 0)
    		{
    			tir.setX(x);
    			tir.setY(y - tir.getImage().getHeight());
    		}
    	}
    	
    	if(tir.getY() > -100)
    		tir.setY(tir.getY() - 0.4f * delta);
    	
    	// ======================== ENNEMIS ==============================
    	for(int i=0; i < 4; i++)
    	{
    		for(int j=0; j < 2; j++)
    		{
	    		if(ennemi[i][j] != null)
	    		{
			    	if(droite)
			    	{
			    		if(ennemi[i][j].getX() < gc.getWidth() - 120)
			    		{
			    			ennemi[i][j].setX(ennemi[i][j].getX() + 0.2f * delta);
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
			    	else
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
	    	
	    			if(ennemi[i][j].touche(tir))
	    	    	{
	    	    		xExplo = ennemi[i][j].getX();
	    	    		yExplo = ennemi[i][j].getY();
	    	    		if(explosion.isStopped())
	    	    		{
	    	    			explosion.restart();
	    	    		}
	    	    		ennemi[i][j] = null;
	    	    		tir.setX(-100);
	    	    		tir.setY(-100);
	    	    		nbEnnemis--;
	    	    	}
	    		}
    		}
    	}
    	
    	if(nbEnnemis == 0)
    	{
    		
    		
    	}
 
    }
 
    public void render(GameContainer gc, Graphics g) 
			throws SlickException 
    {
    	land.draw(0, 0);
    	tank.draw(x, y, scale);
    	g.drawImage(tir.getImage(), tir.getX(), tir.getY());
    	for(int i =0; i < 4; i++)
    	{
    		for(int j=0; j < 2;j++)
    			if(ennemi[i][j] != null)
    			{
    				g.drawImage(ennemi[i][j].getImage(), ennemi[i][j].getX(), ennemi[i][j].getY());
    			}
    	}
    	g.drawAnimation(explosion, xExplo, yExplo);
    }
 
    public static void main(String[] args) 
			throws SlickException
    {
         AppGameContainer app = 
			new AppGameContainer(new spaceInvaders(8));
 
         app.setDisplayMode(800, 600, false);
         app.start();
         app.setTargetFrameRate(60);
    }
}