package nebula.core.intro;

import nebula.core.NebulaGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Intro3Jeu extends BasicGameState
{

	Image image;
	float scale;
	float cpt = 0f;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		image = new Image("ressources/images/histoire/nebula fuite.png");
		scale = 1.0f;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		image.draw(0, 0, scale * container.getWidth(), scale * container.getHeight());
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
			cpt += 0.001f * delta;		
		}
		else
		{
			((NebulaGame)game).next(this.getID());
		}
	}

	@Override
	public int getID() 
	{
		return 5;
	}

}
