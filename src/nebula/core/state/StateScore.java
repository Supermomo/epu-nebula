package nebula.core.state;

import java.awt.RenderingHints.Key;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.StateID;
import nebula.core.NebulaGame.TransitionType;
import nebula.minigame.Minigame;

public class StateScore extends BasicGameState{
	
	private Font font;
	private int lastState=0;
	private int score=0;
	private String message= "Jeu terminé";
	
	
	@Override
	public int getID() {
		return StateID.Score.value;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		font=((NebulaGame)game).getFont();	
		score=0;
		lastState=0;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
			int x=container.getWidth()/2;
			int y=container.getHeight()/2;
			font.drawString(x, y, message, Color.white);
			font.drawString(x, y+100, "Score : "+score, Color.white);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		Input input=container.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_ESCAPE)){
			if(NebulaGame.isScenario){
				((NebulaGame)game).next(lastState);
			}
			else{
				((NebulaGame)game).next(getID(), TransitionType.HorizontalSplit);
			}
		}
	}

	public int getLastState() {
		return lastState;
	}

	public void setLastState(int lastState) {
		this.lastState = lastState;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
