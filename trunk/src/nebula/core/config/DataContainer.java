package nebula.core.config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nebula.core.state.AbstractMinigameState.Difficulty;


/**
 * Contains all config data
 */
class DataContainer implements Serializable
{
    // Serial version for the data container
    private static final long serialVersionUID = 1L;

    // Datas
    private Difficulty adventureDifficulty;
    private Difficulty rapidmodeDifficulty;
    private int adventureScore;
    private Map<Integer, Integer> scores;


    public DataContainer ()
    {
        // Default data
        adventureDifficulty = Difficulty.Medium;
        rapidmodeDifficulty = Difficulty.Medium;
        adventureScore = 0;
        scores = new HashMap<Integer, Integer>();
    }


    public Difficulty getAdventureDifficulty ()
    {
        return adventureDifficulty;
    }

    public void setAdventureDifficulty (Difficulty difficulty)
    {
        this.adventureDifficulty = difficulty;
    }

    public Difficulty getRapidmodeDifficulty ()
    {
        return rapidmodeDifficulty;
    }

    public void setRapidmodeDifficulty (Difficulty difficulty)
    {
        this.rapidmodeDifficulty = difficulty;
    }

    public int getAdventureScore ()
    {
        return adventureScore;
    }

    public void setAdventureScore (int score)
    {
        this.adventureScore = score;
    }


    /**
     * Serialization
     */
    private void writeObject (ObjectOutputStream out) throws IOException
    {
        // Write datas
        out.write(adventureDifficulty.ordinal());
        out.write(rapidmodeDifficulty.ordinal());
        out.write(adventureScore);
        out.writeObject(scores);
    }

    @SuppressWarnings("unchecked")
    private void readObject (ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        // Read datas
        adventureDifficulty = Difficulty.values()[in.readInt()];
        rapidmodeDifficulty = Difficulty.values()[in.readInt()];
        adventureScore = in.readInt();
        scores = (HashMap<Integer, Integer>) in.readObject();
    }
}
