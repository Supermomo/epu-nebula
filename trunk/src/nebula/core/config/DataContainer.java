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
    
    private Difficulty difficulty;
    
    
    public DataContainer ()
    {
        // Default data
        difficulty = Difficulty.Medium;
    }
    
    public void setDifficulty (Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }
    
    public Difficulty getDifficulty ()
    {
        return difficulty;
    }
    
    private void writeObject (ObjectOutputStream out) throws IOException
    {
        // Write difficulty
        out.write(difficulty.ordinal());
    }
    
    private void readObject (ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        // Read difficulty
        difficulty = Difficulty.values()[in.read()];
    }
}
