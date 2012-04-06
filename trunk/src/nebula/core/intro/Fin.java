package nebula.core.intro;

import nebula.core.NebulaGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Fin extends BasicGameState
{

	Image image;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		image = new Image("ressources/images/miscellaneous/fin.png");		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		image.draw(0, 0, container.getWidth(), container.getHeight());
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_ENTER))
		{
			container.exit();
		}
	}

	@Override
	public int getID() 
	{
		return 12;
	}

}