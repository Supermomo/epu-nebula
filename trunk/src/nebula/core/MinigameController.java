package nebula.core;


/**
 * Base type for a minigame controller. Each minigame controller must subclass
 * this class
 */
public class MinigameController
{
	int jeuCourant = 3;
	private int[] Scores;
	
	public int getJeuCourant()
	{
		return jeuCourant;
	}
	
	public void setJeuCourant(int jeuCourant)
	{
		this.jeuCourant = jeuCourant;
	}
	
	public int jeuSuivant()
	{
		return jeuCourant++;
	}
	
	public int getScores(int rang)
	{
		return Scores[rang];
	}
	
	public void setScores(int score, int rang)
	{
		Scores[rang] = score;
	}
}
