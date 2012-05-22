package nebula.core.config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nebula.core.NebulaGame.Minigame;
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
    private Minigame adventureMinigame;
    private int adventureScore;
    private int adventureBestScore;
    private Map<Integer, Integer> rapidmodeScores;


    public DataContainer ()
    {
        // Default data
        adventureDifficulty = Difficulty.Medium;
        rapidmodeDifficulty = Difficulty.Medium;
        adventureMinigame = null;
        adventureScore = 0;
        adventureBestScore = 0;
        rapidmodeScores = new HashMap<Integer, Integer>();

        // Default scores
        for (int i = 0; i < Minigame.values().length; i++)
            rapidmodeScores.put(Integer.valueOf(i), 0);
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

    public Minigame getAdventureMinigame ()
    {
        return adventureMinigame;
    }

    public void setAdventureMinigame (Minigame minigame)
    {
        this.adventureMinigame = minigame;
    }

    public int getAdventureScore ()
    {
        return adventureScore;
    }

    public void setAdventureScore (int score)
    {
        this.adventureScore = score;
    }

    public int getAdventureBestScore ()
    {
        return adventureBestScore;
    }

    public void setAdventureBestScore (int score)
    {
        this.adventureBestScore = score;
    }

    public int getRapidmodeScore (Minigame minigame)
    {
        Integer score = rapidmodeScores.get(minigame.ordinal());

        if (score == null) return 0;
        return score.intValue();
    }

    public void setRapidmodeScore (Minigame minigame, int score)
    {
        this.rapidmodeScores.put(Integer.valueOf(minigame.ordinal()), score);
    }


    /**
     * Serialization
     */
    private void writeObject (ObjectOutputStream out) throws IOException
    {
        // Write datas
        out.writeInt(adventureDifficulty.ordinal());
        out.writeInt(rapidmodeDifficulty.ordinal());

        if (adventureMinigame != null)
            out.writeInt(adventureMinigame.ordinal());
        else
            out.writeInt(-1);

        out.writeInt(adventureScore);
        out.writeInt(adventureBestScore);
        out.writeObject(rapidmodeScores);
    }

    @SuppressWarnings("unchecked")
    private void readObject (ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        // Read datas
        adventureDifficulty = Difficulty.values()[in.readInt()];
        rapidmodeDifficulty = Difficulty.values()[in.readInt()];

        int adventureMinigameIndex = in.readInt();
        if (adventureMinigameIndex != -1)
            adventureMinigame = Minigame.values()[adventureMinigameIndex];
        else
            adventureMinigame = null;

        adventureScore = in.readInt();
        adventureBestScore = in.readInt();
        rapidmodeScores = (HashMap<Integer, Integer>) in.readObject();
    }
}
