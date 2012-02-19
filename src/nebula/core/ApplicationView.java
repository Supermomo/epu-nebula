package nebula.core;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;


/**
 * Application view: main window of the application
 */
public class ApplicationView extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Create the main application window
     */
    public ApplicationView ()
    {
        super("Nebula");
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        JLabel lblFentrePrincipale = new JLabel("Fenêtre principale");
        lblFentrePrincipale.setFont(new Font("Lucida Grande", Font.BOLD, 13));
        lblFentrePrincipale.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblFentrePrincipale, BorderLayout.CENTER);
        
        // Pack, lock minimum size and center the window
        setMinimumSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(null);
    }
}
