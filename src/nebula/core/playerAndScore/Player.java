package nebula.core.playerAndScore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;


public class Player implements Serializable {

	private static final long serialVersionUID = -5096287962127194107L;
	
	private HashMap<MiniGamesEnum, Score> scores;
	private String name;
	private ScenarioProgression progression;

	/**
	 * Create a new default player with no scores and the history progression at
	 * the star
	 * 
	 * @param playerName
	 */
	private Player(String playerName) {
		name = playerName;
		progression = ScenarioProgression.START;
		scores = new HashMap<MiniGamesEnum, Score>();
	}

	/**
	 * Return a loaded player (loaded from the player name)<BR>
	 * return a default player if the player can't be loaded
	 * 
	 * @param player
	 * @return
	 */
	public static Player load(String playerName) {
		String filename = playerName + ".data";
		Player p;
		try {
			FileInputStream fos = new FileInputStream(filename);
			ObjectInputStream out = new ObjectInputStream(fos);
			p = (Player) out.readObject();
			out.close();
		} catch (Exception e) {
			System.out.println("unable to load player :" + playerName);
			p = new Player(playerName);
		}
		return p;
	}

	/**
	 * save the player's data into a file named "playerName.data"
	 */
	public void save() {
		String filename = name + ".data";
		FileOutputStream fis = null;
		ObjectOutputStream oit = null;
		try {
			fis = new FileOutputStream(filename);
			oit = new ObjectOutputStream(fis);
			oit.writeObject(this);
			oit.flush();
			oit.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * add the new score if there is no score yet for that game of if the existing score is lower
	 * @param sco
	 */
	public void addScore(Score sco) {

		if (scores.containsKey(sco.GameEnum)) {
			if (sco.getScore() > scores.get(sco.GameEnum).getScore()) {
				// Replace the score if the new one is higher
				scores.put(sco.GameEnum, sco);
			}
		} else {
			// If there is no score for that game, we add this one
			scores.put(sco.GameEnum, sco);
		}

	}

	public String getName() {
		return name;
	}
	
	public ScenarioProgression getProgression(){
		return progression;
	}
}
