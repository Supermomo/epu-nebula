package nebula.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import nebula.core.state.AbstractMinigameState.Difficulty;


/**
 * Nebula config class
 * Contains all the nebula config/parameters
 */
public class NebulaConfig
{
    private static String playerName;
    private static DataContainer dataContainer;


    /**
     * Load the player data from the database
     * @param playerName The player name
     */
    public static void loadData (String playerName)
    {
        NebulaConfig.playerName = playerName;
        
        try
        {
            FileInputStream fis =
                new FileInputStream(getFileName());
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            try
            {
                // Read data container
                dataContainer = (DataContainer) ois.readObject();
            }
            finally
            {
                // Close streams
                try { ois.close(); } finally { fis.close(); }
            }
        }
        catch (Exception exc)
        {
            // Load default config
            NebulaConfig.loadDefaultConfig();
        }
    }
    
    /**
     * Save the player data into the database
     */
    public static void saveData ()
    {
        // Make dirs
        new File(getFileFolder()).mkdirs();
        
        try
        {
            FileOutputStream fos =
                new FileOutputStream(getFileName(), false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            try
            {
                // Write data
                oos.writeObject(dataContainer);
            }
            finally
            {
                // Close streams
                try { oos.close(); } finally { fos.close(); }
            }
        }
        catch (Exception exc) {}
    }
    
    /**
     * Load the default config for new players
     */
    private static void loadDefaultConfig ()
    {
        dataContainer = new DataContainer();
    }
    
    /**
     * Get the player name
     * @return The player name
     */
    public static String getPlayerName ()
    {
        return playerName;
    }
    
    /**
     * Get the file folder
     * @return The file folder
     */
    private static String getFileFolder ()
    {
        return
            System.getProperty("user.home") +
            System.getProperty("file.separator") +
            ".nebula" +
            System.getProperty("file.separator");
    }
    
    /**
     * Get the file name
     * @return The file name
     */
    private static String getFileName ()
    {
        return
            getFileFolder() +
            playerName +
            ".save";
    }
    
    /**
     * Get difficulty
     * @return The difficulty
     */
    public static Difficulty getDifficulty ()
    {
        return dataContainer.getDifficulty();
    }
    
    /**
     * Set difficulty
     * @param difficulty The difficulty
     */
    public static void setDifficulty (Difficulty difficulty)
    {
        dataContainer.setDifficulty(difficulty);
    }
}
