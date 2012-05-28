package nebula.core.helper;

import nebula.core.state.AbstractMinigameState.Difficulty;


/**
 * Util helper
 */
public class Utils
{
    /**
     * Convert time in seconds to string
     */
    public static String secondsToString (int sec)
    {
        return Integer.toString(sec/60) +
               " : " +
               (sec % 60 < 10 ? "0" : "") +
               Integer.toString(sec % 60);
    }

    /**
     * Returns the score in the nebula default ranges
     */
    public static int checkScoreRange (int score, Difficulty difficulty)
    {
        if (Difficulty.Easy.equals(difficulty))
        {
            score = Math.max(1000, score);
            score = Math.min(score, 2000);
        }
        else if (Difficulty.Medium.equals(difficulty))
        {
            score = Math.max(2000, score);
            score = Math.min(score, 3000);
        }
        else if (Difficulty.Hard.equals(difficulty))
        {
            score = Math.max(3000, score);
            score = Math.min(score, 4000);
        }
        else if (Difficulty.Insane.equals(difficulty))
        {
            score = Math.max(4000, score);
            score = Math.min(score, 5000);
        }

        return score;
    }
}
