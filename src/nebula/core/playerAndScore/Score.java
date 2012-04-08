package nebula.core.playerAndScore;

public class Score {
	
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
