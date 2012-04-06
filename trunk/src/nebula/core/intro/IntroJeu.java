package nebula.core.intro;

import nebula.core.NebulaGame;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class IntroJeu extends BasicGameState
{

	Image image, cadre;
	StringBuilder s;
	float scale;
	float cpt = 0f;
	float x = -1000,y = -1000;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		image = new Image("ressources/images/histoire/nebula_intro.jpg");
		cadre = new Image("ressources/images/miscellaneous/cadre.png");
		scale = 1.0f;
		((NebulaGame) game).getUFont().loadGlyphs();
		s = new StringBuilder();
		s.append("Bidibop est un alien. Et aujourd'hui il a 10 ans.\n");
		s.append("C'est a dire qu'aujourd'hui il peut conduire son\n");
		s.append("propre vaisseau spatial !\n");
		s.append("Et ça tombe bien car Bidibop construit depuis\n");
		s.append("2 ans une superbe soucoupe rouge avec son papa.");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		image.draw(0, 0, scale * container.getWidth(), scale * container.getHeight());
		cadre.draw(x, y, container.getWidth()*0.90f, container.getHeight() * 0.3f);
		((NebulaGame)game).getUFont().drawString(x/0.55f + container.getWidth()*0.01f, y + container.getHeight()*0.03f, s.toString());
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
				x = container.getWidth() * 0.05f;
				y = container.getHeight() * 0.7f;
				if(cpt < 8)
				{
					cpt += 0.001f * delta;
				}
				else if(cpt < 10)
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
