package nebula.core.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.StateID;

public class StateScore extends BasicGameState{
	
	private Font font;

	@Override
	public int getID() {
		return StateID.Score.value;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		font=((NebulaGame)game).getFont();	
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
			int x=container.getWidth()/2;
			int y=container.getHeight()/2;
			font.drawString(x, y, "Bravo !!", Color.white);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
	}
	
	
}
