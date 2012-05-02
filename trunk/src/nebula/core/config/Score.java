package nebula.core.config;

import java.io.Serializable;

public class Score implements Serializable{

	private static final long serialVersionUID = -7646321226283725678L;
	
	public MiniGamesEnum GameEnum;
	private int score;
	
	public Score(int scoreGame, MiniGamesEnum game){
		score=scoreGame;
		GameEnum=game;
	}
	
	public int getScore(){
		return score;
	}

}
