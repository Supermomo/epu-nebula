package nebula.game.spaceInvaders;
import org.newdawn.slick.*;

public class spaceInvaders extends BasicGame{
	
	Image land = null;
	// Gestion du joueur avec position x,y et echelle
	Image tank = null;
	float x = 400;
	float y = 480;
	float scale = 1.0f;
	// Image de l'arme
	Image tir = null;
	float xTir = -100;
	float yTir = -100;
	// Image de l'ennemi
	Image ennemi = null;
	float xEnnemi = 20;
	float yEnnemi = 50;
	boolean droite = true;
	// Image de la desctruction
	Image explo = null;
	float xExplo = -100;
	float yExplo = -100;
	int time = 0;
 
    public spaceInvaders()
    {
        super("Space Invaders");
    }
 
    @Override
    public void init(GameContainer gc) 
			throws SlickException {
    	land = new Image("assets/spaceInvaders/fond.png");
    	tank = new Image("assets/spaceInvaders/Char.png");
    	tir = new Image("assets/spaceInvaders/tir.png");
    	ennemi = new Image("assets/spaceInvaders/ship.png");
    	explo = new Image("assets/spaceInvaders/explo.png");
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
    		if(yTir < 0)
    		{
    			xTir = x;
    			yTir = y - tir.getHeight();
    		}
    	}
    	
    	if(yTir > -100)
    		yTir -= 0.4f * delta;
    	
    	// ======================== ENNEMIS ==============================
    	if(droite)
    	{
    		if(xEnnemi < gc.getWidth() - 120)
    			xEnnemi += 0.2f * delta;
    		else
    			droite = false;
    	}
    	else
    	{
    		if(xEnnemi > 40)
    			xEnnemi -= 0.2f * delta;
    		else
    			droite = true;
    	}
    	
    	if(xTir > xEnnemi && xTir < (xEnnemi + ennemi.getWidth())
    			&& yTir <= yEnnemi)
    	{
    		xExplo = xEnnemi;
    		yExplo = yEnnemi;
    		time = 100;
    		xEnnemi = -100;
    		yEnnemi = -100;
    		xTir = -100;
    		yTir = -100; 
    	}
    	
    	if(time >= 0)
    	{
    		time --;
    	}
    	else
    	{
    		xExplo = -100;
    		yExplo = -100;
    	}
    		
 
    }
 
    public void render(GameContainer gc, Graphics g) 
			throws SlickException 
    {
    	land.draw(0, 0);
    	tank.draw(x, y, scale);
    	tir.draw(xTir, yTir, scale);
    	ennemi.draw(xEnnemi, yEnnemi, scale);
    	explo.draw(xExplo, yExplo, scale);
    }
 
    public static void main(String[] args) 
			throws SlickException
    {
         AppGameContainer app = 
			new AppGameContainer(new spaceInvaders());
 
         app.setDisplayMode(800, 600, false);
         app.start();
    }
}