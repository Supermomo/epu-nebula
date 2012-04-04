package nebula.minigame.spaceInvaders;

import nebula.core.NebulaGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class IntroSpaceInvaders extends BasicGameState
{

	Image image;
	float scale;
	float cpt = 0f;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		container.setTargetFrameRate(2);
		image = new Image("assets/images/spaceInvaders/histoire/nebula_intro.jpg");
		scale = 12.0f;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		image.draw(0, 0, scale);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		if(scale <= 1)
		{
			if(cpt < 10)
			{
				cpt += 0.005f * delta;				
			}
			else
			{
				((NebulaGame)game).next(this.getID());
			}
		}
		else
		{
			scale -= 0.005f * delta;
		}
	}

	@Override
	public int getID() 
	{
		return 3;
	}

}
