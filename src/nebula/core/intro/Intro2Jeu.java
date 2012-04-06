package nebula.core.intro;

import nebula.core.NebulaGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Intro2Jeu extends BasicGameState
{

	Image image, cadre, tete;
	StringBuilder s;
	float scale;
	float cpt = 0f;
	float x = -1000,y = -1000;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		image = new Image("ressources/images/histoire/attaqueNebula.png");
		scale = 1.0f;
		cadre = new Image("ressources/images/miscellaneous/cadre.png");
		tete = new Image("ressources/images/histoire/father.png");
		((NebulaGame) game).getUFont().loadGlyphs();
		s = new StringBuilder();
		s.append("Bidibop, vite ! Dans ton vaisseau ! Il faut \n");
		s.append("que tu partes chercher de l'aide !\n");
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		image.draw(0, 0, scale * container.getWidth(), scale * container.getHeight());
		cadre.draw(x, y, container.getWidth()*0.8f, container.getHeight() * 0.3f);
		tete.draw(0, y, container.getWidth()*0.15f, container.getHeight() * 0.3f);
		((NebulaGame)game).getUFont().drawString(x + container.getWidth()*0.05f, y + container.getHeight()*0.03f, s.toString());
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_ENTER))
		{
			((NebulaGame)game).next(this.getID(),1);
		}
		
		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			((NebulaGame)game).enterState(0);
		}
		
		if(cpt < 3)
		{
			cpt += delta * 0.001f;
		}
		else
		{
			x = container.getWidth() * 0.2f;
			y = container.getHeight() * 0.7f;
		}
	}

	@Override
	public int getID() 
	{
		return 4;
	}

}
