package nebula.core;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import nebula.core.helper.Console;


/**
 * Application controller: main entrance point of the application
 */
public class ApplicationController
{
    /**
     * Construtor with program arguments
     * @param args Program arguments
     */
    public ApplicationController (String[] args)
    {
        // Some application initialization before UI
    }
    
    /**
     * Run the application
     */
    public void run ()
    {
        Console.log("Starting application");
        
        // Run the application view
        EventQueue.invokeLater(new Runnable()
        {
            public void run ()
            {
                try
                {
                    ApplicationView appView = new ApplicationView();
                    appView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                    appView.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing (WindowEvent e) {
                            beforeClosing();
                        }
                    });
                    
                    appView.setVisible(true);
                }
                catch (Exception e) { e.printStackTrace();}
            }
        });
    }
    
    /**
     * Execute this method before closing the application
     */
    private void beforeClosing ()
    {
        Console.log("Closing application");
    }

    /**
     * Main function
     * @param args Program arguments
     */
    public static void main (String[] args)
    {
        ApplicationController app = new ApplicationController(args);
        app.run();
    }
}
