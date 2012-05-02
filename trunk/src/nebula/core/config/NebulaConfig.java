package nebula.core.config;

import nebula.core.state.AbstractMinigameState.Difficulty;


/**
 * Nebula config class
 * Contains all the nebula config/parameters
 */
public class NebulaConfig
{
    private static String playerName;
    private static Difficulty difficulty;


    /**
     * Load the player data from the database
     * @param playerName The player name
     */
    public static void loadData (String playerName)
    {
        NebulaConfig.playerName = playerName;
        NebulaConfig.loadDefaultConfig();
    }
    
    /**
     * Save the player data into the database
     */
    public static void saveData ()
    {
        
    }
    
    /**
     * Load the default config for new players
     */
    private static void loadDefaultConfig ()
    {
        difficulty = Difficulty.Medium;
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
     * Get difficulty
     * @return The difficulty
     */
    public static Difficulty getDifficulty ()
    {
        return difficulty;
    }
    
    /**
     * Set difficulty
     * @param difficulty The difficulty
     */
    public static void setDifficulty (Difficulty difficulty)
    {
        NebulaConfig.difficulty = difficulty;
    }
}
