package nebula.core.helper;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Console helpers
 */
public class Console
{
    /**
     * Log helper: log the given string in the console
     * @param log Log string
     */
    public static void log (String log)
    {
        logString(log, false);
    }
    
    /**
     * Log error helper: log the given string in the console
     * @param log Log string
     */
    public static void logError (String log)
    {
        logString(log, true);
    }
    
    /**
     * Private method to write the string into the corresponding stream
     * @param log   Log string
     * @param error true to write the string into the error stream
     */
    private static void logString (String log, boolean error)
    {
        // Log string with current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String str = "[" + sdf.format(new Date()) + "] " + log;
        
        if (error)
            System.err.println(str);
        else
            System.out.println(str);
    }
}
