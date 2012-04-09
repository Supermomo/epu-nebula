package nebula.core;

import nebula.core.NebulaGame.StateID;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class OptionMenuState extends BasicGameState
{
	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		//Font font = new UnicodeFont("data/lesson.ttf", 48, false, false);
		//t = new TextField(container, font, 150,70,500,35);

	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g)
			throws SlickException {
		
		g.scale(1.5f, 1.5f);
		g.setColor(Color.white);
		g.drawString("Option", 50, 50);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return StateID.OptionMenu.value;
	}

}
