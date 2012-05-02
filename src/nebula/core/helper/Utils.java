package nebula.core.helper;


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
}
