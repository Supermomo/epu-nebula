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

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Credits state
 */
public class CreditsState extends AbstractState
{
    // Credits string
    private static final String credits =
        "$==== Crédits ====\n" +
        " \n" +
        " \n" +
        "Nebula - Projet DeViNT 2012\n" +
        "Polytech'Nice-Sophia\n" +
        " \n" +
        " \n" +
        "$==== Créateurs / Développeurs ====\n" +
        " \n" +
        "Gwenn Aubert\n" +
        "Thomas Di'Meco\n" +
        "Matthieu Maugard\n" +
        "Gaspard Perrot\n" +
        " \n" +
        " \n" +
        "$==== Casting ====\n" +
        " \n" +
        "Bidibop : Matthieu Maugard\n" +
        "Père de Bidibop : Matthieu Maugard\n" +
        "Sage : Matthieu Maugard\n" +
        "Farfafée : Matthieu Maugard\n" +
        " \n" +
        " \n" +
        "$==== Testeurs ====\n" +
        " \n" +
        "Les élèves de Clément Ader\n" +
        "Laura Martellotto la Marmotte\n" +
        "Jessica Lionne la... Lionne\n" +
        "Brice Martini le Soumis\n" +
        "Bruce Springsteen the Boss\n" +
        " \n" +
        " \n" +
        "$==== Bibliothèques ====\n" +
        " \n" +
        "Slick (bibliothèque graphique)\n" +
        " \n" +
        " \n" +
        "$==== Audio ====\n" +
        " \n" +
        "Sons : www.universal-soundbank.com\n" +
        "Musiques : www.audionautix.com\n" +
        " \n" +
        " \n" +
        "$==== Remerciements ====\n" +
        " \n" +
        "Les élèves de Clément Ader\n" +
        "Les enseignants de Polytech\n" +
        "Momo & Gwenn les gitans\n" +
        "La fonction intersect() de Slick\n" +
        "Les moustiques\n" +
        "Toi\n" +
        "Moi\n" +
        "Et tout ceux qui le veulent...\n" +
        " \n" +
        " \n" +
        " \n" +
        "$- Merci -";


    // Credits properties
    private static final float CREDITS_SPEED = 0.04f;
    private static final float CREDITS_SPACE = 4.0f;

    private float y;
    private float strHeight;
    private String[] strings;
    private Font font;
    private Image imgTopFade, imgBottomFade;


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // No background
        setUseDefaultBackground(false);

        // Images
        imgTopFade = new Image("ressources/images/common/fadeTop.png");
        imgBottomFade = new Image("ressources/images/common/fadeBottom.png");

        // Font
        font  = NebulaFont.getFont(FontName.Batmfa, FontSize.Medium);

        // Music
        initMusic("ressources/sons/common/credits.ogg", 0.5f, true);

        // Split credits string
        strings = credits.split("\n");

        y = gc.getHeight();
        strHeight = font.getHeight("Azerty0123");
    }


    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);

        Input input = gc.getInput();

        // Credits speed
        if (input.isKeyDown(Input.KEY_UP) ||
            input.isKeyDown(Input.KEY_DOWN) ||
            input.isKeyDown(Input.KEY_SPACE))
            y -= CREDITS_SPEED * 5 * delta;
        else
            y -= CREDITS_SPEED * delta;

        // Escape
        if (input.isKeyPressed(Input.KEY_ENTER) ||
            input.isKeyPressed(Input.KEY_ESCAPE) ||
            y + strings.length * (CREDITS_SPACE + strHeight) < 0.0f)
            nebulaGame.enterState(NebulaState.MainMenu.id, TransitionType.Fade);
    }


    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);

        // Render credits
        for (int i = 0; i < strings.length; i++)
        {
            String str = strings[i];

            // Colors
            Color color = Color.white;

            if (!str.isEmpty() && str.charAt(0) == '$')
            {
                str = str.substring(1, str.length());
                color = Color.yellow;
            }

            // Draw
            font.drawString(
                gc.getWidth()/2 - font.getWidth(str)/2,
                y + i*(strHeight + CREDITS_SPACE),
                str,
                color);
        }

        // Render fades
        imgTopFade.draw(0, 0, gc.getWidth(), imgTopFade.getHeight());
        imgBottomFade.draw(0, gc.getHeight()-imgBottomFade.getHeight(), gc.getWidth(), imgBottomFade.getHeight());
    }


    @Override
    public int getID() { return NebulaState.Credits.id; }
}
