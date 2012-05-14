package nebula.core.config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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


    public DataContainer ()
    {
        // Default data
        adventureDifficulty = Difficulty.Medium;
        rapidmodeDifficulty = Difficulty.Medium;
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


    /**
     * Serialization
     */
    private void writeObject (ObjectOutputStream out) throws IOException
    {
        // Write difficulties
        out.write(adventureDifficulty.ordinal());
        out.write(rapidmodeDifficulty.ordinal());
    }

    private void readObject (ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        // Read difficulties
        adventureDifficulty = Difficulty.values()[in.read()];
        rapidmodeDifficulty = Difficulty.values()[in.read()];
    }
}
