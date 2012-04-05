package nebula.core.intro;

import nebula.core.NebulaGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class IntroJeu extends BasicGameState
{

	Image image, cadre;
	float scale;
	float cpt = 0f;
	float x = -1000,y = -1000;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		image = new Image("ressources/images/histoire/nebula_intro.jpg");
		cadre = new Image("ressources/images/miscellaneous/cadre.png");
		scale = 12.0f;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		image.draw(0, 0, scale * container.getWidth(), scale * container.getHeight());
		cadre.draw(x, y, container.getWidth()*0.7f, container.getHeight() * 0.25f);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		if(scale <= 1)
		{
			if(cpt < 2)
			{
				cpt += 0.001f * delta;		
			}
			else
			{
				x = container.getWidth() * 0.15f;
				y = container.getHeight() * 0.7f;
				if(cpt < 10)
				{
					cpt += 0.001f * delta;
				}
				else
				{
					((NebulaGame)game).next(this.getID(),1);
				}
					
			}
		}
		else
		{
			scale -= 0.0025f * delta;
		}
	}

	@Override
	public int getID() 
	{
		return 3;
	}

}
