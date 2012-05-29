package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Player name state
 */
public class PlayerNameState extends AbstractState
{
    public static final int PLAYERNAME_MAXSIZE = 12;
    private static final int TEXTFIELD_MARGIN = 4;

    private static final String TITLE_TEXT = "Tape ton nom";
    private static final String ERROR_TEXT = "Entre ton nom correctement";

    private TextField textField;
    private Font font, fontTitle;
    private boolean errorOccured;


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        errorOccured = false;

        // Fonts
        font  = NebulaFont.getFont(FontName.Batmfa, FontSize.Medium);
        fontTitle  = NebulaFont.getFont(FontName.Batmfa, FontSize.Large);

        // Text field
        int tfWidth = font.getWidth("W") * (PLAYERNAME_MAXSIZE+2) + 2 * TEXTFIELD_MARGIN;
        int tfHeight = font.getHeight("Azerty001") + 2 * TEXTFIELD_MARGIN;

        textField = new TextField(gc, font, gc.getWidth()/2 - tfWidth/2, gc.getHeight()/2 - tfHeight/2, tfWidth, tfHeight);
        textField.setBackgroundColor(new Color(1.0f, 1.0f, 0.0f, 0.1f));
        textField.setBorderColor(Color.yellow);
        textField.setCursorVisible(true);
        textField.setMaxLength(PLAYERNAME_MAXSIZE);
        textField.setTextColor(Color.white);
    }


    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);

        Input input = gc.getInput();
        String playerName = textField.getText().toLowerCase();

        // Validation
        if (input.isKeyPressed(Input.KEY_ENTER))
        {
            if (playerName == null || !playerName.matches("[a-z0-9]+"))
                errorOccured = true;
            else
            {
                // Load game
                nebulaGame.loadPlayer(playerName);
                nebulaGame.initAndEnterState(NebulaState.MainMenu.id, TransitionType.ShortFade);
            }
        }

        // Format text field
        if (playerName != null && !playerName.matches("[a-z0-9]*"))
            textField.setText(playerName.substring(0, playerName.length()-1));

        // Set text field focus
        if (!textField.hasFocus())
            textField.setFocus(true);

        // Escape key
        if (input.isKeyPressed(Input.KEY_ESCAPE))
            nebulaGame.enterState(NebulaState.PlayerMenu.id);
    }


    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);

        // Text field
        textField.render(gc, g);

        // Title
        int titleWidth = fontTitle.getWidth(TITLE_TEXT);
        int titleHeight = fontTitle.getHeight(TITLE_TEXT);

        fontTitle.drawString(gc.getWidth()/2 - titleWidth/2, gc.getHeight()/2 - titleHeight - 64.0f , TITLE_TEXT, Color.yellow);

        // Error message
        if (errorOccured)
        {
            int errorWidth = font.getWidth(ERROR_TEXT);
            font.drawString(gc.getWidth()/2 - errorWidth/2, gc.getHeight()/2 + 64.0f , ERROR_TEXT, Color.red);
        }
    }


    @Override
    public int getID() { return NebulaState.PlayerName.id; }
}
