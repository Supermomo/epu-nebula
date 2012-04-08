package nebula.core.playerAndScore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GeneralScore implements Serializable {

	private static final long serialVersionUID = -4773432590321886491L;
	private HashMap<String, PlayerScoreDuo> scoreList;
	private String generalScorePath = "ressources/data/generalScore.data";

	public GeneralScore() {
		try {
			load();
		} catch (Exception e) {
			System.out.println("load failed");
			scoreList = new HashMap<String, PlayerScoreDuo>();
		}
	}

	private void load() throws Exception {
		FileInputStream fos = new FileInputStream(generalScorePath);
		ObjectInputStream out = new ObjectInputStream(fos);
		scoreList = (HashMap<String, PlayerScoreDuo>) out.readObject();
		out.close();
	}

	public void save() {
		FileOutputStream fis = null;
		ObjectOutputStream oit = null;
		try {
			fis = new FileOutputStream(generalScorePath);
			oit = new ObjectOutputStream(fis);
			oit.writeObject(scoreList);
			oit.flush();
			oit.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Add the score if there is no previous score for that player or that game
	 * or the this new score is higher
	 * <BR>
	 * If the player or the game
	 * 
	 * @param playerName
	 * @param sco
	 */
	public void addScore(String playerName, Score sco) {

		if (scoreList.containsKey(playerName)) {
			
			if(scoreList.get(playerName).playerScore.containsKey(sco.GameEnum)){
				
				if(sco.getScore() > scoreList.get(playerName).playerScore.get(
						sco.GameEnum).getScore()){
				//Replace the score if the new one is higher
				scoreList.get(playerName).playerScore.put(sco.GameEnum, sco);
				}
			}
			else{
				//If there is no score for that game, we add this one
				scoreList.get(playerName).playerScore.put(sco.GameEnum, sco);
			}

			//Do nothing when there already is a score for that player, that game and 
			//that score is higher than the new one
		}
		else{
			//if that player do not exist, we create it
			PlayerScoreDuo pl = new PlayerScoreDuo(playerName);
			pl.playerScore.put(sco.GameEnum, sco);
			scoreList.put(playerName, pl);
		}
	}
	

	public String toString() {
		String s = "";
		for (String str : scoreList.keySet()) {
			s = s +"\n"+ scoreList.get(str).toString() + "\n";
		}
		return s;
	}

	private class PlayerScoreDuo implements Serializable {

		private static final long serialVersionUID = 5636941789350153178L;
		
		public String playerName;
		public HashMap<MiniGamesEnum, Score> playerScore;

		public PlayerScoreDuo(String name) {
			playerName = name;
			playerScore = new HashMap<MiniGamesEnum, Score>();
		}

		public String toString() {
			String s = "Name : " + playerName + "\n";
			for (MiniGamesEnum en : playerScore.keySet()) {
				s = s + "         " + en.toString() + " : "
						+ playerScore.get(en).getScore() + "\n";
			}
			return s;
		}
	}
	
//    public static void main(String args[]){
//		GeneralScore gs=new GeneralScore();
//		/*gs.addScore("Gwenn", new Score(42, MiniGamesEnum.SPACEINVADERS));
//		gs.addScore("Gwenn", new Score(42, MiniGamesEnum.SPACEINVADERS));
//		gs.addScore("Gwenn", new Score(666, MiniGamesEnum.SPACEINVADERS));
//		gs.addScore("Gwenn2", new Score(42, MiniGamesEnum.SPACEINVADERS));
//		gs.addScore("Gwenn", new Score(410, MiniGamesEnum.BREAKOUT));
//		gs.addScore("Gwenn3", new Score(333, MiniGamesEnum.GRAVITY));*/
//		System.out.println(gs.toString());
//		//gs.save();
//	}

}
