/**
 * Nebula - Copyright (C) 2012
 * Gwenn Aubert, Thomas Di'Meco, Matthieu Maugard, Gaspard Perrot
 *
 * This file is part of project 'Nebula'
 *
 * 'Nebula' is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * 'Nebula' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Nebula'. If not, see <http://www.gnu.org/licenses/>.
 */
package nebula.core.state;

import java.util.HashMap;
import java.util.Map;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
    private static final String ERROR1_TEXT = "Entre ton nom correctement";
    private static final String ERROR2_TEXT = "Ce joueur existe déjà";

    private int errorOccured, prevTextSize;
    private TextField textField;
    private Font font, fontTitle;
    private static Sound sndError1, sndError2;
    private static Map<Character, Sound> sndCharacters;


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        errorOccured = 0;
        prevTextSize = 0;

        // Fonts
        font  = NebulaFont.getFont(FontName.Batmfa, FontSize.Medium);
        fontTitle  = NebulaFont.getFont(FontName.Batmfa, FontSize.Large);

        // Sounds
        if (sndError1 == null || sndError2 == null)
        {
            sndError1 = new Sound("ressources/sons/menu/typename.ogg");
            sndError2 = new Sound("ressources/sons/menu/playerexists.ogg");
        }

        // Characters
        initCharacters();

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
            {
                errorOccured = 1;

                if (sndError1.playing()) sndError1.stop();
                sndError1.play();
            }
            else if (NebulaConfig.playerExists(playerName))
            {
                errorOccured = 2;

                if (sndError2.playing()) sndError2.stop();
                sndError2.play();
            }
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

        // Character sound
        if (textField.getText().length() > prevTextSize)
        {
            char c = textField.getText().toLowerCase().charAt(textField.getText().length()-1);
            playCharacter(c);
        }

        prevTextSize = textField.getText().length();

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
        if (errorOccured > 0)
        {
            String errorText = (errorOccured == 1 ? ERROR1_TEXT : ERROR2_TEXT);
            int errorWidth = font.getWidth(errorText);

            font.drawString(gc.getWidth()/2 - errorWidth/2, gc.getHeight()/2 + 64.0f , errorText, Color.red);
        }
    }


    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);
        sndError1.play();
    }


    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.leave(gc, game);
        sndError1.stop();
        sndError2.stop();
    }


    private void initCharacters () throws SlickException
    {
        if (sndCharacters != null) return;

        sndCharacters = new HashMap<Character, Sound>();

        for (int d = 48; d <= 122; d++)
        {
            // Jump to letters
            if (d == 58) d = 97;

            char c = (char)d;

            String path = "ressources/sons/menu/characters/" +
                String.valueOf(c) + ".ogg";

            sndCharacters.put(Character.valueOf(c), new Sound(path));
        }
    }


    private void playCharacter (char c)
    {
        // Check character
        if (sndCharacters.containsKey(c))
            sndCharacters.get(c).play();
    }


    @Override
    public int getID() { return NebulaState.PlayerName.id; }
}
